import com.mongodb.MongoClient
import com.mongodb.MongoClientURI


object Database {

    private val connectionString = MongoClientURI(AppConstants.CONNECTION_STRING)
    private val db = MongoClient(connectionString).getDatabase(AppConstants.DATABASE_NAME)
    private val recipesCollection = db.getCollection("jrecipes")

    fun getRecipes() = recipesCollection.find().toList()

}