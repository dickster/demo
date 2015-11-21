package forms.config;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;

import java.io.Serializable;
import java.util.Map;

public interface Config extends Serializable {

    public MetaDataKey<String> NAME = new MetaDataKey<String>() {};
    public MetaDataKey<String> ATTRIBUTES = new MetaDataKey<String>() {};
    public MetaDataKey<String> OPTIONS = new MetaDataKey<String>() {};

    public String getName();

    public String getType();

    public String getProperty();

    public Map<String, String> getAttributes();

    public Component create(String id);

}
