package ec

import java.util.UUID

object ConsistentHashMain {

  /*
   *  CRDT = Commutative/Convergent Replicated Data Type
   *   -> G Counter
   */

  val ring = new Ring(64000)

  ring.addNode("Node1")
  ring.addNode("Node2")
  ring.addNode("Node3")

  1 to 10000 foreach { _ =>
    ring.put(UUID.randomUUID().toString, "whatever")
  }

  println(ring)
}
