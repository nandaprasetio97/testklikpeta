package com.example.todo.utils

import android.app.AlertDialog
import android.content.Context
import android.nfc.FormatException
import android.widget.TextView
import com.example.todo.LatLngException
import com.example.todo.R
import com.example.todo.StringEmptyException

class DialogUtils {
    companion object {
        fun alertDialogError(context: Context, e: Throwable) {
            val alertDialog = AlertDialog.Builder(context)
                .setView(R.layout.error_view)
                .create()
            val errorMessageTextView = alertDialog.findViewById<TextView>(R.id.tv_error_message)
            errorMessageTextView.setText(
                when (e) {
                    is StringEmptyException -> R.string.title_cannot_empty
                    is LatLngException -> R.string.lat_lng_cannot_zero
                    is FormatException -> R.string.format_date_is_not_correct
                    else -> R.string.unknown
                }
            )
        }
    }
}