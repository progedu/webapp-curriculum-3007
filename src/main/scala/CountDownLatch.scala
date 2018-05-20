import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success}

object CountDownLatchSample extends App {
  val random = new Random()
  val randomMaxSeed = 1000
  val maxPromiseCount = 3
  val counter = new AtomicInteger(0)

  val promises = Seq(Promise[Int], Promise[Int], Promise[Int])
  val futures = Seq(Future[Int]{waitFunc()}, Future[Int]{waitFunc()},
                    Future[Int]{waitFunc()}, Future[Int]{waitFunc()},
                    Future[Int]{waitFunc()}, Future[Int]{waitFunc()},
                    Future[Int]{waitFunc()}, Future[Int]{waitFunc()})

  def waitFunc(): Int = {
    val waitVal = random.nextInt(randomMaxSeed)
    Thread.sleep(waitVal)
    waitVal
  }

  futures.foreach { f => f.foreach { time =>
    val idx = counter.getAndIncrement()
    if (idx < maxPromiseCount) {
      promises(idx).success(time)
      promises(idx).future.foreach { time => println(time) }
    }
  }}

  futures.foreach { f => {
    Await.result(f, Duration.Inf)
  }}
  println("finish.")
}
