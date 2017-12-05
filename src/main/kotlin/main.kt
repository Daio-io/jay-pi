import spark.kotlin.ignite

fun main(args: Array<String>) {
    val handlers = Handlers(Database)
    val spark = ignite()
    spark.port(AppConstants.PORT)
    spark.get("/status", function = handlers.statusHandler)
    spark.get("/recipes", function = handlers.recipeHandler)
}