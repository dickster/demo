


//package forms;
//
//import demo.wicketTest.BrovadaWicketTestRunner;
//import demo.wicketTest.WicketHarness;
//import demo.wicketTest.WicketTest;
//import org.apache.wicket.Component;
//import org.apache.wicket.markup.html.basic.Label;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(BrovadaWicketTestRunner.class)
//public class MappingConfigTest extends WicketTest<WicketHarness,Component> {
//
//    MappingConfig<Source,Dest> config;
//    private Dest dest;
//    private Source source;
//
//    @Test
//    public void test_Simple() {
//        config = new MappingConfig<Source, Dest>();
//        source = new Source();
//        dest = new Dest();
//        config.addMapping("s1.s1A", "d1.d1xxx");
//        config.addMapping("s1.s1b", "d1.x");
//        config.addMapping("hello", "d1.y");
//        config.addMapping("foo", "d2.destFoo");
//        config.mapFromAcordToImpl(source, dest);
//        System.out.println(dest);
//    }
//
//    @Override
//    public Component createFixture(String id) {
//        return new Label(id);
//    }
//
//    @Override
//    protected Class<WicketHarness> getHarnessClass() {
//        return WicketHarness.class;
//    }
//
//    @Override
//    public void renderFixture(Component fixture) {
//
//    }
//
//
//    class Source {
//        S1 s1;
//        S2 s2;
//        String hello = "hello";
//        Object foo = "foo";
//
//    }
//
//    class S1 {
//        String s1A = "s1A";
//        String s1b = "s1b";
//    }
//
//    class S2 {
//        String s2A = "s2A";
//        String s2b = "s2b";
//    }
//
//    class Dest {
//        D1 d1;
//        D2 d2;
//    }
//
//    class D1 {
//        String d1XXX;
//        String x,y,z;
//    }
//
//    class D2 {
//        String destHello;
//        Object destFoo;
//    }
//
//}
//
//
//



package forms;

import demo.SamplePage;
import demo.SampleService;
import demo.wicketTest.BrovadaWicketTestRunner;
import demo.wicketTest.WicketHarness;
import demo.wicketTest.WicketPageTest;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

@RunWith(BrovadaWicketTestRunner.class)
public class MappingConfigTest extends WicketPageTest<WicketHarness, SamplePage> {

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

        //getHarness().setText("invalidEmailAddress");

        getHarness().submit();

        List<Serializable> messages = getWicketTester().getMessages(FeedbackMessage.ERROR);
        System.out.println(messages);
       // assertEquals("text.EmailAddressValidator", messages.get(0).toString());

        debugComponentTrees();

        verifyMocks(sampleService);
    }


    @Override
    protected WicketHarness getHarness() {
        return super.getHarness();
    }

//    @Test
//    public void test_tags()  {
//        expectingSampleService();
//
//        renderFixture(this);
//
//        TagTester tagTester = getWicketTester().getTagByWicketId("text");
//        System.out.println("foo type : " + tagTester.getAttribute("type"));
//        System.out.println("foo css class : " + tagTester.getAttribute("class"));
//
//        verifyMocks(sampleService);
//    }
//
//
//    @Test
//    public void test_sampleService()  {
//        expectingSampleService();
//
//        renderFixture(this);
//
//        debugComponentTrees();
//
//        verifyMocks(sampleService);
//    }
//
//    @Test
//    public void test_customFixture()  {
//        expectingSampleService();
//        // if you want to create a different fixture for each test.
//        renderFixture(new IFixtureFactory<SamplePage>() {
//            @Override public SamplePage createFixture(String id) {
//                return new SamplePage(new PageParameters().add("customParam", "foobar"));
//            }
//        });
//        verifyMocks(sampleService);
//    }
//
//    @Test
//    public void test_customPageFixture()  {
//        expectingSampleService();
//        // if you want to create a different fixture for each test.
//        renderFixture(new SamplePage(new PageParameters().add("customParam", "foobar")));
//        verifyMocks(sampleService);
//    }
//
//    @Test
//    @WithContext()
//    public void test_defaultusers()  {
//        expectingSampleService();
//        renderFixture();
//        assertVisible(getHarness().getText());
//        verifyMocks(sampleService);
//    }
//
//    @Test
//    @WithContext({"READ_ONLY"})
//    public void test_users2() {
//        expectingSampleService();
//        renderFixture();
//        assertInvisible(getHarness().getText());
//        verifyMocks(sampleService);
//    }
//
//    @Test
//    public void test_nestedHarness()  {
//        expectingSampleService();
//        renderFixture();
//
//        assertModelValue(getHarness().getAddressHarness().getText(), null);
//
//        String expectedAddr = "123 foobar ave, toronto, ont";
//        getHarness().getAddressHarness().setAddress(expectedAddr);
//        getHarness().submit();
//        assertModelValue(getHarness().getAddressHarness().getText(), expectedAddr);
//
//        verifyMocks(sampleService);
//    }

    private void expectingSampleService() {
        expect(sampleService.helloWorld()).andReturn("bonjour");
        replay(sampleService);
    }

    @Override
    public SamplePage createFixture(String id) {
        return new SamplePage(new PageParameters());
    }

    @Override
    protected Class<WicketHarness> getHarnessClass() {
        return WicketHarness.class;
    }

//    @Override
//    protected TestContextHandler getContextHandler() {
//        //override this to handle context stuff specifically.  for example if you had a custom session class for your application.
//        return new CustomTestContextHandler();
//    }
}
