package pubsub

import com.zink.queue.ConnectionFactory

object Read {

  val con = ConnectionFactory.connect()
  val rc = con.subscribe("BBC7")

  def slurp(acc: Int): Int = {
    rc.read().toString match {
      case "" => acc
      case m if m contains "Daniel" => println(acc + 1) ; slurp(acc + 1)
      case _ => slurp(acc)
    }
  }
  slurp(0)
}
