package com.pfl.lib_common.utils

import com.google.gson.GsonBuilder
import com.tencent.mmkv.MMKV

/**
 * MMKV封装
 */
class SPUtils {

    companion object {

        /**
         * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
         *
         * @param key
         * @param object
         */
        @JvmStatic
        fun put(key: String, `object`: Any) {
            when (`object`) {
                is String -> MMKV.defaultMMKV().putString(key, `object`)
                is Int -> MMKV.defaultMMKV().putInt(key, `object`)
                is Boolean -> MMKV.defaultMMKV().putBoolean(key, `object`)
                is Float -> MMKV.defaultMMKV().putFloat(key, `object`)
                is Long -> MMKV.defaultMMKV().putLong(key, `object`)
                else -> MMKV.defaultMMKV().putString(key, `object`.toString())
            }
        }

        /**
         * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
         *
         * @param key
         * @param defaultObject
         * @return
         */
        @JvmStatic
        operator fun get(key: String, defaultObject: Any): Any? {

            return when (defaultObject) {
                is String -> MMKV.defaultMMKV().getString(key, defaultObject)
                is Int -> MMKV.defaultMMKV().getInt(key, defaultObject)
                is Boolean -> MMKV.defaultMMKV().getBoolean(key, defaultObject)
                is Float -> MMKV.defaultMMKV().getFloat(key, defaultObject)
                is Long -> MMKV.defaultMMKV().getLong(key, defaultObject)
                else -> null
            }

        }

        @JvmStatic
        fun putObject(key: String, `object`: Any) {
            MMKV.defaultMMKV().putString(key, GsonBuilder().create().toJson(`object`))
        }

        @JvmStatic
        inline fun <reified T> getObject(key: String): T {
            return GsonBuilder().create().fromJson(MMKV.defaultMMKV().getString(key, ""), classOf<T>())
        }

        inline fun <reified T> classOf() = T::class.java

        /**
         * 移除某个key值已经对应的值
         *
         * @param key
         */
        @JvmStatic
        fun remove(key: String) {
            MMKV.defaultMMKV().remove(key)
        }

        /**
         * 清除所有数据
         */
        @JvmStatic
        fun clear() {
            MMKV.defaultMMKV().clearAll()
        }

        /**
         * 查询某个key是否已经存在
         *
         * @param key
         * @return
         */
        @JvmStatic
        operator fun contains(key: String): Boolean {
            return MMKV.defaultMMKV().contains(key)
        }

        /**
         * 返回所有的键值对
         *
         * @return
         */
        @JvmStatic
        fun getAll(): Map<String, *> {
            return MMKV.defaultMMKV().all
        }
    }
}