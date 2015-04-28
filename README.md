===============================
README - SuperSimpleServer v0.1
===============================
 -Ibrohim Kholilul Islam (@ibrohimislam)
 -Satria Priambada (@SatriaPriambada)
 -Bimo Aryo Tyasono (@squilliams)
 -Nitho Alif Ibadurrahman (@NithoAlif)
===============================
Specification:
	1. Result is a Map :
		=> Result has members with key identified by [head] and [body].
		=> [head] is an array of string which elements is :
			[0] = Message type
			[1] = Server identity
			[2] = Response date
			[3] = Content type
			[4] = Content message
			[Last-1] = Compression type
			[Last] = Connection status
		=> [body] is an array of byte with encoding UTF-8.
	
	2. sss.properties can be used to define and configure how the server works.
		=> host = The IP address which the server can be accessed through
		=> port = The port which used to communicate between client and the server 
		=> pluginslocation = The location of the plugins used to extend the functionality of the server.