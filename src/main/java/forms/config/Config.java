package forms.config;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;

import java.io.Serializable;
import java.util.Map;

public interface Config extends Serializable {

    //public static MetaDataKey<String> NAME = new MetaDataKey<String>() {};
    public static MetaDataKey<Config> KEY = new MetaDataKey<Config>() {};
    //public static MetaDataKey<String> PROPERTY = new MetaDataKey<String>() {};

    // TODO : refactor this - rename it to getId(); not to be confused with wicketId.
    String getName();
    String getType();
    String getProperty();
    Map<String, String> getAttributes();
    Map<String, Object> getOptions();
    Component create(String id);
    String getPluginName();

}
