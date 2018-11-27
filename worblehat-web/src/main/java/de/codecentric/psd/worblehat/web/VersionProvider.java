package de.codecentric.psd.worblehat.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

public class VersionProvider {

    public static String getVersion() {

        try (InputStream inputStream = VersionProvider.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {

            Manifest manifest = new Manifest(inputStream);
            String version = manifest.getMainAttributes().getValue("Implementation-Version");

            return version == null ? "" : version;

        } catch (IOException e) {

            e.printStackTrace();
        }

        return "";
    }
}
