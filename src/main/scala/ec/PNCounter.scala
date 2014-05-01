package ec

class PNCounter(val pos: GCounter = new GCounter, val neg: GCounter = new GCounter) {

  def inc(node: String): Unit = inc(node, 1L)

  def inc(node: String, long: Long): Unit = pos.inc(node, long)

  def dec(node: String): Unit = dec(node, 1L)

  def dec(node: String, long: Long): Unit = neg.inc(node, long)

  def value = pos.value - neg.value

  def merge(that: PNCounter): PNCounter = {
    this.pos merge that.pos
    this.neg merge that.neg
    this
  }

  override def toString = s"${value.toString}\tPos:$pos\tNeg:$neg"
}
