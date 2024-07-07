OBJS = utility.o PracticalSocket.o Message.o Ping.o Pong.o Query.o QueryHit.o \
ConnectionManager.o Connection.o Logger.o main.o

SRC =   $(OBJ_C:.o=.c)

CC=g++
CPPFLAGS=-g
LDFLAGS=-lpthread
PROGRAM=g

$(PROGRAM): $(OBJS) 
	$(CC) $(CFLAGS) -o $@ $^ $(LDFLAGS)

depend:
	mkdep -MM $(OBJS:.o=.cpp)

clean:
	rm $(OBJS) $(PROGRAM)

include .depend
