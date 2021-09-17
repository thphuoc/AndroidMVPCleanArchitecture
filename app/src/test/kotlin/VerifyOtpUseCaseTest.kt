import com.example.test.data.dao.base.HttpResponse
import com.example.test.data.form.VerifyOtpForm
import com.example.test.data.services.IOtpService
import com.example.test.data.usecases.VerifyOtpUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class VerifyOtpUseCaseTest {

    lateinit var verifyOtpUseCase : VerifyOtpUseCase

    @Mock
    lateinit var otpService: IOtpService

    @Before
    fun setup() {
        otpService = Mockito.mock(IOtpService::class.java)
        verifyOtpUseCase = VerifyOtpUseCase(otpService)
    }

    @Test
    fun `case Otp 5 digits expect throw exception`() {
        val input = VerifyOtpForm("12345")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Otp must be 4 digits")
    }

    @Test
    fun `case Otp 3 digits expect throw exception`() {
        val input = VerifyOtpForm("123")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Otp must be 4 digits")
    }

    @Test
    fun `case Otp invalid expect throw exception`() {
        val input = VerifyOtpForm("1235")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "OTP is invalid")
    }

    @Test
    fun `case Otp is valid expect return Completable`() {
        val input = VerifyOtpForm("1234")
        val response = Single.just(HttpResponse<Any>())
        Mockito.`when`(otpService.validateOtp(input)).thenReturn(response)

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception == null)
    }
}