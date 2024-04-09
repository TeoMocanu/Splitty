package client.utils;

import javafx.scene.image.Image;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
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

    public static String getLanguageFromFile() {
        try {
            Properties properties = new Properties();
            InputStream input = LanguageUtils.class.getClassLoader().getResourceAsStream("language/language.properties");

            properties.load(input);
            return properties.getProperty("language");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLanguageToFile(String language) {
        try {
            Properties properties = new Properties();
            InputStream input = LanguageUtils.class.getClassLoader().getResourceAsStream("language/language.properties");

            properties.load(input);
            properties.setProperty("language", language);

            String dir = System.getProperty("user.dir");
            String file = dir.concat("/client/src/main/resources/language/language.properties");

            FileOutputStream output = new FileOutputStream(file);
            properties.store(output , null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Image getFlag(Locale locale) {
        String dir = System.getProperty("user.dir");
        File file = new File(dir += "/client/src/main/resources/language/flag_"+locale.getLanguage()+".png");
        Image flag = new Image(file.toURI().toString());
        return flag;
    }
}
