package demo.wicketTest;

import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public abstract class WicketFormComponentTest<T extends WicketHarness, F extends Component> extends WicketTest<T,F> {

    // see HTML of containing page/form for usage of these wicket ids.
    public static final String FORM_ID = "form";
    public static final String COMPONENT_ID = "component-id";

    @Override
    protected String getFixtureId() {
        return COMPONENT_ID;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setPath(FORM_ID, getPath());
    }

    @Override
    public void renderFixture() {
        super.renderFixture();
    }

    @Override
	public void renderFixture(final F component) {
        DummyFormPage page = new DummyFormPage<F>(new PageParameters()) {
            @Override
            protected F createComponent(String id) {
                return component;
            }
        };
		getWicketTester().startPage(page);
	}




}

