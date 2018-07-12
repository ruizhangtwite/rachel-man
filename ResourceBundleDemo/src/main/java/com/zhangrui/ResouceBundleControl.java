package com.zhangrui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 模擬ResourceBundle.Control的newBundle()方法
 * 2018-04-21 14:03
 * Created by zhangrui.
 */
public class ResouceBundleControl extends ResourceBundle.Control {

    private String charset;

    public ResouceBundleControl(String charset) {
        this.charset = charset;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = toBundleName(baseName, locale);
        ResourceBundle bundle = null;

        final String resourceName = toResourceName(bundleName, "properties");
        if (resourceName == null) {
            return bundle;
        }
        final ClassLoader classLoader = loader;
        final boolean reloadFlag = reload;
        InputStream stream = null;
        try {
            stream = AccessController.doPrivileged(
                    new PrivilegedExceptionAction<InputStream>() {
                        public InputStream run() throws IOException {
                            InputStream is = null;
                            if (reloadFlag) {
                                URL url = classLoader.getResource(resourceName);
                                if (url != null) {
                                    URLConnection connection = url.openConnection();
                                    if (connection != null) {
                                        // Disable caches to get fresh data for
                                        // reloading.
                                        connection.setUseCaches(false);
                                        is = connection.getInputStream();
                                    }
                                }
                            } else {
                                is = classLoader.getResourceAsStream(resourceName);
                            }
                            return is;
                        }
                    });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        if (stream != null) {
            try {
                InputStreamReader reader = new InputStreamReader(stream, charset);
                bundle = new PropertyResourceBundle(reader);
            } finally {
                stream.close();
            }
        }

        return bundle;
    }
}
