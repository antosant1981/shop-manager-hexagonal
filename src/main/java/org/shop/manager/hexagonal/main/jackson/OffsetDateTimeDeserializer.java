package org.shop.manager.hexagonal.main.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    public OffsetDateTimeDeserializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            return OffsetDateTime.parse(p.getText(), dateTimeFormatter).toInstant().atOffset(ZoneOffset.UTC);
        } catch (DateTimeParseException exception) {
            return LocalDateTime.parse(p.getText(), dateTimeFormatter).atOffset(ZoneOffset.UTC);
        }
    }
}
