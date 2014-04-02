package hungryworker

import com.zink.queue.ConnectionFactory


object SenderCollector extends App{

  val fileName = "/Users/loyc01/Desktop/access.log"
  val lines: Iterator[String] = scala.io.Source.fromFile(fileName).getLines()

  val con = ConnectionFactory.connect()

  val wc = con.publish("Work")
  wc.write("Hello")

  for (line <- lines) {
    wc.write(line)
  }
  wc.write("")

  val rc = con.subscribe("Ack")

  def read(): Unit = {
    println(rc.read().toString)
    read()
  }

  read()
}
