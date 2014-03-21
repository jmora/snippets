import sys, os, os.path, re, itertools

config = {}
exec(open("quicktikz.conf").read(), config)
codes = []

class Node():
  def __init__(self, text):
    self.text = text.split(':')[-1].strip()
    self.code = self.makeCode(self.text)
    self.type = config['types'].get(text.split(':')[0], 'block')
    
  def makeCode(self, name):
    r = ''.join(name.lower().split())
    subindex = ''
    i = 1
    while r + subindex in codes:
      i += 1
      subindex = str(i)
    r += subindex
    codes.append(r)
    return r

def getPosition(previous, first):
  if previous is None:
    return ''
  orientation = 'below' if first else 'right'
  #orientation = 'below left' if first else 'below right'
  return '%s=of %s'%(orientation, previous.code)

def makeDefinitions(nodes):
  t = '\\node [{type}{position}] ({code}) {{{text}}};\n'
  previous = None
  for line in nodes:
    first = True
    for n in line:
      position = getPosition(previous, first)
      position = ', '+position if position else position
      yield t.format(position=position, code=n.code, text=n.text, type=n.type)
      previous = n
      first = False
    previous = line[0]
  
def makeConnections(nodes):
  t =  '\\path [line] ({code1}) -- ({code2});\n'
  flatnodes = [n for line in nodes for n in line]
  for i in range(len(flatnodes)-1):
    yield t.format(code1=flatnodes[i].code, code2=flatnodes[i+1].code)
    
if __name__ == "__main__":
  lines = config['lines'].split('\n')
  unique = []
  nodes = [[Node(s) for s in l.split(',')] for l in lines]
  with open(config['output'], 'w') as output:
    for l in makeDefinitions(nodes):
      output.write(l)
    for l in makeConnections(nodes):
      output.write(l)  

