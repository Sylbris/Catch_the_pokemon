package api;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * A custom Json serializer.
 */
public class DWGraph_Json_Serializer implements JsonSerializer<directed_weighted_graph> {
    /**
     * We go over our graph nodes add them to the json object, then we go over that node
     * neighbours and add them to the json object as well.
     *
     * This method runs in O(|V| + |E|)
     *
     * @param dwg
     * @param type
     * @param jsonSerializationContext
     * @return
     */
    @Override
    public JsonElement serialize(directed_weighted_graph dwg, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject main=new JsonObject();

        JsonArray Edges=new JsonArray();
        JsonArray Nodes=new JsonArray();
        main.add("Edges", Edges);
        main.add("Nodes", Nodes);
        for(node_data n: dwg.getV()){

            JsonObject node=new JsonObject();
            if(n.getLocation()!=null) {
                geo_location node_location = n.getLocation();
                double x = node_location.x();
                double y = node_location.y();
                double z = node_location.z();
                String pos = "" + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z);
            }
            node.addProperty("pos",0);
            node.addProperty("id", n.getKey());
            Nodes.add(node);
            for(edge_data e:dwg.getE(n.getKey())){
                JsonObject edge=new JsonObject();
                edge.addProperty("src", e.getSrc());
                edge.addProperty("w", e.getWeight());
                edge.addProperty("dest", e.getDest());
                Edges.add(edge);
            }
        }

        return main;
    }
}
