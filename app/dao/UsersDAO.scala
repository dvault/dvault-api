package it.dvault
package dao

import scala.concurrent.Future

import play.api.db.slick.Config.driver.simple._

import dao.utils.Order
import models.User

trait UsersDAO extends CrudDAO[User] {
  this: ImplicitSession =>
}

trait DefaultUsersDAO extends UsersDAO {
  this: ImplicitSession =>

  val table = UsersDAO.users

  def create(user: User): Future[User] = {
    val id: Int = UsersDAO.users.autoInc.insert(user)

    Future.successful(user.copy(id = Some(id)))
  }

  def update(user: User): Future[User] = ???

}

object UsersDAO {

  class Users extends TableWithId[User]("users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def email = column[String]("email", O.NotNull)
    def admin = column[Boolean]("admin")

    def * = id.? ~ email ~ admin <> (User.apply _, User.unapply _)
    def autoInc = * returning id
  }

  val users = new Users

}



