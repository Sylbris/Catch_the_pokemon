package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    private static Random _rnd = null;
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
    @org.junit.jupiter.api.Test
    void runtime(){
        long start = new Date().getTime();
        directed_weighted_graph g0 = graph_creator(500000,490000,1);
        long finish = new Date().getTime();
        double timeElapsed = (finish - start)/1000;
        boolean time=timeElapsed<10;
        Assertions.assertEquals(true,time);
    }

    @org.junit.jupiter.api.Test
    void getNode() {
        directed_weighted_graph dwg=new DWGraph_DS();
        node_data n=new DWNode_DS();
        dwg.addNode(n);
        assertEquals(dwg.getNode(n.getKey()).getKey(),n.getKey());
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
        directed_weighted_graph g1=new DWGraph_DS(g0);
        g1.connect(0,2,1.5);
        assertEquals(g1.getEdge(0,2).getWeight(),1.5);
        assertNotEquals(g1.getEdge(0,2).getWeight(),2);
        g1.connect(0,2,2);
        assertEquals(g1.getEdge(0,2).getWeight(),2);
    }


    @org.junit.jupiter.api.Test
    void connect() {
        directed_weighted_graph g1=new DWGraph_DS(g0);
        g1.connect(3,5,-1);
        assertEquals(g1.getEdge(3,5),null);
        g1.connect(3,5,0.01);
        assertEquals(g1.getEdge(3,5).getWeight(),0.01);
        g1.connect(2,2,2);
        assertEquals(g1.getEdge(2,2),null);
    }

    @org.junit.jupiter.api.Test
    void getV() {
        Collection <node_data> n= g0.getV();
        Iterator<node_data> i=n.iterator();
        while(i.hasNext()){
            node_data j=i.next();
            Assertions.assertNotNull(j);
        }
    }

    @org.junit.jupiter.api.Test
    void getE() {
        directed_weighted_graph g1=new DWGraph_DS(g0);
        Collection <edge_data> n= g1.getE(0);
        Iterator<edge_data> i=n.iterator();

        while(i.hasNext()){
            edge_data j=i.next();
            Assertions.assertNotNull(j);
        }
        g1.removeNode(0);
        Collection <edge_data> r= g1.getE(0);
        assertNull(r);



    }

    @org.junit.jupiter.api.Test
    void removeNode() {
        directed_weighted_graph g1=new DWGraph_DS(g0);
        g1.removeNode(0);
        assertEquals(g1.edgeSize(),6);
        assertEquals(g1.nodeSize(),5);
        node_data n=new DWNode_DS(10);
        g1.addNode(n);
        assertEquals(g1.edgeSize(),6);
        node_data n1=new DWNode_DS(0);
        g1.addNode(n1);
        assertEquals(g1.edgeSize(),6);
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
        directed_weighted_graph g1=new DWGraph_DS(g0);
        g1.removeEdge(0,3);
        assertEquals(g1.getEdge(0,3),null);
        assertEquals(g1.getEdge(3,0).getWeight(),3);
        g1.removeEdge(0,2);
        assertEquals(g1.getEdge(0,2),null);
        g1.connect(0,2,1);
        assertEquals(g1.getEdge(0,2).getWeight(),1);
        g1.removeEdge(0,19);
        assertEquals(g1.getEdge(0,19),null);
        assertEquals(g1.getEdge(15,2),null);

    }

    @org.junit.jupiter.api.Test
    void nodeSize() {
        directed_weighted_graph g1=new DWGraph_DS(g0);
        assertEquals(g1.nodeSize(),6);
        g1.removeNode(0);
        assertEquals(g1.nodeSize(),5);
        g1.removeNode(0);
        assertEquals(g1.nodeSize(),5);
    }

    @org.junit.jupiter.api.Test
    void edgeSize() {
        directed_weighted_graph g1=new DWGraph_DS(g0);
        assertEquals(g1.edgeSize(),9);
        g1.removeEdge(2,0);
        assertEquals(g1.edgeSize(),9);
        g1.removeEdge(5,2);
        assertEquals(g1.edgeSize(),8);
        g1.removeEdge(5,2);
        assertEquals(g1.edgeSize(),8);
        g1.connect(2,2,-3);
        assertEquals(g1.edgeSize(),8);
        /*
        g0.connect(0,1,1);
        g0.connect(0,3,5);
        g0.connect(1,4,1);
        g0.connect(1,2,10);
        g0.connect(2,4,4);
        g0.connect(2,3,2);
        g0.connect(3,0,3);
        g0.connect(4,5,1);
        g0.connect(5,2,2);

        */

    }

    /**
     * Creates a graph to test
     * @param v
     * @param e
     * @param seed
     * @return
     */
    public static directed_weighted_graph graph_creator(int v,int e, int seed){
        directed_weighted_graph dwg=new DWGraph_DS();
        _rnd=new Random(seed);
        for(int i=0;i<v;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }

        int[] nodes = nodes(dwg);

        System.out.println(nodes.length);
        while(dwg.edgeSize() < e) {
            int a = nextRnd(0,v);
            int b = nextRnd(0,v);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            dwg.connect(i,j, w);
        }
        System.out.println("okay");
        return dwg;
    }

    /**
     * Returns the next random
     * @param min
     * @param max
     * @return
     */
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();

        node_data[] nodes = new node_data[size];

        V.toArray(nodes); // O(n) operation

        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}

        Arrays.sort(ans);
        return ans;
    }
}