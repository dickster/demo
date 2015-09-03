package demo;

import demo.resources.Resource;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class ManagerPage extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference MULTISELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-multiselect.js");
    private static final CssResourceReference MULTISELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-multiselect.css");
    private static final ResourceReference MANAGERPAGE_CSS = new CssResourceReference(Resource.class,"css/managerPage.css");
    private static final CssResourceReference DATERANGEPICKER_CSS = new CssResourceReference(Resource.class, "bootstrap-daterangepicker/daterangepicker-bs3.css");
    private static final JavaScriptResourceReference MOMENT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-daterangepicker/moment.min.js");
    private static final JavaScriptResourceReference DATERANGEPICKER_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-daterangepicker/daterangepicker.js");


    public ManagerPage(final PageParameters parameters) {
        add(createLogo());
        add(new Link("logout") {
            @Override public void onClick() {
                logout();
            }
        });
        add(new Link("help") {
            @Override public void onClick() {
                help();
            }
        });
        add(new Link("settings") {
            @Override public void onClick() {
                settings();
            }
        });
        add(new Label("subheader", Model.of("New Policy for John D'oh")));
        add(new WebMarkupContainer("statusIcon").add(new AttributeAppender("class", "glyphicon-pencil")));
        add(new WebMarkupContainer("dropzone"));
        add(new Form("form")
            .add(new Button("submit"))
            .add(new Button("refer"))
//            .add(new DateRangePicker("daterange"))
            .add(new Button("quote")));

        add(new FormsAndAttachmentsPanel("formsAttachments"));
    }

    protected void logout() {
        System.out.println("logout");
    }

    protected void help() {
        System.out.println("help");
    }

    protected void settings() {
        System.out.println("settings");
    }

    private Component createLogo() {
        return new Link("logo") {
            @Override
            public void onClick() {
                logoClicked();
            }
        }.add(new Image("image", "") {
            // for debugging only...remove this when you define the proper image.
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                checkComponentTag(tag, "img");
                tag.put("src","http://placehold.it/150x50/acbfd6/000000&text=GWI");
            }
        });
    }

    protected void logoClicked() {
        System.out.println("image clicked");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(JQUERY_UI_JS));
        response.render(CssHeaderItem.forReference(JQUERY_UI_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(MULTISELECT_JS));
        response.render(CssHeaderItem.forReference(MULTISELECT_CSS));
        response.render(CssHeaderItem.forReference(MANAGERPAGE_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(MOMENT_JS));
        response.render(JavaScriptReferenceHeaderItem.forReference(DATERANGEPICKER_JS));
        response.render(CssHeaderItem.forReference(DATERANGEPICKER_CSS));

    }

}


