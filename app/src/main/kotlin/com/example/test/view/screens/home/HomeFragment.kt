package com.example.test.view.screens.home

import android.os.Bundle
import android.view.View
import com.example.test.R
import com.example.test.view.base.StateFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HomeFragment : StateFragment<Any>() {
    override val layoutResId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()
    private val loginFlowScope by lazy {
        getKoin().getOrCreateScope(
            "loginFlow",
            named("LoginFlowData")
        )
    }
    private val loginFlowData: LoginFlowData by loginFlowScope.inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDesc.text = getString(R.string.lb_welcome, loginFlowData.email)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginFlowScope.closed
    }

    fun test(a:Int, array: Array<Int>) : Int {
        if(a !in array.indices) {
            return -1
        }
        return array.sortedArray()[a]
    }

    fun test1(array: Array<Int>) : List<Int> {
        val newList = arrayListOf<Int>()
        newList.addAll(array.filter { it%2==0 })
        newList.addAll(array.filter { it%2==1 })
        return newList
    }

    fun findLowestMultiplied(array: Array<Int>) : Int {
        val sortedArray = array.sortedArray()
        if(array.isEmpty()) return 0
        if(array.size == 1) return array[0]
        if(array.size == 2) return array[0] * array[1]

        return if(sortedArray.any {it < 0}) {
            sortedArray[0] * sortedArray[sortedArray.size-1]
        } else {
            sortedArray[0] * sortedArray[1]
        }
    }
}