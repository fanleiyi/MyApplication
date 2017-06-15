package com.company.mplayer.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by pjy on 2017/4/17.
 */

public class BaseTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
