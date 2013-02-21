from itertools import chain

def hanio(d,s,g,a):
  for x in (chain(hanio(d-1,s,a,g), [(d,s,g)], hanio(d-1,a,g,s)) if d else []): yield x
  
hanoi = lambda x: [print('move %d from %s to %s'%e) for e in hanio(x,'a','c','b')]

hanoi(54)
