package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.DialogButton;
import org.apache.wicket.Component;

public class DialogButtonConfig extends FormComponentConfig {

    private final String dialogName;

    public DialogButtonConfig(String id, String dialogName) {
        super(id, WidgetTypeEnum.DIALOG_BUTTON);
        withCss("btn");
        this.dialogName = dialogName;
    }

    @Override
    public Component create(String id) {
        return new DialogButton(id, this);
    }

    public String getDialogName() {
        return dialogName;
    }
}



//<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
//        Launch demo modal
//</button>


//<div class="modal fade">
//<div class="modal-dialog">
//<div class="modal-content">
//<div class="modal-header">
//<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
//<h4 class="modal-title">Modal title</h4>
//</div>
//<div class="modal-body">
//<p>One fine body&hellip;</p>
//</div>
//<div class="modal-footer">
//<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
//<button type="button" class="btn btn-primary">Save changes</button>
//</div>
//</div><!-- /.modal-content -->
//</div><!-- /.modal-dialog -->
//</div><!-- /.modal -->