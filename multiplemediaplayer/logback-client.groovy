
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.INFO

def USER_HOME = System.getProperty("user.home")

def TIMESTAMP = timestamp("HHmmss")

appender("FILE", FileAppender) {
    file = "/${USER_HOME}/vlcj-pro-logs/vlcj-pro-client.log"
    append = false
    encoder(PatternLayoutEncoder) {
        pattern = "%-36(%d{HH:mm:ss.SSS} [%thread]) %-5level %72logger{72} - %msg%n"
//        immediateFlush = false
    }
}

append("CONSOLE" ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%-36(%d{HH:mm:ss.SSS} [%thread]) %-5level %72logger{72} - %msg%n"
//        immediateFlush = false
    }
}

logger("ch"                   , ERROR)
logger("org"                  , ERROR)
logger("com"                  , ERROR)

logger("uk.co.caprica.vlcj"   , INFO )
logger("uk.co.caprica.vlcjpro", DEBUG)

root(DEBUG, ["FILE"])
