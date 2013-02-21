hanoi = lambda d,s,g,a: d and (hanoi(d-1,s,a,g) or print('move %d from %s to %s'%(d,s,g)) or hanoi(d-1,a,g,s))

hanoi(300,'a','c','b')
