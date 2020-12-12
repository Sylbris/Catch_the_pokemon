package api;

public class DWEdge_Location implements edge_location {
    edge_data e;

    public DWEdge_Location(edge_data e){
        this.e=e;
    }
    /**
     * Returns the edge on which the location is.
     *
     * @return
     */
    @Override
    public edge_data getEdge() {
        return e;
    }

    /**
     * Returns the relative ration [0,1] of the location between src and dest.
     *
     * @return
     */
    @Override
    public double getRatio() {
        return 0;
    }
}
