package com.wemeal.presentation.helper

import android.app.Activity
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onPreShow
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.wemeal.R
import com.wemeal.data.model.MaterialDialogContent
import com.wemeal.presentation.extensions.addAllViews
import com.wemeal.presentation.extensions.inflateView
import com.wemeal.presentation.extensions.visible

object CustomMaterialDialog {

    fun showSimpleDialog(
        callingClassActivity: Activity, dialogContent: MaterialDialogContent,
        positiveButtonClickClosure: () -> Unit
    ) {
        MaterialDialog(callingClassActivity).show {
            customView(viewRes = R.layout.custom_simple_dialog_layout, noVerticalPadding = true)
            if (dialogContent.negativeText != null)
                view.apply {
                    findViewById<TextView>(R.id.centeredSimpleDialogNegativeTextView).apply {
                        setText(dialogContent.negativeText)
                        setOnClickListener { dismiss() }
                        visible()
                    }
                    findViewById<ImageView>(R.id.centeredSimpleDialogContentActionButtonDivider)
                        .visible()
                }

            view.apply {
                findViewById<TextView>(R.id.centeredSimpleDialogPositiveTextView).apply {
                    setText(dialogContent.positiveText)
                    setOnClickListener {
                        dismiss()
                        positiveButtonClickClosure.invoke()
                    }
                }
                findViewById<TextView>(R.id.customSimpleDialogTitleTextView).setText(dialogContent.title)
                val contentTextResource = dialogContent.content
                val content = dialogContent.contentText
                if (contentTextResource != null)
                    findViewById<TextView>(R.id.customSimpleDialogContentTextView).setText(
                        contentTextResource
                    )
                else if (content != null)
                    findViewById<TextView>(R.id.customSimpleDialogContentTextView).text = content
                cancelOnTouchOutside(false)
            }
        }
    }

    fun showSingleChoiceDialog(
        callingClassActivity: Activity,
        dialogContent: MaterialDialogContent, items: List<String>,
        negativeButtonClickListener: (() -> Unit)? = null, choiceSelection: (String) -> Unit
    ) {

        require(items.isNotEmpty()) {
            "The items list for single choice must not be empty"
        }

        MaterialDialog(callingClassActivity).show {

            customView(viewRes = R.layout.custom_single_choice_dialog, noVerticalPadding = true)

            val radioButtons = items.map { singleContent ->
                (view.inflateView(R.layout.single_radio_button_layout) as MaterialRadioButton).apply {
                    text = singleContent
                }
            }

            if (dialogContent.negativeText != null)
                view.apply {
                    findViewById<TextView>(R.id.customSingleChoiceDialogNegativeTextView).apply {
                        setText(dialogContent.negativeText)
                        setOnClickListener {
                            dismiss()
                            negativeButtonClickListener?.invoke()
                        }
                        visible()
                    }
                    findViewById<ImageView>(R.id.customSingleChoiceDialogActionButtonDivider)
                        .visible()
                }

            view.apply {
                findViewById<RadioGroup>(R.id.customSingleChoiceDialogRadioGroup).apply {
                    addAllViews(radioButtons)
                }
                findViewById<TextView>(R.id.customSingleChoiceDialogTitleTextView).setText(
                    dialogContent.title
                )
                val contentTextResource = dialogContent.content
                val content = dialogContent.contentText
                if (contentTextResource != null)
                    findViewById<TextView>(R.id.customSingleChoiceDialogContentTextView).setText(
                        contentTextResource
                    )
                else if (content != null)
                    findViewById<TextView>(R.id.customSingleChoiceDialogContentTextView).text =
                        content
                findViewById<TextView>(R.id.customSingleChoiceDialogPositiveTextView).apply {
                    setText(dialogContent.positiveText)
                    setOnClickListener {
                        val selectedRadioButton = radioButtons.first { it.isChecked }
                        choiceSelection.invoke(selectedRadioButton.text.toString())
                        dismiss()
                    }
                }
            }

            onPreShow {
                radioButtons[0].isChecked = true
            }

            cancelOnTouchOutside(false)
        }
    }
}