#!/usr/bin/env python
# -*- coding: utf-8 -*-

# crash test fuzzer for echo mirage logged traffic

# radamsa -o :6666 -m bf buf/sent-11
# radamsa -n inf -s 1232187213123 -o :6666 buf/sent-16
# radamsa -n inf -m bf,bp -s 52321872213123 -o :6666 buf/sent-9 -M -

import socket
import sys
import time

#if len(sys.argv) < 3:
#    sys.stderr.write("Usage:\n    python {} <ip>\n".format(sys.argv[0]))
#    exit(1)

# -----

#host = sys.argv[1]
host = '10.5.5.2'
port = 6129
save = False # will save all sent bufs to 'sent-(num)' files, remember to turn this off before fuzzing!
fuzz_sent = 20 # row to fuzz, comment out to disable fuzzing
debug = 3 # debug level 0 (nothing) - 5 (all)
radamsa_port = 6666
socket_timeout = 1 # secs

# -----

try:
    fuzz_sent
except:
    fuzz_sent = None

echo = open('traffic-2.log').read().strip().replace('\r','').split('\n')[1:-1]
comm = []

count = 0
buf = ''
row = 0
while echo:
    line = echo.pop(0)
    if 'TCP: Received' in line:
        sent = False
        count = int(line.split(' ')[2])
        continue
    if 'TCP:Sent' in line:
        sent = True
        count = int(line.split(' ')[1])
        continue
    if not len(line): # empty line, ignore it
        continue
    if count > 0: # gather buffer
        index = line.index(' ')
        line = line[index+1:index+1+16*3].replace(' ','').decode('hex')
        buf += line
        count -= len(line)
    if count == 0: # add to 'comm' table
        row += 1
        #print sent, len(buf)
        comm += [[sent, buf]]
        if save and sent:
            with open('sent-{}'.format(row),'wb') as f:
                f.write(buf)
        buf = ''
#print comm

fuzzing = fuzz_sent != None

if fuzzing:
    orig_data_len = len(comm[fuzz_sent-1][1])
    fuzz_data_used = True
    hist_fuzz_data = None
iteration = 0
seek = 0
while True:
    # get radamsa payload
    if fuzzing and fuzz_data_used:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect(('localhost', int(radamsa_port),))
        time.sleep(0.002)
        fuzz_data = sock.recv(65535)
        #print orig_data_len, len(fuzz_data)
        sock.close()
        fuzz_data_used = False

    try:
        # connect to server (and if enabled, fuzz)
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.settimeout(socket_timeout)
        sock.connect((host, int(port),)) # connect to server
    except:
        print '[!] Failed to connect to server'
        if fuzzing and hist_fuzz_data:
            print '[⚫] Crashed?', hist_fuzz_data.encode('hex') 
        break

    # clear screen
    if fuzzing:   
        sys.stdout.write(chr(27)+'[2J' + chr(27)+'[1;1H')

    # communicate
    if debug > 2:
        if fuzzing:
            print '[ ] Connected (iter: {}, seek: {}, sent:{}, orig: {}, fuzz: {})'.format(iteration, seek, fuzz_sent, orig_data_len, len(fuzz_data))
        else:
            print '[ ] Connected'
    row = 0
    rows = len(comm)
    for sent,buf in comm:
        row += 1
        try:
            if sent == True:
                if row == fuzz_sent:
                    fuzz_data_used = True
                    buf = fuzz_data
                if fuzzing and row == fuzz_sent:
                    print '[•] {}/{} - Sending {} bytes...'.format(row, rows, len(buf))
                elif not fuzzing or debug > 3:
                    print '[·] {}/{} - Sending {} bytes...'.format(row, rows, len(buf))
                sock.send(buf)
            else:
                if not fuzzing or debug > 3:
                    print '[ ] {}/{} - Receiving {} bytes...'.format(row, rows, len(buf)),
                data = sock.recv(len(buf))
                if not fuzzing or debug > 3:
                    print data == buf
                #
                #print 'recv:', data.encode('hex')
                #print 
                #print 'orig:', buf.encode('hex')
                #print
        except socket.error:
            if debug > 2:
                print '[ ] {}/{} - Server disconnected'.format(row, rows)
            break
    sock.close()
    time.sleep(0.01)
    if fuzzing:
        hist_fuzz_data = fuzz_data[:]
        if fuzz_data_used:
            seek += 1
    iteration += 1
    
    # print out information about potential fuzz points, if no fuzzing is enabled
    if not fuzzing:
        print '[i] Rows which could potentially be fuzzed:',
        row_id = 0
        for sent, buf in comm:
            row_id += 1
            if row_id > row:
                break
            if sent == True:
                print row_id,
        break
