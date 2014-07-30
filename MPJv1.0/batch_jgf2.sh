#!/bin/sh
SECTION=section2
CORENUM=16
#for node in 8 16 32 64 128 256 512 1024 2048 5096
for node in 1 2 4 8 16 32 64 128 256
do
  PROC=`expr $node \* $CORENUM`
  if [ $node -lt 72 ]; then
    QUEUE="short"
  elif [ $node -lt 216 ]; then
    QUEUE="small"
  elif [ $node -lt 372 ]; then
    QUEUE="medium"
  elif [ $node -lt 480 ]; then
    QUEUE="large"
  else
    QUEUE="x-large"
  fi 
  #pjsub -j -S -L "rscgrp=$QUEUE,node=$node" --mpi "proc=$PROC" -o log/${SECTION}x${node}x${CORENUM} jgf2.sh 
  pjsub -j -L "rscgrp=$QUEUE,node=$node" --mpi "proc=$PROC" -o log/${SECTION}x${node}x${CORENUM} jgf2.sh 
done
