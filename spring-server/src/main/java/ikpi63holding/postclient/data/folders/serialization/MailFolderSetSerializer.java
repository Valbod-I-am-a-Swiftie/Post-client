package ikpi63holding.postclient.data.folders.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ikpi63holding.postclient.data.folders.MailFolderSet;
import java.io.IOException;

public class MailFolderSetSerializer extends StdSerializer<MailFolderSet> {

    public MailFolderSetSerializer() {
        this(null);
    }

    public MailFolderSetSerializer(Class<MailFolderSet> t) {
        super(t);
    }

    @Override
    public void serialize(
            MailFolderSet mailFolderSet, JsonGenerator jsonGenerator, SerializerProvider serializer)
            throws IOException {
        var jsonMap = mailFolderSet.getMap();
        jsonGenerator.writeStartArray();
        for (MailFolderJsonObject jsonObject : jsonMap) {
            jsonGenerator.writeObject(jsonObject);
        }
        jsonGenerator.writeEndArray();
    }

}