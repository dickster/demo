package forms.widgets;

import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;

public class DelegatingComponent<C extends Config> extends Component implements HasConfig<C> {

    // see #AbstractOutputTransformerContainer or  #AbstractTransformerBehavior
    // attach one of them to each.

    private final C config;
    private final Component delegate;

    public DelegatingComponent(String id, C config, Component delegate) {
        super(id);
        this.config = config;
        this.delegate = delegate;
    }

    @Override
    protected void onRender() {
        delegate.internalRenderComponent();
    }

    @Override
    public C getConfig() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
