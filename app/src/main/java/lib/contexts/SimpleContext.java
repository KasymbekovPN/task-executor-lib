package lib.contexts;

import java.util.HashMap;
import java.util.Map;

import exceptions.contexts.ContextPropertyNonExist;

public class SimpleContext implements Context {

    private final Map<String, Object> storage = new HashMap<>();

    @Override
    public void put(String property, Object value) {
        storage.put(property, value);
    }

    @Override
    public Object get(String property) throws ContextPropertyNonExist {
        if (storage.containsKey(property)){
            return storage.get(property);
        }

        throw new ContextPropertyNonExist(String.format("Context doesn't contain property %s", property));
    }

    @Override
    public <T> T get(String property, Class<T> type) throws ContextPropertyNonExist {
        if (storage.containsKey(property)){
            return type.cast(storage.get(property));
        }
        
        throw new ContextPropertyNonExist(String.format("Context doesn't contain property %s", property));
    }
}
