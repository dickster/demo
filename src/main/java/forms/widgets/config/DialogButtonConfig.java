package forms.widgets.config;

import forms.WidgetTypeEnum;
import forms.widgets.WfAjaxLink;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class DialogButtonConfig extends AjaxLinkConfig<WfAjaxLink> {

    private DialogConfig dialogConfig;

    public DialogButtonConfig(String id) {
        super(id, WidgetTypeEnum.DIALOG_INVOKING_BUTTON);
    }

    @Override
    public WfAjaxLink create(String id) {
        return new WfAjaxLink(id, this) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                System.out.println("link clicked");
                // show dialog. need a hook to dialog's markupId.
                // c = getWfComponent(dialogConfig.getId())
                // mid = c.getMarkupId();
                // appendJavascript("$(#mid).modal('show')");
                // c.setVisible(true);
                //

            }
        };
    }

    public DialogButtonConfig forDialog(DialogConfig dialogConfig) {
        this.dialogConfig = dialogConfig;
        return this;
    }
}
