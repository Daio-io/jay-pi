import models.Recipe
import org.json.JSONArray
import org.json.JSONObject

class Handlers(private val database: Database) {

    val recipeHandler = sparkHandler {
        val ingredients = request.queryParams("ingredients")

        response.header("Content-Type", AppConstants.JSON_CONTENT)

        val recipes = if (ingredients == null) {
            database.getRecipes()
        } else database.getRecipes().filter { it.ingredients.find { it.name.contains(ingredients, ignoreCase = true) } != null }

        val jsonArray = JSONArray()
        recipes.forEach {
            val item = JSONObject(it.toJson())
            jsonArray.put(item)
        }

        generateResponse("success", jsonArray.toString())
    }

    val saveHandler = sparkHandler {
        response.header("Content-Type", AppConstants.JSON_CONTENT)
        request.queryParams("apikey")?.takeIf { it == AppConstants.API_KEY }
                ?: return@sparkHandler generateResponse("failed", "\"Invalid API KEY\"")

        val body = request.body()

        val recipe = Recipe.fromJson(body)

        database.saveRecipe(recipe)
        generateResponse("success", "\"Recipe added\"")
    }

    val statusHandler = sparkHandler {
        response.header("Content-Type", AppConstants.JSON_CONTENT)
        "{\"status\":\"OK\"}"
    }

}

fun generateResponse(status: String, res: String?): String {
    return res?.let { "{\"status\":\"$status\", \"result\": $res}" } ?: "{\"status\":\"failed\", \"result\":\"[]\"}"
}
