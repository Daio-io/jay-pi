package models

import org.json.JSONArray
import org.json.JSONObject

data class Recipe(val title: String,
                  val method: String,
                  val image: String,
                  val group: String,
                  val ingredients: List<Ingredient>,
                  val time: String): Model {

    override fun toJson(): String {
        val jsonRecip = JSONObject().apply {
            put("title", title)
            put("time", time)
            put("group", group)
            put("method", method)
            put("image", image)
        }

        val ingArray = JSONArray()
        ingredients.forEach {
            val obj = JSONObject().apply {
                put("amount", it.amount)
                put("name", it.name)
                put("type", it.type)
            }
            ingArray.put(obj)
        }
        jsonRecip.put("ingredients", ingArray)

        return jsonRecip.toString()
    }

    companion object {
        fun fromJson(json: String): Recipe {
            val obj = JSONObject(json)
            val title = obj.getString("title")
            val method = obj.getString("method")
            val group = obj.getString("group")
            val time = obj.getString("time")
            val image = obj.getString("image")
            val ingredients = obj.getJSONArray("ingredients")

            val ings = ingredients.map {
                val ingJson = it.toString()
                Ingredient.fromJson(ingJson)
            }

            return Recipe(title, method, image, group, ings, time)
        }
    }


}

data class Ingredient(val amount: String,
                      val name: String,
                      val type: String) {

    companion object {
        fun fromJson(json: String): Ingredient {
            val obj = JSONObject(json)
            return Ingredient(obj.optString("amount"), obj.getString("name"), obj.getString("type"))
        }
    }
}

interface Model {
    fun toJson(): String
}
