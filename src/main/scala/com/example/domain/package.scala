package com.example

import java.util.UUID
import eu.timepit.refined._
import eu.timepit.refined.api.Validate.Plain
import eu.timepit.refined.api.{Refined, Validate}

import scala.util.{Failure, Success, Try}


package object domain {
  case class TypeValidationException(ex: String) extends Exception(ex)

  case class UserMeta(id: UUID,
                      name: String,
                      login: String,
                      _type: UserType,
                      becameClearTime: Int,
                      groups: Vector[String])

  case class UserData(id: UUID,
                      name: String,
                      login: String,
                      password: Password,
                     _type: UserType,
                      creationDate: Int,
                      becameClearTime: Int,
                      groups: Vector[String])

  case class AdminMeta(id: UUID, login: String, _type: UserType)

  implicit def either2Try[T](income: Either[String, T]): Try[T] = {
    income match {
      case Right(message) => Success(message)
      case Left(ex) => Failure(TypeValidationException(ex))
    }
  }
  type UserType = Refined[String, UserTypePredicate]

  object UserType {
    def unapply(arg: UserType): Option[String] = Some(arg.value)

    def apply(arg: String): Try[UserType] = refineV[UserTypePredicate](arg)
  }
  case class UserTypePredicate()

  implicit val validateRights: Plain[String, UserTypePredicate] =
    Validate.fromPredicate(
      (right: String) => UserTypes.contains(right),
      right => s"Right $right does not exist",
      UserTypePredicate()
    )

  final private val UserTypes = Set(
    "admin",
    "read_admin",
    "read_write_admin",
    "user"
  )
}