package ec

import scala.collection.mutable

class GCounter(var map: mutable.Map[String, Long] = mutable.Map()) {

  def inc(node: String): Unit = inc(node, 1L)

  def inc(node: String, long: Long): Unit = {
    val existing: Long = getForNode(node)
    map.put(node, long + existing)
  }

  def value = map.values.sum

  def merge(that: GCounter): GCounter = {
    val tuples =
      for (node <- this.keys ++ that.keys)
      yield node -> math.max(this.getForNode(node), that.getForNode(node))
    tuples foreach { case (node, num) => map.put(node, num) }
    this
  }

  def keys: Set[String] = map.keys.toSet

  def getForNode(name: String) = map.get(name) getOrElse 0L

  override def toString = s"${value.toString} $map"
}
