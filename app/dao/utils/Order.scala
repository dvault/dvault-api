package it.dvault
package dao.utils


sealed trait Order

object Order {
  object Asc extends Order
  object Desc extends Order
}


