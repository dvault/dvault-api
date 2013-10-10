package it.dvault
package dao

import scala.slick.session.Session

trait ImplicitSession {
  implicit val implicitSession: Session
}

