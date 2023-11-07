package config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * json 序列化/反序列化器配置
 *
 * @author anonymous
 * @version 1.0
 */
@Configuration
public class JsonConfig {


    public static class LongIdDeserializer extends JsonDeserializer<Long> {
        private final JsonDeserializer<String> deserializer = new StringDeserializer();

        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            String id = deserializer.deserialize(jsonParser, deserializationContext);
            if(!Pattern.matches("^\\d+$", id)) throw new IllegalArgumentException("非法 String ID 参数");
            return Long.parseLong(id);
        }
    }

    public static class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {
        private final JsonDeserializer<String> deserializer = new StringDeserializer();
        @Override
        public ObjectId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            return new ObjectId(deserializer.deserialize(jsonParser, deserializationContext));
        }
    }

    public static class ObjectIdSerializer extends JsonSerializer<ObjectId> {
        private final StringSerializer serializer = new StringSerializer();
        @Override
        public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            serializer.serialize(objectId.toHexString(), jsonGenerator, serializerProvider);
        }
    }
}
