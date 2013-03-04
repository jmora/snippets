#!/bin/bash

hanoiAux()
{
  if [ $1 -eq 1 ]
  then
    echo "move $1 from s to g"
  else
    hanoiAux $(( $1 - 1 )) > level$1
    cat <(sed -e 's/g/x/' -e 's/a/g/' -e 's/x/a/' < level$1) \
        <(echo "move $1 from s to g")                        \
        <(sed -e 's/s/x/' -e 's/a/s/' -e 's/x/a/' < level$1)
    rm level$1
  fi
}

hanoi(){
sed -e "s/a/$4/" -e "s/s/$2/" -e "s/g/$3/" <(hanoiAux $1)
}

hanoi 20 a c b
