package forms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import demo.PageLayout;
import demo.wicketTest.WicketPageTest;
import forms.config.Config;
import forms.config.RowConfig;
import forms.config.SectionConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.junit.Before;
import org.junit.Test;

/**
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
 *
 *
 */
public class SectionConfigTest extends WicketPageTest {

    private PageLayout layout;
    private Gson gson;

    @Before
    public void setup() {
        layout = new PageLayout();
        gson = new GsonBuilder().create();
    }

    @Test
    public void test_sectionConfig() {
        Label c1 = new Label("foo");
        Label c2 = new Label("bar");
        Label d1 = new Label("x");
        Label d2 = new Label("y");
        setName(c1, c2, d1, d2);
        SectionConfig sec = new SectionConfig("TEST").withRow(c1,c2).withRow(d1,d2);
        layout.add(sec);
        System.out.println(gson.toJson(layout));
    }
    @Test
    public void test_sectionConfigNoName() {
        Label c1 = new Label("foo");
        Label c2 = new Label("bar");
        Label d1 = new Label("x");
        Label d2 = new Label("y");
        setName(c1, c2, d1, d2);
        SectionConfig sec = new SectionConfig().withRow(c1,c2).withRow(d1,d2);
        layout.add(sec);
        System.out.println(gson.toJson(layout));
    }

    private void setName(Component... comps) {
        for (Component c:comps) {
            c.setMetaData(Config.NAME, c.getId());
        }
    }

    @Test
    public void test_sectionConfig1a() {
        Label c1 = new Label("foo");
        Label c2 = new Label("bar");
        setName(c1, c2);
        layout.add(new SectionConfig("SECTION")
                .withRow(new RowConfig()
                        .withCol(c1, PageLayout.COL_MD_1)
                        .withCol(c2, PageLayout.COL_MD_3)));
        System.out.println(gson.toJson(layout));
    }

    @Test
    public void test_sectionConfig2() {
        Label c1 = new Label("foo");
        Label c2 = new Label("bar");
        Label d1 = new Label("hello");
        Label d2 = new Label("world");
        setName(c1, c2, d1, d2);
        layout.add(new SectionConfig("SA").withRow(c1, c2))
              .add(new SectionConfig("SB").withRow(d1, d2));
        System.out.println(gson.toJson(layout));
    }

    @Test
    public void test_sectionConfig3() {
        Label c1 = new Label("foo");
        Label c2 = new Label("bar");
        setName(c1, c2);
        layout.add(new SectionConfig("sec")
                .withRow(new RowConfig(c1, c2)));
        System.out.println(gson.toJson(layout));
    }

    @Test
    public void test_sectionConfig4() {
    // TODO: create one based on settings of WebPage.
        // e.g.
        // row.1=c1,c2
        // c1.col=col-md-4
        // c2.col=col-md-2
        //  .: a simple properties file could layout page.
        // actually, it's probably easier just to define a json object.

    }

    @Override
    public Component createFixture(String id) {
        return new Label("foo");
    }

    @Override
    protected Class getHarnessClass() {
        return String.class;
    }

    @Override
    public void renderFixture(Component fixture) {

    }
}




