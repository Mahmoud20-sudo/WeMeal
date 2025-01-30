package com.wemeal.presentation.util

import android.R.attr
import android.content.Context
import android.text.Editable
import android.text.Layout
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.wemeal.presentation.util.MentionEditText.*
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern
import com.wemeal.presentation.extensions.logEvent
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import androidx.compose.runtime.mutableStateMapOf
import com.wemeal.data.model.main.Result
import com.wemeal.presentation.extensions.activity
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import android.text.Spannable

import android.R.attr.font
import android.graphics.Typeface
import android.text.style.StyleSpan

import androidx.core.content.res.ResourcesCompat
import com.wemeal.R


class MentionEditText : AppCompatEditText {
    private var mPattern: Pattern? = null
    private var mAction: Runnable? = null
    private var mMentionTextColor = 0
    private var mIsSelected = false
    private var mLastSelectedRange: Range? = null
    private var mRangeArrayList: MutableList<Range?>? = null
    private var mOnMentionInputListener: OnMentionInputListener? = null
    private var isMentioned = false
    private var position = 0
    private val mentionList: ArrayList<Result> = ArrayList<Result>()

    //        private ArrayList<String> mentionName = new ArrayList<>();
//    private val patterns = ArrayList<Pattern>()

    private var usersMap: MutableMap<Pattern, Result>? = mutableStateMapOf()

    private lateinit var mediumTypeFace: Typeface
    private var from = 0
    private var to = 0
    private var mLayout: Layout? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection? {
        return HackInputConnection(super.onCreateInputConnection(outAttrs), true, this)
    }

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(text, type)
        if (mAction == null) {
            mAction = Runnable {
                Log.e("%%%%", position.toString())
                this@MentionEditText.setSelection(
                    if (position > 0) position else Objects.requireNonNull(getText())?.length ?: 0
                )
                position = 0
            }
        }
        postDelayed(mAction, 100)
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        colorMentionString()
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        try {
            if (mLastSelectedRange == null || !mLastSelectedRange!!.isEqual(selStart, selEnd)) {
                val closestRange = getRangeOfClosestMentionString(selStart, selEnd)
                if (closestRange != null && closestRange.to == selEnd) {
                    mIsSelected = false
                }
                val nearbyRange = getRangeOfNearbyMentionString(selStart, selEnd)
                if (nearbyRange != null) {
                    if (selStart == selEnd) {
                        this.setSelection(nearbyRange.getAnchorPosition(selStart))
                    } else {
                        if (selEnd < nearbyRange.to) {
                            this.setSelection(selStart, nearbyRange.to)
                        }
                        if (selStart > nearbyRange.from) {
                            this.setSelection(nearbyRange.from, selEnd)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("IndexOutOfBoundsException", e.localizedMessage)
        }
    }


    fun setIsMentioned(isMentioned: Boolean) {
        this.isMentioned = isMentioned
    }

    fun setMentionTextColor(color: Int) {
        mMentionTextColor = color
    }

    fun getMentionList(excludeMentionCharacter: Boolean): ArrayList<Result> {
//        if (TextUtils.isEmpty(this.getText().toString())) {
//            return mentionList;
//        } else {
//
////            Matcher matcher = this.mPattern.matcher(this.getText().toString());
////
////            while (matcher.find()) {
////                String mentionText = matcher.group();
////                if (excludeMentionCharacter) {
////                    mentionText = mentionText.substring(1);
////                }
////
////                if (!mentionList.contains(mentionText)) {
////                    mentionList.add(mentionText);
////                }
////            }
////
////            return mentionList;
//        }
        return mentionList
    }//            Log.e("AAA", y + "");

    //    private Layout layout = null;
    val currentCursorLine: Float
        get() {
            val pos = selectionStart
            if (layout != null) {
                val line = layout!!.getLineForOffset(pos)
//                val baseline = layout!!.getLineBaseline(line)
//                val ascent = layout!!.getLineAscent(line)
//                val x = layout!!.getPrimaryHorizontal(pos)
//                return when {
//                    line <= 5 -> (line * 19f)
//                    line in 6..10 -> (line * 17f)
//                    else -> (line * 18f)
//                } //(baseline + ascent) / 3f
                //            Log.e("AAA", y + "");
                return line * 19f
            }
            return lineCount.toFloat()
        }

    override fun onPreDraw(): Boolean {
        mLayout = layout
        if (layout != null) {
            // use layout here...
            val n = layout!!.getEllipsisCount(0)
            // work requiring a layout is finished; remove listener from view
            viewTreeObserver.removeOnPreDrawListener(this)
        }
        return true
    }

    fun setOnMentionInputListener(onMentionInputListener: OnMentionInputListener) {
        this.mOnMentionInputListener = onMentionInputListener
    }

    fun setCursorPosition(position: Int) {
        this.position = position
    }

    private fun init() {
        typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
        mediumTypeFace = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        viewTreeObserver.addOnPreDrawListener(this)
        mRangeArrayList = ArrayList<Range?>(5)
        mPattern = Pattern.compile("@[\\u4e00-\\u9fa5\\w\\-]+")
        mMentionTextColor = -65536
        addTextChangedListener(MentionTextWatcher())
        filters = arrayOf(filter, LengthFilter(POST_TEXT_EMS))
    }

    private fun colorMentionString() {
        mIsSelected = false
        if (mRangeArrayList != null) {
            mRangeArrayList!!.clear()
        }
        val spannableText = this.text
        if (spannableText != null && !TextUtils.isEmpty(spannableText.toString())) {
            val oldSpans = spannableText.getSpans(
                0, spannableText.length,
                ForegroundColorSpan::class.java
            ) as Array<ForegroundColorSpan?>
            val var4 = oldSpans.size
            for (var5 in 0 until var4) {
                val oldSpan = oldSpans[var5]
                spannableText.removeSpan(oldSpan)
            }
            //
            var lastMentionIndex = -1
            val text = spannableText.toString()
            //            mPattern = Pattern.compile("Eman [\\u4e00-\\u9fa5\\w\\-\\s]");
            for (pattern in usersMap?.keys!!) {
                for (user in getMentionList(false)) {
//                    val user = usersMap?.get(pattern)
                    val name = "${user?.firstName} ${user?.lastName} "
                    val matcher = pattern.matcher(name)
                    if (matcher.find()) {
                        val start = text.indexOf(name)
                        val end = start + name.length
                        if (start == -1 || end == -1) continue

                        //change color for mentioned span
                        spannableText.setSpan(
                            ForegroundColorSpan(mMentionTextColor),
                            start,
                            end,
                            33
                        )

                        //change font for mentioned span
                        spannableText.setSpan(
                            StyleSpan(mediumTypeFace.style),
                            start,
                            end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        lastMentionIndex = end
                        mRangeArrayList!!.add(Range(start, end))
                    }
                }
            }
        }
    }

    private fun getRangeOfClosestMentionString(selStart: Int, selEnd: Int): Range? {
        return if (mRangeArrayList == null) {
            null
        } else {
            val var3: Iterator<*> = mRangeArrayList!!.iterator()
            var range: Range
            do {
                if (!var3.hasNext()) {
                    return null
                }
                range = var3.next() as Range
            } while (!range.contains(selStart, selEnd))
            range
        }
    }

    private fun getRangeOfNearbyMentionString(selStart: Int, selEnd: Int): Range? {
        return if (mRangeArrayList == null) {
            null
        } else {
            val var3: Iterator<*> = mRangeArrayList!!.iterator()
            var range: Range
            do {
                if (!var3.hasNext()) {
                    return null
                }
                range = var3.next() as Range
            } while (!range.isWrappedBy(selStart, selEnd))
            range
        }
    }

    //    public void setMentionName(ArrayList<String> mentionName) {
    //        this.mentionName = mentionName;
    //    }
    interface OnMentionInputListener {
        fun onMentionCharacterInput(isMention: Boolean)
    }

    private inner class Range(var from: Int, var to: Int) {
        fun isWrappedBy(start: Int, end: Int): Boolean {
            return start > this.from && start < this.to || end > this.from && end < this.to
        }

        fun contains(start: Int, end: Int): Boolean {
            return this.from <= start && this.to >= end
        }

        fun isEqual(start: Int, end: Int): Boolean {
            return this.from == start && this.to == end || this.from == end && this.to == start
        }

        fun getAnchorPosition(value: Int): Int {
            return if (value - this.from - (this.to - value) >= 0) this.to else this.from
        }
    }

    private inner class HackInputConnection(
        target: InputConnection?,
        mutable: Boolean,
        editText: MentionEditText
    ) :
        InputConnectionWrapper(target, mutable) {
        private val editText: EditText
        override fun sendKeyEvent(event: KeyEvent): Boolean {
            return if (event.action == 0 && event.keyCode == 67) {
                val selectionStart = editText.selectionStart
                val selectionEnd = editText.selectionEnd
                val closestRange = getRangeOfClosestMentionString(selectionStart, selectionEnd)
                if (closestRange == null) {
                    mIsSelected = false
                    super.sendKeyEvent(event)
                } else if (!mIsSelected && selectionStart != closestRange.from) {
                    mIsSelected = true
                    mLastSelectedRange = closestRange
                    from = closestRange.from
                    to = closestRange.to
                    this.setSelection(closestRange.to, closestRange.from)
                    true
                } else {
                    mIsSelected = false
                    super.sendKeyEvent(event)
                }
            } else {
                super.sendKeyEvent(event)
            }
        }

        override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
            return if (beforeLength == 1 && afterLength == 0) {
                sendKeyEvent(KeyEvent(0, 67)) && sendKeyEvent(KeyEvent(1, 67))
            } else {
                deleteFromPatter()
                super.deleteSurroundingText(beforeLength, afterLength)
            }
        }

        init {
            this.editText = editText
        }
    }

    private fun deleteFromPatter() {
        val test = Objects.requireNonNull(text).toString().substring(from, to)
        for (userMp in usersMap?.keys!!) {
            if (userMp.matcher(test).find())
                usersMap?.remove(userMp)
        }

        from = 0
        to = 0
    }

    private inner class MentionTextWatcher : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, index: Int, i1: Int, count: Int) {

            var isAfterMention: CharSequence = ""
            try {
                isAfterMention = Objects.requireNonNull(text).toString().subSequence(
                    text.toString().indexOf("@"),
                    text.toString().indexOf("@") + 1
                )
            } catch (e: Exception) {
                Log.e("EXCEPTION", e.message ?: "")
            }
            if (count == 1 && !TextUtils.isEmpty(charSequence)) {
                val mentionChar = charSequence.toString()[index]
                if (mOnMentionInputListener != null) {
                    mOnMentionInputListener!!.onMentionCharacterInput(
                        '@' == mentionChar || (isAfterMention.isNotEmpty() && charSequence.indexOf("@ ") == -1)
                    )
                }
            } else {
                mOnMentionInputListener?.onMentionCharacterInput(
                    charSequence.isNotEmpty() &&
                            (charSequence[charSequence.length - 1] == '@' ||
                                    (isAfterMention.isNotEmpty() && charSequence.indexOf("@ ") == -1))
                )
            }
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }

    fun setPatterns(usersMap: MutableMap<Pattern, Result>?) {
        this.usersMap = usersMap
    }

    private var filter =
        InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                if (end == POST_TEXT_EMS) {
                    context.activity()?.logEvent(
                        event = CustomEvent.USER_EXCEED_CHARACTER_LIMIT_CREATE_POST,
                        EventCase.SUCCESS
                    )
                    return@InputFilter text;
                }
            }
            null
        }

    companion object {
        const val DEFAULT_MENTION_PATTERN = "@[\\u4e00-\\u9fa5\\w\\-]+"
    }
}