package api;

import java.io.Serializable;
import java.util.Objects;

public class DWEdge_DS implements edge_data, Serializable {
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;

    public DWEdge_DS(){
    }
    /**
     * Constructor
     * @param src
     * @param dest
     * @param weight
     */
    public DWEdge_DS(int src,int dest,double weight){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
    }

    /**
     * Copy Constructor
     * @param e
     */
    public DWEdge_DS(edge_data e){
        src=e.getSrc();
        dest=e.getDest();
        weight=e.getWeight();

    }

    /**
     * Equals method : 2 edges are equal iff they
     * have the same src,dest & weight.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWEdge_DS dwEdge_ds = (DWEdge_DS) o;
        return src == dwEdge_ds.src &&
                dest == dwEdge_ds.dest &&
                Double.compare(dwEdge_ds.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, weight);
    }

    /**
     * The id of the source node of this edge.
     *
     * @return
     */
    @Override
    public int getSrc() {
        return src;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return
     */
    @Override
    public int getDest() {
        return dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info=s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;
    }
}
