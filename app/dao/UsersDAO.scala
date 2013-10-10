package it.dvault
package dao

import scala.concurrent.Future
import java.util.UUID

import play.api.db.slick.Config.driver.simple._

import dao.utils.Order
import models.User

trait UsersDAO {
  this: ImplicitSession =>

  def create(user: User): Future[User] = {
    val id: Int = UsersDAO.users.autoInc.insert(user)

    Future.successful(user.copy(id = Some(id)))
  }
  def delete(id: Int): Future[Unit] = ???

  def get(id: Int): Future[User] = {
    val u = for {
      u <- UsersDAO.users if u.id is id
    } yield (u)

    Future.successful(u.first)
  }
  def list(sort: Order, limit: Int): Future[Seq[User]] = {
    val u = Query(UsersDAO.users)
    val q = (sort match {
      case Order.Asc             => u.sortBy(_.id.asc)
      case Order.Desc            => u.sortBy(_.id.desc)
    }).take(limit)

    Future.successful(q.list.toSeq)
  }
  def list(from: Int, sort: Order, limit: Int, include: Boolean): Future[Seq[User]] = {
    val u = Query(UsersDAO.users)
    val q = (sort match {
      case Order.Asc if include  => u.filter(_.id >= from).sortBy(_.id.asc)
      case Order.Asc             => u.filter(_.id > from).sortBy(_.id.asc)
      case Order.Desc if include => u.filter(_.id <= from).sortBy(_.id.desc)
      case Order.Desc            => u.filter(_.id < from).sortBy(_.id.desc)
    }).take(limit)

    Future.successful(q.list.toSeq)
  }
  def update(user: User): Future[User] = ???

}

object UsersDAO {

  class Users extends Table[User]("users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def email = column[String]("email", O.NotNull)
    def admin = column[Boolean]("admin")

    def * = id.? ~ email ~ admin <> (User.apply _, User.unapply _)
    def autoInc = * returning id
  }

  val users = new UsersDAO.Users
}



