package api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;
/**
 * This class represent an implement of the dw_graph_algorithms interface.
 * dwg - the graph were going to operate on
 * node_map - a temporary node map to contain a copy of the graph nodes. Check shortestPathDist for more info.
 */
public class DWGraph_Algo implements dw_graph_algorithms{
    directed_weighted_graph dwg;
    HashMap<Integer,DWTNode_DS> node_map=new HashMap<>();
    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        dwg=g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return dwg;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(dwg);
    }

    /**
     * Preforms BFS algorithm on the graph.
     * https://en.wikipedia.org/wiki/Breadth-first_search
     * We mark our nodes as unvisited (color them in blue) , iterate over the nodes and color them in red .
     * We add the nodes to the que , repeat until the queue is empty.
     *
     * Runs in O( |V|) time complexity.
     * @return
     */
    public boolean bfs(directed_weighted_graph dwg){
        for (node_data n : dwg.getV()) { // set the distance to max for all nodes from the source node.
            n.setInfo("blue"); // lets mark all nodes as blue for unvisited.
        }
        Queue<node_data> queue = new LinkedList<>(); // we define a queue to save all nodes.
        queue.add(dwg.getV().iterator().next());//add the first none null node to the queue.
        int count=0;//we mark the amount of nodes visited.

        while (!queue.isEmpty()) { //bfs
            node_data curr = queue.poll(); //pull the 1st node from the queue.
            for (edge_data adjacent : dwg.getE(curr.getKey())) {//iterate all of currs neibours.
                if (dwg.getNode(adjacent.getDest()).getInfo()=="blue") {//if we haven't seen the neibour we put it in the queue.
                    dwg.getNode(adjacent.getDest()).setInfo("red");
                    count++;
                    queue.add(dwg.getNode(adjacent.getDest()));
                }

            }

        }

        if(count==dwg.nodeSize())
            return true;
        return false;
    }

    /**
     *
     * @param dwg
     * @param amount_of_nodes
     * @return
     */
    public ArrayList<node_data> bfs_n_nodes(directed_weighted_graph dwg, int amount_of_nodes, int src_node){
        if(dwg.getNode(src_node)==null) {
            return null;
        }

        for (node_data n : dwg.getV()) { // set the distance to max for all nodes from the source node.
            n.setInfo("blue"); // lets mark all nodes as blue for unvisited.
        }

        Queue<node_data> queue = new LinkedList<>(); // we define a queue to save all nodes.
        queue.add(dwg.getNode(src_node));//add the first none null node to the queue.
        int count=0;//we mark the amount of nodes visited.
        ArrayList <node_data>awl=new ArrayList<>();
            while (count<amount_of_nodes ) { //bfs

                node_data curr = queue.poll(); //pull the 1st node from the queue.

                for (edge_data adjacent : dwg.getE(curr.getKey())) {//iterate all of currs neibours.

                    if (dwg.getNode(adjacent.getDest()).getInfo() == "blue") {//if we haven't seen the neibour we put it in the queue.
                        dwg.getNode(adjacent.getDest()).setInfo("red");
                        count++;
                        queue.add(dwg.getNode(adjacent.getDest()));
                        awl.add(dwg.getNode(adjacent.getDest()));

                    }


                }
                //count++;

            }
            return awl;



    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * We preform bfs once , we revert the graph and we do it again.
     * If both results are true , then our graph is strongly connected.
     * More information can be found : https://www.geeksforgeeks.org/check-given-directed-graph-strongly-connected-set-2-kosaraju-using-bfs/
     *
     * Time Complexity : Runs in O( |V|) time complexity.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (dwg.nodeSize() == 0 || dwg.nodeSize() == 1) // if our graph contains 0/1 nodes its connected.
            return true;

        if(!bfs(dwg)){ //Means graph is not even connected.
            return false;
        }
        DWGraph_DS dwg_copy= new DWGraph_DS(dwg);
        dwg_copy.reverse_graph(dwg);
        if(bfs(dwg_copy)){

            return true;
        }
        return false;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * We copy all the graph nodes into DWTNode_DS nodes and create a priority queue.
     * The distance of each node is already set to infinity (Check DWTNode) , We mark all the nodes "blue"
     * for unvisited. Iterate the graph and check each time for the node with the minimum distance to the "src" node.
     * Using our priority queue we can always pick the node with minimum distance.
     * We then update the nodes distance from the "src" node and insert it to the priority queue, and we update its parent.
     * All is left is choose the targets node distance and return it.
     *
     * We use Dijkstra's algorithm here:
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     *
     * This method runs in O(|V|+|E|)
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (dwg.getNode(src) == null || dwg.getNode(dest) == null) // if our graph can't reach either of them.
            return -1;

        if (src == dest)// in case we get the same node.
            return 0;

        PriorityQueue<DWTNode_DS> queue = new PriorityQueue<>(11, new Comparator<DWTNode_DS>() {//we define a comparator.
            @Override
            public int compare(DWTNode_DS o1, DWTNode_DS o2) {
                if(o1.getDistance()<o2.getDistance())
                    return -1;
                if(o1.getDistance()>o2.getDistance())
                    return 1;
                else
                    return 0;
            }
        });


        for (node_data n : dwg.getV()) {// set the distance to max for all nodes from the source node.
            node_map.put(n.getKey(),new DWTNode_DS(n));
            n.setInfo("blue"); // lets mark all nodes as blue for unvisited.
        }

        node_map.get(src).setDistance(0);
        node_map.get(src).setParent(-1);

        queue.add(node_map.get(src));//we add the src node to the queue.

        while (!queue.isEmpty()) {

            DWTNode_DS curr = queue.remove();//pull the first from the queue.
            dwg.getNode(curr.getKey()).setInfo("red");

            for (edge_data adjacent : dwg.getE(curr.getKey())) {//iterate all of currs neibours.

                if (dwg.getNode(adjacent.getDest()).getInfo() == "blue") { // lets check all unvisited children

                    if(node_map.get(adjacent.getDest()).getDistance()>(curr.getDistance() + dwg.getEdge(adjacent.getSrc(), adjacent.getDest()).getWeight())) {//if new distance is smaller, update the it.
                        node_map.get(adjacent.getDest()).setDistance(curr.getDistance() + dwg.getEdge(adjacent.getSrc(), adjacent.getDest()).getWeight());//set the tag of the node to be the previous Weight + new Weight.
                        queue.add(node_map.get(adjacent.getDest()));

                        node_map.get(adjacent.getDest()).setParent(curr.getKey());

                    }


                }
            }
        }
        if(node_map.get(dest).getDistance()==Integer.MAX_VALUE){
            return -1;
        }
        return node_map.get(dest).getDistance(); //if we haven't found it return -1.
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * The algorithm performs shortestPathDist between the given nodes. Each node has a parent attribute.
     * We now iterate over the parent node of the dest node until we get to our source node, each step we add the node
     * to the list. All is left is to reverse and the list and return it.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if(dwg.getNode(src)==null || dwg.getNode(dest)==null)//if either of them doesn't exist we return null.
            return null;

        if(shortestPathDist(src,dest)==-1)
            return null;

       List<node_data> nodelist=new LinkedList<>();

        if(src==dest) {
            nodelist.add(dwg.getNode(src));
            return nodelist;
        }
       nodelist.add(dwg.getNode(dest));
        DWTNode_DS curr=node_map.get(dest);

        while(curr.getParent()!=-1){
            nodelist.add(dwg.getNode(curr.getParent()));
            curr=node_map.get(curr.getParent());
            }

        Collections.reverse(nodelist);

       return nodelist;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * Uses Json custom serializer to save the graph.
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DWGraph_DS.class, new DWGraph_Json_Serializer())
                .setPrettyPrinting()
                .create();

        String json=gson.toJson(dwg);

        try {
            FileWriter file_name = new FileWriter("./"+file+ ".json");
            file_name.write(json);
            file_name.flush();
            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * This methods uses DWGraph_Json_Deserializer to parse the json into a graph.
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) { //Needs some working.
        try {

            GsonBuilder gson=new GsonBuilder();
            gson.registerTypeAdapter(directed_weighted_graph.class,new DWGraph_Json_Deserializer());
            Gson customGson=gson.create();

            FileReader reader=new FileReader("./"+file+".json");
            directed_weighted_graph dwg_reloaded=customGson.fromJson(reader,directed_weighted_graph.class);

            dwg=dwg_reloaded;
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }
}
