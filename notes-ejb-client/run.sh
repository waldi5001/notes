#/bin/sh

~/ibm/was_client/bin/launchClient.sh -JVMOptions '-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000' ~/Documents/notes/notes-ejb-client-ear/target/notes-ejb-client-ear-1.0-SNAPSHOT.ear -CCBootstrapHost=localhost -CCdumpJavaNameSpace=true -CCverbose=true