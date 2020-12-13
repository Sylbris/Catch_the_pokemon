![alt text](https://i.insider.com/5798d6b1dd08951e578b47e6?width=700&format=jpeg&auto=webp)

# Catch the Pokemon
[![Generic badge](https://img.shields.io/badge/build-passing-<COLOR>.svg)](https://shields.io/)
[![Maintenance](https://img.shields.io/badge/issues-2-red.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)

Catch the pokemon is an assignment given by Ariel university in OOP class.
The purpose of the assignment is to design an algorithmic solution to catch 
the maximum amount of Pokemons. More information about the game can be found in wiki pages.

# Desciption
The first part of the project is based on a directed weighted graph implementation,
I choose a Hashmap in order to store the nodes , the entries contain the node key and
and the values are the nodes themself.

To implement the edges i choose a Hashmap of Hashmaps, So Hashmap<Interger,Hashmap<Integer,edge>>
each entry indicates the source of the edge, and each edge has a Hashmap containing its neighbours.
![alt text](https://ibb.co/xHT48Ny)

I choose a Hashmap since its cost efficient regarding insertion and deletion (O(1)).

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

# Installation

# Overview

# Test
