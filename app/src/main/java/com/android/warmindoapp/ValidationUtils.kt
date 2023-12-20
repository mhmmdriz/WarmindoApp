package com.android.warmindoapp

import android.text.TextUtils
import android.util.Patterns

object ValidationUtils {

    fun isTextNotEmpty(text: String?): Boolean {
        return !TextUtils.isEmpty(text)
    }
}
