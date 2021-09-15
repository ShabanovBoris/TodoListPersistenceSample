package com.bosha.notespersistencesample.presentation.utils

import com.bosha.notespersistencesample.presentation.ui.addition.AdditionFragment


fun AdditionFragment.validateFields(): Boolean {
    if (binding.tvTitle.editText?.text.isNullOrEmpty()) {
        binding.tvTitle.error = "Empty title"
        return false
    }
    return true
}