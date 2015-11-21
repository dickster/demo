package forms.config;

import com.google.common.collect.Lists;
import forms.widgets.Dialog;
import org.apache.wicket.Component;

import java.util.List;

public class DialogConfig extends GroupConfig {


    private List<DialogSubmitButtonConfig> buttons = Lists.newArrayList();

    public DialogConfig(String id) {
        super(id);
    }

    @Override
    public Component create(String id) {
        return new Dialog(id, this);
    }

    public DialogConfig withButtons(DialogSubmitButtonConfig... configs) {
        for (DialogSubmitButtonConfig config:configs) {
            buttons.add(config);
            config.setDialogId(getName());
        }
        return this;
    }

    public List<DialogSubmitButtonConfig> getButtons() {
        return buttons;
    }
}


//<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
//        Launch demo modal
//</button>

//panel with a contained group??
//look for configs with specific names?
//listView...
//populate();
//
//new Panel(GroupConfig){}

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