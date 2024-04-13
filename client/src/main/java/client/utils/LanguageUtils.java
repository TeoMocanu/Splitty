package client.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static javax.swing.JComponent.getDefaultLocale;

public class LanguageUtils {

    private static final ObjectProperty<Locale> LOCALE;

    static {
        LOCALE = new SimpleObjectProperty<>(getDefaultLocale());
        LOCALE.addListener((observable, oldVal, newVal) -> Locale.setDefault(newVal));
    }

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

    /**
     * get the default locale. This is the systems default if contained in the supported locales, english otherwise.
     *
     * @return the systems default language if supported and else English.
     */
    public static Locale getDefaultLocale() {
        Locale sysDefault = new Locale(getLanguageFromFile());
        return getSupportedLocales().contains(sysDefault) ? sysDefault : new Locale("en");
    }

    public static Locale getLocale() {
        return LOCALE.get();
    }

    public static void switchLanguage() {
        List<Locale> list = getSupportedLocales();
        int index = list.indexOf(LOCALE.get());

        System.out.println("LOCALE: " + LOCALE.get());
        System.out.println("index: " + index);
        System.out.println("list: " + list);
        System.out.println("locale: " + getLocale());
        System.out.println("list: " + list.get(0));

        setLocale(list.get((index+1)%list.size()));
    }

    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        setLanguageToFile(locale.getLanguage());
//        Locale.setDefault(locale);
    }

    public static ObjectProperty<Locale> localeProperty() {
        return LOCALE;
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

    /**
     * gets the string with the given key from the resource bundle for the current locale and uses it as first argument
     * to MessageFormat.format, passing in the optional args and returning the result.
     *
     * @param key
     *         message key
     * @param args
     *         optional arguments for the message
     * @return localized formatted string
     */
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages", getLocale());
        String retStr;
        try {
            retStr = bundle.getString(key);
        }
        catch (MissingResourceException e) {
            try {
                ResourceBundle bundle2 = ResourceBundle.getBundle("language.messages", new Locale("en"));
                retStr = bundle2.getString(key);
            }
            catch (MissingResourceException e2) {
                retStr = "missing: " + key;
            }
        }
        return MessageFormat.format(retStr, args);
    }

    /**
     * creates a String Binding to a localized String that is computed by calling the given func
     *
     * @param func
     *         function called on every change
     * @return StringBinding
     */
    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, LOCALE);
    }

    /**
     * creates a String binding to a localized String for the given message bundle key
     *
     * @param key
     *         key
     * @return String binding
     */
    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), LOCALE);
    }

    public static ObjectBinding<Image> createObjectBinding() {
        return Bindings.createObjectBinding(() -> getFlag(), LOCALE);
    }

    public static void update(Labeled entity, String key) {
        entity.textProperty().bind(createStringBinding(key));
    }

//    public static void update(Labeled entity, String key) {
//        entity.textProperty().bind(createStringBinding(key));
//    }

    public static void update(TableColumn entity, String key) {
        entity.textProperty().bind(createStringBinding(key));
    }

    public static void update(Text entity, String key) {
        entity.textProperty().bind(createStringBinding(key));
    }

    public static void update(TextField entity, String key) {
        entity.promptTextProperty().bind(createStringBinding(key));
    }

    public static void update(ImageView entity){
        entity.imageProperty().bind(createObjectBinding());
    }

    public static Image getFlag() {
        String dir = System.getProperty("user.dir");
        File file = new File(dir += "/client/src/main/resources/language/flag_"+LOCALE.get().getLanguage()+".png");
        Image flag = new Image(file.toURI().toString());
        return flag;
    }


}
