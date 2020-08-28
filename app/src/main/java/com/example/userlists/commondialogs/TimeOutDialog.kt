package com.example.userlists.commondialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.userlists.R

class TimeOutDialog(private val context: Context, f: () -> Unit) {

    private var dialog: AlertDialog

    init {
        dialog = AlertDialog.Builder(context)
            .setTitle(R.string.connection_time_out)
            .setMessage(R.string.internet_error_message)
            .setPositiveButton(R.string.retry) { _, _ ->
                f.invoke()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
    }

    fun show(message: Int) {
        dialog.setMessage(context.getString(message))
        dialog.show()
    }
}