package com.example.test.data.usecases

import com.example.test.data.form.IPageForm

/**
 * Before you define a function. You must know what is input/output
 * I: input should be store in a Page Form in order to avoid a lot of parameters pass in a function
 * O: output of usecase.
 */
interface BaseUseCase<I: IPageForm, O> {

    fun execute(input: I) : O
}