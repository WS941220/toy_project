package com.example.toy_project.di.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.toy_project.data.source.local.Converters
import java.util.*

@Entity(tableName = "memo")
@TypeConverters(Converters::class)
data class Memo @JvmOverloads constructor(
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "image") var image: List<String>,
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
) {
    @ColumnInfo(name = "checked") var isChecked = false

    val isEmpty
        get() = title.isEmpty() && content.isEmpty() && image.isEmpty()
}