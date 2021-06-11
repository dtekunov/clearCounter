package com.example.mongo
import akka.actor.typed.ActorSystem
import org.mongodb.scala._

class Entrypoint(system: ActorSystem[_]) {
  def mongoClient: MongoClient =
    MongoClient(system.settings.config.getString("main.mongo.uri"))

  val adminsDatabase: MongoDatabase =
    mongoClient.getDatabase("admins")

  val usersDatabase: MongoDatabase =
    mongoClient.getDatabase("users")
}
