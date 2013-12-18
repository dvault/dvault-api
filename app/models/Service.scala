package it.dvault
package models

import org.joda.time.DateTime

case class Service(
  id: Option[Int],
  categoryId: Int,
  parentServiceId: Option[Int],
  url: Url,
  secret: EncryptedSecret,
  created: DateTime,
  metadata: Map[String, String],
  notes: String
  ) {
}

