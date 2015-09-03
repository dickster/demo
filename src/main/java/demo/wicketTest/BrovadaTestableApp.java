package demo.wicketTest;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;


public class BrovadaTestableApp<T extends Session> extends MockApplication {

	private ComponentTestInjector injector;
//	private User user = new User();
	private Session session;
	
	public BrovadaTestableApp(ComponentTestInjector injector) {
		this.injector = injector;
        injector.getInjector().bind(this);
	}

    /**
     * CAVEAT : if you override this, the rendering of the pages won't be the same.
     * for example, some TagTester related code won't work because wicket:ids aren't rendered.
     *
     */
//    @Override
//    public RuntimeConfigurationType getConfigurationType() {
//        return RuntimeConfigurationType.DEPLOYMENT;
//    }

    @Override
	protected void init() {
        getComponentInstantiationListeners().add(new IComponentInstantiationListener() {
            @Override
            public void onInstantiation(Component component) {
                injector.inject(component);
            }
        });
		super.init();
	}

	@Override
	public Session newSession(Request request, Response response) {
		//  NOTE : in order to make tests able to just set session data once before startPage or rendering stuff, i only create a new session 
		//  the first time thru. 
		if (session==null) { 
			session = createSession(request);
        }
		/* TODO : populate this with required fields like user etc..*/
		return session;
	}

    protected T createSession(Request request) {
        // override this if you have a custom session type and you want to add plunk values in it.
        WebSession session = new WebSession(request);
        return (T) session;
    }

}	
	
	
