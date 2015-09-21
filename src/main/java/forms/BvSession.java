package forms;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class BvSession extends WebSession {

    public BvSession(Request request) {
        super(request);
    }

}
