import spark.kotlin.RouteHandler

fun sparkHandler(f: RouteHandler.()->Any): RouteHandler.() -> Any = f