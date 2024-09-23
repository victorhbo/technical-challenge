package com.victorhbo.technicalchallengecatspictures.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.victorhbo.technicalchallengecatspictures.R

object ProgressDialog {
    private var dialog: AlertDialog? = null

    fun show(context: Context) {
        if (dialog == null) {
            val inflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.progress_dialog, null)

            dialog = AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create()

            dialog?.show()
        }
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}