package taskExecutorLib.contexts;

public interface Context {
    void put(String property, Object value);
    Object get(String property) throws PropertyNonExist;
    <T> T get(String property, Class<T> type) throws PropertyNonExist;
}
