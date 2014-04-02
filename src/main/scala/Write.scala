
import com.zink.queue._

object Write extends App {
  val fileName = "/Users/loyc01/Desktop/access.log"
  val lines: Iterator[String] = scala.io.Source.fromFile(fileName).getLines()

  val con = ConnectionFactory.connect()

  val wc = con.publish("BBC7")
  wc.write("Hello")

  for (line <- lines) {
    wc.write(line)
  }
  wc.write("")
}

