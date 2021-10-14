import com.phuoc.data.base.HttpResponse
import com.phuoc.domain.form.RequestOtpForm
import com.phuoc.data.services.IOtpService
import com.phuoc.domain.usecases.RequestOtpUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class RequestOtpUseCaseTest {

    lateinit var verifyOtpUseCase : com.phuoc.domain.usecases.RequestOtpUseCase

    @Mock
    lateinit var otpService: com.phuoc.data.services.IOtpService

    @Before
    fun setup() {
        otpService = Mockito.mock(com.phuoc.data.services.IOtpService::class.java)
        verifyOtpUseCase = com.phuoc.domain.usecases.RequestOtpUseCase(otpService)
    }

    @Test
    fun `case Email pattern is invalid expect throw exception`() {
        val input = com.phuoc.domain.form.RequestOtpForm("abc")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Email invalid")
    }

    @Test
    fun `case Email suffix is invalid expect throw exception`() {
        val input = com.phuoc.domain.form.RequestOtpForm("abc@abc.com")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Email invalid")
    }

    @Test
    fun `case Email pattern is valid expect no exception return`() {
        val input = com.phuoc.domain.form.RequestOtpForm("abc@positivethinking.tech")
        val response = Single.just(com.phuoc.data.base.HttpResponse<Any>())
        Mockito.`when`(otpService.requestOtp(input)).thenReturn(response)

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception == null)
    }


    fun test(k: Int, array: Array<Int>) {

    }
}