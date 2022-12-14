package dev.rollczi.litescheduledaction.config;

import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import net.dzikoysk.cdn.source.Resource;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationService {

    private final File dataFolder;
    private final Cdn cdn = CdnFactory.createYamlLike()
            .getSettings()
            .withMemberResolver(Visibility.PACKAGE_PRIVATE)
            .build();

    private final Set<Reloadable> reloadables = new HashSet<>();

    public ConfigurationService(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T extends Reloadable> T load(T config) {
        Resource resource = config.resource(dataFolder);

        T load = cdn.load(resource, config)
                .orThrow(RuntimeException::new);
        cdn.render(load, resource)
                .orThrow(RuntimeException::new);

        this.reloadables.add(load);
        return load;
    }

    public void reloadAll() {
        for (Reloadable reloadable : this.reloadables) {
            this.load(reloadable);
        }
    }

}
