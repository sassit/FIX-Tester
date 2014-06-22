This tiny program tries to get as much info out of a Logon handshake as possible. Reason being that some vendor products don't let you see the clear picture.

To run, call the produced jar with the config file:

Example file:

[default]
FileStorePath=logs/initiator
ConnectionType=initiator
SenderCompID=BANZAI
TargetCompID=EXEC
SocketConnectHost=localhost
StartTime=00:00:00
EndTime=00:00:00
HeartBtInt=5
ReconnectInterval=5

[session]
BeginString=FIX.4.4
SocketConnectPort=9880
Username=XUsername
Password=XPassword

The underlying framework used for this is QuickFIXJ.