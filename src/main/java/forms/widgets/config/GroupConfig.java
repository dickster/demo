package forms.widgets.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import forms.WidgetTypeEnum;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class GroupConfig<T extends Component & HasConfig & HasTemplate> extends Config<T> {

    private List<Config> configs = Lists.newArrayList();
    private @Expose String title;
    private String template;


    public GroupConfig(@Nonnull String name) {
        super(name, WidgetTypeEnum.CONTAINER);
    }

    public GroupConfig(@Nonnull String name, WidgetTypeEnum type) {
        super(name, type);
    }

    public List<Config> getConfigs() {
        return ImmutableList.copyOf(configs);
    }

    public String getTitle() {
        return title;
    }

    public GroupConfig withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public final String getType() {
        return WidgetTypeEnum.GROUP.toString();
    }

    public GroupConfig withConfigs(Config... configs) {
        return withConfigs(Lists.newArrayList(configs));
    }

    public GroupConfig withConfigs(@Nonnull List<Config> configs) {
        this.configs.addAll(configs);
        return this;
    }

    public GroupConfig withConfig(Config config) {
        configs.add(config);
        return this;
    }
    public GroupConfig withConfig(Config config, int index) {
        configs.add(index, config);
        return this;
    }
    // ALIAS - maybe this short form is better?
    public <T extends GroupConfig>  T  with(Config config) {
        return (T) withConfig(config);
    }


    @Override
    public String getProperty() {
        return getId();
    }

    @Override
    public abstract T create(String id);

    private List<Config> getConfigsDeep() {
        List<Config> result = Lists.newArrayList();
        result.add(this);
        for (Config config:configs) {
            result.add(config);
            if (config instanceof GroupConfig) {
                result.addAll(((GroupConfig)config).getConfigsDeep());
            }
        }
        return result;
    }

    public GroupConfig<T> withTemplate(String template) {
        this.template = template;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public GroupConfig<T> injectTemplateId(String templateId) {
        withOption("templateId", templateId);
        return this;
    }
}
