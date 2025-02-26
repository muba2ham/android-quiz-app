import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question @JvmOverloads constructor(
    @SerializedName("id")
    val id: Int,

    @SerializedName("text")
    val text: String,

    @SerializedName("type")
    val type: QuestionType,

    @SerializedName("options")
    val options: List<String> = emptyList(),

    @SerializedName("correctAnswers")
    val correctAnswers: List<Int> = emptyList(),

    var correctAnswersList: MutableList<String> = mutableListOf()
) : Parcelable

enum class QuestionType {
    TRUE_FALSE, SINGLE_CHOICE, MULTI_CHOICE, TEXT
}