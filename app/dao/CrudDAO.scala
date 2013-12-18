package it.dvault
package dao

import scala.concurrent.Future
import dao.utils.Order
import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.Column

abstract class TableWithId[A](_tableName: String) extends Table[A](_tableName) {
  def id: Column[Int]
}

trait CrudDAO[A] {
  this: ImplicitSession =>

  def table: TableWithId[A]

  private def selectOne(id: Int) = for {
    el <- table if el.id is id
  } yield (el)

  def create(element: A): Future[A]
  def update(element: A): Future[A]

  def delete(id: Int): Future[Unit] = {
    val q = selectOne(id)

    q.delete

    Future.successful(())
  }

  def get(id: Int): Future[A] = {
    val q = selectOne(id)

    Future.successful(q.first)
  }

  def list(sort: Order, limit: Int): Future[Seq[A]] = {
    val t = Query(table)
    val q = (sort match {
      case Order.Asc             => t.sortBy(_.id.asc)
      case Order.Desc            => t.sortBy(_.id.desc)
    }).take(limit)

    Future.successful(q.list.toSeq)
  }


  def list(from: Int, sort: Order, limit: Int, include: Boolean): Future[Seq[A]] = {
    val t = Query(table)

    val q = (sort match {
      case Order.Asc if include  => t.filter(_.id >= from).sortBy(_.id.asc)
      case Order.Asc             => t.filter(_.id > from).sortBy(_.id.asc)
      case Order.Desc if include => t.filter(_.id <= from).sortBy(_.id.desc)
      case Order.Desc            => t.filter(_.id < from).sortBy(_.id.desc)
    }).take(limit)

    Future.successful(q.list.toSeq)
  }

}


