CC = gcc
CFLAGS = -g -Wall -ansi -std=c99 -pedantic -D_GNU_SOURCE
SYS=$(shell uname)

ifeq ($(SYS),Linux)
  LIBS=-lpthread
else
  LIBS=-lsocket -lnsl -lpthread
endif

PROGS = TCPEchoClient TCPEchoClient4 TCPEchoClient6 GetAddrInfo TCPEchoServer \
TCPEchoServer4 TCPEchoServer6 UDPEchoClient UDPEchoServer BruteForceCoding \
SigAction UDPEchoServer-SIGIO UDPEchoClient-Timeout

OTHER = HandleTCPClient.o DieWithMessage.o TCPServerUtility.o

VOTEMAINS = VoteClientTCP.o VoteServerTCP.o
AUX =  DieWithMessage.o TCPServerUtility.o AddressUtility.o TCPClientUtility.o
BINDELIM = VoteEncodingBin.o DelimFramer.o
BINLEN = VoteEncodingBin.o LengthFramer.o
TEXTDELIM = VoteEncodingText.o DelimFramer.o
TEXTLEN = VoteEncodingText.o LengthFramer.o
HFILES = practical.h framer.h TCPEchoServer.h voteencoding.h voteprotocol.h
CFILES = $(addsuffix .c, $(PROGS)) $(addsuffix .c, $(basename $(OTHER))) \
	$(addsuffix .c, $(basename $(VOTEMAINS))) \
	$(addsuffix .c, $(basename $(BINDELIM)))  \
	$(addsuffix .c, $(basename $(TEXTLEN)))
MFILES = Makefile

% : %.c

% : %.o $(OTHER)
	$(CC) $(CFLAGS) $^ -o $* $(LIBS)

all : $(PROGS) $(OTHER)

votebindelim: $(VOTEMAINS) $(BINDELIM) $(AUX)
	$(CC) $(CFLAGS) -o VoteClientTCP VoteClientTCP.o $(BINDELIM) $(AUX)
	$(CC) $(CFLAGS) -o VoteServerTCP VoteServerTCP.o $(BINDELIM) $(AUX)

votebinlen: $(VOTEMAINS) $(BINLEN) $(AUX)
	$(CC) $(CFLAGS) -o VoteClientTCP VoteClientTCP.o $(BINLEN) $(AUX)
	$(CC) $(CFLAGS) -o VoteServerTCP VoteServerTCP.o $(BINLEN) $(AUX)

votetextdelim: $(VOTEMAINS) $(TEXTDELIM) $(AUX)
	$(CC) $(CFLAGS) -o VoteClientTCP VoteClientTCP.o $(TEXTDELIM) $(AUX)
	$(CC) $(CFLAGS) -o VoteServerTCP VoteServerTCP.o $(TEXTDELIM) $(AUX)

votetextlen: $(VOTEMAINS) $(TEXTLEN) $(AUX)
	$(CC) $(CFLAGS) -o VoteClientTCP VoteClientTCP.o $(TEXTLEN) $(AUX)
	$(CC) $(CFLAGS) -o VoteServerTCP VoteServerTCP.o $(TEXTLEN) $(AUX)

multi:  MulticastSender.o MulticastReceiver.o DieWithMessage.o
	$(CC) $(CFLAGS) -o MulticastSender MulticastSender.o DieWithMessage.o
	$(CC) $(CFLAGS) -o MulticastReceiver MulticastReceiver.o DieWithMessage.o

tarball: $(HFILES) $(CFILES) $(MFILES)
	tar czf pocket.tgz $^

%clean :
	$(RM) $* $*.o $(OTHER)

clean :
	$(RM) *.o $(PROGS)

.PHONY : clean
.PHONY : all
.PRECIOUS : %.o %.c
