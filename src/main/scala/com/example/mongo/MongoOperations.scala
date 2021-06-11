package com.example.mongo

import akka.actor.typed.ActorSystem
import com.example.domain.{UserData, UserMeta}

import java.util.UUID
import scala.util.Try

class MongoOperations(system: ActorSystem[_]) {

  private val mongoInstance = new Entrypoint(system)

  def getUserById(id: UUID): Option[UserMeta] = ???

  def getUserByLogin(login: String): Option[UserMeta] = ???

  def addUser(user: UserData): Unit = ???
}
