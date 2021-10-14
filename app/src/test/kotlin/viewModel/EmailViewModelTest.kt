package viewModel

import RxSchedulerRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.phuoc.domain.form.RequestOtpForm
import com.phuoc.domain.usecases.RequestOtpUseCase
import com.example.test.view.base.ScreenEnum
import com.example.test.view.base.ViewNavigator
import com.example.test.view.base.ViewState
import com.example.test.view.base.ViewStateEnum
import com.example.test.view.screens.email.EmailViewModel
import io.reactivex.Completable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

@RunWith(JUnit4::class)
class EmailViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    lateinit var useCase: com.phuoc.domain.usecases.RequestOtpUseCase
    lateinit var viewModel: EmailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.markState(Lifecycle.State.RESUMED)
        Mockito.`when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)

        useCase = Mockito.mock(com.phuoc.domain.usecases.RequestOtpUseCase::class.java)
        viewModel = EmailViewModel(useCase)
    }

    @Test
    fun `test email invalid pattern expect throw exception`() {
        val input = com.phuoc.domain.form.RequestOtpForm("12345")
        val completable = Completable.error(Exception("Email invalid"))
        Mockito.`when`(useCase.execute(input)).thenReturn(completable)

        viewModel.requestOtp(input)

        var viewState: ViewState<*>? = null
        viewModel.viewStateLiveData.observe(lifecycleOwner, {
            viewState = it
        })
        Assert.assertEquals(ViewStateEnum.ERROR, viewState?.viewState)
    }

    @Test
    fun `test email valid expect navigate to OTP page`() {
        val input = com.phuoc.domain.form.RequestOtpForm("abc@positivethinking.tech")
        val completable = Completable.complete()
        Mockito.`when`(useCase.execute(input)).thenReturn(completable)

        viewModel.requestOtp(input)

        var viewNavigator: ViewNavigator? = null
        viewModel.navigatorState.observe(lifecycleOwner, {
            viewNavigator = it
        })

        Assert.assertEquals(ScreenEnum.SRC_OTP, viewNavigator?.screen)
    }
}