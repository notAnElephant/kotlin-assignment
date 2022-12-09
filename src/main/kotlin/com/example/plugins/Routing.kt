package com.example.plugins

import com.example.dao.dao
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

/**
 * Configure routing
*/
fun Application.configureRouting() {
    routing {
        static("/static") {
            resources("files")
        }
        //redirect to all offers
        get("/") {
            call.respondRedirect("offers")
        }
        route("offers") {
            //all offers
            get {
                call.respond(FreeMarkerContent("index.ftl", mapOf("offers" to dao.allOffers())))
            }
            //detail view of a single offer
            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(FreeMarkerContent("show.ftl", mapOf("offer" to dao.offer(id))))
            }
        }
        //"my offers" (i.e. saved offers)
        get("saved_offers") {
            call.respond(
                FreeMarkerContent(
                    "saved_offers.ftl",
                    mapOf("offers" to (dao.allOffers().filter { it.isSaved }))
                )
            )
        }
        //either save or delete an offer - html forms don't have a delete method, so we use a post method with an _action parameter
        post("saved_offers/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            when (formParameters.getOrFail("_action")) {
                "save" -> {
                    dao.addToMyOffers(id)
                }

                "delete" -> {
                    dao.removeFromMyOffers(id)
                }
            }
            call.respondRedirect("/saved_offers")
        }
    }
}