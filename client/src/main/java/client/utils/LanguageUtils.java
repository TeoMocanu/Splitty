package client.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class LanguageUtils {
    public static List<Locale> getSupportedLocales() {
        try {
            return Files.walk(Paths.get(Objects.requireNonNull(LanguageUtils.class.getClassLoader().getResource("language")).toURI()))
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.startsWith("messages_"))
                    .map(name -> name.substring(9, name.lastIndexOf('.'))) // extract language code
                    .map(Locale::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
