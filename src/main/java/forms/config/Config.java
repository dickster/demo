package forms.config;

import forms.WidgetTypeEnum;
import org.apache.wicket.MetaDataKey;

public interface Config {

    public MetaDataKey<String> NAME = new MetaDataKey<String>() {};

    // marker interface for GroupConfig or WidgetConfig.
    public String getName();

    public WidgetTypeEnum getType();


}
