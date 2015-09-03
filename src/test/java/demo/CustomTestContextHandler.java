package demo;

import demo.wicketTest.BrovadaWicketTestContext;
import demo.wicketTest.TestContextHandler;
import org.apache.wicket.Session;

public class CustomTestContextHandler implements TestContextHandler {

    @Override
    public void initializeContext(Session session, BrovadaWicketTestContext tc) {
        session.setAttribute("custom-user", tc.getName());
    }
}
