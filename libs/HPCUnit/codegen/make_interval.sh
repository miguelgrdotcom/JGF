#!/bin/sh

for i in `seq 2 7`
do
  ruby folder_interval.rb ${i}
done

