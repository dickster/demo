package demo;

import com.google.common.collect.Lists;
import demo.resources.Resource;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.validation.validator.PatternValidator;

import java.util.Date;
import java.util.List;

public class HomePage extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final JavaScriptResourceReference LIBPHONENUMBER_JS = new JavaScriptResourceReference(Resource.class, "libphonenumber.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-multiselect.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");
    private static final JavaScriptResourceReference MULTISELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.js");
    private static final CssResourceReference MULTISELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-multiselect.css");
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/typeahead.bundle.js");
    private static final CssResourceReference TYPEAHEAD_CSS = new CssResourceReference(Resource.class,"bootstrap-3.1.1-dist/css/typeahead.bootstrap.css");

    private final EasyFeedback feedback;
    private List<ISection> sections = Lists.newArrayList();


    // test data....
    List<InsuredTab> insured = Lists.newArrayList(
            new InsuredTab("Petroslav Kablowskisapalbhaala"),
            new InsuredTab("Derek Dick"),
            new InsuredTab("John D'oh")
    );
    List<ConvictionTab> convictions = Lists.newArrayList(
            new ConvictionTab("DUI"),
            new ConvictionTab("robbery"),
            new ConvictionTab("assault")
    );

    public HomePage(final PageParameters parameters) {

        add(new Form("form")
                .add(new SkypeLink("skype"))
                .add(new AjaxSubmitLink("submit") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        feedback.update(target, form);
                    }

                    @Override
                    protected void onError(AjaxRequestTarget target, Form<?> form) {
                        super.onError(target, form);
                        feedback.update(target, form);
                    }
                })
                .add(new EasyTabbedPanel<InsuredTab>("insureds", insured, Model.of("Insured")) {
                    @Override protected InsuredTab createNewTab() {
                        return new InsuredTab("New Person")
                                .withDefault(getTab(0));
                    }

                    @Override public String getHref() {
                        return "#one";
                    }
                }.allowingOneOrMore()
                    .withAddTooltip("Add Insured Person")
                    .setStatus(FeedbackState.HAS_INFO))
//                .add(new EasyTabbedPanel<ContactTab>("contact", new ContactTab("New Contact"), Model.of("Contact")) {
//                    @Override public String getHref() {
//                        return "#two";
//                    }
//                }.allowingOnlyOne())
//                .add(new EasyTabbedPanel<ConvictionTab>("convictions", convictions, Model.of("Convictions")) {
//                    @Override
//                    protected ConvictionTab createNewTab() {
//                        return new ConvictionTab("New Conviction");
//                    }
//
//                    @Override
//                    public String getHref() {
//                        return "#three";
//                    }
//
//                    @Override
//                    protected EasyTabbedPanelOptions getOptions() {
//                        EasyTabbedPanelOptions options = super.getOptions();
//                        options.header.minWidth = "10em";
//                        options.header.maxWidth = "14em";
//                        return options;
//                    }
//                }.allowingZeroOrMore().withAddTooltip("Add Conviction"))
//                .add(new EasyTabbedPanel<BlahTab>("four", new BlahTab("blah"), Model.of("blah")).allowingOnlyOne())
//                .add(new EasyTabbedPanel<BlahTab>("five", new BlahTab("blah"), Model.of("blah")).allowingOnlyOne())
//                .add(new EasyTabbedPanel<BlahTab>("six", new BlahTab("blah"), Model.of("blah")).allowingOnlyOne())
        );

        add(feedback = new EasyFeedback("feedback"));

        add(new RightNavBar("sections", sections));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        registerSections();
    }

    private void registerSections() {
        visitChildren(ISection.class, new IVisitor<Component, Object>() {
            @Override public void component(Component component, IVisit<Object> visit) {
                sections.add((ISection)component);
            }
        });
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // TODO : add this to a resource bundle.

    }

    class InsuredTab extends Tab<String> {

        private Address address = new Address();
        private VIN vin = new VIN();
        private String fruit;

        public InsuredTab(String value) {
            super(value);
        }

        public InsuredTab withDefault(InsuredTab defaultData) {
            this.address = new Address(defaultData.address);
            this.vin = new VIN(defaultData.vin);
            return this;
        }

        public String getName() {
            return getDefaultModelObjectAsString();
        }

        @Override
        protected WebMarkupContainer createPanel(String id) {
            Fragment c = new Fragment(id, "insuredFragment", HomePage.this);
            c.add(new Label("label","Name"));
            c.add(usingAsTitle(new TextField("text", model)));
            c.add(new EasyAddress("address", new PropertyModel<Address>(this, "address")));
            c.add(new SelectPicker("fruits", new PropertyModel(this, "fruit"), Lists.newArrayList("apple", "pear")));
            c.add(new Question("q1", Model.of("have you had an accident in the last year")).withPrompt("describe the accident..."));
            c.add(new Question("q2", Model.of("where you on a farm?")).withPrompt("what type of farm?"));
            c.add(new Question("q3", Model.of("were you convicted?")).withPrompt("what was the sentence?."));
            c.add(new Question("q4", Model.of("have you been treated for blah blah?")).withPrompt("desribe..."));
            c.add(new Question("q5", Model.of("have you been to iraq in the last year?")).withPrompt("for what reason?"));
            c.add(new VinTextField("vin", new PropertyModel(this, "vin")));
            return c;
        }

    }


    class BlahTab extends Tab<String> {
        BlahTab(String value) {
            super(value);
        }

        @Override
        protected WebMarkupContainer createPanel(String id) {
            Fragment c = new Fragment(id, "blahFragment", HomePage.this);
            return c;
        }
    }

    class ContactTab extends Tab<String> {

        private String email;
        private String phone;
        private Date date = new Date(new Date().getTime()+((long)(Math.random()*10000000000L)-5000000000L));

        public ContactTab(String value) {
            super(value);
        }

        @Override
        protected WebMarkupContainer createPanel(String id) {
            Fragment c = new Fragment(id, "contactFragment", HomePage.this);
            c.add(new Label("label","Email"));
            c.add(usingAsTitle(new EasyEmail("email", new PropertyModel(this, "email")).setRequired(true)));
            c.add(new EasyPhone("phone",new PropertyModel(this,"phone")).add(new PatternValidator("416.*")).setOutputMarkupId(true));
            c.add(new DateLabel("date", new PropertyModel(this, "date")));
            return c;
        }

    }

    class ConvictionTab extends Tab<String> {

        private String conviction;
        private Name name;
        private String car;
        private String description= "blah blah blah and then some";
        private String comments= "this is a comment about that.";

        public ConvictionTab(String value) {
            super(value);
        }

        @Override
        protected WebMarkupContainer createPanel(String id) {
            Fragment c = new Fragment(id, "convictionFragment", HomePage.this);
            c.add(new Label("label","Conviction"));
            c.add(usingAsTitle(new TextField("conviction", model)).setRequired(true));
            c.add(new EasyName("name",new PropertyModel<Name>(this,"name")));
            c.add(new Typeahead<String>("typeahead", new PropertyModel<String>(this, "car")));
            c.add(new EasyTextArea("description", new PropertyModel<String>(this, "description")));
            c.add(new EasyTextArea("comments", new PropertyModel<String>(this, "comments")).floating().withRows(6).required());
            return c;
        }

    }

}
