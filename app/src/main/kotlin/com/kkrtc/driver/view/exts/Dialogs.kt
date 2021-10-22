package com.kkrtc.driver.view.exts

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.kkrtc.driver.R
import com.kkrtc.driver.view.base.StateFragment
import kotlinx.android.synthetic.main.dialog_message_yes_no.view.*

fun StateFragment<*>.showMessageYesNoDialog(
    title: String,
    message: String,
    onYes: () -> Unit = {},
    onNo: () -> Unit = {}
) {
    context?.run {
        MaterialDialog(this).show {
            customView(viewRes = R.layout.dialog_message_yes_no, scrollable = false)
            view.btnCancel.setOnClickListener {
                dismiss()
                onNo()
            }
            view.btnOk.setOnClickListener {
                dismiss()
                onYes()
            }

            view.tvTitle.text = title
            view.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            view.tvDialogMessage.text = message
            cancelable(false)
        }
    }
}