package com.victorhbo.technicalchallengecatspictures.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.victorhbo.technicalchallengecatspictures.R

object AlertDialogUtil {
    fun showErrorAlertDialog(context: Context, msg: String) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.error))
            .setMessage(msg)
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()
    }
}
