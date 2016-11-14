package demo.wicketTest;

import org.apache.wicket.Session;

public class BrovadaTestContextHandler implements TestContextHandler {

	@Override
	public void initializeContext(Session session, BrovadaWicketTestContext tc) {
        // setup values here...you'll typically want to decode parameter and create some object.
        // e.g. Locale = new Locale(tc.getId());  session.setLocale(etc..)
        // recall the tc.getId() is just a string (to avoid project dependencies).
        session.setAttribute("user", tc.getName());
    }

}
