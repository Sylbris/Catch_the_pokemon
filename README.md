![alt text](https://i.insider.com/5798d6b1dd08951e578b47e6?width=700&format=jpeg&auto=webp)

# Catch the Pokemon
[![Generic badge](https://img.shields.io/badge/build-passing-<COLOR>.svg)](https://shields.io/)
[![Generic badge](https://img.shields.io/badge/gson-2.8.6-yellow.svg)](https://shields.io/)
[![Generic badge](https://img.shields.io/badge/JavaDoc-Click-green.svg)](https://sylbris.github.io/Catch_the_pokemon/docs/index.html)
[![Maintenance](https://img.shields.io/badge/issues-2-red.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)

Catch the pokemon is an assignment given by Ariel university in OOP class.
The purpose of the assignment is to design an algorithmic solution to catch 
the maximum amount of Pokemons. More information about the game can be found in wiki pages.

# Description
The first part of the project is based on a directed weighted graph implementation,
I choose a Hashmap in order to store the nodes , the entries contain the node key and
and the values are the nodes themself.

To implement the edges i choose a Hashmap of Hashmaps, So Hashmap<Interger,Hashmap<Integer,edge>>
each entry indicates the source of the edge, and each edge has a Hashmap containing its neighbours.
![alt text](https://i.ibb.co/FVTPqF9/leonardo.jpg)

I choose a Hashmap since its cost efficient regarding insertion and deletion (O(1)).

The second part is a dynamic game in which i go extensively about in the Wikipages.
https://github.com/Sylbris/Catch_the_pokemon/wiki

# Methods
| DWGraph_DS  | Use | Run time | 
| ------------- | ------------- | --------|
| Copy Consructor | Deep copy  | O(n^2) | 
| reverse_graph()  | Reverses the graph  | O(n^2) | 
| get_node(int key)  | gets the node  | O(1) |
| getEdge(int src, int dest) | get the edge  | O(1) | 
| addNode(node_data n))  | add the node to the graph  | O(1) | 
| connect(int src, int dest, double w)  | Content Cell  | O(1) | 
| getV()  | Get a collection of all nodes  | O(1) | 
| getE(int node_id)  | Get a collection edges from node  | O(1) | 
| removeNode(int key)  | remove a node from the graph  | O(K) | 
| removeEdge(int src, int dest)  | remove an edge from the graph  | O(1) | 
| nodesize  | return the amount of nodes  | O(1) | 
| edgesize  | return the amount of edges  | O(1) | 
| getMC  | indicated the number of changes on the graph  | O(1) | 

| DWGraph_Algo  | Use | Run time | 
| ------------- | ------------- | --------|
| init | initiliaze the graph  | O(1) | 
| get_graph()  | Returns the graph  | O(1) | 
| copy()  | returns a deep copy of the graph | O(n^2) |
| bfs(directed_weighted_graph dwg) | performs one bfs on a given graph  | O(V+E) | 
| isConnected()  | check if the graph is strongly connected  | O(n^2) | 
| shortestPathDist(int src, int dest)  | returns the shortest path between given nodes.  | O(V+E) | 
| shortestPath(int src, int dest)  | returns a list of the shortest path  | O(V+E) | 
| save(String file)  | Saves the graph in json format  | O(V+E) | 
| load(String load)  | Load the graph in json format  | O(V+E) | 


| Ex2  | Use | Run time | 
| ------------- | ------------- | --------|
| entrance_pop_ip | choose level | O(1) | 
| run()  | core of the game  | O(1) | 
| moveAgants()  | goes over each agent and chooses a step. | O(num_of_agents*(V+E) |
| nextNode() | choose the next move for the agent.  | O(V+E) | 
| nextNode_Many_agents()  | choose next move if many agents are active  | O(V+E) | 
| calculate_edges_worth  | goes over the graph and calculates each edge worth and updates it.  | O(V+E) | 
| calculate_sub_graph(int amount)  | return a sub graph  | O(amount) | 
| calc_graph_value  | Calculate the total graph value by its edges  | O(V+E) | 
| Graph_maker  | Load the graph in json format  | O(V+E) | 
| init(game_service game)  | initiate the game  | -- | 


More information and implement methods can be found in Java docs :

https://sylbris.github.io/Catch_the_pokemon/docs/index.html

# Installation
Copy the repository , run Ex2

or

From the command line :

```java -jar Ex2.jar your_id level_num```

# Overview

This project uses varias graph algorithms such as Breadth first search and Dijkstra's algorithm.
More information can be found : 
https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

https://en.wikipedia.org/wiki/Breadth-first_search

https://en.wikipedia.org/wiki/Shortest_path_problem



# Test
There are a couple test files for any none trivial class.

# Common issues
There are currenctly few issues, please check the issues tab for more information.
