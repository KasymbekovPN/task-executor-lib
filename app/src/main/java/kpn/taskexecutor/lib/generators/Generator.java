package kpn.taskexecutor.lib.generators;

import java.util.Optional;

import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seeds.Seed;

public interface Generator {
    Optional<Seed> getNextIfExist(Context context);
}
