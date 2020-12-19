package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest2 {

    @BeforeEach
    void setUp() {
    }

    @Test
    void bfs() {
    }

    @Test
    void bfs_n_nodes() {
        directed_weighted_graph g0 = DWGraph_DSTest.graph_creator(6,0,1);
        g0.connect(0,1,1);
        g0.connect(0,3,5);
        g0.connect(1,4,1);
        g0.connect(1,2,10);
        g0.connect(2,4,4);
        g0.connect(2,3,2);
        g0.connect(3,0,3);
        g0.connect(4,5,1);
        g0.connect(5,2,2);

        DWGraph_Algo dwg=new DWGraph_Algo();
        dwg.init(g0);
        ArrayList<node_data> nodes=dwg.bfs_n_nodes(g0,3,0);
        for(node_data n:nodes){
            System.out.println("key: " + n.getKey());
        }

    }
}