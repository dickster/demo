package demo;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.Collections;
import java.util.List;

public class EasyFeedback extends Panel implements IFeedback {

    public static final String FEEDBACK_UPDATE_JS = "easy.feedback.update(%s);";
    public static final JavaScriptResourceReference FEEDBACK_JS = new JavaScriptResourceReference(EasyFeedback.class,"feedback.js");
    private final FeedbackMessagesModel model;
    private FeedbackState state = FeedbackState.VOID;
    private String msg;

    private transient Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public EasyFeedback(String id) {
        super(id);
        model = new FeedbackMessagesModel(this);
        model.setFilter(new EasyFeedbackFilter());
        setOutputMarkupPlaceholderTag(true);
        add(new WebMarkupContainer("icon").add(new AttributeAppender("class", new PropertyModel(this, "state.css"))));
        add(new Label("message",new PropertyModel(this,"msg")) {
            @Override public boolean isVisible() {
                return anyErrorMessage();
            }
        });
        add(new AttributeModifier("class", Model.of("feedback")));
    }

    private void notifyErrorListeners(final AjaxRequestTarget target, Form<?> form) {
        model.detach();
        form.getPage().visitChildren(FeedbackListener.class, new IVisitor() {
            @Override public void component(Object o, IVisit visit) {
                ((FeedbackListener) o).resetErrors(target);
            }
        });

        for (final FeedbackMessage msg:getCurrentMessages()) {
            form.getPage().visitChildren(FeedbackListener.class, new IVisitor() {
                @Override public void component(Object o, IVisit visit) {
                    System.out.println("has error " + msg.getReporter().getMarkupId() + " --> " + o.getClass().getSimpleName());
                    ((FeedbackListener)o).hasError(target, msg);
                }
            });
        }
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        state = anyErrorMessage() ? FeedbackState.HAS_ERROR : FeedbackState.VOID;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(FEEDBACK_JS));
        response.render(OnLoadHeaderItem.forScript(String.format(FEEDBACK_UPDATE_JS, gson.toJson(getOptions()))));
        model.detach();
    }

    private FeedbackOptions getOptions() {
        return new FeedbackOptions();
    }

    private List<Message> getMessagesToUpdate() {
        return Lists.newArrayList(Iterables.transform(getCurrentMessages(), new Function<FeedbackMessage, Message>() {
            @Override public Message apply(final FeedbackMessage feedbackMessage) {
                return new Message(feedbackMessage);
            }
        }));
    }

    public final boolean anyErrorMessage() {
        return anyMessage(FeedbackMessage.ERROR);
    }

    public final boolean anyMessage() {
        return anyMessage(FeedbackMessage.UNDEFINED);
    }

    public final boolean anyMessage(int level) {
        List<FeedbackMessage> msgs = getCurrentMessages();
        for (FeedbackMessage msg : msgs) {
            if (msg.isLevel(level)) {
                return true;
            }
        }
        return false;
    }

    // TODO : set max messages, allow for sorting?

    protected final List<FeedbackMessage> getCurrentMessages() {
        return Collections.unmodifiableList(model.getObject());
    }

    public void update(AjaxRequestTarget target, Form<?> form) {
        target.add(this);
        notifyErrorListeners(target,form);
        updateMessage();
    }

    private void updateMessage() {
        List<FeedbackMessage> msgs = getCurrentMessages();
        if (msgs.size()==0) {
            msg = "";
        } else if (msgs.size()==1) {
            msg = msgs.get(0).getMessage().toString();
        } else {
            msg = "there are " + msgs.size() + " errors.";   // TODO : format this property with properties file.
        }
    }

    class EasyFeedbackFilter implements IFeedbackMessageFilter {
        @Override public boolean accept(FeedbackMessage message) {
            return true;  // does nothing for now...
        }
    }


    class FeedbackOptions {
        List<Message> messages = getMessagesToUpdate();
        String id = EasyFeedback.this.getMarkupId();
        // TODO : if more than one message, count them...and format message.

        FeedbackOptions() {
        }
    }


    class Message {
        String level;
        String reporter;
        String msg;  // allow this to be HTML?

        Message(FeedbackMessage msg) {
            this.reporter = msg.getReporter().getMarkupId();
            this.msg = msg.getMessage().toString();
            this.level = msg.getLevelAsString();
        }

    }

}


