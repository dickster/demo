package forms.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class GroupConfig implements Config {

    private String title;
    private String name;
    private boolean useDefaultLayout = true;
    private Boolean renderBodyOnly = false;
    private String css = "form_group";
    private int min=1, max = Integer.MAX_VALUE;

    private List<Config> configs = Lists.newArrayList();

    public GroupConfig(@Nonnull String name) {
        this.name = name;
        withTitle(name); // use name as default title.
    }

    public List<Config> getConfigs() {
        return ImmutableList.copyOf(configs);
    }

    protected List<WidgetConfig> getWidgetConfigs() {
        List<WidgetConfig> result = Lists.newArrayList();
        for (Config config:configs) {
            if (config instanceof WidgetConfig) {
                result.add((WidgetConfig)config);
            }
            else if (config instanceof GroupConfig) {
                result.addAll(((GroupConfig)config).getWidgetConfigs());
            }
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public GroupConfig withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public final String getType() {
        return WidgetTypeEnum.GROUP.toString();
    }

    public GroupConfig withName(String name) {
        this.name = name;
        return this;
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

    // ALIAS - maybe this short form is better?
    public GroupConfig with(Config config) {
        return withConfig(config);
    }

    public boolean isUseDefaultLayout() {
        return useDefaultLayout;
    }

    public GroupConfig usingDefaultLayout(boolean useDefaultLayout) {
        this.useDefaultLayout = useDefaultLayout;
        return this;
    }

    public String getCss() {
        return css;
    }

    public GroupConfig withCss(String css) {
        this.css = css;
        return this;
    }

    public @Nullable Config getConfigWithName(@Nonnull String name) {
        List<Config> c = getConfigsDeep();
        for (Config config:c) {
            if (name.equals(config.getName())) {
                return config;
            }
        }
        throw new IllegalArgumentException("can't find config with name " + name);
    }

    protected List<Config> getConfigsDeep() {
        List<Config> result = Lists.newArrayList();
        for (Config config:configs) {
            result.add(config);
            if (config instanceof GroupConfig) {
                result.addAll(((GroupConfig)config).getConfigsDeep());
            }
        }
        return result;
    }

}
