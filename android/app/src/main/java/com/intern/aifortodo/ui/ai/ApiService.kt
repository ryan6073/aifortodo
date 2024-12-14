import com.intern.aifortodo.ui.ai.AiResponse
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Body

interface AiService {
    @POST("/summary")
    suspend fun getSummary(@Body request: RequestBody): AiResponse

    @POST("/question")
    suspend fun askQuestion(@Body request: RequestBody): AiResponse
}