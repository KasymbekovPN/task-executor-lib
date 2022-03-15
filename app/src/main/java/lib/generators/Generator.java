package lib.generators;

import java.util.Optional;

import javax.swing.text.AbstractDocument.Content;

import lib.seeds.Seed;

public interface Generator {
    Optional<Seed> getNextIfExist(Content content);
}
