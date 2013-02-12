import json as js
import httplib

def jsonCall(verb, url, data=None):
  print ("%s on %s with %s"%(verb, url, data))
  if(url[:7] == 'http://'):
    url = url[7:]
    safe = False
  elif (url[:8] == 'https://'):
    url = url[8:]
    safe = True
  else:
    print('I only handle urls that start with http or https in this snippet, sorry.')
    return
  headers = {'Content-Type': 'application/json'}
  (baseurl, extendedurl) = url.split('/', 1)
  extendedurl = '/' + extendedurl
  conn = httplib.HTTPConnection(baseurl) if not safe else httplib.HTTPSConnection(baseurl)
  if (data is None):
    conn.request(verb, extendedurl, headers=headers)
  else:
    conn.request(verb, extendedurl, js.dumps(data), headers)
  resp = conn.getresponse()
  if (resp.status != 200):
    return ((resp.status, "Error %d: %s\n%s"%(resp.status, resp.reason, bytes.decode(resp.read()))))
  ret = bytes.decode(resp.read())
  if (len(ret) == 0):
    ret = "null"
  return (resp.status, js.loads(ret))
