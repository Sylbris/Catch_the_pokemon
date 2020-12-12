package api;

import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    private static Random _rnd = null;

    @org.junit.jupiter.api.Test
    void reverse_graph() {
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
        directed_weighted_graph dwg=new DWGraph_DS();
        for(int i=0;i<3;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }
        dwg.connect(0,2,1.5);
        assertEquals(dwg.getEdge(0,2).getWeight(),1.5);
        assertNotEquals(dwg.getEdge(0,2).getWeight(),2);
        assertEquals(dwg.getEdge(0,4),null);
        assertEquals(dwg.getEdge(2,0),null);
    }


    @org.junit.jupiter.api.Test
    void connect() {
    }

    @org.junit.jupiter.api.Test
    void getV() {
        directed_weighted_graph dwg=new DWGraph_DS();
        for(int i=0;i<3;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }
        Collection <node_data> n= dwg.getV();
        Iterator<node_data> i=n.iterator();
        while(i.hasNext()){
            node_data j=i.next();
            Assertions.assertNotNull(j);
        }
    }

    @org.junit.jupiter.api.Test
    void getE() {
        directed_weighted_graph dwg=new DWGraph_DS();
        for(int i=0;i<3;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }
        dwg.connect(0,2,1.5);
        dwg.connect(0,3,6.4);
        dwg.connect(0,1,3.5);

        Collection <edge_data> n= dwg.getE(0);
        Iterator<edge_data> i=n.iterator();

        while(i.hasNext()){
            edge_data j=i.next();
            Assertions.assertNotNull(j);
        }


    }

    @org.junit.jupiter.api.Test
    void removeNode() {
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
        directed_weighted_graph dwg=new DWGraph_DS();
        for(int i=0;i<3;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }
        dwg.connect(0,2,1.5);
        dwg.connect(0,1,3.5);
        dwg.connect(2,0,4.6);

        dwg.removeEdge(0,2);
        assertEquals(dwg.getEdge(0,2),null);
        assertEquals(dwg.getEdge(2,0).getWeight(),4.6);
        dwg.removeEdge(0,2);
        assertEquals(dwg.getEdge(0,2),null);

    }

    @org.junit.jupiter.api.Test
    void nodeSize() {
        directed_weighted_graph dwg=new DWGraph_DS();
        for(int i=0;i<3;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }
        assertEquals(dwg.nodeSize(),3);
        dwg.removeNode(0);
        assertEquals(dwg.nodeSize(),2);
    }

    @org.junit.jupiter.api.Test
    void edgeSize() {
        directed_weighted_graph dwg=new DWGraph_DS();
        for(int i=0;i<3;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }
        dwg.connect(0,2,1.5);
        dwg.connect(0,1,3.5);
        dwg.connect(2,0,4.6);
        dwg.connect(2,0,5.4);
        assertEquals(dwg.edgeSize(),3);
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