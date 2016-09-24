#!/usr/bin/env python
"""
I've found pinhole tcp proxy and extended it to add xor tcp proxy functionality, new, additional parameter is xor byte

usage 'pinhole port host [newport]'

Pinhole forwards the port to the host specified.
The optional newport parameter may be used to
redirect to a different port.

eg. pinhole 80 webserver
    Forward all incoming WWW sessions to webserver.

    pinhole 23 localhost 2323
    Forward all telnet sessions to port 2323 on localhost.
"""

import sys
from socket import *
from threading import Thread
import time

LOGGING = 1

def log( s ):
    if LOGGING:
        print '%s:%s' % ( time.ctime(), s )
        sys.stdout.flush()

def xor( text, byte ):
    return ''.join(map(lambda x: chr(ord(x)^byte), list(text)))

class PipeThread( Thread ):
    pipes = []
    def __init__( self, source, sink, xorbyte ):
        Thread.__init__( self )
        self.source = source
        self.sink = sink
        self.xorbyte = xorbyte

        log( 'Creating new pipe thread  %s ( %s -> %s )' % \
            ( self, source.getpeername(), sink.getpeername() ))
        PipeThread.pipes.append( self )
        log( '%s pipes active' % len( PipeThread.pipes ))

    def run( self ):
        while 1:
            try:
                data = self.source.recv( 1024 )
                if not data: break
                self.sink.send( xor( data, self.xorbyte ) )
            except:
                break

        log( '%s terminating' % self )
        PipeThread.pipes.remove( self )
        log( '%s pipes active' % len( PipeThread.pipes ))
        
class Pinhole( Thread ):
    def __init__( self, port, newhost, newport, xorbyte ):
        Thread.__init__( self )
        log( 'Redirecting: localhost:%s -> %s:%s' % ( port, newhost, newport ))
        self.newhost = newhost
        self.newport = newport
        self.xorbyte = xorbyte
        self.sock = socket( AF_INET, SOCK_STREAM )
        self.sock.bind(( '', port ))
        self.sock.listen(5)
    
    def run( self ):
        while 1:
            newsock, address = self.sock.accept()
            log( 'Creating new session for %s %s ' % address )
            fwd = socket( AF_INET, SOCK_STREAM )
            fwd.connect(( self.newhost, self.newport ))
            PipeThread( newsock, fwd, self.xorbyte ).start()
            PipeThread( fwd, newsock, self.xorbyte ).start()
       
if __name__ == '__main__':

    print 'Starting Pinhole'

    import sys
    #sys.stdout = open( 'pinhole.log', 'w' )

    if len( sys.argv ) > 1:
        port = newport = int( sys.argv[1] )
        newhost = sys.argv[2]
        if len( sys.argv ) > 3: newport = int( sys.argv[3] )
        if len( sys.argv ) > 4: xorbyte = int( sys.argv[4] )
        Pinhole( port, newhost, newport, xorbyte ).start()
    else:
        Pinhole( 8000, '127.0.0.1', 8001, 0xF0).start()
        Pinhole( 8001, '192.168.132.128', 80, 0xF0).start()
