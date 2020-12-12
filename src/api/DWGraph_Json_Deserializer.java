package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        for(int i=0;i<Nodes.size();i++){
            JsonObject jsonNodeData=Nodes.get(i).getAsJsonObject();
            String str=jsonNodeData.get("pos").getAsString();
            List<String> coords = Arrays.asList(str.split(","));

            float x=Float.parseFloat(coords.get(0));
            float y=Float.parseFloat(coords.get(1));

            geo_location gl_node=new DWGeo_Location(x,y,0);

            node_data n=new DWNode_DS();
            dwg.addNode(n);
            dwg.getNode(i).setLocation(gl_node);
        }

       for(int i=0;i< Edges.size();i++){
           JsonObject jsonEdgeData=Edges.get(i).getAsJsonObject();
           int src=jsonEdgeData.get("src").getAsInt();
           int dest=jsonEdgeData.get("dest").getAsInt();
           double weight=jsonEdgeData.get("w").getAsDouble();
           dwg.connect(src,dest,weight);
       }

        return dwg;
    }
}
