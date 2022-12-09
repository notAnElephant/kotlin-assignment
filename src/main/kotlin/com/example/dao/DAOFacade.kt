package com.example.dao

import com.example.models.Offer

interface DAOFacade {

    suspend fun allOffers(): List<Offer>
    suspend fun offer(id: Int): Offer?
    suspend fun addNewOffer(newOffer: Offer): Offer?
    suspend fun updateOffer(newOffer: Offer): Boolean
    suspend fun deleteOffer(id: Int): Boolean
    suspend fun addToMyOffers(id: Int): Boolean
    suspend fun removeFromMyOffers(id: Int): Boolean
}