package org.shop.manager.hexagonal.main;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.shop.manager.hexagonal.main.jackson.IdentifierDeserializer;
import org.shop.manager.hexagonal.main.jackson.IdentifierSerializer;
import org.shop.manager.hexagonal.main.jackson.OffsetDateTimeDeserializer;
import org.shop.manager.hexagonal.main.jackson.OffsetDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {

            builder.modulesToInstall(new JavaTimeModule());
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            builder.serializers(
                    new OffsetDateTimeSerializer(),
                    new IdentifierSerializer());
            builder.deserializers(
                    new OffsetDateTimeDeserializer(),
                    new IdentifierDeserializer());
        };
    }
}
