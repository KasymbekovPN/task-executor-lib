package taskExecutorLib.contexts;

import java.util.HashMap;
import java.util.Map;

public class SimpleContext implements Context {

    private final Map<String, Object> storage = new HashMap<>();

    @Override
    public void put(String property, Object value) {
        storage.put(property, value);
    }

    @Override
    public Object get(String property) throws PropertyNonExist {
        if (storage.containsKey(property)){
            return storage.get(property);
        }

        throw new PropertyNonExist(String.format("Context doesn't contain property %s", property));
    }

    @Override
    public <T> T get(String property, Class<T> type) throws PropertyNonExist {
        if (storage.containsKey(property)){
            return type.cast(storage.get(property));
        }
        
        throw new PropertyNonExist(String.format("Context doesn't contain property %s", property));
    }
}
