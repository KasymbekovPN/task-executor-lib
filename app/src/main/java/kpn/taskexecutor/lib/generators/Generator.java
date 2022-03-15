package kpn.taskexecutor.lib.generators;

import java.util.Optional;

import javax.swing.text.AbstractDocument.Content;

import kpn.taskexecutor.lib.seeds.Seed;

public interface Generator {
    Optional<Seed> getNextIfExist(Content content);
}
