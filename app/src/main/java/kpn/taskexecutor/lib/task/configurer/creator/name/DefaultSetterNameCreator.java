package kpn.taskexecutor.lib.task.configurer.creator.name;

import java.util.function.UnaryOperator;

public class DefaultSetterNameCreator implements UnaryOperator<String> {

    @Override
    public String apply(String t) {
        String newBegin = "set" + String.valueOf(t.charAt(0)).toUpperCase();
        return  newBegin + t.substring(1);
    }
}
