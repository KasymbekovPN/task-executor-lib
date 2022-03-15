package kpn.taskexecutor.lib.seeds;

import java.util.Map;

public interface Seed {
    Class<?> getType();
    Map<String, Object> getFields();
}
