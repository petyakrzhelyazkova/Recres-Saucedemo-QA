package testframework;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesManager {
    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream in = PropertiesManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in != null) {
                CONFIG.load(in);
            } else {
                System.err.println("[WARN] config.properties not found on classpath");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private PropertiesManager() {}

    public static Properties getConfigProperties() { return CONFIG; }

    public static String getOrDefault(String key, String fallback) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;
        return CONFIG.getProperty(key, fallback);
    }
}
