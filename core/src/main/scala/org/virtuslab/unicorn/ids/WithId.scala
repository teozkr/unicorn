package org.virtuslab.unicorn.ids

import scala.slick.lifted.MappedTo

/**
 * Base trait for all ids in system.
 * It is existential trait so it can have only defs.
 *
 * @author Krzysztof Romanowski, Jerzy Müller
 */
trait BaseId extends Any with MappedTo[Long] {
  def id: Long

  override def value = id
}

/**
 * Base class for all entities that contains an id.
 *
 * @author Krzysztof Romanowski
 */
trait WithId[I] {

  /** @return id of entity (optional, entities does not have ids before save) */
  def id: Option[I]
}