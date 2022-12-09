package com.example.models

import org.jetbrains.exposed.sql.Table

/**
 * Offer
 *
 * @property id ID of the offer
 * @property name Name of the offer
 * @property location Location of the offer
 * @property description A brief description of the offer
 * @property price  Price of the offer
 * @property stars Number of stars of the offer (0-5)
 * @property imageName Name of the image file in the files/images folder
 * @property isSaved Whether the offer is saved to "my offers" or not
 */
data class Offer(
    val id: Int = 0,
    val name: String,
    val location: String,
    val description: String,
    val price: Int,
    val stars: Int,
    val imageName: String,
    var isSaved: Boolean = false,
)

object Offers : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val location = varchar("location", 1024)
    val description = varchar("description", 1024)
    val price = integer("price")
    val stars = integer("stars")
    var isSaved = bool("isSaved")
    val imageName = varchar("imageName", 128)

    override val primaryKey = PrimaryKey(id)
}

