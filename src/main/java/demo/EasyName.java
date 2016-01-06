package demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;

import java.lang.reflect.Type;
import java.util.Locale;

public class EasyName extends TextField<Name> {

    // TODO DD : make this a generic behavior that handles JSON form desc = {label:{'a','b','c'}, label2:{'x','y','z'}, etc.. }    did you mean?   or maybe this? -->
    private static final String DID_YOU_MEAN_JS = "$('#%s').data('nameWidget').didYouMean(%s);";

    private static final String EASY_NAME_INIT_JS = "easy.name.create(%s);";
    private static final JavaScriptResourceReference NAME_JS = new JavaScriptResourceReference(EasyName.class,"name.js");

    private final AbstractDefaultAjaxBehavior behavior;

    private IConverter<Name> nameConverter = new NameConverter();


    public EasyName(String id, IModel<Name> model) {
        super(id,model);
        setOutputMarkupId(true);
        add(behavior = new AbstractDefaultAjaxBehavior() {
            // TODO : need to make sure this is called before form is submitted??
            // e.g. if user types in name then clicks on submit button will the change event be fired? before or after submit is called?
            @Override protected void respond(AjaxRequestTarget target) {
                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
                String text = params.getParameterValue("text").toString();
                validateName(text, target);
            }
        });
    }

    @Override
    public <C> IConverter<C> getConverter(Class<C> type) {
        return (type.equals(Name.class)) ?
            (IConverter<C>) nameConverter :
                super.getConverter(type);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem.forReference(NAME_JS));
        response.render(OnDomReadyHeaderItem.forScript(String.format(EASY_NAME_INIT_JS, new Gson().toJson(new NameOptions()))));
    }

    private void validateName(String text, AjaxRequestTarget target) {
        try {
            Name name = nameConverter.convertToObject(text, Locale.getDefault());
            if (name.isAmbiguous()) {
                target.appendJavaScript(didYouMean(name));
            }
        } catch (ConversionException e) {
            target.appendJavaScript(didYouMean(bestGuess(text)));
        }
    }

    private String didYouMean(Name name) {
        if (name==null) {
            name=new Name();
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(Name.class,new NameAdapter()).create();
        return String.format(DID_YOU_MEAN_JS, getMarkupId(), gson.toJson(name));
    }

    private Name bestGuess(String text) {
        String[] tokens = text.split(" ");
        if (tokens.length==3) {
            return new Name().withFirstName(tokens[0]).withMiddleName(tokens[1]).withLastName(tokens[2]);
        } else if (tokens.length==2) {
            return new Name().withFirstName(tokens[0]).withMiddleName("").withLastName(tokens[1]);
        } else {
            return new Name().withFirstName(tokens[0]).withMiddleName("").withLastName(text.substring(tokens[0].length()).trim());
        }
    }

    class NameOptions {
        String id = EasyName.this.getMarkupId();
        String callback = behavior.getCallbackUrl().toString();
    }

    class NameAdapter implements JsonSerializer<Name> {

        @Override
        public JsonElement serialize(Name name, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("first", name.getFirst());
            object.addProperty("middle", name.getMiddle());
            object.addProperty("last", name.getLast());
            return object;
        }
    }

    class NameConverter extends AbstractConverter<Name> {

        private transient NameParser parser = new NameParser();

        NameConverter() {
        }

        @Override
        protected Class getTargetType() {
            return Name.class;
        }

        @Override
        public Name convertToObject(String text, Locale locale) throws ConversionException {
            try {
                return StringUtils.isBlank(text) ?
                    new Name() :
                    parser.parseName(text).peek();
            } catch (Throwable t) {
                throw new ConversionException("can't parse " + text, t.getCause());
            }
        }
    }

}
