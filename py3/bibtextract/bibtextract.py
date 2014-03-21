import sys, os, os.path, re, itertools

config = {}
exec(open("bibtextract.conf").read(), config)


class Reference():
  def __init__(self, text):
    self.text = [text]
    self.__citation = re.findall(config['citeinhead'], text)[0]
  def append(self, line):
    self.text.append(line)
  def __str__(self):
    return ''.join(self.text)
  @property
  def citation(self):
    return self.__citation
    

def referenceExtractor(filename):
  inside = False
  start = config['start']
  end = config['end']
  current = None
  with open(filename, 'r', encoding=config['encoding']) as f:
    for l in f:
      if inside:
        current.append(l)
        if re.search(end, l):
          inside = False
          yield current
      else:
        if re.search(start, l):
          current = Reference(l)
          inside = True

def getCitations():
  res = []
  for root, dirs, files in os.walk('.', topdown=False):
    for name in filter(lambda x: x[-3:] == 'tex', files):
      with open(os.path.join(root, name), 'r', encoding=config['encoding']) as f:
        for l in f:
          for c in config['citationcommands']:
            for r in re.findall(r"\\%s\{([^\}]*)\}"%c, l):
              res += r.split(',')
  return set(res)

def writeRefs(refs):
  with open(config['localbib'], 'w', encoding=config['encoding']) as f:
    for r in refs:
      f.write(str(r))
      f.write('\n')


if __name__ == "__main__":
  os.chdir(config['basedir'])
  
  citations = getCitations()
  references = list(filter(lambda x: x.citation in citations, referenceExtractor(config['localbib'])))
  citationsInReferences = list(map(lambda x: x.citation, references))
  pending = list(filter(lambda x: x not in citationsInReferences, citations))
  toadd = filter(lambda x: x.citation in pending, referenceExtractor(config['globalbib']))
  writeRefs(itertools.chain(references, toadd))
  
  
