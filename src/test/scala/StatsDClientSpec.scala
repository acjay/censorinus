import org.scalatest._
import scala.collection.mutable.ArrayBuffer
import github.gphat.censorinus.{Metric,MetricSender,StatsDClient}
import github.gphat.censorinus.statsd.Encoder

class StatsDClientSpec extends FlatSpec with Matchers {

  val s = new TestSender()
  val client = new StatsDClient(flushInterval = 1000)

  "StatsDClient" should "deal with gauges" in {
    client.gauge("foobar", 1.0)
    val m = client.getQueue.poll
    m.name should be ("foobar")
    m.value should be ("1.0")
    m.metricType should be ("g")
  }

  it should "deal with counters" in {
    client.counter("foobar", 1.0)
    val m = client.getQueue.poll
    m.name should be ("foobar")
    m.value should be ("1.0")
    m.metricType should be ("c")
  }

  it should "deal with increments" in {
    client.increment("foobar")
    val m = client.getQueue.poll
    m.name should be ("foobar")
    m.value should be ("1.0")
    m.metricType should be ("c")
  }

  it should "deal with decrements" in {
    client.increment("foobar")
    val m = client.getQueue.poll
    m.name should be ("foobar")
    m.value should be ("1.0")
    m.metricType should be ("c")
  }

  it should "deal with histograms" in {
    client.histogram("foobar", 1.0)
    val m = client.getQueue.poll
    m.name should be ("foobar")
    m.value should be ("1.0")
    m.metricType should be ("h")
  }

  it should "deal with meters" in {
    client.meter("foobar", 1.0)
    val m = client.getQueue.poll
    m.name should be ("foobar")
    m.value should be ("1.0")
    m.metricType should be ("m")
  }

  it should "deal with sets" in {
    client.set("foobar", "fart")
    val m = client.getQueue.poll
    m.name should be ("foobar")
    m.value should be ("fart")
    m.metricType should be ("s")
  }
}
