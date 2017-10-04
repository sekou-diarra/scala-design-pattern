

trait Notifier {
  val notificationMessage: String

  def printNotification(): Unit = {
    println(notificationMessage)
  }

  def clear()
}


class NotifierImpl(val notificationMessage: String) extends Notifier {
  override def clear(): Unit = println("Clear")
}

trait Beeper {
  def beep(times: Int): Unit = {
    assert(times >= 0)
    1 to times foreach (i => println(s"Beep ....$i"))
  }
}

object BeeperRunner {
  val TIMES = 10

  def main(args: Array[String]): Unit = {
    val beeper = new Beeper {}
    beeper.beep(TIMES)
  }
}

abstract class Connector {
  def connect()

  def close()
}

trait ConnectorWithHelper extends Connector {
  def findDriver(): Unit = {
    println("Find...")
  }
}

class PgSqlConnector extends ConnectorWithHelper {
  override def close(): Unit = println("Closed")

  override def connect(): Unit = println("COnnected...")
}

trait Ping {
  def ping(): Unit = {
    println("ping")
  }
}

trait Pong {
  def pong(): Unit = {
    println("pong")
  }
}

trait PingPong extends Ping with Pong {
  def pingPong(): Unit = {
    ping()
    pong()
  }
}

object Runner extends PingPong {
  def main(args: Array[String]): Unit = {
    pingPong()
  }
}

object MixinRunner extends Ping with Pong {
  def main(args: Array[String]): Unit = {
    ping()
    pong()
  }
}

class Watch(brand: String, initialTime: Long) {
  def getTime(): Long = System.currentTimeMillis() - initialTime

}

object WatchUSer {
  def main(args: Array[String]): Unit = {
    val expensiveWatch = new Watch("expensive brande", 1000L) with Alarm with Notifier {
      override def trigger(): String = "The alarm was triggered."

      override def clear(): Unit = {
        println("Alarm cleared.")
      }

      override val notificationMessage: String = "Alarm is ..."
    }
    val cheapWatch = new Watch("cheap brand", 1000L) with Alarm {
      override def trigger(): String = "The alarm was triggered."
    }

    println(expensiveWatch.trigger())
    expensiveWatch.printNotification()
    println(s"The time is ${expensiveWatch.getTime()}.")
    expensiveWatch.clear()
    println(cheapWatch.trigger())
    println("Cheap watches cannot manually stop the alarm...")
  }
}

//object ReallyExpensiveWatchUser {
//  def main(args: Array[String]): Unit = {
//    val reallyExpensiveWatch = new Watch("Really expensive brand", 1000L) with ConnectorWithHelper {
//      override def connect(): Unit = {
//        println("conectect with other ...")
//      }
//
//      override def close(): Unit = {
//        println("closed ....")
//      }
//    }
//   println("Using the really expensive watch.")
//    reallyExpensiveWatch.findDriver()
//    reallyExpensiveWatch.connect()
//    reallyExpensiveWatch.close()
//  }
//}

object SelfTypeWatchUser {
  def main(args: Array[String]): Unit = {
    //    val watch = new Watch("alarm with notification", 1000L) with
    //      AlarmNotifier {
    //      }
    //  }
    val watch = new Watch("alarm with notification", 1000L) with AlarmNotifier with Notifier {
      override def trigger(): String = "Alarm triggered."

      override def clear(): Unit = {
        println("Alarm cleared.")
      }

      override val notificationMessage: String = "The notification."
    }
    println(watch.trigger())
    watch.printNotification()
    println(s"The time is ${watch.getTime()}.")
    watch.clear()
  }

}

trait Alarm {
  def trigger:String

}

trait AlarmNotifier {
  this: Notifier =>

  def trigger(): String
}

trait FormalGreeting {
  def hello(): String
//  def getTime():Int
}
trait InformalGreeting {
  def hello(): String
//  def getTime():String
}

class Greeter extends FormalGreeting with InformalGreeting {
  override def hello(): String = "Good Morning..."
}

object GreeterUser{
  def main(args: Array[String]): Unit = {
    val greeter = new Greeter()
    System.out.println(greeter.hello())
  }
}

trait A {
  def hello(): String = "Hello, I am trait A!"
}
trait B {
  def hello(): String = "Hello, I am trait B!"
}

object Clashing extends A with B {
  def main(args: Array[String]): Unit = {
    println(hello())
  }
}
