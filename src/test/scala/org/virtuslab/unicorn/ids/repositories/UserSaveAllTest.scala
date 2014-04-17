package org.virtuslab.unicorn.ids.repositories

import org.junit.Test
import org.junit.Assert._
import org.virtuslab.unicorn.ids._
import play.api.db.slick.Config.driver.simple._
import play.api.test.FakeApplication
import play.api.Play
import org.junit.Before
import org.junit.AfterClass
import org.junit.After

class UserSaveAllTest extends TestCommons {

  case class UserId(id: Long) extends BaseId

  object UserId extends IdCompanion[UserId]

  case class User(id: Option[UserId],
    email: String,
    firstName: String,
    lastName: String) extends WithId[UserId]

  class Users(tag: Tag) extends IdTable[UserId, User](tag, "USERS") {

    def email = column[String]("EMAIL", O.NotNull)

    def firstName = column[String]("FIRST_NAME", O.NotNull)

    def lastName = column[String]("LAST_NAME", O.NotNull)

    override def * = (id.?, email, firstName, lastName) <> (User.tupled, User.unapply)
  }

  @Before
  def before(): Unit = {
    app = new FakeApplication(additionalConfiguration = testDb)
    Play.start(app)
  }

  @After
  def afterJUnit: Unit = {
    Play.stop()
  }

  def testImpl(implicit session: Session) = {
    // setup
    val usersQuery: TableQuery[Users] = TableQuery[Users]
    object UsersRepository extends BaseIdRepository[UserId, User, Users]("USERS", usersQuery)
    usersQuery.ddl.create

    val admin = User(Some(UserId(1)), "admin@vl.pl", "admin", "admin") //Admin must have id == 1

    val users = ("abscdef").map {
      case l => User(None, s"$l@$l.pl", s"name-$l", s"lastname-$l")
    } :+ admin

    UsersRepository saveAll (users)

    val savedUsers = UsersRepository.findAll().sortBy(_.email)
    val sortedUsers = users.sortBy(_.email)
    assertEquals(sortedUsers.size, savedUsers.size)
    savedUsers zip sortedUsers foreach {
      case (savedUser, startUser) =>
        assertEquals(startUser.firstName, savedUser.firstName)
        assertEquals(startUser.lastName, savedUser.lastName)
        assertEquals(startUser.email, savedUser.email)
    }
  }

  @Test
  def test(): Unit = {
    rollback(s => testImpl(s))
  }
}