package jp.thinkdifferent.devsurface.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "destinations_table")
data class DestinationElement(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val data: String
)