package demo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.wicket.util.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class JerseyRestService implements RestService {


    @Override
    public InputStream doRest(String url) {
        Client client = getClient();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.accept("application/pdf").get(ClientResponse.class);
        //        response.getEntity(String.class);
        InputStream is = response.getEntity(InputStream.class);
        return is;
    }


    /*package protected*/ Client getClient() {
        return Client.create();
    }

}
