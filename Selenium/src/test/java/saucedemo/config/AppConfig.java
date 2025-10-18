package saucedemo.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:env/dev.properties",
        "classpath:env/${env}.properties",
        "system:properties"
})
public interface AppConfig extends Config {
    @Key("baseUrl") String baseUrl();

    @Key("username") @DefaultValue("") String username();
    @Key("password") @DefaultValue("") String password();
}

