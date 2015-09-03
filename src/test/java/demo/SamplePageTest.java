package demo;

import demo.wicketTest.BrovadaWicketTestRunner;
import demo.wicketTest.BrovadaWicketTestRunner.WithContext;
import demo.wicketTest.IFixtureFactory;
import demo.wicketTest.TestContextHandler;
import demo.wicketTest.WicketPageTest;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.TagTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

@RunWith(BrovadaWicketTestRunner.class)
public class SamplePageTest extends WicketPageTest<SamplePageHarness, SamplePage> {

    private SampleService sampleService;

    @Override
	@Before
    public void setUp() throws Exception {
    	super.setUp();
        sampleService = wire(SampleService.class);
    }

    @Test
    public void test_submit()  {
        expectingSampleService();

        renderFixture(this);

        debugComponentTrees();

        getHarness().setText("invalidEmailAddress");

        getHarness().submit();

        List<Serializable> messages = getWicketTester().getMessages(FeedbackMessage.ERROR);
        System.out.println(messages);
        assertEquals("text.EmailAddressValidator", messages.get(0).toString());

        debugComponentTrees();

        verifyMocks(sampleService);
    }

    @Test
    public void test_ajax()  {
        expectingSampleService();

        renderFixture(this);

        getHarness().clickText();
        assertComponentOnAjaxResponse(getHarness().getText());

        assertInvisible("form:label");
        assertVisible("form:text");

        verifyMocks(sampleService);
    }

    @Override
    protected SamplePageHarness getHarness() {
        return super.getHarness();
    }

    @Test
    public void test_tags()  {
        expectingSampleService();

        renderFixture(this);

        TagTester tagTester = getWicketTester().getTagByWicketId("text");
        System.out.println("foo type : " + tagTester.getAttribute("type"));
        System.out.println("foo css class : " + tagTester.getAttribute("class"));

        verifyMocks(sampleService);
    }


    @Test
    public void test_sampleService()  {
        expectingSampleService();

        renderFixture(this);

        debugComponentTrees();

        verifyMocks(sampleService);
    }

    @Test
    public void test_customFixture()  {
        expectingSampleService();
        // if you want to create a different fixture for each test.
        renderFixture(new IFixtureFactory<SamplePage>() {
            @Override public SamplePage createFixture(String id) {
                return new SamplePage(new PageParameters().add("customParam", "foobar"));
            }
        });
        verifyMocks(sampleService);
    }

    @Test
    public void test_customPageFixture()  {
        expectingSampleService();
        // if you want to create a different fixture for each test.
        renderFixture(new SamplePage(new PageParameters().add("customParam", "foobar")));
        verifyMocks(sampleService);
    }

    @Test
    @WithContext()
    public void test_defaultusers()  {
        expectingSampleService();
        renderFixture();
        assertVisible(getHarness().getText());
        verifyMocks(sampleService);
    }

    @Test
    @WithContext({"READ_ONLY"})
    public void test_users2() {
        expectingSampleService();
        renderFixture();
        assertInvisible(getHarness().getText());
        verifyMocks(sampleService);
    }

    @Test
    public void test_nestedHarness()  {
        expectingSampleService();
        renderFixture();

        assertModelValue(getHarness().getAddressHarness().getText(), null);

        String expectedAddr = "123 foobar ave, toronto, ont";
        getHarness().getAddressHarness().setAddress(expectedAddr);
        getHarness().submit();
        assertModelValue(getHarness().getAddressHarness().getText(), expectedAddr);

        verifyMocks(sampleService);
    }

    private void expectingSampleService() {
        expect(sampleService.helloWorld()).andReturn("bonjour");
        replay(sampleService);
    }

    @Override
	public SamplePage createFixture(String id) {
		return new SamplePage(new PageParameters());
	}

    @Override
    protected Class<SamplePageHarness> getHarnessClass() {
        return SamplePageHarness.class;
    }

    @Override
    protected TestContextHandler getContextHandler() {
        //override this to handle context stuff specifically.  for example if you had a custom session class for your application.
        return new CustomTestContextHandler();
    }
}
