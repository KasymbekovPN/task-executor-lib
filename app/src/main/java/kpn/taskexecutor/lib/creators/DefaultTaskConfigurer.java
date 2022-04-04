package kpn.taskexecutor.lib.creators;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.namecreator.CamelSetterNameCreator;
import kpn.taskexecutor.lib.namecreator.CamelSetterNamesCreator;
import kpn.taskexecutor.lib.namecreator.MethodExtractor;
import kpn.taskexecutor.lib.namecreator.MethodFilter;
import kpn.taskexecutor.lib.namecreator.MethodFilterImpl;
import kpn.taskexecutor.lib.namecreator.RecursivePublicMethodExtractor;
import kpn.taskexecutor.lib.namecreator.TaskCreator;
import kpn.taskexecutor.lib.namecreator.TaskCreatorImpl;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.tasks.Task;

public class DefaultTaskConfigurer implements TaskConfigurer {

    private final Function<Set<String>, Map<String, String>> setterNamesCreator;
    private final MethodExtractor methodExtractor;
    private final MethodFilter methodFilter;
    private final TaskCreator taskCreator;

    public static Builder build(){
        return new Builder();
    }

    private DefaultTaskConfigurer(Function<Set<String>, Map<String, String>> setterNamesCreator,
                           MethodExtractor methodExtractor,
                           MethodFilter methodFilter,
                           TaskCreator taskCreator) {
        this.setterNamesCreator = setterNamesCreator;
        this.methodExtractor = methodExtractor;
        this.methodFilter = methodFilter;
        this.taskCreator = taskCreator;
    }

    @Override
    public Task configure(Seed seed) throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure {
        Map<String, String> expectedSetterNames = setterNamesCreator.apply(seed.getFields().keySet());
        Map<String, Method> methods = methodExtractor.extract(seed.getType());
        Map<String, Method> setterMethods = methodFilter.filter(methods, expectedSetterNames);
        return taskCreator.create(seed, setterMethods);
    }

    public static class Builder{
        private Function<Set<String>, Map<String, String>> setterNamesCreator;
        private MethodExtractor methodExtractor;
        private MethodFilter methodFilter;
        private TaskCreator taskCreator;

        public Builder setterNamesCreator(Function<Set<String>, Map<String, String>> setterNamesCreator){
            this.setterNamesCreator = setterNamesCreator;
            return this;
        }

        public Builder methodExtractor(MethodExtractor methodExtractor){
            this.methodExtractor = methodExtractor;
            return this;
        }

        public Builder methodFilder(MethodFilter methodFilter){
            this.methodFilter = methodFilter;
            return this;
        }

        public Builder taskCreator(TaskCreator taskCreator){
            this.taskCreator = taskCreator;
            return this;
        }

        public DefaultTaskConfigurer build(){
            checkOrSet();
            return new DefaultTaskConfigurer(setterNamesCreator, methodExtractor, methodFilter, taskCreator);
        }

        private void checkOrSet() {
            checkOrSetSetterNamesCreator();
            checkOrSetMethodExtractor();
            checkOrSetMethodFilter();
            checkOrSetTaskCreator();
        }

        private void checkOrSetSetterNamesCreator() {
            if (setterNamesCreator == null){
                setterNamesCreator = new CamelSetterNamesCreator(new CamelSetterNameCreator());
            }
        }

        private void checkOrSetMethodExtractor() {
            if (methodExtractor == null){
                methodExtractor = new RecursivePublicMethodExtractor();
            }
        }

        private void checkOrSetMethodFilter() {
            if (methodExtractor == null){
                methodFilter = new MethodFilterImpl();
            }
        }

        private void checkOrSetTaskCreator() {
            if (taskCreator == null){
                taskCreator = new TaskCreatorImpl();
            }
        }
    }
}
