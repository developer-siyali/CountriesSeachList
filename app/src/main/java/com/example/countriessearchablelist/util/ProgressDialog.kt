package com.example.countriessearchablelist.util

import android.app.Activity
import android.app.AlertDialog
import android.os.CountDownTimer
import com.example.countriessearchablelist.R

class ProgressDialog {

    lateinit var dialog: AlertDialog

    fun showProgressBar(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity.layoutInflater
        builder.setView(layoutInflater.inflate(R.layout.loading_dialog, null))
        dialog = builder.create()
    }

    fun showDialog() {
        dialog.show()
    }

    fun dismissDialog() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                dialog.dismiss()
            }
        }.start()
    }
}