package forms.widgets.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class GroupConfig<T extends Component & HasConfig> extends Config<T> {

    private @DontSendInJson boolean renderBodyOnly = false;
    private @DontSendInJson List<Config> configs = Lists.newArrayList();
    private String title;

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

    public Boolean getRenderBodyOnly() {
        return renderBodyOnly;
    }

    public GroupConfig withRenderBodyOnly(Boolean renderBodyOnly) {
        this.renderBodyOnly = renderBodyOnly;
        return this;
    }

    public GroupConfig withConfigs(Config... configs) {
        return withConfigs(Lists.newArrayList(configs));
    }

    public GroupConfig withConfigs(@Nonnull List<Config> configs) {
        this.configs = configs;
        return this;
    }

    public GroupConfig withConfig(Config config) {
        configs.add(config.withParent(this));
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

    public Config getConfigWithName(@Nonnull String name) {
        List<Config> c = getConfigsDeep();
        for (Config config:c) {
            if (name.equals(config.getId())) {
                return config;
            }
        }
        throw new IllegalArgumentException("can't find config with name " + name);
    }

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

}
