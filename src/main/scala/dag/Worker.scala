package dag

import com.zink.queue._
import scala.concurrent._
import ExecutionContext.Implicits.global


class Worker(name: String) {

  val con = ConnectionFactory.connect()
  val rc = con.subscribe(name)

  def reply(channel: String): Unit = {
    val wc = con.publish(channel)
    wc.write(s"[worker $name] Found one!")
  }

  def run(): Unit = {
    rc.read() match {
      case Message(m, back) if m.contains(name) => reply(back); run()
      case _ => run()
    }
  }
}
