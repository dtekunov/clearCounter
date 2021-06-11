package com.example

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.stream.scaladsl.Sink
import akka.util.Timeout
import com.example.userRegistry._
import com.example.userRegistry.Command
import com.example.utils.Responses.{badTypeValidationResponse, getUserResponse, internalServerErrorResponse, noSuchUserResponse, pongResponse}

import java.util.UUID
import scala.util.{Failure, Success, Try}

class UserRoutes(userRegistry: ActorRef[Command])(implicit val system: ActorSystem[_]) {

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

//  def getUsers(): Future[Users] =
//    userRegistry.ask(GetUsers)
//  def getUser(id: String): Future[GetUserResponse] =
//    userRegistry.ask(GetUser(name, _))
//  def createUser(user: User): Future[ActionPerformedResponse] =
//    userRegistry.ask(CreateUser(user, _))
//  def deleteUser(name: String): Future[ActionPerformedResponse] =
//    userRegistry.ask(DeleteUser(name, _))

  def getUserById(id: UUID): Future[GetUserResponse] =
    userRegistry.ask(GetUserById(id, _))

  def getUserByLogin(login: String): Future[GetUserResponse] =
    userRegistry.ask(GetUserByLogin(login, _))

  //#all-routes
  //#users-get-post
  //#users-get-delete
  val userRoutes: Route =
    pathPrefix("counter") {
      pathPrefix("users") {
        get {
          //#-- get by <id> is a service route to get meta info
          parameter("id") { id =>
            Try(UUID.fromString(id)) match {
              case Success(uuid) =>
                onComplete(getUserById(uuid)) {
                  case Success(response) =>
                    response.maybeUser match {
                      case Some(userMeta) => getUserResponse(userMeta)
                      case None => noSuchUserResponse("No such user", uuid)
                    }
                  case Failure(ex) => internalServerErrorResponse(ex.getLocalizedMessage)
                }
              case Failure(ex) => badTypeValidationResponse(ex.getLocalizedMessage, id)
            }
          } ~ parameter("login") { login =>
            onComplete(getUserByLogin(login)) {
              case Success(response) =>
                response.maybeUser match {
                  case Some(userMeta) => getUserResponse(userMeta)
                  case None => noSuchUserResponse("No such user", login)
                }
              case Failure(ex) => internalServerErrorResponse(ex.getLocalizedMessage)
            }
          }
        } ~ post {
          extractRequest { request =>
            implicit val ec: ExecutionContextExecutor = system.executionContext
            onComplete(request.entity.withSizeLimit(1024).dataBytes
//              .limit(MAX_ALLOWED_SIZE) todo: think about it
              .runWith(Sink.seq)) {
                case Success(res) => complete(res.toString) //TODO
                case Failure(ex) =>
                system.log.error(s"User creation failed due to [$ex]")
                internalServerErrorResponse(ex.getLocalizedMessage)
            }
          }
        } ~ delete {
          pongResponse
        }
      } ~
        pathPrefix("groups") {
          pongResponse
        }
    } ~
      pathPrefix("service") {
       pongResponse
    } ~
      pathPrefix("ping") { pongResponse }
}
