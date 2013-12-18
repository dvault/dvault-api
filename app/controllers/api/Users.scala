package it.dvault
package controllers.api

import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.Codec
import play.api.mvc.BodyParsers
import play.api.http.Writeable
import play.api.Play.current

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.api.db.slick._

import dao.utils.Order

import models.User
import dao.DataProvider
import dao.DefaultDataProvider

import scala.concurrent.Future

abstract class Users extends Controller {

  object Format {
    import play.api.data.mapping._
    import play.api.http.ContentTypes
    import play.api.libs.json.JsValue
    import play.api.libs.json.JsNumber
    import play.api.libs.json.JsString
    import play.api.libs.json.JsArray
    import play.api.libs.json.Json

    /**
     * Serialize User using JSON, with light format (id, email)
     */
    val userLightJson: Write[User, JsValue] = Write[User, JsValue]{ user =>
      Json.obj(
        "id" -> (user.id.map(JsNumber(_)).getOrElse(JsNumber(0)): JsValue),
        "email" -> JsString(user.email)
      )
    }

    val userJson: Write[User, JsValue] = Write[User, JsValue]{ user =>
      Json.obj(
        "id" -> (user.id.map(JsNumber(_)).getOrElse(JsNumber(0)): JsValue),
        "email" -> JsString(user.email)
      )
    }

    /**
     * Write Seq[User] using JSON
     */
    implicit def usersWriteable(implicit codec: Codec): Writeable[Seq[User]] = {
      val toJsArray: Write[Seq[JsValue], JsValue] = Write[Seq[JsValue], JsValue](JsArray(_))

      val toJson: Write[Seq[User], JsValue] = Writes.seq(userLightJson) compose toJsArray

      Writeable(users => codec.encode(toJson.writes(users).toString), Some(ContentTypes.JSON))
    }

    /**
     * Write User using JSON
     */
    implicit def userWriteable(implicit codec: Codec): Writeable[User] = {
      Writeable(users => codec.encode(userJson.writes(users).toString), Some(ContentTypes.JSON))
    }

    val userRule = From[JsValue] { __ =>
      import play.api.data.mapping.json.Rules._
      (Rules.ignored(None)(__.path) and
       (__ \ "email").read[String] and
       (__ \ "admin").read[Boolean])(User.apply _)
    }
  }
  import Format._

  /**
   * Where to get data from
   */
  def dataProvider: DataProvider

  /*
   * Routes
   */
  /**
   * List all users
   * this route has pagination
   */
  // TODO: implement pagination
  def index = Action.async { request =>
    DB.withSession{ implicit session =>
      dataProvider.users
        .list(Order.Desc, 10)
        .map(Ok(_))
    }
  }

  /**
   * Retreive one user
   */
  def get(id: Int) = Action.async { request =>
    DB.withSession { implicit session =>
      dataProvider.users
        .get(id)
        .map(Ok(_))
    }
  }

  /**
   * Create one user
   */
  def create = Action.async (BodyParsers.parse.json){ request =>
    userRule.validate(request.body).map { newUser =>
      DB.withSession { implicit session: Session =>

        dataProvider.users
          .create(newUser)
          .map(Ok(_))
      }
      // TODO handle failed to parse error
    }.getOrElse {
      Future.successful(InternalServerError)
    }
  }

  /**
   * Update one user
   */
  def update(id: Long) = TODO

  /**
   * Delete one user
   */
  def delete(id: Long) = TODO
}

object Users extends Users {
  def dataProvider = DefaultDataProvider
}


