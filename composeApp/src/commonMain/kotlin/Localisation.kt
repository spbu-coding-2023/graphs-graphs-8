import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import view.screens.SettingsJSON
import java.io.File
import java.time.LocalDateTime

@Serializable
class TranslationPair(val code: String, val localisation: String)

@Serializable
class TranslationList(val transList: List<TranslationPair>)

fun localisation(text: String): String{
    val language = getLocalisation()
    try {
        val data = Json.decodeFromString<TranslationList>(object {}.javaClass.getResourceAsStream("localisation/$language.json")?.bufferedReader()!!.readText())
        for (wordPair in data.transList) {
            if (wordPair.code == text){
                return wordPair.localisation
            }
        }
        return text
    }
    catch (ex: Exception){
        File("src/logs/localisationError.log").appendText("LOCALISATION ERROR ${LocalDateTime.now()} -- Key $text is not found in $language\nEXCEPTION IS $ex\n")
        return text
    }
}

fun getLocalisation(): String{
    try {
        val language = Json.decodeFromString<SettingsJSON>(File("src/settings.json").readText()).language
        return language
    }
    catch(ex: Exception){
        File("src/logs/localisationError.log").appendText("FILE OF LOC NOT FOUND ${LocalDateTime.now()} -- EXCEPTION IS $ex\n")
        return "en-US"
    }
}