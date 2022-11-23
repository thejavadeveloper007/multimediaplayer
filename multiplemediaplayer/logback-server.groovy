
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.INFO

def USER_HOME                  = System.getProperty("user.home"             )

def VLCJ_PRO_PID               = System.getProperty("vlcjProPid"            )
def VLCJ_PRO_MEDIA_PLAYER_PORT = System.getProperty("vlcjProMediaPlayerPort")
def VLCJ_PRO_CALLBACK_PORT     = System.getProperty("vlcjProCallbackPort"   )
def VLCJ_PRO_HEARTBEAT_PORT    = System.getProperty("vlcjProHeartbeatPort"  )

def TIMESTAMP                  = timestamp("HHmmss")

appender("FILE", FileAppender) {
    file = "/${USER_HOME}/vlcj-pro-logs/vlcj-pro-server_${VLCJ_PRO_PID}.log"
    append = false
    encoder(PatternLayoutEncoder) {
        pattern = "%-36(%d{HH:mm:ss.SSS} [%thread]) %-5level %44logger{44} - %msg%n"
//        immediateFlush = false
    }
}

logger("ch"                   , ERROR)
logger("org"                  , ERROR)
logger("com"                  , ERROR)

logger("uk.co.caprica.vlcj"   , DEBUG)
logger("uk.co.caprica.vlcjpro", DEBUG)

root(DEBUG, ["FILE"])
