package demo.wicketTest;

import org.apache.wicket.Component;
import org.apache.wicket.util.tester.DummyPanelPage;
import org.junit.Before;


public abstract class WicketComponentTest<T extends WicketHarness, F extends Component> extends WicketTest<T,F> {

    @Override
	@Before 
	public void setUp() throws Exception {
		super.setUp();
		setPath(DummyPanelPage.TEST_PANEL_ID);
	}
	
	@Override
	public void renderFixture() {
		getWicketTester().startComponentInPage(this.createFixture(DummyPanelPage.TEST_PANEL_ID));
	}

}
