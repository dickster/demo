package demo;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RightNavBar extends Panel implements FeedbackListener {

    private Map<ISection,Set<FeedbackMessage>> errors = Maps.newHashMap();
    private List<? extends ISection> sections;

    public RightNavBar(String id, List<? extends ISection> sections) {
        super(id);
        this.sections = sections;
        setOutputMarkupId(true);
        add(new ListView<ISection>("sections", sections) {
            @Override protected void populateItem(ListItem<ISection> item) {
                ISection section = item.getModelObject();
                MarkupContainer link = new WebMarkupContainer("link");
                link.add(new Label("label", Model.of(item.getIndex() + 1)));
                link.add(new AttributeModifier("href", section.getHref()));
                item.add(new AttributeModifier("title", new PropertyModel(section, "tooltip")));
                Set<FeedbackMessage> msgs = errors.get(section);
                if (msgs!=null && msgs.size()>0) {
                    item.add(new AttributeModifier("class", "has-error"));
                }
                item.add(link);
            }
        });
    }

    @Override
    public void hasError(AjaxRequestTarget target, FeedbackMessage msg) {
        Component reporter = msg.getReporter();
        //find associated ISection in model and if so, mark it as dirty.
        ISection section = findParentSection(reporter);
        if (section!=null) {
            Set<FeedbackMessage> msgs = errors.get(section);
            if (msgs==null) {
                msgs = Sets.newHashSet();
                errors.put(section,msgs);
            }
            msgs.add(msg);
        }
        target.add(RightNavBar.this);
    }

    private ISection findParentSection(Component reporter) {
        Component parent = reporter.getParent();
        while (parent!=null) {
            if (parent instanceof ISection) {
                return (ISection) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    @Override
    public void resetErrors(AjaxRequestTarget target) {
        errors.clear();
    }
}
