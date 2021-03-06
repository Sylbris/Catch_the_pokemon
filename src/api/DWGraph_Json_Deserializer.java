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
        for(JsonElement i:Nodes){
            //JsonObject jsonNodeData=Nodes.get(i).getAsJsonObject();
            int key=((JsonObject)i).get("id").getAsInt();
            String [] str=((JsonObject)i).get("pos").getAsString().split(",");
            //List<String> coords = Arrays.asList(str.split(","));
            //System.out.println("size: "+ coords.size());
            double x,y,z;
            if(str.length>1) {
                 x = Double.parseDouble(str[0]);
                 y = Double.parseDouble(str[1]);
                 z = Double.parseDouble(str[2]);
            }
            else{
                x=0;y=0;z=0;
            }

            geo_location gl_node=new DWGeo_Location(x,y,z);

            node_data n=new DWNode_DS(key);

            //System.out.println("key: "+ n.getKey());
            n.setLocation(gl_node);
            dwg.addNode(n);
        }
        Iterator <node_data> it=dwg.getV().iterator();
       for(JsonElement e:Edges){

           int src=((JsonObject)e).get("src").getAsInt();
           int dest=((JsonObject)e).get("dest").getAsInt();
           double weight=((JsonObject)e).get("w").getAsDouble();
           dwg.connect(src,dest,weight);
       }

        return dwg;
    }
}
