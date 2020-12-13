package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

/**
 *  This class represents a Json deserializer to parse json into graph.
 */
public class DWGraph_Json_Deserializer implements JsonDeserializer<directed_weighted_graph> {

    /**
     * We get our json, first we add all the graph nodes.
     * Then we check which nodes are connected and connected them in our graph.
     *
     * This method runs in O(|V|)
     * @param jsonElement
     * @param type
     * @param jsonDeserializationContext
     * @return
     * @throws JsonParseException
     */
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject= jsonElement.getAsJsonObject();
        directed_weighted_graph dwg=new DWGraph_DS();
        //code to build graph from json
        JsonArray Nodes=jsonObject.get("Nodes").getAsJsonArray();
        JsonArray Edges= jsonObject.get("Edges").getAsJsonArray();
        int firstkey=0;
        for(int i=0;i<Nodes.size();i++){
            JsonObject jsonNodeData=Nodes.get(i).getAsJsonObject();
            String [] str=jsonNodeData.get("pos").getAsString().split(",");
            //List<String> coords = Arrays.asList(str.split(","));
            //System.out.println("size: "+ coords.size());
            Double x=Double.parseDouble(str[0]);
            Double y=Double.parseDouble(str[1]);
            Double z=Double.parseDouble(str[2]);

            geo_location gl_node=new DWGeo_Location(x,y,0);

            node_data n=new DWNode_DS();
            if(i==0)
                firstkey=n.getKey();
            //System.out.println("key: "+ n.getKey());
            n.setLocation(gl_node);
            dwg.addNode(n);
        }
        Iterator <node_data> it=dwg.getV().iterator();
       for(int i=0;i< Edges.size();i++){

           JsonObject jsonEdgeData=Edges.get(i).getAsJsonObject();

           int src=jsonEdgeData.get("src").getAsInt();
           int dest=jsonEdgeData.get("dest").getAsInt();
           double weight=jsonEdgeData.get("w").getAsDouble();
           dwg.connect(src+firstkey,dest+firstkey,weight);
       }

        return dwg;
    }
}
