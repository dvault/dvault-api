package it.dvault
package models

import play.api.libs.json.JsValue

case class User(
  id: Option[Int],
  email: String,
  admin: Boolean
  ) {
}

object User {
  def create(email: String, admin: Boolean): User = User(None, email, admin)
}

