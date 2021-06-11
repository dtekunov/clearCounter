package com.example.utils

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpHeader, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import com.example.domain.UserMeta
import com.example.jsonFormatters.JsonWriter

object Responses {

  private final val baseHeaders = Vector.empty[HttpHeader]

  final def pongResponse: StandardRoute =
    complete(HttpResponse(
      status = StatusCodes.OK,
      headers = baseHeaders,
      entity = HttpEntity(
        contentType = ContentTypes.`application/json`,
        string = JsonWriter.format("pong", None)
      )))

  def badTypeValidationResponse[T](message: String, notValidated: T): StandardRoute =
    complete(HttpResponse(
      status= StatusCodes.BadRequest,
      headers = baseHeaders,
      entity = HttpEntity(
        contentType=ContentTypes.`application/json`,
        string = JsonWriter.format(message, None)
      )))

  def noSuchUserResponse[T](message: String, userInfo: T): StandardRoute =
    complete(HttpResponse(
      status= StatusCodes.NotFound,
      headers = baseHeaders,
      entity = HttpEntity(
        contentType=ContentTypes.`application/json`,
        string = JsonWriter.format(message, None)
      )))

  def getUserResponse(userMeta: UserMeta): StandardRoute =
    complete(HttpResponse(
      status= StatusCodes.OK,
      headers = baseHeaders,
      entity = HttpEntity(
        contentType=ContentTypes.`application/json`,
        string = JsonWriter.format(userMeta)
      )))

  def internalServerErrorResponse(message: String): StandardRoute =
    complete(HttpResponse(
      status= StatusCodes.InternalServerError,
      headers = baseHeaders,
      entity = HttpEntity(
        contentType=ContentTypes.`application/json`,
        string = JsonWriter.format(message, None)
      )))

  def postUserImpossibleResponse(message: String): StandardRoute =
    complete(HttpResponse(
      status= StatusCodes.ExpectationFailed,
      headers = baseHeaders,
      entity = HttpEntity(
        contentType=ContentTypes.`application/json`,
        string = JsonWriter.format(message, None)
      )))
}
