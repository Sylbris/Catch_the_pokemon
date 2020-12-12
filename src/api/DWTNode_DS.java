package api;

import java.util.LinkedList;
import java.util.List;
/**
 * This class represents a temporary node with fewer attributes than DWNode_DS.
 * used mostly for DWGraph_Algo.
 */
public class DWTNode_DS {
    private int key;
    private double distance=Integer.MAX_VALUE;
    private int parent=0;

    /**
     * Constructor
     */
    public DWTNode_DS(){
        distance=Integer.MAX_VALUE;
        parent=0;

    }

    /**
     * Copy Constructor
     * @param n
     */
    public DWTNode_DS(node_data n){
        key=n.getKey();
    }

    /**
     * Get the node key.
     * @return
     */
    public int getKey(){
        return key;
    }
    /**
     * Get the node distance.
     * @return
     */
    public double getDistance() {
        return distance;
    }
    /**
     * Set the node distance.
     * @return
     */
    public void setDistance(double distance){
        this.distance=distance;
    }
    /**
     * Get the node parent.
     * @return
     */
    public int getParent(){
        return parent;
    }
    /**
     * Set the node distance.
     * @return
     */
    public void setParent(int new_parent){
        parent=new_parent;
    }
}
