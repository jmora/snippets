calln = 0;
url = 'http://linkeddata4.dia.fi.upm.es:8080/MatcherService/rest/semanticmatcher/matchingsSpace/matcherCallTest'
printf = (s, n) -> ($ '#jc'+n).append("<p>" + s + "</p>")
jqXHRprinter = (jqXHR, n) ->
  printf (jqXHR.statusText + ' ' + jqXHR.status), n
  printf jqXHR.responseText, n
succgen = (n) -> (a,b,c) -> jqXHRprinter c, n
errgen = (n) -> (a,b,c) -> jqXHRprinter a, n

### it's all about this, lots of data in the ajax call ###
jsonCall = (verb, address, params = []) ->
  calln += 1
  ($ '#results').append '<div id="jc' + calln + '"><h1 class="call"> Call #'+ calln + '</h1></div>'
  printf 'verb: ' + verb + '<br/>url: ' + address + '<br/>params: ' + params, calln
  $.ajax
    headers:
      Accept: 'application/json'
      'Content-Type': 'application/json' 
    url: address
    type: verb
    data: params
    dataType: 'json'
    success: succgen calln
    error: errgen calln

# data, data everywhere

data1 = 
  fields: []
  type: 'addition'
  ontologies: ['https://raw.github.com/jmora/snippets/master/java/relationCheck/resources/flights.owl']
data2 =
  fields: [
    semanticType: 'CompraVueloEnDolares'
    syntacticType: 'text'
    flow: 'input'
    id: 'dq'
  ,
    semanticType: 'CompraVuelo'
    syntacticType: 'text'
    flow: 'output'
    id: 'dr'
  ,
    semanticType: 'CompraVuelo'
    syntacticType: 'text'
    flow: 'input'
    id: 'ds'
  ,
    semanticType: 'CompraVuelo'
    syntacticType: 'text'
    flow: 'input'
    id: 'dt'
  ]
  type: 'addition'
  ontologies: []
  
# here you write the calls that you want to try
jsonCall "PUT", 'http://barajas.dia.fi.upm.es/wares/testserv/'
jsonCall "POST", 'http://barajas.dia.fi.upm.es/wares/testserv/', data1
jsonCall "POST", 'http://barajas.dia.fi.upm.es/wares/testserv/', data2
jsonCall "GET", 'http://barajas.dia.fi.upm.es/wares/testserv/'
jsonCall "DELETE", 'http://barajas.dia.fi.upm.es/wares/testserv/'