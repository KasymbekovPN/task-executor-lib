package kpn.taskexecutor.lib.task.configurer.creator.names;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class DefaultSetterNamesCreator implements Function<Set<String>, Map<String, String>> {
    
    private final UnaryOperator<String> creator;

    public DefaultSetterNamesCreator(UnaryOperator<String> creator) {
        this.creator = creator;
    }

    @Override
    public Map<String, String> apply(Set<String> t) {
        Map<String, String> result = new HashMap<>();
        t.forEach(k -> result.put(k, creator.apply(k)));
        return result;
    }
}
