package devandroid.vinicanallesdev.equipesroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Equipe::class], version = 1)
abstract class EquipeDatabase : RoomDatabase() {

    abstract fun equipeDAO(): EquipeDAO

    companion object {

        @Volatile
        private var INSTANCE: EquipeDatabase? = null

        fun getDatabase(context: Context): EquipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EquipeDatabase::class.java,
                    "equiperoom.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}