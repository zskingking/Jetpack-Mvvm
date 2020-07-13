package com.zs.zs_jetpack.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.R


/**
 * des 对话框,仅用于显示简单提示信息,每次弹窗都相互独立
 * @author zs
 * @date 2020-03-12
 */
class DialogUtils {

    companion object {
        private var dialog: Dialog? = null

        /**
         * 二次确认对话框
         */
        fun confirm(context: Context, tips: String, onClick: (View) -> Unit) {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            //提示信息
            view.findViewById<TextView>(R.id.tvContent).apply {
                text = tips
            }
            //确认
            view.findViewById<TextView>(R.id.tvConfirm).clickNoRepeat {
                onClick.invoke(view)
                dismiss()
            }
            //取消
            view.findViewById<TextView>(R.id.tvCancel).clickNoRepeat {
                dismiss()
            }
            AlertDialog.Builder(context).apply {
                setView(view)
                dialog = create()
            }
            dialog?.show()
        }


        /**
         * 提示对话框
         */
        fun tips(context: Context, tips: String, onClick: (View) -> Unit) {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            //提示信息
            view.findViewById<TextView>(R.id.tvContent).apply {
                text = tips
            }
            //确认
            view.findViewById<TextView>(R.id.tvConfirm).clickNoRepeat {
                onClick.invoke(view)
                dismiss()
            }
            //取消
            view.findViewById<TextView>(R.id.tvCancel).apply {
                visibility = View.GONE
            }
            AlertDialog.Builder(context).apply {
                setView(view)
                dialog = create()
            }
            dialog?.show()
        }

        /**
         * loading
         */
        fun showLoading(context: Context, msg: String?) {
            AlertDialog.Builder(context).apply {
                setView(
                    LayoutInflater.from(context).inflate(R.layout.dialog_loading,
                        null).apply {
                    findViewById<TextView>(R.id.tvMsg).text = msg
                })
                dialog = this.create()
            }
            dialog?.show()
        }


        /**
         * 隐藏对话框
         */
        fun dismiss() {
            dialog?.dismiss()
            //必须滞空，否则会造成内存泄漏
            dialog = null
        }

    }
}