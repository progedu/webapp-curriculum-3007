import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}
import scala.util.Random

object CountDownLatchSample extends App {
  val maxMills = 1000
  val futuresCount = 8
  val printStartsNumber = 3
  val random = new Random()
  val counter = new AtomicInteger(0)

  val promises: Seq[Promise[Int]] =
    (1 to printStartsNumber).map(i => Promise[Int])

  for (i <- 1 to futuresCount) {
    val count = counter.getAndIncrement()
    if (count < promises.length) {
      promises(count).success(random.nextInt(maxMills))
    }
  }

  val results = promises.map(p => Await.result(p.future, Duration.Inf))

  results.foreach(println)
}
