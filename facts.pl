:- include('kb.pl').

isWithinGrid(X,Y):-
    gridSize(X1,Y1), X<X1 , Y<Y1, X>=0, Y>=0.

adj(X, Y, R, C):-
    ((X is R, Y is C-1); (X is R, Y is C+1); (X is R-1, Y is C); (X is R+1, Y is C)).

isGoal(S):-
    jonLoc(_,_,S),forall(wwLoc(X, Y), deadWW(X, Y, S)).

deadWW(X, Y, result(A, S)):-
  wwLoc(X, Y),
  ((A = kill, jonLoc(X1, Y1, S), currentDG(N, S), N>0, adj(X, Y, X1, Y1)) ; deadWW(X, Y, S)).

isValidMove(X, Y, S):-
  isWithinGrid(X, Y),\+obstLoc(X, Y),  (\+wwLoc(X, Y) ; deadWW(X, Y, S)), \+isGoal(S).

moveNorth(X, Y, A, S):-
  (A = north, jonLoc(Xold, Y, S), X is  Xold - 1,isValidMove(X, Y, S)).

moveWest(X, Y, A, S):-
  (A = west  , jonLoc(X, Yold, S), Y is  Yold - 1,isValidMove(X, Y, S)).

moveEast(X, Y, A, S):-
  (A = east, jonLoc(X, Yold, S), Y is Yold + 1,isValidMove(X, Y, S)).

moveSouth(X, Y, A, S):-
  (A = south, jonLoc(Xold, Y, S), X is Xold + 1,isValidMove(X, Y, S)).


move(X, Y, A, S):-
    moveNorth(X, Y, A, S);
    moveWest(X, Y, A, S);
    moveEast(X, Y, A, S);
    moveSouth(X, Y, A, S).

jonLoc(X, Y, result(A, S)):-
    move(X, Y, A, S);
    (jonLoc(X, Y, S),
        (( A = kill, currentDG(N,S), N>0);
        (A = collect, dgLoc(X, Y)))).


currentDG(N, result(A, S)):-
    (A = kill, currentDG(M,S), M>0, N is M-1);
     A = collect, maxDG(N);
    currentDG(N, S) ,
      move(X, Y, A, S) .
