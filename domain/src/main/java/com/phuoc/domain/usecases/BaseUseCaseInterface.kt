package com.phuoc.domain.usecases

import com.phuoc.domain.form.IPageForm

/**
 * Before you define a function. You must know what is input/output
 * I: input should be store in a Page Form in order to avoid a lot of parameters pass in a function
 * O: output of usecase.
 */
interface BaseUseCaseInterface<I: IPageForm, O> {

    fun execute(input: I) : O
}