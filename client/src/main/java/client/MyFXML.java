/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ResourceBundle;

import client.utils.LanguageUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import javafx.util.Pair;

public class MyFXML {

    private Injector injector;
//    private Locale locale;
//    private ResourceBundle bundle;

    @Inject
    public MyFXML(Injector injector) {
        this.injector = injector;
//        String language = LanguageUtils.getLanguageFromFile();
//        this.locale = new Locale(language);
//        this.bundle = ResourceBundle.getBundle("language.messages", locale);
    }

//    public void setLocale(Locale locale) {
//        this.locale = locale;
//        LanguageUtils.setLanguageToFile(locale.getLanguage());
//        this.bundle = ResourceBundle.getBundle("language.messages", locale);
//    }
//
//    public Locale getLocale() {
//        return locale;
//    }
//
//    public String getFromBundle(String key) {
//        return bundle.getString(key);
//    }

    public <T> Pair<T, Parent> load(Class<T> c, String... parts) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages", LanguageUtils.getLocale());
            var loader = new FXMLLoader(getLocation(parts), bundle, null, new MyFactory(), StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            return new Pair<>(ctrl, parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getLocation(String... parts) {
        var path = Path.of("", parts).toString();
        return MyFXML.class.getClassLoader().getResource(path);
    }

    private class MyFactory implements BuilderFactory, Callback<Class<?>, Object> {

        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return new Builder() {
                @Override
                public Object build() {
                    return injector.getInstance(type);
                }
            };
        }

        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
}