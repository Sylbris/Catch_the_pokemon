package api;


import java.io.Serializable;
import java.util.Objects;
/**
 * This class represents an implement of the node_data interface.
 */
public class DWNode_DS implements node_data, Serializable {
    private geo_location gl;
    private int key;
    private double weight;
    private String info;
    private int tag;
    private static int i=0;

    /**
     * Constructor
     */
    public DWNode_DS() {
        key=i;
        i++;
        gl=new DWGeo_Location();
    }

    /**
     * Copy constructor
     * @param n
     */
    public DWNode_DS(node_data n) {
        gl=n.getLocation();
        key=n.getKey();
        weight=n.getWeight();
    }

    /**
     * Compare nodes by their keys.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWNode_DS dwNode_ds = (DWNode_DS) o;
        return key == dwNode_ds.key;
    }

    /**
     * Will use the node keys to hash.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */
    @Override
    public int getKey() {
        return key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public geo_location getLocation() {
        return gl;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.gl=p;
    }

    /**
     * Returns the weight associated with this node.
     *
     * @return
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        weight=w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
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
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;
    }

}
