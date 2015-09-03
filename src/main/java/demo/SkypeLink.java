package demo;

import com.google.gson.Gson;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class SkypeLink extends WebMarkupContainer {

    private static final String INIT = "Skype.ui(%s);";



//    <script type="text/javascript">
//            Skype.ui({
//        "name": "call",
//                "element": "SkypeButton_Call_derek.brovada_1",
//                "participants": ["deepsteve"],
//        "imageSize": 32
//    });
//    </script>
//

    public SkypeLink(String id) {
        super(id);
        this.setMarkupId("SkypeButton_"+getMarkupId(true));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // TODO : add <script type="text/javascript" src="http://www.skypeassets.com/i/scom/js/skype-uri.js"></script> via resource.
        String options = new Gson().toJson(getOptions());
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT, options)));
    }

    private Object getOptions() {
        return new SkypeOptions();
    }


    class SkypeOptions {
        private String name = "call";
        private String element = SkypeLink.this.getMarkupId();
        private String[] participants = {"deepsteve"};
        private Integer imageSize = 32;

    }
}
