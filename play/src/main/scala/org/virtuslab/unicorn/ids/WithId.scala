package org.virtuslab.unicorn.ids

import play.api.data.format.{ Formats, Formatter }
import play.api.mvc.{ QueryStringBindable, PathBindable }


/**
 * Base class for companion objects for id classes.
 * Adding this will allow you not to import mapping from your table class every time you need it.
 *
 * @tparam I type of Id
 * @author Krzysztof Romanowski, Jerzy Müller
 */
abstract class IdCompanion[I <: BaseId]
  extends PlayImplicits[I]
  with Applicable[I] {

  /** Ordering for ids - it is normal simple ordering on inner longs ascending */
  implicit lazy val ordering: Ordering[I] = Ordering.by[I, Long](_.id)
}

/**
 * Implicits required by Play.
 *
 * @tparam I type of Id
 * @author Krzysztof Romanowski, Jerzy Müller
 */
protected[unicorn] trait PlayImplicits[I <: BaseId] {
  self: Applicable[I] =>

  /**
   * Type mapper for route files.
   * @param longBinder path bindable for Long type.
   * @return path bindable for I
   */
  implicit def pathBindable(implicit longBinder: PathBindable[Long]): PathBindable[I] =
    longBinder.transform(apply, _.id)

  /** Implicit for mapping id to routes params for play */
  implicit val toPathBindable: QueryStringBindable[I] =
    QueryStringBindable.bindableLong.transform(apply, _.id)

  /** Form formatter for I */
  implicit lazy val idMappingFormatter: Formatter[I] = new Formatter[I] {

    override val format = Some(("format.numeric", Nil))

    override def bind(key: String, data: Map[String, String]) =
      Formats.longFormat.bind(key, data).right.map(apply).left.map {
        case errors if data.get(key).forall(_.isEmpty) => errors.map(_.copy(message = "id.empty"))
        case errors => errors.map(_.copy(message = "id.invalid"))
      }

    override def unbind(key: String, value: I): Map[String, String] =
      Map(key -> value.id.toString)
  }
}