// No specific order. Search for //.

// Pink links
javascript:(function(){
  var l = document.getElementsByTagName('a');
  for (var i = 0; i < l.length; i++){
    l[i].setAttribute("style", "color: #FF00FF");
  };
})()

// Selection to fixed width:
javascript:(function(){
  var selection = window.getSelection().getRangeAt(0);
  var selectedText = selection.extractContents();
  var font = document.createElement('font');
  font.face = "Consolas, 'Liberation Mono', monospace, Courier";
  font.appendChild(selectedText);
  selection.insertNode(font);
}())

// Nice <pre>:
javascript:(function(){
  var sheet = document.styleSheets[document.styleSheets.length - 1];
  sheet.insertRule("pre {tab-size: 2; font-family: Consolas, 'Liberation Mono', monospace, Courier}", sheet.cssRules.length);
}())

// Selection to pre
javascript:(function(){
  var selection = window.getSelection().getRangeAt(0);
  var selectedText = selection.extractContents();
  var pre = document.createElement('pre');
  pre.appendChild(selectedText);
  selection.insertNode(pre);
}())