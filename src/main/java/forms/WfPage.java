package forms;

import com.google.common.eventbus.Subscribe;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class WfPage extends WebPage {

    private DynamicForm form;

    public WfPage(PageParameters parameters) {
        super(parameters);
        // do this at application level.
        WfUtils.getFormBasedWorkflow().register(this);
        addOrReplaceForm();
    }

    private void addOrReplaceForm() {
        FormBasedWorkflow workflow = WfUtils.getFormBasedWorkflow();
        FormConfig formConfig = workflow.getCurrentFormConfig();
        addOrReplace(form = new DynamicForm("form", formConfig));
    }

    public void startProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }

    public void endProgress(WfState state) {
        //TODO : whatever you want...show spinner, dialog, etc...
    }




//    private final void changeAsyncState(final WfState state, final WfEvent event) {
//        enteringAsyncState(state,event);
//        Callable<WfState> asyncTask = new Callable<WfState>() {
//            @Override
//            public @Nullable WfState call() throws Exception {
//                return state.enter(context, event);
//            }
//        };
//        Futures.addCallback(executor.submit(asyncTask), new FutureCallback<WfState>() {
//            public void onSuccess(WfState state) {
//                currentState = state;
//                leavingAysncState(state,event);
//            }
//
//            public void onFailure(Throwable thrown) {
//                ; // hmmm...what to do here??? leave in old state.
//                System.out.println("couldn't enter state : " + state);
//            }
//        });
//    }

//    protected void enteringAsyncState(WfState state, WfEvent event) {
//        // override this to show progress bar, log, whatever...
//        timer.push(new Date());
//        System.out.println("starting state " + state);
//    }
//
//    protected void leavingAysncState(WfState state, WfEvent event) {
//        // override this to hide progress bar, log, whatever...
//        Date date = timer.pop();
//        System.out.println("ending state " + state + " after " + (new Date().getTime()-date.getTime()) + " millis");
//    }



}
