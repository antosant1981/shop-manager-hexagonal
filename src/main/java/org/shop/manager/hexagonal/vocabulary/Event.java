package org.shop.manager.hexagonal.vocabulary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        property = "type"
)
public interface Event {
    @JsonIgnore
    default String getType() {
        return this.getClass().getSimpleName();
    }
}
