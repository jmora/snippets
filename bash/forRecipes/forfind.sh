#!/bin/bash

for name in $( find -name *.ttl )
do
  rm $name
done
