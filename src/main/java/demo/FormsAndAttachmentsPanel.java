package demo;

import com.google.common.collect.Lists;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;

public class FormsAndAttachmentsPanel extends Panel {
    private List<String> forms = Lists.newArrayList("Business Owners Policy", "Restaurant Supplement", "Acord #123");

    public FormsAndAttachmentsPanel(String id) {
        super(id);
        add(new WebMarkupContainer("dropzone"));
        add(new ListView<String>("forms", forms) {
            @Override
            protected void populateItem(final ListItem<String> item) {
                AjaxLink link = new AjaxLink("form") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        formClicked(item);
                    }
                };
                link.add(new Label("label", item.getModel()));
                item.add(link);
                link.add(new WebMarkupContainer("icon").add(new AttributeAppender("class", " " +getIcon(item))));

                if (item.getIndex()==0) {  // if item.isSelected()...
                    link.add(new AttributeAppender("class", " active"));
                }
            }
        });
    }

    private String getIcon(ListItem<String> item) {
        String[] icons = {"fa-file-code-o","fa-file-pdf-o", "fa-file-text-o", "fa-file-image-o"};
        return icons[item.getIndex()%icons.length];
    }

    protected void formClicked(ListItem<String> item) {
    }
}
