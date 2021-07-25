import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Random, Success}

object CountDownLatchSample extends App {
  val random = new Random()
  val futures: Seq[Future[Int]] = (for (i <- 1 to 8) yield Future {
    val waitMilliSec = random.nextInt(1000)
    Thread.sleep(waitMilliSec)
    waitMilliSec
  }).toList

  var completed: Seq[Int] = Nil

  futures.foreach(f => f onComplete {
    case Success(i) =>
      completed = completed :+ i
      if (completed.size == 3) completed.foreach(time => println(s"waitingTime:${time}"))
  })

  //  ↓回答例
  //  val indexCounter = new AtomicInteger()
  //  val random = new Random()
  //  val promises: Seq[Promise[Int]] = for (i <- 1 to 3) yield Promise[Int]
  //  val futures: Seq[Future[Int]] = for (i <- 1 to 8) yield Future[Int] {
  //    val waitMilliSec = random.nextInt(1000)
  //    Thread.sleep(waitMilliSec)
  //    waitMilliSec
  //  }
  //
  //  futures.foreach { f =>
  //    f.foreach { case waitMilliSec =>
  //      val index = indexCounter.getAndIncrement()
  //      if (index < promises.length) promises(index).success(waitMilliSec)
  //    }
  //  }
  //
  //  promises.foreach { p =>
  //    p.future.foreach {
  //      case waitMilliSec => println(s"waitintMilliSec:${waitMilliSec}")
  //    }
  //  }

  Thread.sleep(5000)

}
