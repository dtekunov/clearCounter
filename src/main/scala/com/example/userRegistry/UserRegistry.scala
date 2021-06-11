package com.example.userRegistry

import com.example.mongo.MongoOperations

//#user-registry-actor
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object UserRegistry {
  def apply(mongo: MongoOperations): Behavior[Command] = registry(mongo)

  private def registry(mongo: MongoOperations): Behavior[Command] =
    Behaviors.receiveMessage {
      case GetUserById(id, replyTo) =>
        val user = mongo.getUserById(id)
        replyTo ! GetUserResponse(user)
        Behaviors.same

      case CreateUser(user, replyTo) =>
        mongo.addUser(user)
        replyTo ! ActionPerformedResponse(s"User created, ID:[${user.id}]; login:[${user.login}]")
        Behaviors.same


//      case GetUsers(replyTo) =>
//        replyTo ! Users(users.toSeq)
//        Behaviors.same
//      case CreateUser(user, replyTo) =>
//        replyTo ! ActionPerformed(s"User ${user.name} created.")
//        registry(users + user)
//      case GetUser(name, replyTo) =>
//        replyTo ! GetUserResponse(users.find(_.name == name))
//        Behaviors.same
//      case DeleteUser(name, replyTo) =>
//        replyTo ! ActionPerformed(s"User $name deleted.")
//        registry(users.filterNot(_.name == name))
    }
}
//#user-registry-actor
