package kpn.taskexecutor.lib.contexts;

import kpn.taskexecutor.exceptions.PropertyNotFoundException;

public interface Context {
    void put(String property, Object value);
    Object get(String property) throws PropertyNotFoundException;
    <T> T get(String property, Class<T> type) throws PropertyNotFoundException;
}
