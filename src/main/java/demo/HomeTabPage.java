package demo;

import com.google.common.collect.Lists;
import demo.resources.Resource;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.List;

public class HomeTabPage extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final JavaScriptResourceReference LIBPHONENUMBER_JS = new JavaScriptResourceReference(Resource.class, "libphonenumber.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference MULTISELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-multiselect.js");
    private static final CssResourceReference MULTISELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-multiselect.css");
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/typeahead.bundle.js");
    private static final CssResourceReference TYPEAHEAD_CSS = new CssResourceReference(Resource.class,"bootstrap-3.1.1-dist/css/typeahead.bootstrap.css");

    private final EasyFeedback feedback;
    private List<ISection> sections = Lists.newArrayList();

    // test data....
    List<Insured> insureds = Lists.newArrayList(
            new Insured("Petroslav Kablowskisapalbhaala"),
            new Insured("Derek Dick"),
            new Insured("John D'oh")
    );


    public HomeTabPage(final PageParameters parameters) {

        add(new Form("form")
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
                .add(new EasyTabbedPanel2<Insured>("insured", insureds) {
                    @Override
                    protected Tab<Insured> createTab(Insured data, int index) {
                        return new InsuredTab(data);
                    }

                    @Override
                    protected WebMarkupContainer createPanel(String id, IModel<Insured> model) {
                        Fragment c = new Fragment(id, "insuredFragment", HomeTabPage.this);
                        c.add(new Label("label", "Name"));
                        c.add(new TextField("text", new PropertyModel(model, "name")));
                        c.add(new EasyAddress("address", new PropertyModel<Address>(model, "address")));
                        c.add(new Question("q1", Model.of("have you had an accident in the last year")).withPrompt("describe the accident..."));
                        c.add(new Question("q2", Model.of("where you on a farm?")).withPrompt("what type of farm?"));
                        c.add(new Question("q3", Model.of("were you convicted?")).withPrompt("what was the sentence?."));
                        c.add(new Question("q4", Model.of("have you been treated for blah blah?")).withPrompt("describe..."));
                        c.add(new Question("q5", Model.of("have you been to iraq in the last year?")).withPrompt("for what reason?"));
                        c.add(new VinTextField("vin", new PropertyModel(model, "vin")));
                        return c;
                    }

                    @Override public String getHref() {
                        return "#one";
                    }
                }
                .allowingOneOrMore()
                .withAddTooltip("Add Insured Person")
                .setStatus(FeedbackState.HAS_INFO))
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
        response.render(JavaScriptReferenceHeaderItem.forReference(JQUERY_UI_JS));
//        response.render(JavaScriptReferenceHeaderItem.forReference(LIBPHONENUMBER_JS));
        response.render(CssHeaderItem.forReference(JQUERY_UI_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(MULTISELECT_JS));
        response.render(CssHeaderItem.forReference(MULTISELECT_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(TYPEAHEAD_JS));
        response.render(CssHeaderItem.forReference(TYPEAHEAD_CSS));
    }



    class InsuredTab extends Tab<Insured> {

        public InsuredTab(IModel<Insured> insured) {
            super(insured);
        }

        public InsuredTab(Insured insured) {
            super(insured);
        }

//        public InsuredTab withDefault(InsuredTab defaultData) {
//            this.address = new Address(defaultData.address);
//            this.vin = new VIN(defaultData.vin);
//            return this;
//        }

        @Override
        protected WebMarkupContainer createPanel(String id) {
            Fragment c = new Fragment(id, "insuredFragment", HomeTabPage.this);
            c.add(new Label("label","Name"));

            c.add(usingAsTitle(new TextField("text", new PropertyModel(model, "name"))));
            c.add(new EasyAddress("address", new PropertyModel<Address>(model, "address")));
            c.add(new Question("q1", Model.of("have you had an accident in the last year")).withPrompt("describe the accident..."));
            c.add(new Question("q2", Model.of("where you on a farm?")).withPrompt("what type of farm?"));
            c.add(new Question("q3", Model.of("were you convicted?")).withPrompt("what was the sentence?."));
            c.add(new Question("q4", Model.of("have you been treated for blah blah?")).withPrompt("desribe..."));
            c.add(new Question("q5", Model.of("have you been to iraq in the last year?")).withPrompt("for what reason?"));
            c.add(new VinTextField("vin", new PropertyModel(model, "vin")));
            return c;
        }
    }


}
