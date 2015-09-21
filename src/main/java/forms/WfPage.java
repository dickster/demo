package forms;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class WfPage extends WebPage implements WorkflowPage {

    private @SpringBean WorkflowManager workflowManager;
    private DynamicForm form;

    public WfPage(FormBasedWorkflow workflow) {
        super();
        workflowManager = new WorkflowManager(workflow, this);
        form = workflowManager.addOrReplaceForm();
    }

    @Override
    public Workflow getWorkflow() {
        return workflowManager.getWorkflow();
    }
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



