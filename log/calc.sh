#!/bin/sh

 
for pre in orig hu; do
for log in `ls seq/${pre}-* par/${pre}-* mpi/${pre}-*`; do
    echo ${log} >> ${pre}-ALL.txt;
    cat ${log} | grep 'Kernel\|Total' | \
	gawk '{  \
		title=$1; val[count++]=$2;  \
		}  \
		END {   \
			asort(val,newval);   \
			for (i=2; i < count; i++) {  \
				sum+=newval[i];   \
			}   \
			print title " : " sum/(count-2)  \
		}' >> ${pre}-ALL.txt  \
; done ; done
