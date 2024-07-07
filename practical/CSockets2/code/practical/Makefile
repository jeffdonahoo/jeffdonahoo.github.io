#
#   A wrapper for C++ sockets on Unix and Windows
#   Copyright (C) 2002
#
#   This program is free software; you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation; either version 2 of the License, or
#   (at your option) any later version.
#
#   This program is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with this program; if not, write to the Free Software
#   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
#

CXX = g++
CXXFLAGS = -Wall -ansi -pedantic -g

ifeq ($(shell uname),SunOS)
  LIBS = -lsocket -lnsl
endif

TARGETS = PlusOneClient PlusOneServer SurveyServer SurveyServer2 SurveyClient SurveyClient2 AdminClient2

all: $(TARGETS)

SampleServer: SampleServer.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o SampleServer SampleServer.o PracticalSocket.o $(LIBS)

SampleClient: SampleClient.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o SampleClient SampleClient.o PracticalSocket.o $(LIBS)

PlusOneClient: PlusOneClient.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o PlusOneClient PlusOneClient.o PracticalSocket.o $(LIBS)

PlusOneServer: PlusOneServer.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o PlusOneServer PlusOneServer.o PracticalSocket.o $(LIBS)

SurveyServer: SurveyServer.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o SurveyServer SurveyServer.o SurveyCommon.o PracticalSocket.o $(LIBS) -lpthread

SurveyServer2: SurveyServer2.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o SurveyServer2 SurveyServer2.o SurveyCommon.o PracticalSocket.o $(LIBS) -lpthread

SurveyServer3: SurveyServer3.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o SurveyServer3 SurveyServer3.o SurveyCommon.o PracticalSocket.o $(LIBS) -lpthread

SurveyClient: SurveyClient.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o SurveyClient SurveyClient.o SurveyCommon.o PracticalSocket.o $(LIBS)

SurveyClient2: SurveyClient2.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o SurveyClient2 SurveyClient2.o SurveyCommon.o PracticalSocket.o $(LIBS)

AdminClient2: AdminClient2.o SurveyCommon.o PracticalSocket.o
	$(CXX) $(CXXFLAGS) -o AdminClient2 AdminClient2.o SurveyCommon.o PracticalSocket.o $(LIBS)

clean:
	$(RM) $(TARGETS)
	$(RM) *.o
