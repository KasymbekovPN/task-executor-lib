package kpn.taskexecutor.lib.namecreator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

//< rename -> DEfault...
public class CamelSetterNamesCreator implements Function<Set<String>, Map<String, String>> {
    
    private final UnaryOperator<String> creator;

    public CamelSetterNamesCreator(UnaryOperator<String> creator) {
        this.creator = creator;
    }

    @Override
    public Map<String, String> apply(Set<String> t) {
        Map<String, String> result = new HashMap<>();
        t.forEach(k -> result.put(k, creator.apply(k)));
        return result;
    }
}
