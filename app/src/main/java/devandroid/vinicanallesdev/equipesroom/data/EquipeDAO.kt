package devandroid.vinicanallesdev.equipesroom.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EquipeDAO {

    @Insert
    fun inserirEquipe(equipe: Equipe)

    @Update
    suspend fun atualizarEquipe (equipe: Equipe)

    @Delete
    suspend fun apagarEquipe(equipe: Equipe)

    @Query("SELECT * FROM equipe ORDER BY nome")
    suspend fun listarEquipes(): List<Equipe>
}