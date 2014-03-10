package org.virtuslab.unicorn.ids

import scala.slick.driver.JdbcDriver

/**
 * Created by Łukasz Dubiel on 10.03.14.
 */
object PlayUnicorn extends UnicornConfig with UnicornCore {
  override val driver: JdbcDriver = play.api.db.slick.Config.driver
}
