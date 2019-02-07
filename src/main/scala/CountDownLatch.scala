import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.Random

object CountDownLatchSample extends App {
  private val count = new AtomicInteger(0)
  val random = new Random()

  val promises = for (_ <- 1 to 3) yield Promise[Int]
  val futures = for(_ <- 1 to 8) yield Future[Int] {
    val waitMilliSec = random.nextInt(1000)
    Thread.sleep(waitMilliSec)
    waitMilliSec
  }

  futures.foreach{ f => f.foreach { waitMilliSec =>
    val c = count.getAndIncrement
    if (c < promises.length) {
      promises(c).success(waitMilliSec)
    }
  }}

  promises.foreach( p => p.future.foreach(println(_)))
  Thread.sleep(5000)
}
