package org.shop.manager.hexagonal.main.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.shop.manager.hexagonal.vocabulary.Identifier;

import java.io.IOException;

public class IdentifierSerializer extends StdSerializer<Identifier> {

    public IdentifierSerializer() {
        super(Identifier.class);
    }

    @Override
    public void serialize(Identifier identifier, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (identifier != null) {
            jsonGenerator.writeString(identifier.asString());
        } else {
            jsonGenerator.writeNull();
        }
    }
}
