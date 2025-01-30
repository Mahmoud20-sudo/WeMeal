package com.wemeal.presentation.util;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.core.content.res.ResourcesCompat;

import com.wemeal.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialMentionAutoComplete extends AppCompatMultiAutoCompleteTextView {

//    MentionAutoCompleteAdapter mentionAutoCompleteAdapter;
    ArrayMap<String, String> map = new ArrayMap<>();

    String formattedOfString = "@%s ";

    public SocialMentionAutoComplete(Context context) {
        super(context);
        initializeComponents();
    }

    public SocialMentionAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeComponents();
    }

    public SocialMentionAutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeComponents();
    }

    private void initializeComponents() {
        addTextChangedListener(textWatcher);
        setTokenizer(new SpaceTokenizer());
    }


    /***
     *This function returns the contents of the AppCompatMultiAutoCompleteTextView into my desired Format
     *You can write your own function according to your needs
     **/

    public String getProcessedString() {

        String s = getText().toString();

        for (Map.Entry<String, String> stringMentionPersonEntry : map.entrySet()) {
            s = s.replace(stringMentionPersonEntry.getKey(), stringMentionPersonEntry.getValue());
        }
        return s;
    }

    /**
     *This function will process the incoming text into mention format
     * You have to implement the processing logic
     * */
    public void setMentioningText(String text) {

        map.clear();

        Pattern p = Pattern.compile("\\[([^]]+)]\\(([^ )]+)\\)");
        Matcher m = p.matcher(text);

        String finalDesc = text;

        while (m.find()) {
            String  mentionPerson = "";
            String name = m.group(1);
            String id = m.group(2);
            //Processing Logic
            finalDesc = finalDesc.replace("@[" + name + "](" + id + ")", "@" + name);

            mentionPerson = name;
            map.put("@" + name, mentionPerson);
        }
        int textColor = ResourcesCompat.getColor(getResources(), R.color.teal_200, null);
        Spannable spannable = new SpannableString(finalDesc);
        for (Map.Entry<String, String> stringMentionPersonEntry : map.entrySet()) {
            int startIndex = finalDesc.indexOf(stringMentionPersonEntry.getKey());
            int endIndex = startIndex + stringMentionPersonEntry.getKey().length();
            spannable.setSpan(new ForegroundColorSpan(textColor), startIndex, endIndex , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannable);
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {

            if (!s.toString().isEmpty() && start < s.length()) {

                String name = s.toString().substring(0, start + 1);

                int lastTokenIndex = name.lastIndexOf(" @");
                int lastIndexOfSpace = name.lastIndexOf(" ");
                int nextIndexOfSpace = name.indexOf(" ", start);

                if (lastIndexOfSpace > 0 && lastTokenIndex < lastIndexOfSpace) {
                    String afterString = s.toString().substring(lastIndexOfSpace, s.length());
                    if (afterString.startsWith(" ")) return;
                }

                if (lastTokenIndex < 0) {
                    if (!name.isEmpty() && name.startsWith("@")) {
                        lastTokenIndex = 1;
                    } else
                        return;
                }

                int tokenEnd = lastIndexOfSpace;

                if (lastIndexOfSpace <= lastTokenIndex) {
                    tokenEnd = name.length();
                    if (nextIndexOfSpace != -1 && nextIndexOfSpace < tokenEnd) {
                        tokenEnd = nextIndexOfSpace;
                    }
                }

                name = s.toString().substring(lastTokenIndex, tokenEnd).trim();
                Pattern pattern = Pattern.compile("^(.+)\\s.+");
                Matcher matcher = pattern.matcher(name);
                if (!matcher.find()) {
                    name = name.replace("@", "").trim();
                    if (!name.isEmpty()) {
                        Log.e("aa","AA");
                        setMentioningText(name);
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public class SpaceTokenizer implements MultiAutoCompleteTextView.Tokenizer {

        public int findTokenStart(CharSequence text, int cursor) {

            int i = cursor;

            while (i > 0 && text.charAt(i - 1) != ' ') {
                i--;
            }
            while (i < cursor && text.charAt(i) == ' ') {
                i++;
            }
            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {

            int i = cursor;
            int len = text.length();

            while (i < len) {
                if (text.charAt(i) == ' ') {
                    return i;
                } else {
                    i++;
                }
            }

            return len;
        }

        public CharSequence terminateToken(CharSequence text) {

            int i = text.length();

            while (i > 0 && text.charAt(i - 1) == ' ') {
                i--;
            }

            if (i > 0 && text.charAt(i - 1) == ' ') {
                return text;
            } else {
                // Returns colored text for selected token
                SpannableString sp = new SpannableString(String.format(formattedOfString, text));
                int textColor = ResourcesCompat.getColor(getResources(), R.color.teal_200, null);
                sp.setSpan(new ForegroundColorSpan(textColor), 0, text.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sp;
            }
        }
    }
}