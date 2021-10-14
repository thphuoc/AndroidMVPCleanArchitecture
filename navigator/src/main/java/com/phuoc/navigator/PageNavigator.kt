package com.phuoc.navigator

import androidx.fragment.app.Fragment

object PageNavigator {
    private val pages = hashMapOf<PageEnum, Class<*>>()
    fun register(page: PageEnum, clazz: Class<*>) {
        pages[page]
    }

    fun getPageClass(page:PageEnum) : Class<*>? {
        return pages[page]
    }
}

fun Fragment.next(page: PageEnum) {
    PageNavigator.getPageClass(page)?.let {

    }
}