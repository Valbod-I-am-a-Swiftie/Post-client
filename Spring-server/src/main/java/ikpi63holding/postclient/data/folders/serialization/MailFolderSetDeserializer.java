package ikpi63holding.postclient.data.folders.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ikpi63holding.postclient.data.folders.MailFolderSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MailFolderSetDeserializer extends StdDeserializer<MailFolderSet> {

    public MailFolderSetDeserializer() {
        this(null);
    }

    public MailFolderSetDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MailFolderSet deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode root = jp.getCodec().readTree(jp);
        List<MailFolderJsonObject> jsonObjectList = new ArrayList<>();
        for (JsonNode node: root)
        {
            MailFolderJsonObject jsonObject = new MailFolderJsonObject();
            jsonObject.setName(node.get("name").asText());
            jsonObject.setType(node.get("type").asText());
            jsonObjectList.add(jsonObject);
        }
        return new MailFolderSet(jsonObjectList);
    }
}