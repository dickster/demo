package demo.wicketTest;

import org.apache.wicket.Session;

public interface TestContextHandler {
	
	void initializeContext(Session session, BrovadaWicketTestContext tc);

}
