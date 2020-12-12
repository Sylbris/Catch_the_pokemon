package api;

import java.io.Serializable;

/**
 * This class represent an implement for geo_location interface.
 */
public class DWGeo_Location implements geo_location, Serializable {
    private double x;
    private double y;
    private double z;

    /**
     * Constructor
     */
    public DWGeo_Location(){
        x=0;
        y=0;
        z=0;
    }

    /**
     * Copy Constructor
     * @param x
     * @param y
     * @param z
     */
    public DWGeo_Location(double x, double y , double z){
        this.x=x;
        this.y=y;
        this.z=z;

    }

    /**
     * Copy Constructor
     * @param gl
     */
    public DWGeo_Location(geo_location gl){
        x=gl.x();
        y=gl.y();
        z=gl.z();
    }
    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    /**
     * Calculate the distance between geo_locations
     * and return it as double.
     * @param g
     * @return
     */
    @Override
    public double distance(geo_location g) {
        return Math.sqrt(
                         (  (y-g.y())*(y-g.y())    )
                        +(  (x-g.x())*(x-g.x()     )
                        +(  (z-g.z()))*(z-g.z()    )
                    )) ;
    }
}
