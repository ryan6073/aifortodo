package com.intern.aifortodo.extensions

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intern.aifortodo.R

class PopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_bottom_sheet_dialog_list_dialog)

        showDialog()
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("提示")
            .setMessage("你好")
            .setPositiveButton("确定") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}