package api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer,node_data> graph_nodes;
    private HashMap<Integer,HashMap<Integer,edge_data>> graph_edges; //keeps track of out going edges from node.
    private HashMap<Integer,HashMap<Integer,edge_data>> graph_in_edges; //keeps track of incoming edges to node.
    private int mode_count;
    private int edges;
    private int node_size;

    /**
     * Constructor
     */
    public DWGraph_DS(){
        graph_nodes=new HashMap<>();
        graph_edges=new HashMap<>();
        graph_in_edges=new HashMap<>();
        mode_count=0;
        edges=0;
    }

    /**
     * Implement a way to check if 2 graphs are equal
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        DWGraph_DS dwg = (DWGraph_DS) o;
        if (o == null || getClass() != o.getClass() || dwg.node_size!=this.node_size || dwg.edgeSize()!=this.edgeSize()) return false;
        for (node_data n : dwg.getV()) {
            if(!n.equals(graph_nodes.get(n.getKey())))
                return false;
            for(edge_data e: dwg.getE(n.getKey())){
                if(this.getEdge(n.getKey(),e.getDest())==null)
                    return false;
            }
        }

        return true;
    }

    /**
     * Temp, needs to be adjusted if we want to use hashing on our graphs.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(edges, node_size);
    }

    /**
     * Deep copy of the graph
     * @param g
     */
    public DWGraph_DS(directed_weighted_graph g){
        this.graph_nodes=new HashMap<>();
        this.graph_edges=new HashMap<>();
        this.graph_in_edges=new HashMap<>();

        for(node_data i:g.getV()){
            node_data n=new DWNode_DS(i);
            graph_nodes.put(n.getKey(),n);
            HashMap<Integer,edge_data> k=new HashMap<>();
            HashMap<Integer,edge_data> r=new HashMap<>();
            graph_edges.put(n.getKey(),k);
            graph_in_edges.put(n.getKey(),r);
        }
        for(node_data n:g.getV()){
            for(edge_data e:g.getE(n.getKey()))
            {
                this.connect(n.getKey(),e.getDest(),e.getWeight());
            }
        }

        edges=g.edgeSize();
        this.mode_count=g.getMC();
        this.node_size=g.nodeSize();
    }

    /**
     * Reverses the graph (naive swap)
     * This will be later used in graph algorithms.
     */
    public void reverse_graph(){
        HashMap<Integer,HashMap<Integer,edge_data>> temp=graph_edges;
        this.graph_edges=graph_in_edges;
        this.graph_in_edges=temp;
    }
    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if(graph_nodes.containsKey(key))
            return graph_nodes.get(key);
        else
            return null;
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(src!=dest && graph_edges.containsKey(src) && graph_edges.containsKey(dest) && graph_edges.get(src).containsKey(dest))
            return graph_edges.get(src).get(dest);
        else
            return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(n!=null && !graph_nodes.containsKey(n.getKey())) {
            graph_nodes.put(n.getKey(), n);

            HashMap<Integer,edge_data> k=new HashMap<>();
            HashMap<Integer,edge_data> r=new HashMap<>();

            graph_edges.put(n.getKey(),k);
            graph_in_edges.put(n.getKey(),r);
        }

    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(src!=dest && w>0 && !graph_edges.get(src).containsKey(dest) && graph_edges.containsKey(src) && graph_edges.containsKey(dest)) {
            edge_data e = new DWEdge_DS(src, dest, w); //Create a new edge.
            graph_edges.get(src).put(dest,e);//Select the node key->put it in the hashmap.
            graph_in_edges.get(dest).put(src,e);//put the same edge in the in edges hashmap.
            edges++;
        }


    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return graph_nodes.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return graph_edges.get(node_id).values();
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key) { //runs over all nodes in the graph so needs to be fixed.
        if(graph_nodes.containsKey(key) && graph_edges.containsKey(key) && graph_in_edges.containsKey(key)){
            int j=0;
            for(int i: graph_edges.get(key).keySet()){
                graph_in_edges.get(i).remove(key);
                j++;
            }
            for(int i:graph_in_edges.get(key).keySet()){
                graph_edges.get(i).remove(key);
                j++;
            }

            graph_nodes.remove(key);
            graph_edges.remove(key);
            graph_in_edges.remove(key);
            edges-=j;
        }
        return null;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {

        if(src!=dest && getEdge(src,dest)!=null && graph_edges.containsKey(src) && graph_in_edges.containsKey(dest)){ //we remove the edge from both hashmaps.
            edge_data e=graph_edges.get(src).get(dest); //the deleted edge to be erased.
            edges--; //we decrement the number of nodes in the graph.
            graph_edges.get(src).remove(dest); //HashMap<Integer,HashMap<Integer,edge_data>>
            graph_in_edges.get(dest).remove(src); //HashMap<Integer,HashMap<Integer,edge_data>>
            return e;
        }
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {

        node_size= graph_nodes.size();
        return node_size;
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return edges;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return mode_count;
    }
}
