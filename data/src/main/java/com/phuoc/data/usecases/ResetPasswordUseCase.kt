package com.phuoc.data.usecases

import com.phuoc.data.rxmapper.transformCompletable
import com.phuoc.data.services.IAuthService
import com.phuoc.domain.form.ResetPasswordForm
import com.phuoc.domain.usecases.IResetPasswordUseCase
import com.phuoc.domain.utils.Validator
import io.reactivex.Completable

class ResetPasswordUseCase(private val service: IAuthService) : IResetPasswordUseCase {
    override fun execute(input: ResetPasswordForm): Completable {
        when {
            input.currentPassword.isBlank() ||
                    input.newPassword.isBlank() ||
                    input.newConfirmPassword.isBlank() -> {
                return Completable.error(Exception("Form invalid"))
            }
            input.newPassword != input.newConfirmPassword -> {
                return Completable.error(Exception("Confirm password is not match"))
            }
            !Validator.isValidPassword(input.newConfirmPassword) -> {
                return Completable.error(Exception("Password must be at least 6 letters or digits"))
            }
        }

        return service.resetPassword(
            current_password = input.currentPassword,
            new_password = input.newPassword,
            new_confirm_password = input.newConfirmPassword
        ).transformCompletable()
    }
}