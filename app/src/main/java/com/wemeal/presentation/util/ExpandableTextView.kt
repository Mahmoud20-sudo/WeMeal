package com.wemeal.presentation.util

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.*
import android.graphics.drawable.GradientDrawable.Orientation.*
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.wemeal.R
import com.wemeal.data.model.user.UserModel

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = R.attr.expandableTextView
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var mediumTypeFace: Typeface =
        ResourcesCompat.getFont(context, R.font.montserrat_semibold)!!
    private lateinit var onShowClickListener: (isExpanded: Boolean) -> Unit
    private lateinit var onCaptionClickListener: () -> Unit
    private var fcs: ForegroundColorSpan? = null
    private var mOriginalText: CharSequence? = ""
    private var mCollapsedLines: Int? = 0
    private var mReadMoreText: CharSequence = ""
    private var mReadLessText: CharSequence = ""
    private var isExpanded: Boolean = false
    private var mAnimationDuration: Int? = 0
    private var foregroundColor: Int? = 0
    private var initialText: String? = ""
    private var isUnderlined: Boolean? = false
    private var mEllipsizeTextColor: Int? = 0

    private lateinit var visibleText: String
    private var mentionsList: List<UserModel>? = null

    fun setMentionList(mentionsList: List<UserModel>?) {
        this.mentionsList = mentionsList
    }

    fun setOnCaptionClickListener(onCaptionClickListener: () -> Unit) {
        this.onCaptionClickListener = onCaptionClickListener
    }

    fun setOnShowClickListener(onShowClickListener: (isExpanded: Boolean) -> Unit) {
        this.onShowClickListener = onShowClickListener
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (initialText.isNullOrBlank()) {
            initialText = text.toString()
            visibleText = visibleText()
            setEllipsizedText(isExpanded)
            setForeground(isExpanded)
        }
    }

    private fun initMentionedText(spanText: SpannableStringBuilder): SpannableStringBuilder {
        mentionsList?.forEach { mentionedUser ->
            val userName = "@${mentionedUser.username}"
            val fullName = "${mentionedUser.firstName} ${mentionedUser.lastName}"

            if (spanText.contains(userName)) {

                spanText.replace(
                    spanText.indexOf(userName),
                    spanText.indexOf(userName) + userName.length,
                    fullName
                )

                spanText.setSpan(
                    ForegroundColorSpan(Color.parseColor("#FF8A50DE")),
                    spanText.indexOf(fullName),
                    spanText.indexOf(fullName) + fullName.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spanText
    }

    private fun toggle() {
        if (visibleText.isAllTextVisible()) {
            return
        }

        isExpanded = !isExpanded

        maxLines = if (!isExpanded) {
            mCollapsedLines!!
        } else {
            COLLAPSED_MAX_LINES
        }

        val startHeight = measuredHeight

        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        val endHeight = measuredHeight

        animationSet(startHeight, endHeight).apply {
            duration = mAnimationDuration?.toLong()!!
            start()

            addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    if (!isExpanded)
                        setEllipsizedText(isExpanded)
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
        }

        setEllipsizedText(isExpanded)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        mOriginalText = text
        super.setText(text, type)
    }

    //private functions
    init {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView).apply {
            try {
                mCollapsedLines =
                    getInt(R.styleable.ExpandableTextView_collapsedLines, COLLAPSED_MAX_LINES)
                mAnimationDuration =
                    getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION)
                mReadMoreText = getString(R.styleable.ExpandableTextView_readMoreText) ?: ""
                mReadLessText = getString(R.styleable.ExpandableTextView_readLessText) ?: ""
                foregroundColor =
                    getColor(R.styleable.ExpandableTextView_foregroundColor, Color.TRANSPARENT)
                isUnderlined = getBoolean(R.styleable.ExpandableTextView_isUnderlined, false)
                isExpanded = getBoolean(R.styleable.ExpandableTextView_isExpanded, false)
                mEllipsizeTextColor =
                    getColor(R.styleable.ExpandableTextView_ellipsizeTextColor, Color.BLUE)
            } finally {
                this.recycle()
            }

            fcs = ForegroundColorSpan(Color.rgb(158, 158, 158))

            linksClickable = true
            movementMethod = LinkMovementMethod.getInstance()
        }

        if (!isExpanded)
            maxLines = mCollapsedLines!!
    }

    private fun setInitialClickedSpan(text: String): Spannable {
        val spanText = SpannableStringBuilder()
        val captionClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onCaptionClickListener()
            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.isUnderlineText = false // this remove the underline
            }
        }

        spanText.append(initialText)
        spanText.setSpan(
            captionClickSpan,
            0,
            initialText.toString().length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spanText
    }

    private fun setVisibleClickedSpan(text: String): Spannable {
        var spanText = SpannableStringBuilder()
        val captionClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onCaptionClickListener()
            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.isUnderlineText = false // this remove the underline
            }
        }

        spanText.append(
            visibleText.substring(
                0,
                visibleText.length - mReadMoreText.toString().length
            )
        )
        spanText.setSpan(
            captionClickSpan,
            0,
            visibleText.length - mReadMoreText.toString().length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spanText
    }

    private fun setEllipsizedText(isExpanded: Boolean) {
        if (initialText?.isBlank()!!)
            return

        val spanText =
            if (isExpanded && !visibleText.isAllTextVisible()) {
                val spanText = SpannableStringBuilder()
                spanText.append(setInitialClickedSpan(initialText.toString()))
                    .append(DEFAULT_ELLIPSIZED_TEXT)
                    .append(setLessMoreClickedSpan(mReadLessText.toString(), false))
            } else if (isExpanded || visibleText.isAllTextVisible() || mCollapsedLines!! == COLLAPSED_MAX_LINES) {
                val spanText = SpannableStringBuilder()
                spanText.append(setInitialClickedSpan(initialText.toString()))
            } else {
                val spanText = SpannableStringBuilder()
                spanText.append(setVisibleClickedSpan(visibleText))
                    .append(DEFAULT_ELLIPSIZED_TEXT)
                    .append(setLessMoreClickedSpan(mReadMoreText.toString(), true))
            }
        text = initMentionedText(spanText)
    }

    private fun setLessMoreClickedSpan(text: String, isExpanded: Boolean): Spannable {
        val spanText = SpannableStringBuilder()
        val seeMoreClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                toggle()
                onShowClickListener(isExpanded)
            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.isUnderlineText = false // this remove the underline
            }
        }

        spanText.append(text.span())
        spanText.setSpan(seeMoreClickSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spanText
    }

    private fun visibleText(): String {
        var end = 0

        return if (this.lineCount > 3) {
            for (i in 0 until mCollapsedLines!!) {
                if (layout.getLineEnd(i) == 0)
                    break
                else
                    end = layout.getLineEnd(i)
            }
            initialText?.substring(0, end - mReadMoreText.toString().length)!!
        } else {
            initialText!!
        }
    }

    private fun setForeground(isExpanded: Boolean) {
        foreground = GradientDrawable(BOTTOM_TOP, intArrayOf(foregroundColor!!, Color.TRANSPARENT))
        foreground.alpha = if (isExpanded) {
            MIN_VALUE_ALPHA
        } else {
            MAX_VALUE_ALPHA
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun animationSet(startHeight: Int, endHeight: Int): AnimatorSet {
        return AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofInt(
                    this,
                    ANIMATION_PROPERTY_MAX_HEIGHT,
                    startHeight,
                    endHeight
                ),
                ObjectAnimator.ofInt(
                    this@ExpandableTextView.foreground,
                    ANIMATION_PROPERTY_ALPHA,
                    foreground.alpha,
                    MAX_VALUE_ALPHA - foreground.alpha
                )
            )
        }
    }

    private fun String.isAllTextVisible(): Boolean {
        return this == text.toString()
    }

    private fun String.span(): SpannableString =
        SpannableString(this).apply {
            setSpan(
                ForegroundColorSpan(Color.parseColor("#7630dc")),
                0,
                this.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(ResourcesCompat.getFont(context, R.font.montserrat_semibold)!!.style),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            if (isUnderlined!!)
                setSpan(
                    UnderlineSpan(),
                    0,
                    this.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
        }

    companion object {
        const val TAG = "ExpandableTextView"
        const val MAX_VALUE_ALPHA = 255
        const val MIN_VALUE_ALPHA = 0
        const val ANIMATION_PROPERTY_MAX_HEIGHT = "maxHeight"
        const val ANIMATION_PROPERTY_ALPHA = "alpha"
    }
}
