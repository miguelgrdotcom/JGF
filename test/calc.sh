#!/bin/sh

for log in `ls {par,mpi}/*.log`; do
    echo ${log} >> ALL.log;
    cat ${log} | grep 'time' | \
    gawk '{  \
            val[count++]=$8;  \
            }  \
            END {   \
                    asort(val,newval);   \
                    for (i=2; i < count; i++) {  \
                            sum+=newval[i];   \
                    }   \
                    print sum/(count-2)  \
            }' >> ALL.log  \
; done
