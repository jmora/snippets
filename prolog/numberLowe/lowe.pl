in(X, [X|Xs], Xs).
in(Y, [X|Xs], [X|Ys]):- in(Y, Xs, Ys).

% suma = combinaciones (conjuntos)
%add([], [], []).
%add([], [Y|Ys], Zy):- in(Y, Zy, Z), add([], Ys, Z).
%add([X|Xs], Y, Zx):- in(X, Zx, Z), add(Xs, Y, Z).

% suma = concatenar (listas)
add([], [], []).
add([], [X|Xs], [X|Xs]).
add([X|Xs], [], [X|Xs]).
add([X|Xs], [Y|Ys], [X,Y|Z]):- add(Xs, Ys, Z).

% sumatorio = aplanar
sum([], []).
sum([X|Xs], S):- add(X, Ss, S), sum(Xs, Ss).

% equal = misma longitud
equal([], []).
equal([_|Xs], [_|Ys]):- equal(Xs, Ys).

% less than = shorter
lessThan([], [_|_]).
lessThan([_|Xs], [_|Ys]):- lessThan(Xs, Ys).

allEqual([]).
allEqual([_]).
allEqual([X1, X2|Xs]):- equal(X1, X2), allEqual([X2|Xs]).

divide(E, P, C, R, S):- ground(P), ground(C), equal(S, P), lessThan(R, P), allEqual([C|S]), sum([R|S], E).
divide(E, P, C, R, S):- ground(P), ground(E), equal(S, P), lessThan(R, P), sum([R|S], E), allEqual([C|S]).
