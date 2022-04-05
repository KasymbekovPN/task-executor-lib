package kpn.taskexecutor.lib.seed.generator;

import java.util.Optional;

import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.Seed;

public interface Generator {
    Optional<Seed> getNextIfExist(Context context);
}
