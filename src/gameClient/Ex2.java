package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import api.*;

import javax.swing.*;

public class Ex2 implements Runnable{
    private static GUI_Frame _win;
    private static Arena _ar;
    private static int arg=0;
    private static long dt=100;
    private static HashMap<Integer,directed_weighted_graph> agents_graph;
    private static directed_weighted_graph dwg_reloaded;
    private static dw_graph_algorithms dwg_algo;
    private static int scenario_num=23;
    private static long id=0;
    private static String [] levels;
    private static JList list;


    public static void main(String[] a) {

        if(a.length>0){
            id=Long.parseLong(a[0]);
            scenario_num=Integer.parseInt(a[1]);
        }
        else { entrance_pop_up(); }
        Thread client = new Thread(new Ex2());
        client.start();



    }
    public static void entrance_pop_up(){
        levels=new String[24];
        for(int i=0;i<24;i++){
            levels[i]=Integer.toString(i);
        }
        String ID_l="ID";
        String Choose_level="Choose a level";
        Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");
        id=Long.parseLong(JOptionPane.showInputDialog(null,ID_l));
        scenario_num=JOptionPane.showOptionDialog(null,Choose_level,Choose_level,JOptionPane.PLAIN_MESSAGE,1,errorIcon,levels,0);

    }


    @Override
    public void run() {



                game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
                	//int id = 305475915;
                	//game.login(id);
                init(game);
                game.startGame();
                _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
                int ind = 0;
                while (game.isRunning()) {

                    try {
                        if (ind % 1 == 0) {
                            _win.repaint();
                            moveAgants(game, dwg_reloaded);
                        }
                        Thread.sleep(dt);
                        ind++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String res = game.toString();
                try {
                    FileWriter file_name = new FileWriter("./" + scenario_num + ".txt");
                    file_name.write(res);
                    file_name.flush();
                } catch (IOException ex) {
                    System.out.println("Couldn't save");
                }
                System.out.println(res);



           game.stopGame();

    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String info=game.toString();
        _ar.setMoves(Arena.game_service_to_moves(info));

        String lg = game.move();
        List<CL_Agent> agents = Arena.getAgents(lg,gg);
        _ar.setAgents(agents);

        String fs =  game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs); //get list of pokemons
        _ar.setPokemons(ffs); //add pokemons to arena.

        for(CL_Agent ag:agents) { //go over all agents
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();

            if(dest==-1) {
                if(calc_graph_value(agents_graph.get(ag.getID()),ffs)<10) {
                    dest = nextNode(gg, src, ag.getID(), agents.size());
                    game.chooseNextEdge(ag.getID(), dest);
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
                }
                else {
                    dest = nextNode_Many_agents(agents_graph.get(ag.getID()), src, ag.getID(), agents.size());
                    game.chooseNextEdge(ag.getID(), dest);
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
                }

            }



        }

    }
    /**
     *  A simple strategy, calculate the maximum worth of each edge,
     *  The first agent goes to the biggest, the second agent to the second biggest,
     *  and so on.
     *
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src, int agent_id, int num_of_agents) {
        int ans = -1;
        dw_graph_algorithms dwg_algo=new DWGraph_Algo();
        dwg_algo.init(g);

        List<DWTEdge_Data> edges_improved=calculate_edges_worth(_ar.getPokemons(),g,src);
        Comparator<DWTEdge_Data> DWEdge_Data_Comparator=((x, y) -> Double.compare(y.getRatio(), x.getRatio()));
        edges_improved.sort(DWEdge_Data_Comparator);

        List<node_data> shortest_path=null;
        DWTEdge_Data e=new DWTEdge_Data();

        shortest_path = dwg_algo.shortestPath(src, edges_improved.get(agent_id).getDest());

            if(src==edges_improved.get(agent_id).getDest())
                return ans=edges_improved.get(agent_id).getSrc();

           // shortest_path=dwg_algo.shortestPath(src,e.getDest());

        Iterator <edge_data> it=g.getE(src).iterator();

        if(shortest_path==null){
            return it.next().getDest();
        }

        if(shortest_path.size()==2){
            dt=50;
        }
        else{
            dt=120;
        }

        ans=shortest_path.get(1).getKey();//second organ in list.
        return ans;

    }

    /**
     * A more sophisticated strategy, once applied we divide the graph into sub graphs,
     * each agent is given its own graph to monitor.
     *
     * @param g
     * @param src
     * @param agent_id
     * @param num_of_agents
     * @return
     */
    private static int nextNode_Many_agents(directed_weighted_graph g, int src, int agent_id, int num_of_agents) {
        int ans = -1;
        dw_graph_algorithms dwg_algo_local = new DWGraph_Algo();
        dwg_algo_local.init(g);

        //get a list of pokemons

        List<DWTEdge_Data> edges_improved=calculate_edges_worth(_ar.getPokemons(),g,src);
        Comparator<DWTEdge_Data> DWEdge_Data_Comparator=((x, y) -> Double.compare(y.getRatio(), x.getRatio()));
        edges_improved.sort(DWEdge_Data_Comparator);
        List<node_data> shortest_path = null;

        shortest_path = dwg_algo.shortestPath(src, edges_improved.get(0).getDest());
        Iterator <edge_data> it;

        if(shortest_path==null){
            shortest_path = dwg_algo.shortestPath(src, edges_improved.get(0).getDest());
            //if(g.getE(src)!=null) {
            //    it = g.getE(src).iterator();
            //    return it.next().getDest();
           // }
           // else {
            //   it= _ar.getGraph().getE(src).iterator();
            //   return it.next().getDest();
            //}
        }
        if(shortest_path.size()==2){
            dt=60;

        }
        else{
            dt=125;
        }
        if(src==edges_improved.get(0).getDest())
            return ans=edges_improved.get(0).getSrc();


        ans=shortest_path.get(1).getKey();//second organ in list.
        return ans;

    }

    /**
     * Calculate the and return a list of all edges s.t each edge has a value based
     * on the pokemon's on it.
     *
     * @param pokedex
     * @param g
     * @param src
     * @return
     */
    public static List<DWTEdge_Data> calculate_edges_worth(List<CL_Pokemon> pokedex, directed_weighted_graph g,int src){
        List<DWTEdge_Data> edges_improved = new ArrayList<>();
        dw_graph_algorithms dwg_algo = new DWGraph_Algo();
        dwg_algo.init(g);

        for (CL_Pokemon cl_p : pokedex) { //add all edges to the list
                if(g.getEdge(cl_p.get_edge().getSrc(), cl_p.get_edge().getDest())!=null) {
                    g.getEdge(cl_p.get_edge().getSrc(), cl_p.get_edge().getDest()).setTag(g.getEdge(cl_p.get_edge().getSrc(), cl_p.get_edge().getDest()).getTag() + (int) cl_p.getValue());
                    DWTEdge_Data temp_node = new DWTEdge_Data(g.getEdge(cl_p.get_edge().getSrc(), cl_p.get_edge().getDest()));
                    double shortest_path = dwg_algo.shortestPathDist(src, temp_node.getDest());
                    temp_node.setRatio(shortest_path / temp_node.getValue());
                    edges_improved.add(temp_node);
                }

        }
        return edges_improved;
    }

    /**
     *  Creates a sub graph of the original graph.
     * @param dwg
     * @param src
     * @param amount
     * @return
     */
    public static  directed_weighted_graph calculate_sub_graph(directed_weighted_graph dwg,int src,int amount){

        dw_graph_algorithms dwg_algo = new DWGraph_Algo();
        dwg_algo.init(dwg);
        DWGraph_Algo dwg2=(DWGraph_Algo)dwg_algo;
        ArrayList <node_data> my_list= dwg2.bfs_n_nodes(dwg,amount,src);
        directed_weighted_graph copy=new DWGraph_DS(dwg);

        for(node_data n:dwg.getV()){

            if(!my_list.contains(copy.getNode(n.getKey())))
                copy.removeNode(n.getKey());
        }
        return copy;
    }

    /**
     * Calculates the value of the given graph, this is used
     * to calculate the value of the sub graph each agent is given.
     * If the value of the sub graph is equal to 0 we change our strategy.
     *
     * @param dwg
     * @param pokedex
     * @return
     */
    public static double calc_graph_value(directed_weighted_graph dwg,List<CL_Pokemon> pokedex){
        if(dwg==null)
            return 0;
        double graph_value=0;
        pokedex=_ar.getPokemons();
        for(CL_Pokemon clp:pokedex){
            if(dwg.getEdge(clp.get_edge().getSrc(),clp.get_edge().getDest())!=null){
                graph_value+=clp.getValue();
            }
        }
        return graph_value;
    }

    /**
     * A simple program that converts a string to graph using a Json builder.
     * @param graph
     * @return
     */
    public directed_weighted_graph Graph_maker(String graph){

        GsonBuilder gson=new GsonBuilder();
        gson.registerTypeAdapter(directed_weighted_graph.class,new DWGraph_Json_Deserializer());
        Gson customGson=gson.create();
        directed_weighted_graph dwg_reloaded=customGson.fromJson(graph,directed_weighted_graph.class);
        return dwg_reloaded;

    }
    /**
     * Build the game based on the given game data.
     * Will take all our json data from the server and parse it.
     * Including positioning our agents.
     * @param game
     */
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        dwg_reloaded=Graph_maker(g);
        dwg_algo = new DWGraph_Algo();
        dwg_algo.init(dwg_reloaded);

        list=new JList(levels);

        //Setting our game
        _ar = new Arena(); //builds the arena of the graph.
        _ar.setGame(game);
        _ar.setGraph(dwg_reloaded);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win=new GUI_Frame(_ar);
        String info = game.toString();
        _ar.setLevel(Arena.json2Level(info));
        JSONObject line;
        agents_graph=new HashMap<>();

        try {
            line = new JSONObject(info);

            JSONObject game_json = line.getJSONObject("GameServer");

            int num_of_agents = game_json.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());

            //int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());

            for(int a = 0;a<cl_fs.size();a++) {
                //Arena.updateEdge(cl_fs.get(a),gg);
                Arena.updateEdge(cl_fs.get(a),dwg_reloaded);
            }

            PriorityQueue<CL_Pokemon> p_queue_pokemon=new PriorityQueue<>((x, y) -> Double.compare(y.getValue(), x.getValue())); //Max queue

            for(CL_Pokemon cl_p:cl_fs){ //add all pokemons to priority max queue.
                p_queue_pokemon.add(cl_p);
            }

            for(int a = 0;a<num_of_agents && !p_queue_pokemon.isEmpty();a++) {//Adds all agents

                CL_Pokemon clp=p_queue_pokemon.poll();
                int agent_src_node=clp.get_edge().getSrc();
                game.addAgent(agent_src_node);
                directed_weighted_graph an_agent_graph=calculate_sub_graph(dwg_reloaded,a*(dwg_reloaded.nodeSize()/(num_of_agents)), (dwg_reloaded.nodeSize()/(num_of_agents+1))+4);
                agents_graph.put(a,an_agent_graph);

            }

        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
