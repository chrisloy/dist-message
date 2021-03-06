package pubsub


import com.zink.queue._

object Write {
  val fileName = "/Users/loyc01/Desktop/access.log"
  val lines: Iterator[String] = scala.io.Source.fromFile(fileName).getLines()

  val con = ConnectionFactory.connect()

  val wc = con.publish("BBC7")

  for (line <- lines) {
    wc.write(line)
  }
  wc.write("")
}

