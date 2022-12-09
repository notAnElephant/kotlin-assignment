package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Offer
import com.example.models.Offers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*

/**
 * DAO Facade implementation
 */
class DAOFacadeImpl : DAOFacade {
    /**
     * Transforms a ResultRow to an Offer, mapping the corresponding values from the database
     */
    private fun resultRowToOffer(row: ResultRow) = Offer(
        id = row[Offers.id],
        name = row[Offers.name],
        location = row[Offers.location],
        description = row[Offers.description],
        price = row[Offers.price],
        stars = row[Offers.stars],
        imageName = row[Offers.imageName],
        isSaved = row[Offers.isSaved],
    )

    /**
     * Gets all offers from the database
     * @return a list of all offers
     */
    override suspend fun allOffers(): List<Offer> = dbQuery {
        Offers.selectAll().map { resultRowToOffer(it) }
    }

    /**
     * Gets an offer by its id
     *
     * @param id
     * @return the offer with the given id, or null if it doesn't exist
     */
    override suspend fun offer(id: Int): Offer? = dbQuery {
        Offers
            .select { Offers.id eq id }
            .map(::resultRowToOffer)
            .singleOrNull()
    }

    /**
     * Add a new offer to the Offers table
     *
     * @param newOffer
     * @return
     */
    override suspend fun addNewOffer(newOffer: Offer): Offer? = dbQuery {
        Offers.insert {
            it[name] = newOffer.name
            it[location] = newOffer.location
            it[description] = newOffer.description
            it[price] = newOffer.price
            it[stars] = newOffer.stars
            it[imageName] = newOffer.imageName
            it[isSaved] = newOffer.isSaved
        }.resultedValues?.singleOrNull()?.let(::resultRowToOffer)
    }

    /**
     * Update an offer
     *
     * @param newOffer the updated offer
     * @return
     */
    override suspend fun updateOffer(newOffer: Offer): Boolean = dbQuery {
        Offers.update({ Offers.id eq newOffer.id }) {
            it[name] = newOffer.name
            it[location] = newOffer.location
            it[description] = newOffer.description
            it[price] = newOffer.price
            it[stars] = newOffer.stars
            it[imageName] = newOffer.imageName
            it[isSaved] = newOffer.isSaved
        } > 0
    }

    /**
     * Delete an offer by its id
     *
     * @param id
     * @return true if the offer was deleted, false otherwise
     */
    override suspend fun deleteOffer(id: Int): Boolean = dbQuery {
        Offers.deleteWhere { Offers.id eq id } > 0
    }

    /**
     * Add an offer by id to "my offers", i.e set the isSaved field to true
     *
     * @param id
     * @return true if the offer was added, false otherwise
     */
    override suspend fun addToMyOffers(id: Int): Boolean = dbQuery {
        Offers.update({ Offers.id eq id }) { it[isSaved] = true } > 0
    }

    /**
     * Remove an offer from "my offers", i.e set the isSaved field to false
     *
     * @param id
     * @return
     */
    override suspend fun removeFromMyOffers(id: Int): Boolean = dbQuery {
        Offers.update({ Offers.id eq id }) { it[isSaved] = false } > 0
    }
}

/**
 * Dao object
 * Initializing the db with sample data if it's empty
 */
val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if (allOffers().isEmpty()) {
            addNewOffer(
                Offer(
                    name = "A Parlament maga",
                    location = "Budapest",
                    description = "Ez egy rövid leírás a Parlamentről, mint szállás (lol)",
                    price = 200000,
                    stars = 5,
                    imageName = "bp.webp",
                )
            )
            addNewOffer(
                Offer(
                    name = "Eger szívében",
                    location = "Eger",
                    description = "Ez egy rövid leírás egy Egerben található szállásról",
                    price = 24000,
                    stars = 4,
                    imageName = "eger.webp",
                )
            )
            addNewOffer(
                Offer(
                    name = "Balatonfüredi kikapcsolódás",
                    location = "Balatonfüred",
                    description = "Ez egy rövid leírás egy Balatonfüreden található szállásról",
                    price = 40000,
                    stars = 3,
                    imageName = "füred.webp",
                )
            )
        }
    }
}