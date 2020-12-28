package com.cypress.myapplication.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.cypress.myapplication.R

class LoadingDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: android.app.AlertDialog.Builder = object : android.app.AlertDialog.Builder(context, theme) {}
        return builder.create()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        dialog?.setContentView(R.layout.fragment_dialog)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent, null)))
    }

    companion object {
        fun newInstance(): LoadingDialogFragment {
            return LoadingDialogFragment()
        }
    }
}