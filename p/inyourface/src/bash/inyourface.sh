#! /bin/sh

DIRNAME=`dirname $0`
PROGNAME=`basename $0`

# Setup INYOURFACE_HOME
if [ "x$INYOURFACE_HOME" = "x" ]; then
    INYOURFACE_HOME=`cd $DIRNAME/..; pwd`
fi
export INYOURFACE_HOME

# Setup the JVM
if [ "x$JAVA_HOME" != "x" ]; then
    JAVA=$JAVA_HOME/bin/java 
else
    JAVA="java"
fi  

INYOURFACE_BOOT_CLASSPATH="$INYOURFACE_HOME/bin/inyourface.jar"

# Setup the classpath
if [ "x$INYOURFACE_CLASSPATH" = "x" ]; then
    INYOURFACE_CLASSPATH="$INYOURFACE_BOOT_CLASSPATH"
    for jar in `ls $INYOURFACE_HOME/lib/`; do
      INYOURFACE_CLASSPATH="$INYOURFACE_CLASSPATH:$INYOURFACE_HOME/lib/$jar"
    done;
else
    INYOURFACE_CLASSPATH="$INYOURFACE_CLASSPATH:$INYOURFACE_BOOT_CLASSPATH"
fi  

exec "$JAVA" \
    $JAVA_OPTS \
    -Dprogram.name="$PROGNAME" \
    -classpath $INYOURFACE_CLASSPATH \
    com.synacktiv.inyourface "$@"

