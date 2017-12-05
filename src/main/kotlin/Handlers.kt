import org.json.JSONArray
import org.json.JSONObject

class Handlers(private val database: Database) {

    val recipeHandler = sparkHandler {
        response.header("Content-Type", AppConstants.JSON_CONTENT)
        val jsonArray = JSONArray()
        database.getRecipes().forEach {
            val item = JSONObject(it.toJson())
            jsonArray.put(item)
        }
        JsonResult("success", jsonArray.toString()).toJson()
    }

    val statusHandler = sparkHandler {
        response.header("Content-Type", AppConstants.JSON_CONTENT)
        "{\"status\":\"OK\"}"
    }

}


interface Model {
    fun toJson(): String
}

class JsonResult(private val status: String, val res: String?) : Model {

    override fun toJson(): String {
        return res?.let { "{\"status\":\"$status\", \"result\": $res}" } ?: "{\"status\":\"failed\", \"text\":\"\"}"
    }
}