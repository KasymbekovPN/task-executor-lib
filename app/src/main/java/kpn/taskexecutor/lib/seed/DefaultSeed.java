package kpn.taskexecutor.lib.seed;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultSeed implements Seed {

    private final Class<?> type;
    private final Map<String, Object> fields;

    public static Builder builder(){
        return new Builder();
    }

    private DefaultSeed(Class<?> type, Map<String, Object> fields){
        this.type = type;
        this.fields = Collections.unmodifiableMap(fields);
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public Map<String, Object> getFields() {
        return fields;
    }

    public static class Builder{
        private final Map<String, Object> fields = new HashMap<>();

        private Class<?> type;

        public Builder type(Class<?> type){
            this.type = type;
            return this;
        }

        public Builder field(String key, Object value){
            fields.put(key, value);
            return this;
        }

        public Seed build(){
            return new DefaultSeed(type, fields);
        }
    }
}
