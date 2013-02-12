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
  semanticTypes = ['account', 'creditCard', 'person', 'e-mail']
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
  base = "http://linkeddata4.dia.fi.upm.es:8080/MatcherService/rest/matcher/matchingsSpace/"
  id = "matcherCallTest"
  printf(jsonCall('PUT', base+id))
  printf(jsonCall('GET', base+id))
  names = {'flow':'flow', 'semanticType':'semanticType', 'name':'id', 'syntacticType':'syntacticType'}
  for i in range(10):
    #data = {'ontologies': [], 'operation':'addition', 'fields':generateWidget(ug, names)}
    data = {'ontologies': [], 'type':'addition', 'fields':generateWidget(ug, names)}    
    printf(jsonCall('POST', base+id, data))
    printf(jsonCall('GET', base+id))
  #names = {'flow':'kind', 'semanticType':'semanticType', 'ID':'uri', 'syntacticType':'type'}
  #for i in range(10):
  #  data = {'ontologies': [], 'oldFields':[], 'newFields':generateWidget(ug, names)}
  #  printf(jsonCall('POST', base+id, data))
  #  printf(jsonCall('GET', base+id))
    
    
  
  
