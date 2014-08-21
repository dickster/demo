package demo;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.io.Serializable;

public class Question extends Panel {


    private static final String INIT = "easy.question().init(%s)";
    private static final JavaScriptHeaderItem JS = JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(Question.class, "easyQuestion.js"));
    private static final CssHeaderItem CSS = CssHeaderItem.forReference(new CssResourceReference(Question.class,"easyQuestion.css"));

    private Answer answer = new Answer();

    private boolean hasTextInput = false;
    private TextArea text;
    private String prompt;
    private String label;


    public Question(String id, IModel<String> q) {
        super(id);
        setOutputMarkupId(true);
        add(new CheckBox("checkbox", new PropertyModel<Boolean>(answer, "answered")));
        add(new Label("label", q));
        add(text = new TextArea("textarea", new PropertyModel<String>(answer, "rawAnswer")));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JS);
        response.render(CSS);
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT, new Gson().toJson(getOptions()))));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        text.setVisible(hasTextInput);
        if (StringUtils.isNotBlank(prompt)) {
            text.add(new AttributeModifier("placeholder",prompt));
        }
    }

    protected EasyQuestionOptions getOptions() {
        return new EasyQuestionOptions();
    }

    public Question withInput() {
        this.hasTextInput = true;
        return this;
    }

    public Question withPrompt(String prompt) {
        this.prompt = prompt;
        return withInput();
    }


    class Answer implements Serializable {
        private String rawAnswer;
        private boolean answered = false;

        Answer() {
        }


        public String getAnswer() {
            return answered ? rawAnswer : null;
        }

        public String getRawAnswer() {
            return rawAnswer;
        }

        public void setRawAnswser(String answer) {
            this.rawAnswer = answer;
        }

        public void setAnswer(String answer) {
            this.rawAnswer = answer;
        }

        public boolean isAnswered() {
            return answered;
        }

        public void setAnswered(boolean answered) {
            this.answered = answered;
        }
    }

    public class EasyQuestionOptions {
        String id = Question.this.getMarkupId();
    }
}
