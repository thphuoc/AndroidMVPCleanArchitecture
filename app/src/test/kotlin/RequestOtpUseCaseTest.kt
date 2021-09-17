import com.example.test.data.dao.base.HttpResponse
import com.example.test.data.form.RequestOtpForm
import com.example.test.data.services.IOtpService
import com.example.test.data.usecases.RequestOtpUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class RequestOtpUseCaseTest {

    lateinit var verifyOtpUseCase : RequestOtpUseCase

    @Mock
    lateinit var otpService: IOtpService

    @Before
    fun setup() {
        otpService = Mockito.mock(IOtpService::class.java)
        verifyOtpUseCase = RequestOtpUseCase(otpService)
    }

    @Test
    fun `case Email pattern is invalid expect throw exception`() {
        val input = RequestOtpForm("abc")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Email invalid")
    }

    @Test
    fun `case Email suffix is invalid expect throw exception`() {
        val input = RequestOtpForm("abc@abc.com")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Email invalid")
    }

    @Test
    fun `case Email pattern is valid expect no exception return`() {
        val input = RequestOtpForm("abc@positivethinking.tech")
        val response = Single.just(HttpResponse<Any>())
        Mockito.`when`(otpService.requestOtp(input)).thenReturn(response)

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception == null)
    }
}