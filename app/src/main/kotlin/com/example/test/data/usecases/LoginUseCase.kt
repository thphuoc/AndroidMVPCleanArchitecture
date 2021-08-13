package com.example.test.data.usecases

import com.example.test.data.apimgr.APIService
import com.example.test.data.form.LoginForm
import io.reactivex.Completable

class LoginUseCase : BaseUseCase<LoginForm, Completable> {
    override fun execute(input: LoginForm): Completable {
        //TODO: we call API in here, but in this test I will fake login
        APIService.setAccessToken("Set access token here after login success")
        return if (input.email == "devblock" && input.password == "2021") {
            Completable.complete()
        } else {
            Completable.error(Exception("Unauthorized"))
        }
    }
}