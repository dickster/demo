package forms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

import java.util.List;
import java.util.Set;

public class FormConfig<T> implements HasWidgetConfigs {

    private String name;
    private String title;
    private String version;
    private Class<T> modelObject;
    private List<WidgetConfig<?>> widgetConfigs = Lists.newArrayList();
    private IFormValidator validator;

    public FormConfig() {
    }

    public List<WidgetConfig<?>> getWidgetConfigs() {
        return ImmutableList.copyOf(widgetConfigs);
    }

    public void validateAcordVersion(String version) throws IllegalStateException {
        if (StringUtils.isBlank(version)) {
            return;
        }

        Set<String> acordVersions = Sets.newHashSet();
        for (WidgetConfig config:widgetConfigs) {
            String v = config.getAcordVersion();
            if (v != null) {
                acordVersions.add(v);
            }
        }
        if (acordVersions.size()>1) {
            throw new IllegalStateException("there are conflicting versions of acord in this form --> " + acordVersions);
        }
        if (acordVersions.size()==1 && !acordVersions.contains(version)) {
            throw new IllegalStateException("this form is supposed to be Acord Version : " + version  +  " but contains fields for version " + acordVersions.iterator().next());
        }
        // note : it's possible that form could contain NO acord fields. (which is valid, no exceptions thrown).
    }


}
