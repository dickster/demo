package forms;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

import java.util.Set;

public class FormConfig<T> extends GroupConfig {

    private String version;
    private IFormValidator validator;

    public FormConfig() {
        setRenderBodyOnly(false);
    }


    public void validateAcordVersion(String version) throws IllegalStateException {
        if (StringUtils.isBlank(version)) {
            return;
        }

        Set<String> acordVersions = Sets.newHashSet();
        for (WidgetConfig config: getWidgetConfigs()) {
            String v = config.getAcordVersion();
            if (StringUtils.isNotBlank(v)) {
                acordVersions.add(v);
            }
        }
        if (acordVersions.size()>1) {
            throw new IllegalStateException("there are conflicting versions of acord in this form --> " + acordVersions);
        }
        if (acordVersions.size()==1 && !acordVersions.contains(version)) {
            throw new IllegalStateException("this form is supposed to be Acord Version : " + version  +  " but contains fields for version " + acordVersions.iterator().next());
        }
        // if (acordVersions.size()==0) {   // that's ok. it's possible to have no acord fields on a form
    }



}
