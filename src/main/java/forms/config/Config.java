package forms.config;

import org.apache.wicket.MetaDataKey;

public interface Config {

    public MetaDataKey<String> NAME = new MetaDataKey<String>() {};

    public String getName();

    public String getType();

}
