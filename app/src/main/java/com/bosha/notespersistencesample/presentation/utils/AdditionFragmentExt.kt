package com.bosha.notespersistencesample.presentation.utils

import android.widget.Toast
import com.bosha.notespersistencesample.databinding.FragmentAdditionBinding
import com.bosha.notespersistencesample.presentation.ui.addition.AdditionFragment


fun AdditionFragment.validateFields(): Boolean {
    if ((binding.tvTitle.editText?.text.isNullOrEmpty())
    ) {
        binding.tvTitle.error = "Empty title"
        return false
    }
    //color button
    if (checkedColor == null) {
        Toast.makeText(binding.root.context, "Please, choose the color", Toast.LENGTH_SHORT).show()
        return false
    }

    return true
}

