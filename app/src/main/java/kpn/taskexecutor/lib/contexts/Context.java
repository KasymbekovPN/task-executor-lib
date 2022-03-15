package kpn.taskexecutor.lib.contexts;

import kpn.taskexecutor.exceptions.contexts.ContextPropertyNonExist;

public interface Context {
    void put(String property, Object value);
    Object get(String property) throws ContextPropertyNonExist;
    <T> T get(String property, Class<T> type) throws ContextPropertyNonExist;
}
