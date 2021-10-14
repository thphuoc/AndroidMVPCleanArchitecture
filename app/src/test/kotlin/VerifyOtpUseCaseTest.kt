import com.phuoc.data.base.HttpResponse
import com.phuoc.data.services.IOtpService
import com.phuoc.data.usecases.VerifyOtpUseCase
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito


@RunWith(JUnit4::class)
class VerifyOtpUseCaseTest {

    lateinit var verifyOtpUseCase: com.phuoc.data.usecases.VerifyOtpUseCase

    @Mock
    lateinit var otpService: com.phuoc.data.services.IOtpService

    @Before
    fun setup() {
        otpService = Mockito.mock(com.phuoc.data.services.IOtpService::class.java)
        verifyOtpUseCase = com.phuoc.data.usecases.VerifyOtpUseCase(otpService)
    }

    @Test
    fun `case Otp 5 digits expect throw exception`() {
        val input = com.phuoc.domain.form.VerifyOtpForm("12345")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Otp must be 4 digits")
    }

    @Test
    fun `case Otp 3 digits expect throw exception`() {
        val input = com.phuoc.domain.form.VerifyOtpForm("123")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "Otp must be 4 digits")
    }

    @Test
    fun `case Otp invalid expect throw exception`() {
        val input = com.phuoc.domain.form.VerifyOtpForm("1235")

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception?.message == "OTP is invalid")
    }

    @Test
    fun `case Otp is valid expect return Completable`() {
        val input = com.phuoc.domain.form.VerifyOtpForm("1234")
        val response = Single.just(com.phuoc.data.base.HttpResponse<Any>())
        Mockito.`when`(otpService.validateOtp(input)).thenReturn(response)

        val exception = verifyOtpUseCase.execute(input).blockingGet()
        assert(exception == null)
    }

    fun createObservable(): Observable<String> {
        return Observable.create {
            it.onNext(Thread.currentThread().name)
            it.onComplete()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test() {
        val scope = CoroutineScope(Dispatchers.IO)
        val channel = Channel<Int>()
        scope.launch {
            for (i in 0..10) {
                println("World $i")
                channel.send(i)
            }
        }
        println("World")

        Thread.sleep(1000)
    }

    suspend fun test1() {
//        val channel = Channel<Int>()

        for (i in 0..10) {
            println("World $i")
        }

//        println(channel.receive())
    }
}