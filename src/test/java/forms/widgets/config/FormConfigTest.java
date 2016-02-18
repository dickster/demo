package forms.widgets.config;

import com.google.gson.Gson;
import forms.impl.Demo3FormConfig;
import forms.impl.Form1Config;
import forms.util.ConfigGson;
import junit.framework.TestCase;
import org.junit.Test;

public class FormConfigTest extends TestCase {

    private FormConfig formConfig;

    @Test
    public void test_serialization() {
        Gson gson = new ConfigGson().forSerialization();
        formConfig = new Form1Config();
        String result = gson.toJson(formConfig);
        System.out.println(result);

        FormConfig config = gson.fromJson(result, FormConfig.class);
        System.out.println(config);

        formConfig = new Demo3FormConfig();
        result = gson.toJson(formConfig);
        System.out.println(result);

        config = gson.fromJson(result, FormConfig.class);
        System.out.println(config);
    }

    @Test
    public void test_rendering() {
        Gson gson = new ConfigGson().forRendering();
        formConfig = new Form1Config();
        String result = gson.toJson(formConfig);
        System.out.println(result);

        FormConfig config = gson.fromJson(result, FormConfig.class);
        System.out.println(config);

        formConfig = new Demo3FormConfig();
        result = gson.toJson(formConfig);
        System.out.println(result);

        config = gson.fromJson(result, FormConfig.class);
        System.out.println(config);
    }


}
