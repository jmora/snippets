from jsonCall import jsonCall
import random as rd

def printf(e):
  print(e[0])
  print(e[1])
  print('\n')

def uriGenerator():
  count = 0
  d = ord('z') - ord('a') + 1
  while True:
    yield chr(ord('a') + count // d) + chr(ord('a') + count % d)
    count += 1
 
def generateWidget(ug, names):
  semanticTypes = ['Compra', 'CompraEnEuros', 'CompraEnDolares', 'CompraVuelo', 'CompraVueloEnDolares', 'CompraVueloEnEuros']
  types = ['input', 'output']
  widget = []
  for i in range(rd.randint(3,7)):
    field = {}
    field[names['syntacticType']] = 'text'
    field[names['semanticType']] = rd.choice(semanticTypes)
    field[names['flow']] = rd.choice(types)
    field[names['name']] = next(ug)
    widget.append(field)
  return widget
 
if __name__ == "__main__":
  ug = uriGenerator()
  base = "http://linkeddata4.dia.fi.upm.es:8080/MatcherService/rest/semanticmatcher/matchingsSpace/"
  id = "matcherCallTest"
  printf(jsonCall('DELETE', base+id))
  printf(jsonCall('PUT', base+id))
  printf(jsonCall('GET', base+id))
  names = {'flow':'flow', 'semanticType':'semanticType', 'name':'id', 'syntacticType':'syntacticType'}
  posts = []
  gets = []
  # This should NOT be this way:
  printf(jsonCall('POST', base+id, data = {'ontologies': ['https://raw.github.com/jmora/snippets/master/java/relationCheck/resources/flights.owl'], 'type':'addition', 'fields':[]}))
  for i in range(20):
    #data = {'ontologies': [], 'operation':'addition', 'fields':generateWidget(ug, names)}
    data = {'ontologies': [], 'type':'addition', 'fields':generateWidget(ug, names)}    
    posts.append(jsonCall('POST', base+id, data))
    gets.append(jsonCall('GET', base+id))
  pg = None
  for i in range(len(posts)):
    #printf(posts[i])
    #printf(gets[i])
    if i and len(gets[i][1]['matchings']) == len(gets[i-1][1]['matchings']):
      print('The service is not fast enough in the updates, queueing petitions may be a good idea. \n\n\n\n\n\n\n\n')
