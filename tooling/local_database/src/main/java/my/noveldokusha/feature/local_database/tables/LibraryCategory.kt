package my.noveldokusha.feature.local_database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LibraryCategory (
    @PrimaryKey val id: Int,
    val name: String,
    val isCompleted: Boolean
)