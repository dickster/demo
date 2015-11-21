package forms.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import forms.Group;
import forms.WidgetTypeEnum;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.util.List;

public class GroupConfig extends AbstractConfig {

    private String title;
    private Boolean renderBodyOnly = false;
    private List<Config> configs = Lists.newArrayList();


    public GroupConfig(@Nonnull String name) {
        super(name, WidgetTypeEnum.CONTAINER);
    }

    public List<Config> getConfigs() {
        return ImmutableList.copyOf(configs);
    }

//    protected List<WidgetConfig> getWidgetConfigs() {
//        List<WidgetConfig> result = Lists.newArrayList();
//        for (Config config:configs) {
//            if (config instanceof WidgetConfig) {
//                result.add((WidgetConfig)config);
//            }
//            else if (config instanceof GroupConfig) {
//                result.addAll(((GroupConfig)config).getWidgetConfigs());
//            }
//        }
//        return result;
//    }

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
        configs.add(config);
        return this;
    }
    public GroupConfig withConfig(Config config, int index) {
        configs.add(index, config);
        return this;
    }
    // ALIAS - maybe this short form is better?
    public GroupConfig with(Config config) {
        return withConfig(config);
    }


    @Override
    public String getProperty() {
        return getName();
    }

    @Override
    public Component create(String id) {
        return new Group(id, this);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Config getConfigWithName(@Nonnull String name) {
        List<Config> c = getConfigsDeep();
        for (Config config:c) {
            if (name.equals(config.getName())) {
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
