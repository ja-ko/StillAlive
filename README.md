# Still Alive
Still Alive is a drop-in heartbeat library for your JVM-based application. By default it logs a changing line every 
couple of minutes - without a single line of source-code. This can be used for example to verify your log-monitoring for
long running or idling applications.

## Configuration
You can configure the delay before the initial Log-Line using the system property `dev.jko.alive.delay`. The interval 
between logs can be configured `dev.jko.alive.interval`.

In order to start logging you should register the class using `-javaagent:<JARFILE>`

## Do you really need a library to do this?
Nope. Didn't stop me though.