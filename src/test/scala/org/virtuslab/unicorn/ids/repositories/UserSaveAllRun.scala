package org.virtuslab.unicorn.ids.repositories

object UserSaveAllRun extends App {
  val test = new UserSaveAllTest()
  test.before()
  test.test()
  test.afterJUnit
}