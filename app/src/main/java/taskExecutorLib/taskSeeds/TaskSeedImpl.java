package taskExecutorLib.taskSeeds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TaskSeedImpl implements TaskSeed {

    private final Class<?> type;
    private final Map<String, Object> fields;

    public static Builder builder(){
        return new Builder();
    }

    private TaskSeedImpl(Class<?> type, Map<String, Object> fields){
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

        public TaskSeed build(){
            return new TaskSeedImpl(type, fields);
        }
    }
}
