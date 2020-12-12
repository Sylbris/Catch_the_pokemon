package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import api.*;

public class Ex2_Client implements Runnable{
	private static GUI_Frame _win;
	private static Arena _ar;

	public static void main(String[] a) {
		Thread client = new Thread(new Ex2_Client());
		client.start();
	}
	
	@Override
	public void run() {
		int scenario_num = 0;
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
	//	int id = 999;
	//	game.login(id);
		String g = game.getGraph();
		String pks = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		init(game);
		
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind=0;
		long dt=100;
		
		while(game.isRunning()) {
			moveAgants(game, gg);
			try {
				if(ind%1==0) {
					_win.repaint();
					}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

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

		String lg = game.getAgents();
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
				dest = nextNode(gg, src);
				game.chooseNextEdge(ag.getID(), dest);
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);

			}


		}
		game.move();
	}
	/**
	 *
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(directed_weighted_graph g, int src) {
		int ans = -1;

		dw_graph_algorithms dwg_algo=new DWGraph_Algo();
		dwg_algo.init(g);

		List<CL_Pokemon> pokedex=_ar.getPokemons(); //get a list of pokemons

		PriorityQueue<CL_Pokemon> ratios=new PriorityQueue<>((x, y) -> Double.compare(y.getValue(), x.getValue())); //Max queue
		for(CL_Pokemon cl_p:pokedex){ //add all pokemons to priority max queue.
			double shortest_path=dwg_algo.shortestPathDist(src,cl_p.get_edge().getDest());
			cl_p.setRatio(shortest_path/cl_p.getValue());
			ratios.add(cl_p);
		}


		//PriorityQueue<CL_Pokemon> p_queue_pokemon=new PriorityQueue<>((x, y) -> Double.compare(y.getValue(), x.getValue())); //Max queue

		//for(CL_Pokemon cl_p:pokedex){ //add all pokemons to priority max queue.
		//	p_queue_pokemon.add(cl_p);
		//}

		List<node_data> yes=null;

		int r;
		CL_Pokemon cl=ratios.peek();


		int isChased=0;

		while (isChased==0 && !ratios.isEmpty()) {

			 cl = ratios.peek(); //check for Pokemon with max ratio

			if(cl.getChased_by()==0) { //if that pokemon is not being chased.

				yes = dwg_algo.shortestPath(src, ratios.poll().get_edge().getDest());//chase the max pokemon
				cl.setChased_by(1);

				isChased=1;


			}
			else {

				ratios.poll();


			}
		}



		r=pokedex.indexOf(cl);

		if(src==pokedex.get(r).get_edge().getDest())
				return ans=pokedex.get(r).get_edge().getSrc();

		ans=yes.get(1).getKey();//second organ in list.

		return ans;

	}

	/**
	 * Build the game based on the given game data.
	 * @param game
	 */
	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used(); ////////////////////need to change to my method
		//gg.init(g);
		_ar = new Arena(); //builds the arena of the graph.

		_ar.setGraph(gg);

		_ar.setPokemons(Arena.json2Pokemons(fs));

		_win=new GUI_Frame(_ar);

		String info = game.toString();

		JSONObject line;

		try {
			line = new JSONObject(info);

			JSONObject game_json = line.getJSONObject("GameServer");

			int num_of_agents = game_json.getInt("agents");

			System.out.println(info);
			System.out.println(game.getPokemons());

			//int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());

			for(int a = 0;a<cl_fs.size();a++) {
				Arena.updateEdge(cl_fs.get(a),gg);
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
