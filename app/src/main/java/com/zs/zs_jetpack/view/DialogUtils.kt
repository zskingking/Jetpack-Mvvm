package com.zs.zs_jetpack.view

import android.app.Dialog
import android.content.Context
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zs.zs_jetpack.R


/**
 * des 对话框代理类，对外暴露使用接口，将框架与业务进行隔离
 * @author zs
 * @date 2020-03-12
 */
class DialogUtils {

    companion object {
        private var dialog: Dialog? = null

        /**
         * 提示对话框
         */
        fun tips(context: Context, tips: String, onClick: (View) -> Unit) {
            var dialog: Dialog? = null
            val builder = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            var tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvContent.text = tips
            builder.setView(view)
                .setPositiveButton("确定") { _, _ ->
                    onClick.invoke(view)
                    dialog?.dismiss()
                }
            dialog = builder.create()
            dialog.show()
        }

    }
}