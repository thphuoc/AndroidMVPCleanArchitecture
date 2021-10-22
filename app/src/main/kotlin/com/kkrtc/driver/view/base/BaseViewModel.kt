package com.kkrtc.driver.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel<Data> : ViewModel() {

    var state = ViewState<Data>()
    val viewStateLiveData = MutableLiveData<ViewState<Data>>()
    val messageStateLiveData = MutableLiveData<String>()
    val navigatorState = MutableLiveData<ViewNavigator>()
    private val disposables = CompositeDisposable()

    open fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    open fun publishState(newState: ViewState<Data>) {
        state = newState
        viewStateLiveData.postValue(newState)
    }

    open fun publishState(viewState: ViewStateEnum, message: String? = null) {
        state = state.copy(viewState = viewState, errorMessage = message ?: state.errorMessage)
        viewStateLiveData.postValue(state)
    }

    open fun publishState(
        viewState: ViewStateEnum,
        title: String? = null,
        message: String? = null
    ) {
        state = state.copy(
            viewState = viewState,
            errorTitle = title ?: "",
            errorMessage = message ?: state.errorMessage
        )
        viewStateLiveData.postValue(state)
    }

    fun showToast(message: String) {
        messageStateLiveData.postValue(message)
    }

    fun navigate(navigate: ViewNavigator) {
        navigatorState.postValue(navigate)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        disposables.clear()
    }

    open fun init() {
        viewStateLiveData.postValue(state)
    }

    open fun loadData() {}


}