package org.shop.manager.hexagonal.main.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.shop.manager.hexagonal.vocabulary.Identifier;

import java.io.IOException;

public class IdentifierDeserializer extends StdDeserializer<Identifier> {
    public IdentifierDeserializer() {
        super(Identifier.class);
    }

    @Override
    public Identifier deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return Identifier.fromString(jsonParser.getText());
    }
}
