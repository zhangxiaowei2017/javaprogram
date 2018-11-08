package com.greer.springbootdemo2.HttpUnit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.greer.springbootdemo2.bean.Child;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.io.IOException;

@JsonComponent
public class JsonTransformer {
    public static class Serializer extends JsonSerializer<Child> {
        @Override
        public void serialize(Child child, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        }
    }

    public static class Deserializer extends JsonDeserializer<Child> {
        @Override
        public Child deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return null;
        }
    }
}
