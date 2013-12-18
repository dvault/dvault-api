package it.dvault
package models

import org.joda.time.DateTime

case class Category(
  id: Option[Int],
  parentCategoryId: Option[Int],
  name: String,
  notes: String,
  created: Option[DateTime]
)
