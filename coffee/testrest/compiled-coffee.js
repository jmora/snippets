var calln, data1, data2, errgen, jqXHRprinter, jsonCall, printf, succgen, url;

calln = 0;

url = 'http://linkeddata4.dia.fi.upm.es:8080/MatcherService/rest/semanticmatcher/matchingsSpace/matcherCallTest';

printf = function(s, n) {
  return ($('#jc' + n)).append("<p>" + s + "</p>");
};

jqXHRprinter = function(jqXHR, n) {
  printf(jqXHR.statusText + ' ' + jqXHR.status, n);
  return printf(jqXHR.responseText, n);
};

succgen = function(n) {
  return function(a, b, c) {
    return jqXHRprinter(c, n);
  };
};

errgen = function(n) {
  return function(a, b, c) {
    return jqXHRprinter(a, n);
  };
};

/* it's all about this, lots of data in the ajax call
*/


jsonCall = function(verb, address, params) {
  if (params == null) {
    params = [];
  }
  calln += 1;
  ($('#results')).append('<div id="jc' + calln + '"><h1 class="call"> Call #' + calln + '</h1></div>');
  printf('verb: ' + verb + '<br/>url: ' + address + '<br/>params: ' + params, calln);
  return $.ajax({
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
    },
    url: address,
    type: verb,
    data: params,
    dataType: 'json',
    success: succgen(calln),
    error: errgen(calln)
  });
};

data1 = {
  fields: [],
  type: 'addition',
  ontologies: ['https://raw.github.com/jmora/snippets/master/java/relationCheck/resources/flights.owl']
};

data2 = {
  fields: [
    {
      semanticType: 'CompraVueloEnDolares',
      syntacticType: 'text',
      flow: 'input',
      id: 'dq'
    }, {
      semanticType: 'CompraVuelo',
      syntacticType: 'text',
      flow: 'output',
      id: 'dr'
    }, {
      semanticType: 'CompraVuelo',
      syntacticType: 'text',
      flow: 'input',
      id: 'ds'
    }, {
      semanticType: 'CompraVuelo',
      syntacticType: 'text',
      flow: 'input',
      id: 'dt'
    }
  ],
  type: 'addition',
  ontologies: []
};

jsonCall("PUT", 'http://barajas.dia.fi.upm.es/wares/testserv/');

jsonCall("POST", 'http://barajas.dia.fi.upm.es/wares/testserv/', data1);

jsonCall("POST", 'http://barajas.dia.fi.upm.es/wares/testserv/', data2);

jsonCall("GET", 'http://barajas.dia.fi.upm.es/wares/testserv/');

jsonCall("DELETE", 'http://barajas.dia.fi.upm.es/wares/testserv/');