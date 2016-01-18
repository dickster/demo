package forms.widgets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import demo.Name;
import demo.NameParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;

import java.lang.reflect.Type;
import java.util.Locale;

public class NameTextField extends TextField<Name> {

//    private final AbstractDefaultAjaxBehavior behavior;

    private IConverter<Name> nameConverter = new NameConverter();

    public NameTextField(String id) {
        super(id);
    }


//    public NameTextField(String id, NameConfig config) {
//        super(id, config);
//        add(behavior = new AbstractDefaultAjaxBehavior() {
//            // TODO : need to make sure this is called before form is submitted??
//            // e.g. if user types in name then clicks on submit button will the change event be fired? before or after submit is called?
//            @Override protected void respond(AjaxRequestTarget target) {
//                IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
//                String text = params.getParameterValue("text").toString();
//                validateName(text, target);
//            }
//        });
//    }

    @Override
    public <C> IConverter<C> getConverter(Class<C> type) {
        return (type.equals(Name.class)) ?
                (IConverter<C>) nameConverter :
                super.getConverter(type);
    }

//    @Override
//    public void renderHead(IHeaderResponse response) {
//        response.render(JavaScriptHeaderItem.forReference(NAME_JS));
//        response.render(OnDomReadyHeaderItem.forScript(String.format(EASY_NAME_INIT_JS, new Gson().toJson(new NameOptions()))));
//    }

    private void validateName(String text, AjaxRequestTarget target) {
        try {
            Name name = nameConverter.convertToObject(text, Locale.getDefault());
            if (name.isAmbiguous()) {
                System.out.println("this name is ambiguous");
            }
        } catch (ConversionException e) {
            System.out.println("this name can't be converted.");
            //target.appendJavaScript(didYouMean(bestGuess(text)));
        }
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

//    class NameOptions {
//        String id = EasyName.this.getMarkupId();
//        String callback = behavior.getCallbackUrl().toString();
//    }

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
                        new Name("","") :
                        parser.parseName(text).peek();
            } catch (Throwable t) {
                throw new ConversionException("can't parse " + text, t.getCause());
            }
        }
    }

}
