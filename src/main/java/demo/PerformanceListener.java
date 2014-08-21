/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demo;

 // TODO DD : refactor this into library....outside of this test app.
import java.io.Serializable;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.application.IComponentInitializationListener;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;


public class PerformanceListener implements IComponentInitializationListener, Serializable {

    private boolean debug = false;

    public PerformanceListener(boolean debug) {
        this.debug = debug;
    }

    public PerformanceListener() {
        this(true);
    }

    @Override
    public void onInitialize(Component component) {
        if (monitored(component)) {
            component.add(new PerformanceMonitorBehavior());
        }
    }

    protected boolean monitored(Component component) {
        // suggestion : create a marker interface like "instanceof PerformanceMonitoredMarkerIF"
        return component instanceof WebPage;
    }


    class PerformanceMonitorBehavior extends Behavior implements Serializable {
        public final MetaDataKey<Long> RENDER_TIME = new MetaDataKey<Long>() {};

        @Override
        public void beforeRender(Component component) {
            Long start = System.currentTimeMillis();
            component.setMetaData(RENDER_TIME, start);
            super.beforeRender(component);
        }

        @Override
        public void afterRender(Component component) {
            RequestCycle requestCycle = RequestCycle.get();
            requestCycle.getResponse().write(getDebugString(component, isDebug(requestCycle)));
            // TODO DD : if some arbitrary log setting is enabled, spit this out to a performance log appender?
            super.afterRender(component);
        }

        private boolean isDebug(RequestCycle requestCycle) {
            return (debug || !requestCycle.getRequest().getRequestParameters().getParameterValue("debug").isNull());
        }

        private String getDebugString(Component component, boolean debugModeEnabled) {
            long start = component.getMetaData(RENDER_TIME);
            long duration = System.currentTimeMillis() - start;
            String css = debugModeEnabled ? "debug-info" : "hide";  // TODO DD : figure out what proper "don't show this" class should be.  also add "debug-info" styling to .css file.
            return String.format("<div class='%s'> %s : %f seconds. </div>", css, component.getClass().getSimpleName(), duration / 1000.0);
        }
    }

}
