package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import api.*;

public class Ex2 implements Runnable{
    private static GUI_Frame _win;
    private static Arena _ar;
    private static int arg=0;

    public static void main(String[] a) {
        if(a.length>0)
            arg=Integer.parseInt(a[0]);
        Thread client = new Thread(new Ex2());
            client.start();


    }

    @Override
    public void run() {
        int scenario_num=23;


                game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
                	//int id = 305475915;
                	//game.login(id);
                String g = game.getGraph();
                String pks = game.getPokemons();
                directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
                //directed_weighted_graph gg=new DWGraph_DS();
                //dw_graph_algorithms gr=new DWGraph_Algo();
                //gr.init(gg);
                //gr.load(g);
                //directed_weighted_graph gt=gr.getGraph();
                //System.out.println(gt);
        ///////////////////////////////////////////////////////////////////////////////////////*
        /*
        GsonBuilder gson=new GsonBuilder();
        gson.registerTypeAdapter(directed_weighted_graph.class,new DWGraph_Json_Deserializer());
        Gson customGson=gson.create();


        directed_weighted_graph dwg_reloaded=customGson.fromJson(g,directed_weighted_graph.class);
        System.out.println(dwg_reloaded);*/
        ////////////////////////////////////////////////////////////////////////////////////
                init(game);

                game.startGame();
                _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
                int ind = 0;
                long dt = 100;

                while (game.isRunning()) {
                    //moveAgants(game, gg);
                    //moveAgants(game, dwg_reloaded);
                    try {
                        if (ind % 1 == 0) {
                            _win.repaint();
                            moveAgants(game, gg);
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



            System.exit(0);

    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {

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
                dest = nextNode(gg, src,ag.getID(), agents.size());
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);

            }


        }

    }
    /**
     *
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src, int agent_id, int num_of_agents) {
        int ans = -1;

        dw_graph_algorithms dwg_algo=new DWGraph_Algo();
        dwg_algo.init(g);

        List<CL_Pokemon> pokedex=_ar.getPokemons(); //get a list of pokemons
        List<DWTEdge_Data> edges_improved=new ArrayList<>();

        for(CL_Pokemon cl_p:pokedex){ //add all edges to the list
            
            g.getEdge(cl_p.get_edge().getSrc(),cl_p.get_edge().getDest()).setTag( g.getEdge(cl_p.get_edge().getSrc(),cl_p.get_edge().getDest()).getTag() + (int)cl_p.getValue() );
            DWTEdge_Data temp_node=new DWTEdge_Data(g.getEdge(cl_p.get_edge().getSrc(),cl_p.get_edge().getDest()));
            double shortest_path=dwg_algo.shortestPathDist(src, temp_node.getDest());
            temp_node.setRatio(shortest_path/temp_node.getValue());
            edges_improved.add(temp_node);
            
        }
        //if(num_of_agents>1)
        //    devide_and_conquer(null);

        Comparator<DWTEdge_Data> DWEdge_Data_Comparator=((x, y) -> Double.compare(y.getRatio(), x.getRatio()));
        edges_improved.sort(DWEdge_Data_Comparator);
        List<node_data> shortest_path = dwg_algo.shortestPath(src, edges_improved.get(agent_id).getDest());
        
        
        if(src==edges_improved.get(agent_id).getDest())
            return ans=edges_improved.get(agent_id).getSrc();


        ans=shortest_path.get(1).getKey();//second organ in list.
        return ans;

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
        //System.out.println(g);
        //directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used(); ////////////////////need to change to my method

        GsonBuilder gson=new GsonBuilder();
        gson.registerTypeAdapter(directed_weighted_graph.class,new DWGraph_Json_Deserializer());
        Gson customGson=gson.create();


        directed_weighted_graph dwg_reloaded=customGson.fromJson(g,directed_weighted_graph.class);


        //directed_weighted_graph gg=new DWGraph_DS();
        //dw_graph_algorithms g_algo=new DWGraph_Algo();

        //g_algo.init(gg);
       // g_algo.load(g);

        //directed_weighted_graph gr= g_algo.getGraph();
       // gg.init(g);

        _ar = new Arena(); //builds the arena of the graph.
        _ar.setGame(game);
        //_ar.setGraph(gg);
        //System.out.println(gr);
        _ar.setGraph(dwg_reloaded);

        _ar.setPokemons(Arena.json2Pokemons(fs));


        _win=new GUI_Frame(_ar);

        String info = game.toString();

        JSONObject line;

        try {
            line = new JSONObject(info);

            JSONObject game_json = line.getJSONObject("GameServer");

            int num_of_agents = game_json.getInt("agents");
            //int game_level = game_json.getInt("game_level");

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

            for(int a = 0;a<num_of_agents;a++) {//Adds all agents

                CL_Pokemon clp=p_queue_pokemon.poll();
                int agent_src_node=clp.get_edge().getSrc();
                game.addAgent(agent_src_node);

            }

        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
