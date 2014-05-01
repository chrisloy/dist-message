package ec

import scala.util.Random

object GCounterMain extends App {

  val a = new PNCounter
  val b = new PNCounter
  val c = new PNCounter

  a.inc("a", 34)
  a.dec("a", 27)
  b.inc("b", 23)
  c.inc("c", 10)
  c.inc("d", 33)
  c.dec("d", 33)

  Random.shuffle(Seq(a, b, c).permutations) foreach {
    case Seq(x, y, z) => x merge y merge z
  }

  val kevin = {val pn = new PNCounter ; pn.dec("kevin", 5000) ; pn}

  (a merge c) merge kevin
  a merge (c merge kevin)

  println(a)
  println(b)
  println(c)

//  println(a merge b merge c merge a merge a merge a)

  // also available:
  // numbers, tuples, options, set, maps, vector, q-tree, woot (richard dalloway)
  // hyperloglog, bloom filters

  // "commutative monoid"

  // CRDTs => BBC Academy Archive (Noel's talk)
}
