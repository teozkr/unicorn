package org.virtuslab.unicorn.ids

import scala.slick.driver.JdbcDriver

/**
 * Created by bambucha on 09.03.14.
 */
trait UnicornConfig {

  val driver: JdbcDriver

  type Tag = scala.slick.lifted.Tag

  type TableQuery[T <: scala.slick.lifted.AbstractTable[_]] = scala.slick.lifted.TableQuery[T]

  type Session = driver.profile.simple.Session;
}