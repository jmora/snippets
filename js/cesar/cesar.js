// JavaScript Document
var cesar = new (function () {
	var A = 'A'.charCodeAt(0);
	var a = 'a'.charCodeAt(0);
	var d = 'z'.charCodeAt(0) - a;
	
	var trans = function(v, s, n){
		if (v < s || v > s + d)
			return v;
		return (v + d - s + n) % d + s;
	};
	
	this.encode = function(s, n){
		n = n % d;
		var c = [];
		for (var i = 0; i < s.length; i++)
			c[i] = trans(trans(s.charCodeAt(i), a, n), A, n);
		return String.fromCharCode.apply(this, c);
	};
	
	this.decode = function(s, n){
		return this.encode(s, -n);
	};

})();

var getNSet = function(inverse){
	if (inverse)
		document.getElementById('original').value = cesar.decode(
			document.getElementById('encoded').value,
			document.getElementById('displacement').value);
	else
		document.getElementById('encoded').value = cesar.encode(
			document.getElementById('original').value,
			document.getElementById('displacement').value);
};

