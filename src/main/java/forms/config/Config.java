package forms.config;

import org.apache.wicket.MetaDataKey;

import java.io.Serializable;

public interface Config extends Serializable {

    public MetaDataKey<String> NAME = new MetaDataKey<String>() {};

    public String getName();

    public String getType();

}
