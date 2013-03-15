#!/bin/bash

hanoiAux()
{
  if [ $1 -eq 1 ]
  then
    echo "m$1fstg"
  else
    hanoiAux $(( $1 - 1 )) > level$1
    sed -e 's/g/x/' -e 's/a/g/' -e 's/x/a/' < level$1
    echo "m$1fstg"
    sed -e 's/s/x/' -e 's/a/s/' -e 's/x/a/' < level$1
    rm level$1 
  fi
}

hanoi(){
sed -e "s/a/$4/" -e "s/s/$2/" -e "s/g/$3/" -e 's/t/ to /' -e 's/f/ from /' -e 's/m/move /' <(hanoiAux $1)
}

hanoi 25 a c b

