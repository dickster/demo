package demo.wicketTest;

import org.apache.wicket.markup.html.WebPage;


public abstract class WicketPageTest<T extends WicketHarness, F extends WebPage> extends WicketTest<T,F> {

    protected static final String PAGE_CONTEXT = "";

    @Override
    protected String getFixtureId() {
        return PAGE_CONTEXT;
    }

    @Override
	public void renderFixture(F page) {
		getWicketTester().startPage(page);
	}


}

