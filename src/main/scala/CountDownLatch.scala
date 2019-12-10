import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success}

object CountDownLatchSample extends App {

  private[this] val fs: Seq[Future[Int]] = {
    for (i <- 1 to 8) yield Future {
      val waitMilliSec = new Random().nextInt(1000)
      Thread.sleep(waitMilliSec)
      waitMilliSec
    }
  }

  private[this] var counter = new AtomicInteger(0)

  for (f <- fs;v <- f) {
    if (counter.incrementAndGet == 3) {
      for (f <- fs if f.isCompleted; v <- f) println(s"${v} msec")
    }
  }

  while(counter.get <= 3) Thread.sleep(100)

}
