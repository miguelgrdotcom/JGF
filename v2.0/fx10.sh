#!/bin/sh
# pjsub -j -L "rscgrp=short,node=1" --mpi "proc=1" fx10.sh

JGF_PATH=/group1/gc83/c83001/JGF/v2.0

. /group1/gc83/share/env.sh ompi
SECTION=section2
export CLASSPATH=${CLASSPATH}:${JGF_PATH}:${JGF_PATH}/${SECTION}
java JGFAllSizeC > ${SECTION}-fx10.log 2>&1
exit 1

SECTION=section3
export CLASSPATH=${CLASSPATH}:${JGF_PATH}/${SECTION}:{CLASSPATH}
java JGFAllSizeB > ${SECTION}-fx10.log 2>&1
