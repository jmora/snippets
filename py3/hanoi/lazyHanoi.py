from string import Template
import itertools as it 

def swap(x):
  return Template(x.substitute({'s':'$a', 'a':'$s', 'g':'$g'}))
def switch(x):
  return Template(x.substitute({'s':'$s', 'a':'$g', 'g':'$a'}))

def hanoi(n):
  if (n == 1):
    return [('move 1 from %s to %s', Template('$s$g$a'))]
  else:
    m1tha, m1thb = it.tee(hanoi(n-1))
    return it.chain(map(lambda x: (x[0], switch(x[1])), m1tha),
                    [('move '+ str(n) +' from %s to %s', Template('$s$g$a'))],
                    map(lambda x: (x[0], swap(x[1])), m1thb))

def hanoiCall(n):
  normalNames = {'s':'a', 'a':'b', 'g':'c'}
  for (s,c) in hanoi(n):
    print(s%tuple((c.substitute(normalNames))[:-1]))

if __name__ == '__main__':
  hanoiCall(2)
  print()
  hanoiCall(3)

