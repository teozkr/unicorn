package org.virtuslab.unicorn.ids

import org.scalatest._
import scala.slick.driver.JdbcDriver
import org.virtuslab.unicorn.ids.repositories.{BaseRepositories, BaseQueries}
import scala.slick.jdbc.meta.DatabaseMeta

trait BaseTest extends FlatSpecLike with Matchers

trait Database extends UnicornConfig with Tables with BaseQueries with BaseRepositories  {
  override val driver: JdbcDriver = scala.slick.driver.H2Driver
}

trait AppTest extends BaseTest with BeforeAndAfterEach with Database {

  val dbURL = "jdbc:h2:mem:unicorn"

  val dbDriver = "org.h2.Driver"

  val DB = driver.profile.backend.Database.forURL(dbURL,driver=dbDriver)

  /**
   * Runs function in rolled-back transaction.
   *
   * @param func function to run in rolled-back transaction
   * @tparam A type returned by `f`
   * @return value returned from `f`
   */
  def rollback[A](func: Session => A): A = DB.withTransaction {
    session: Session =>
      val out = func(session)
      session.rollback()
      out
  }
}
