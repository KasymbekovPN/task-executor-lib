package kpn.taskexecutor.lib.seed;

import java.util.Map;

public interface Seed {
    Class<?> getType();
    Map<String, Object> getFields();
}
