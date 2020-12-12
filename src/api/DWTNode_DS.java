package api;

import java.util.LinkedList;
import java.util.List;

public class DWTNode_DS {
    private int key;
    private double distance=Integer.MAX_VALUE;
    private int parent=0;

    public DWTNode_DS(){
        distance=Integer.MAX_VALUE;
        parent=0;

    }
    public DWTNode_DS(node_data n){
        key=n.getKey();
    }

    public int getKey(){
        return key;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance){
        this.distance=distance;
    }

    public int getParent(){
        return parent;
    }

    public void setParent(int new_parent){
        parent=new_parent;
    }
}
