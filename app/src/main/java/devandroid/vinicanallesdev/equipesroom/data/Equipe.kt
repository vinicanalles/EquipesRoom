package devandroid.vinicanallesdev.equipesroom.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Equipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val estadio: String,
    val anoDeFundacao: String?
): Serializable