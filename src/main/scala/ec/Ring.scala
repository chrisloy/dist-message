package ec

import scala.annotation.tailrec

case class Ring(size: Int) {

  import Hasher._

  val sizeBigInt: BigInt = size
  val array = new Array[RingNode](size.toInt)

  def addNode(name: String) = {
    val node = new RingNode(name)
    array(arrayPos(sha1, name)) = node
    array(arrayPos(sha256, name)) = node
    array(arrayPos(sha512, name)) = node
    array(arrayPos(md2, name)) = node
    array(arrayPos(md5, name)) = node
  }

  def put(key: String, value: AnyRef) = {
    findNode(key).put(key, value)
  }

  def get(key: String) = {
    findNode(key).get(key)
  }

  private def findNode(key: String): RingNode = {
    next(arrayPos(sha1, key))
  }

  private def next(current: Int): RingNode = {
    Option(array(current)) match {
      case Some(node) => node
      case None => next((current + 1) % size)
    }
  }

  private def arrayPos(hash: String => BigInt, name: String) = hash(name).mod(size).toInt

  override def toString = array.toSet.mkString("\n")
}
