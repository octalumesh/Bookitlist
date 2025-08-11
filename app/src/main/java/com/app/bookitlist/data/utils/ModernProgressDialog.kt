package com.app.bookitlist.data.utils

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.progressindicator.CircularProgressIndicator

/**
 * Modern DialogFragment-based Progress Dialog
 * Alternative approach using DialogFragment for better lifecycle management
 */
class ModernProgressDialog : DialogFragment() {

    companion object {
        private const val ARG_CANCELABLE = "cancelable"

        fun newInstance(isCancelable: Boolean = false): ModernProgressDialog {
            return ModernProgressDialog().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_CANCELABLE, isCancelable)
                }
            }
        }
    }

    private var messageTextView: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        val isCancelable = arguments?.getBoolean(ARG_CANCELABLE, false) ?: false
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createProgressView()
    }

    private fun createProgressView(): View {
        val context = requireContext()

        val linearLayout = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(60, 40, 60, 40)
        }

        // Add CircularProgressIndicator
        val progressIndicator = CircularProgressIndicator(context).apply {
            isIndeterminate = true
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = android.view.Gravity.CENTER_HORIZONTAL
                topMargin = 20
                bottomMargin = 20
            }
        }

        // Add message TextView


        linearLayout.addView(progressIndicator)

        return linearLayout
    }

    fun updateMessage(message: String) {
        messageTextView?.text = message
    }
}

/**
 * DialogFragment-based Progress Manager
 * Alternative singleton manager using DialogFragment
 */
object DialogFragmentProgressManager {

    private var currentDialog: ModernProgressDialog? = null
    private const val DIALOG_TAG = "ModernProgressDialog"

    fun showProgress(
        fragmentManager: FragmentManager,
        isCancelable: Boolean = false
    ) {
        dismissProgress()

        currentDialog = ModernProgressDialog.newInstance(isCancelable)
        currentDialog?.show(fragmentManager, DIALOG_TAG)
    }

    fun showApiProgress(fragmentManager: FragmentManager) {
        showProgress(fragmentManager, false)
    }

    fun dismissProgress() {
        currentDialog?.dismiss()
        currentDialog = null
    }

    fun updateMessage(message: String) {
        currentDialog?.updateMessage(message)
    }

    fun isShowing(): Boolean {
        return currentDialog?.dialog?.isShowing ?: false
    }
}
