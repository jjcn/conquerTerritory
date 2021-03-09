#!/bin/bash

#annoying thing about docker:
#hard to tell when service inside it is actually up and running..
#have to just try to connect.
x=""
count=0
while [ "$x" == "" ]
do
    if [ "$count" == "20" ]
    then
        echo "Giving up after 20 attempts to connect!"
        exit 1
    fi
    x=`netcat -N -w 1 vcm-17819.vm.duke.edu 1651 < /dev/null`
    let count=count+1
done




nc -N vcm-17819.vm.duke.edu 1651 > testoutput
cat > expectedoutput <<EOF
Hello world.
EOF

diff testoutput expectedoutput
