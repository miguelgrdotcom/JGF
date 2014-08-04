#!/bin/sh

for i in `seq 2 7`
do
  ii=`expr ${i} - 1`
  for j in `seq 0 ${ii}`
  do
    ruby mapper_projection.rb  ${i} ${j}
  done
done

