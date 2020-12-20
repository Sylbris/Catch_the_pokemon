package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DWGraph_AlgoTest {
    private static directed_weighted_graph g0;

    @BeforeAll
    static void createGraph(){
        g0=DWGraph_DSTest.graph_creator(6,0,1);
        g0.connect(0,1,1);
        g0.connect(0,3,5);
        g0.connect(1,4,1);
        g0.connect(1,2,10);
        g0.connect(2,4,4);
        g0.connect(2,3,2);
        g0.connect(3,0,3);
        g0.connect(4,5,1);
        g0.connect(5,2,2);

    }

    @Test
    void isConnected() {
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        directed_weighted_graph g1=new DWGraph_DS(g0);
        ag0.init(g0);
        boolean b = ag0.isConnected();
        Assertions.assertTrue(b);
        g0.removeEdge(3,0);
        ag0.init(g0);
        b=ag0.isConnected();
        Assertions.assertFalse(b);
    }

    @Test
    void shortestPathDist() {
        dw_graph_algorithms ag0;
        ag0=new DWGraph_Algo();
        ag0.init(g0);
        double r=ag0.shortestPathDist(0,5);
        assertEquals(3,r);
    }

    @Test
    void shortestPath() {

        dw_graph_algorithms ag0;
        ag0=new DWGraph_Algo();
        ag0.init(g0);
        List<node_data> new_list=ag0.shortestPath(0,5);
        assertEquals(4,new_list.size());

    }

    @Test
    void save_load() {
        dw_graph_algorithms ag0=new DWGraph_Algo();
        ag0.init(g0);
        String str = "g2";
        assertTrue(ag0.save(str));
        directed_weighted_graph g1=new DWGraph_DS(g0);
        assertEquals(g1,g0);
        ag0.load(str);
        assertEquals(ag0.getGraph().edgeSize(),g1.edgeSize());
        assertEquals(ag0.getGraph().nodeSize(),g1.nodeSize());
        g1.removeNode(0);
        assertNotEquals(ag0.getGraph().edgeSize(),g1.edgeSize());
        assertNotEquals(ag0.getGraph().nodeSize(),g1.nodeSize());


    }

}