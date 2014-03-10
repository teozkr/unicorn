package org.virtuslab.unicorn.ids

import scala.slick.driver.JdbcDriver
import org.virtuslab.unicorn.ids.repositories.{JunctionRepository, BaseRepositories, BaseQueries}

/**
 * Created by bambucha on 09.03.14.
 */
trait UnicornConfig {

  val driver: JdbcDriver

  type Tag = scala.slick.lifted.Tag

  type Session = driver.profile.simple.Session

}

trait UnicornCore extends Tables with BaseQueries with BaseRepositories with JunctionRepository {

  self: UnicornConfig =>

}