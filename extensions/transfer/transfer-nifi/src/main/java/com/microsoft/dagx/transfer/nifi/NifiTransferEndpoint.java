package com.microsoft.dagx.transfer.nifi;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.microsoft.dagx.spi.types.domain.Polymorphic;

import java.util.HashMap;
import java.util.Map;

@JsonTypeName("dagx:nifitransferendpoint")
@JsonDeserialize(builder = NifiTransferEndpoint.NifiTransferEndpointBuilder.class)
public class NifiTransferEndpoint implements Polymorphic {
    @JsonProperty("key")
    private String key;
    private final String type;

    private Map<String, String> properties;

    protected NifiTransferEndpoint(@JsonProperty("type") String type) {
        this.type = type;
        this.properties = new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    @JsonAnyGetter
    public Map<String, String> getProperties(){
        return properties;
    }


    @JsonPOJOBuilder(withPrefix = "")
    public static class NifiTransferEndpointBuilder {
        private String key;
        private String type;
        private final Map<String, String> properties;

        private NifiTransferEndpointBuilder() {
            properties = new HashMap<>();
        }

        @JsonCreator
        public static NifiTransferEndpointBuilder newInstance() {
            return new NifiTransferEndpointBuilder();
        }

        public NifiTransferEndpointBuilder key(String key) {
            this.key = key;
            return this;
        }

        public NifiTransferEndpointBuilder type(String type) {
            this.type = type;
            return this;
        }

        @JsonAnySetter
        public NifiTransferEndpointBuilder properties(Map<String, String> additionalProperties) {
            this.properties.putAll(additionalProperties);
            return this;
        }

        @JsonAnySetter
        public NifiTransferEndpointBuilder property(String key, String value) {
            this.properties.put(key, value);
            return this;
        }

        public NifiTransferEndpoint build() {
            NifiTransferEndpoint nifiTransferEndpoint = new NifiTransferEndpoint(type);
            nifiTransferEndpoint.setKey(key);
            nifiTransferEndpoint.properties = this.properties;
            return nifiTransferEndpoint;
        }
    }
}