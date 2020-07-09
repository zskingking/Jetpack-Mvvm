package com.zs.base_library.utils

import java.io.Closeable
import java.io.IOException

/**
 * des 关闭io工具类
 * @author zs
 * @date 2020-03-09
 */
class CloseUtils private constructor() {
    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 关闭IO
         *
         * @param closeables closeable
         */
        fun closeIO(vararg closeables: Closeable?) {
            for (closeable in closeables) {
                if (closeable != null) {
                    try {
                        closeable.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }
}
