package com.example.userRegistry

import akka.actor.typed.ActorRef
import com.example.domain.{UserData, UserMeta}

import java.util.UUID

sealed trait Command
//case class GetUsers(replyTo: ActorRef[Users]) extends Command
case class CreateUser(user: UserData, replyTo: ActorRef[ActionPerformedResponse]) extends Command
case class GetUserById(id: UUID, replyTo: ActorRef[GetUserResponse]) extends Command
case class GetUserByLogin(login: String, replyTo: ActorRef[GetUserResponse]) extends Command
case class DeleteUserByLogin(login: String, replyTo: ActorRef[ActionPerformedResponse]) extends Command

case class GetUserResponse(maybeUser: Option[UserMeta])
case class ActionPerformedResponse(description: String)

