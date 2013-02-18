from string import Template

def swap(x):
  return Template(x.substitute({'s':'$a', 'a':'$s', 'g':'$g'}))
def switch(x):
  return Template(x.substitute({'s':'$s', 'a':'$g', 'g':'$a'}))

def hanoi(n):
  if (n == 1):
    return [('move 1 from %s to %s', Template('$s$g$a'))]
  else:
    m1th = hanoi(n-1)
    t = [('move '+ str(n) +' from %s to %s', Template('$s$g$a'))]
    return [(x, switch(y)) for (x,y) in m1th] + t + [(x, swap(y)) for (x,y) in m1th]

def hanoiCall(n):
  normalNames = {'s':'a', 'a':'b', 'g':'c'}
  for (s,c) in hanoi(n):
    print(s%tuple((c.substitute(normalNames))[:-1]))

if __name__ == '__main__':
  hanoiCall(2)
  print()
  hanoiCall(3)

