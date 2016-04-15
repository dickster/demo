package demo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.spi.MessageBodyWorkers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.easymock.PowerMock.*;

import javax.ws.rs.core.Response;
import java.io.InputStream;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.verify;


@RunWith( PowerMockRunner.class )
@PrepareForTest( {InquiryService.class, FooBar.class} )
public class InquiryServiceTest {

    public static final String TEST_URL = "BOGUS-URL";//http://www.polyu.edu.hk/iaee/files/pdf-sample.pdf";
    private InquiryService sut;

    @Before
    public void setUp() {
    }

    @Test
    public void test_inquiry() {
        PowerMock.mockStatic(FooBar.class);
        final FooBar mockFoo = createMock(FooBar.class);
        final RestService mockRest = createMock(RestService.class);
        sut = new InquiryService() {
//            @Override
//            public FooBar getFooBar() {
//                return mockFoo;
//            }

            @Override
            public RestService getRestService() {
                return mockRest;
            }
        };

        InputStream is = null;
        expect( FooBar.getInstance() ).andReturn(mockFoo);
        expect(mockFoo.get()).andReturn(TEST_URL);
        expect(mockRest.doRest(TEST_URL)).andReturn(is);
        PowerMock.replay(mockFoo, mockRest, FooBar.class);

        InputStream result = sut.helloWorld();

        assert(result==null);

        verify(mockFoo, mockRest, FooBar.class);

    }



    class BogusResponse extends ClientResponse {

        public BogusResponse(Response.StatusType statusType, InBoundHeaders headers, InputStream entity, MessageBodyWorkers workers) {
            super(statusType, headers, entity, workers);
        }



    }



}



