package demo;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import forms.StartingPoint;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.ResourceAggregator;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.time.Duration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WicketApplication extends WebApplication {

    private static final String COMPRESSED_BROVADA_JS = "brovada.min.js";

    private CompiledJavaScriptBundleReference bundle;
    private Set<String> unbundledItems = Collections.synchronizedSet(new HashSet<String>());

    public WicketApplication() {
        super();
    }

    public Class getHomePage() {
        return StartingPoint.class;
//          return KeysPage.class;
//        return GridstackPage.class;
        //return HomePage.class;
//        return ManagerPage.class;
    }

    @Override
    protected void init() {

        super.init();

        switch (this.getConfigurationType()) {
            case DEVELOPMENT :
                this.getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
                this.getDebugSettings().setComponentUseCheck(false);
//                this.getMarkupSettings().setStripWicketTags(false);
this.getMarkupSettings().setStripWicketTags(true);
                break;

            case DEPLOYMENT :
                this.getResourceSettings().setResourcePollFrequency(null);
                this.getDebugSettings().setComponentUseCheck(false);
                this.getMarkupSettings().setStripWicketTags(true);
                break;

            default:
                throw new RuntimeException("Unknown ConfigurationType: " + this.getConfigurationType());
        }

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
                //        if (getConfigurationType().equals(RuntimeConfigurationType.DEPLOYMENT)) {
        addBundle();
                //        }

//        getRequestCycleSettings().setRenderStrategy(RenderStrategy.ONE_PASS_RENDER);

        //mountPage("keys/foo", KeysPage.class);

        if (getConfigurationType().equals(RuntimeConfigurationType.DEVELOPMENT)) {
            getComponentInitializationListeners().add(new PerformanceListener());
        }

    }

    private void addBundle() {
        this.setHeaderResponseDecorator(new IHeaderResponseDecorator() {
            @Override public IHeaderResponse decorate(IHeaderResponse response) {
                return response instanceof ResourceAggregator ?
                        new ValidatingResourceAggregator((ResourceAggregator) response) : response;
            }
        });

        CompiledJavaScriptBundleReference bundleReference = getJavaScriptBundleReference();
        getResourceBundles().addBundle(JavaScriptHeaderItem.forReference(bundleReference));
    }

    protected CompiledJavaScriptBundleReference getJavaScriptBundleReference() {
        if (bundle==null) {
            bundle = new CompiledJavaScriptBundleReference(getClass(), COMPRESSED_BROVADA_JS, getHeaderItems())
                    .withCompiler(new ClosureJavaScriptCompiler());
        }
        return bundle;
    }

    private List<JavaScriptReferenceHeaderItem> getHeaderItems() {
        List<JavaScriptResourceReference> references = Lists.newArrayList(
//                new JavaScriptResourceReference(GroupPanel.class, "groupPanel.js"),
                new JavaScriptResourceReference(EasyAddress.class, "address.js"),
                new JavaScriptResourceReference(EasyTextArea.class, "easyTextArea.js"));

        return Lists.newArrayList(Iterables.transform(references, new Function<JavaScriptResourceReference, JavaScriptReferenceHeaderItem>() {
            @Override public JavaScriptReferenceHeaderItem apply(final JavaScriptResourceReference reference) {
                return JavaScriptReferenceHeaderItem.forReference(reference);
            }
        }));
    }

    private boolean validateJavaScriptReferenceHeaderItem(JavaScriptReferenceHeaderItem item) {
        boolean inBundle = getJavaScriptBundleReference().contains(item);
        String name = item.getReference().getName();
        // i should check to see that not looking for the bundled .js itself (BROVADA.MIN.JS)
        if (!inBundle && !name.contains("/") && !unbundledItems.contains(name)) {
            // TODO : use logger..
            System.out.println(unbundledItems);
            unbundledItems.add(name);
            System.out.println("the javascript reference [" + item.getReference().getName() + "] is NOT in the resource bundle.");
        }
        return inBundle;
    }

    public Set<String> getUnbundledItems() {
        Joiner.on("/").skipNulls().useForNull("{}").join(Lists.newArrayList("joe",null,"smith"));
        return unbundledItems;

    }



    class ValidatingResourceAggregator implements IHeaderResponse {
        private ResourceAggregator delegate;

        public ValidatingResourceAggregator(ResourceAggregator delegate) {
            this.delegate = delegate;
        }

        @Override
        public void markRendered(Object object) {
            delegate.markRendered(object);
        }

        @Override
        public boolean wasRendered(Object object) {
            return delegate.wasRendered(object);
        }

        @Override
        public void render(HeaderItem item) {
            if (item instanceof JavaScriptReferenceHeaderItem) {
                validateJavaScriptReferenceHeaderItem((JavaScriptReferenceHeaderItem) item);
            }
            delegate.render(item);
        }

        @Override
        public void close() {
            delegate.close();
        }

        @Override
        public Response getResponse() {
            return delegate.getResponse();
        }

        @Override
        public boolean isClosed() {
            return delegate.isClosed();
        }
    }

}