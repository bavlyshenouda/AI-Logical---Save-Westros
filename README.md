By: 
Bavly Shenoua : bavlyshenouda
Islam Osama : IElgohary
Hady Yasser : hady1100


Problem Discussion:
The problem consists of an n*m grid that is generated randomly. This grid describes a world where the agent, namely Jon Snow, is trying to kill all the enemies, namely White Walkers, present in the grid. The grid also contains obstacles that blocks some cells preventing Jon SNow from passing through them. In order for the White Walkers to die, Jon Snow needs to have in his possession Dragon Glass, which is the weapon used to kill White Walkers. Dragon Glass is present at one of the cells in the grid, and the maximum number of Dragon Glass that Jon can carry is also randomly assigned.

For the agent Jon Snow to reach the goal, he needs to navigate through the grid without stepping on any obstacles, find and pick Dragon Glass, and kill all the White Walkers. These sets of actions represent the actions allowed for the Jon Snow to do at any point in time, except that each of these action has constraints that must be true in order for the action to be true, these constraints will be discussed later.

To be able to express the problem in First Order Logic, a set of predicates needs to be introduced to describe the world. In addition, a list of actions needs to present along with the necessary checks that this move can b applied at that point in time. Furthermore, a set of successor state axioms needs to be defined in order to keep track of the changes that happens to the world after applying each of the actions.

GenGrid Modification:
GenGrid is the Java file that is used to generate the random grid that represents the world. In our implementation of the project we limit the size of the grid to 4 by 4 at most, with 2 white walkers at most. This is not due to a certain limitation of the implementation but rather to limit the run time of the search algorithm. Given enough time and computing power this constraints can be lifted. After randomizing the grid, the data randomized is written in a ProLog file that represent the knowledge base. Hence, the generated grid needs to converted to a list of strings, each string represent a predicate that describes an aspect of the initial state of world. All those strings are to be written into a file with extension “.pl”.
wwLoc(x,y): This predicate indicate a location of a white walker, there is one for every white walker.
obstLoc(x,y): This predicate indicate a location of an obstacle, there is one for every obstacle. 
dgLoc(x,y): This predicate indicate the location of the dragon glass on the grid, only one dragon glass location is present, thus only one predicate.
jonLoc(x,y,s0): This predicate indicate the initial position of Jon Snow, which is also randomly generated. As the position change over time, the predicate needs to indicate a situation s0 to point the situation at which this predicate is valid.
maxDG(n): A predicate that states the maximum number of dragon glass that Jon Snow can carry every time it picks dragon glass from the dragon glass location. 
currentDG(n,s0): This predicate states the amount of dragon glass that initially in Jon Snow’s possession. As the number of dragon glass change every time Jon Snow kill or pick more dragon glass, it is important to indicate the situation at which this predicate is valid, initially s0.
gridSize(m,n): Since the size of the grid is also auto generated, this predicate represent the limit of the grid, m and n are constants and represent the walls of the world.

  
Syntax and Semantics of Action Terms:
The set of possible actions that are possible to the agent are represented as a set of predicates. Each has a check to make sure that this action is valid at any given situation.
Move: The agent can move in  directions, and for each of these directions a check is made to make sure that the new location of the agent will be within the grid limits, the new location contains no obstacles or white walkers, and the new location is one cell away from the current Jon Snow location. All these information is encoded within the situation, this to compute these checks, the agent needs to have situation that we are trying to check upon.
moveNorth(X, Y, A, S)
moveWest(X, Y, A, S)
moveEast(X, Y, A, S)
moveSouth(X, Y, A, S)
Collect/Kill: Both collect and kill has no dedicated predicated, these are only defined along with their conditions inside the successor state axioms. In general, for a kill to be possible, the agents needs to have enough dragon glass and to be adjacent to a white walker. For a Collect to be possible the agents needs to be adjacent to the dragon class location.

Successor-State Axioms:
Successor state axioms to update the situation dependent terms in knowledge base.
  
jonLoc(X, Y, result(A, S)):-
    move(X, Y, A, S);
    (jonLoc(X, Y, S),
        (( A = kill, currentDG(N,S), N>0);
        (A = collect, dgLoc(X, Y)))).
The above axiom represent the update to Jon Snow Location. The location change every time the agent applies a move. In case no move action is applied (i.e: the action is either kill or collect, given that either actions are possible at the previous situation) then the location of Jon Snow is the same as the previous situation.


currentDG(N, result(A, S)):-
    (A = kill, currentDG(M,S), M>0, N is M-1);
     A = collect, maxDG(N);
    currentDG(N, S) ,
      move(X, Y, A, S) .

The axiom represents the current number of Dragon Glass available with Jon Snow in each situation.
The number N is changed only if the action is kill or collect. If the action is kill, the number of dragon glass is decreased by 1. If the action is collect, the number of dragon glass is changed to the maximum value that Jon can carry.

deadWW(X, Y, result(A, S)):-
  wwLoc(X, Y),
  ((A = kill, jonLoc(X1, Y1, S), currentDG(N, S), N>0, adj(X, Y, X1, Y1)) ; deadWW(X, Y, S)).

This axiom specifies whether the white walker in location (x,y) is dead. First, there must be a white walker in location (x.y). If the action is kill, Jon Snow is adjacent to the cell (x,y) and Jon has at least 1 piece of dragon glass, then the white walker is dead. If the white walker was dead in the previous situation, it will be dead in the current situation too.


Generate The Plan:
To formulate a plan, we use the query goal(S). Which in turn try to unify S with all possible situation that can be reached from the initial situation and return one it can find a situation that can unify with S to satisfy the goal rules. For a situation to be a goal, it means that at this specific situation there are no more white walkers alive. To achieve this, Prolog tries applying all the combinations of actions in search of achieving the goal situation from the goal situation. After each action an update to the situation is applied using the successor state axioms.

Two Running Examples:

 1- 
wwLoc(0,0).
wwLoc(0,1).
obstLoc(0,2).
dgLoc(1,1).
obstLoc(2,0).
jonLoc(3,2,s0).
maxDG(3).
currentDG(0,s0).
gridSize(4,3).

2- 
wwLoc(0,2).
wwLoc(0,3).
obstLoc(1,0).
dgLoc(1,1).
jonLoc(1,3,s0).
maxDG(2).
currentDG(0,s0).
gridSize(2,4).




