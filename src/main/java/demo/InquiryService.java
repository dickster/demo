package demo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.wicket.util.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class InquiryService {

    private FooBar fooBar;

    /**
     * Hello world.
     *
     * @return the string
     */
    public InputStream helloWorld() {
        //URL from profile properties
        String getUrl = getUrl();

        System.out.println(getUrl+"<-------");

        InputStream inputStream = getRestService().doRest(getUrl);
        String result = null;
//
//        try {
//            result = IOUtils.toString(inputStream, "UTF-8");
//        } catch (IOException ex) {
//            result = "PDF not found";
//        } finally {
//            IOUtils.closeQuietly(inputStream);
//        }
//        System.out.println("<---------------Result ------------------------------>");
//        System.out.println("" + result);
//        System.out.println("<---------------Result ------------------------------>");
//
//        return result;
        return inputStream;
    }

    public String getUrl() {
        FooBar fb = FooBar.getInstance();
        return fb.get();
//        return "http://www.polyu.edu.hk/iaee/files/pdf-sample.pdf";
    }

    public FooBar getFooBar() {
        return new FooBar();
    }

    public RestService getRestService() {
        return new JerseyRestService();
    }
}
