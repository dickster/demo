package forms.config;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.List;

public class SectionConfig {

    private int min=1, max = Integer.MAX_VALUE;

    private List<Config> configs = Lists.newArrayList();

    public SectionConfig(@Nonnull String name) {
       // super(name);
    }

    public SectionConfig withMin(int min) {
        this.min = min;
        return this;
    }

    public SectionConfig withMax(int max) {
        this.max = max;
        return this;
    }

    public SectionConfig withAtLeastOne() {
        this.min = 0;
        this.max = Integer.MAX_VALUE;
        return this;
    }

}
