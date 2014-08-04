#!/bin/sh

for i in `seq 1 7`
do
  ruby huset_maker.rb ${i}
done

