#!/usr/bin/env python3
import sys,os,os.path,re

def processTree(filename, f):
  if os.path.isdir(filename):
    for e in (filename + os.sep + sname for sname in os.listdir(filename)):
      processTree(e, f)
  else:
    f(filename)

res = {}

def doit(filename):
  with open(filename, 'r') as f:
    for l in f:
      r = re.findall('\\cite\{([\w\d,]+)}', l)
      for e in (e for rs in r for e in rs.split(',')):
        res[e] = res.get(e, 0) + 1

if __name__ == '__main__':
  filename = sys.argv[1]
  processTree(filename, doit)
  nres = sorted(res.items(), key = lambda x: x[1])
  for e in nres:
    print(e)
