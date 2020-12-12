package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DWGraph_Json_Deserializer implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject= jsonElement.getAsJsonObject();

        System.out.println("works");
        directed_weighted_graph dwg=new DWGraph_DS();
        //code to build graph from json
        int graph_size=jsonObject.get("node_size").getAsInt();
        for(int i=0;i<graph_size;i++){
            node_data n=new DWNode_DS();
            dwg.addNode(n);
        }

        for(node_data n:dwg.getV()){
            for(edge_data e:dwg.getE(n.getKey()))
            {
                dwg.connect(n.getKey(),e.getDest(),e.getWeight());
            }
        }

        return dwg;
    }
}
