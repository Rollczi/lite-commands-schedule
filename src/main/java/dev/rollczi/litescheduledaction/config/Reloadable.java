package dev.rollczi.litescheduledaction.config;

import net.dzikoysk.cdn.source.Resource;

import java.io.File;

public interface Reloadable {

    Resource resource(File folder);

}
