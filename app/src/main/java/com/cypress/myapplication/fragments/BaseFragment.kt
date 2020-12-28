package com.cypress.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cypress.myapplication.activities.PracticeActivity
import com.cypress.myapplication.constants.LOADING_DIALOG_TAG

open class BaseFragment(layoutResourceId: Int): Fragment(layoutResourceId) {
    private lateinit var fragmetDialog: LoadingDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun setTitle(title: String) {
        (activity as PracticeActivity).setTitle(title)
    }

    fun showLoadingDialog() {
        fragmetDialog = LoadingDialogFragment.newInstance()
        fragmetDialog.show(parentFragmentManager, LOADING_DIALOG_TAG)
    }

    fun hideLoadingDialog() {
        if (this::fragmetDialog.isInitialized && !fragmetDialog.isHidden) {
            fragmetDialog.dismissAllowingStateLoss()
        }
    }
}