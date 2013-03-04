#!/bin/bash

hanoi()
{
  if [ $1 -gt 0 ]
  then
    hanoi $(( $1 - 1 )) $2 $4 $3;
    echo "move $1 from $2 to $3";
    hanoi $(($1 - 1 )) $4 $3 $2;
  fi
}

hanoi 20 a c b
