package demo.wicketTest;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.tester.DummyPanelPage;

public abstract class WicketPanelTest<T extends WicketHarness, F extends Panel> extends WicketTest<T,F> {

    @Override
    protected String getFixtureId() {
        return DummyPanelPage.TEST_PANEL_ID;
    }

    @Override
	public void renderFixture(F fixture) {
        getWicketTester().startComponentInPage(fixture);
	}

}

