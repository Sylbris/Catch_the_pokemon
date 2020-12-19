package api;

public class DWTEdge_Data {
    private int src;
    private int dest;
    private double value=0;
    private static double ratio=0;
    private static int chased=-1;

    public DWTEdge_Data(){

    }

    public DWTEdge_Data(edge_data e){
        src=e.getSrc();
        dest=e.getDest();
        value=e.getTag();
    }
    public DWTEdge_Data(int src, int dest, double value, double ratio) {
        this.src = src;
        this.dest = dest;
        this.value = value;
        this.ratio = ratio;
    }
    public static int isChased(){
        return chased;
    }
    public void setChased(int chased){
        this.chased=chased;
    }

    public int getDest() {
        return dest;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }
}
