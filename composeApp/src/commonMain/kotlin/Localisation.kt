import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import view.screens.SettingsJSON
import java.io.File

@Serializable
class TranslationPair(val code: String, val localisation: String)

@Serializable
class TranslationList(val transList: List<TranslationPair>)

fun localisation(text: String): String{
    try {

        println("HEREEEE")
        val language = Json.decodeFromString<SettingsJSON>(File("src/settings.json").readText()).language
        val data = Json.decodeFromString<TranslationList>(File("src/localisation/$language.json").readText())
        for (wordPair in data.transList) {
            if (wordPair.code == text){
                return wordPair.localisation
            }
        }
        println("HEREEEE")
        return text
    }
    catch (ex: Exception){
        println(ex)
        return text
    }
}