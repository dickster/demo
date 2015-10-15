package demo.wicketTest;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import demo.wicketTest.AbstractWicketTest.BrovadaWicketTester;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class WicketHarness {

    private WicketHarness parent = null;
	private BrovadaWicketTester tester;
	private String path = "";

    public WicketHarness() {
        super();
    }

    public WicketHarness withPath(String path) {
        this.path = appendPath("", path);
        return this;
    }

    public WicketHarness set(FormComponent fc, String text) {
        FormTester formTester = getWicketTester().getFormTester(fc);
        formTester.setValue(fc, text);
        return this;
    }

    public WicketHarness withWicketTester(BrovadaWicketTester tester) {
        this.tester = tester;
        return this;
    }

	private String appendPath(String base, String path) {
		if (StringUtils.isBlank(path)) {
			return base;
		}
        String result = base + path;
		if (!result.endsWith(":")) {
			result +=":";
		}
        return result;
	}

	public String getLastResponseAsString() {
		return getWicketTester().getLastResponseAsString();
	}

    public boolean containersHeader(String header) {
        return getWicketTester().getLastResponse().containsHeader(header);
    }

	protected void click(String... ids) {
		getWicketTester().executeAjaxEvent(getPathFor(ids), "onclick");
	}

    protected void executeAjaxEvent(String event, String... ids) {
        getWicketTester().executeAjaxEvent(getPathFor(ids), event);
    }

	@Deprecated   // use short form "get" instead...
	protected Component getComponent(String... ids) {
		return get(ids);
    }

    public Component get(String... ids) {
		return getWicketTester().getComponentFromLastRenderedPage(getPathFor(ids));
	}
	
	protected boolean isComponentVisible(String... ids) {
		// CAVEAT: can be false positive if you give ids that don't exist. 
		// you might think it's invisible when it really just never was created.
		return get(ids) != null;
	}
	
	public BrovadaWicketTester getWicketTester() {
        return (parent!=null) ? parent.getWicketTester() : tester;
	}

    protected String getPathFor(String... ids) {
        return getPathFor(true, ids);
    }

    protected String getAbsolutePathFor(String... ids) {
        checkArgument(ids!=null);
        return getPathFor(false, ids);
    }

    private final String getPathFor(boolean isRelative, String... ids) {
        checkArgument(ids!=null);
        String path = Joiner.on(":").skipNulls().join(ids);
        return isRelative ?
                getPath() + path :
                path;
    }

    protected String getPrefixedPathFor(String prefix, String... ids) {
		List<String> allIds = Arrays.asList(ids);
		allIds.add(0, prefix);
		return getPathFor((String[]) allIds.toArray());	
	}

	public String getPath() {
		return path;
	}

	protected <X> void selectByDisplayValue(FormTester tester, DropDownChoice<X> dropDown, Predicate<X> predicate) {
		List<? extends X> choices = dropDown.getChoices();
		for(int i = 0; i < choices.size(); i++) {
			if(predicate.apply(choices.get(i))) {
				tester.select(dropDown.getId(), i);
				return;
			}
		}
		throw new IllegalArgumentException("Display value not found in " +  dropDown.getId());
	}

    public Collection<String> getHeaders(String header) {
        return getWicketTester().getLastResponse().getHeaders(header);
    }

    public void submit() {
        // caveat : this is a convenience method that assumes you just want the first form i can find in page.  (you probably only have one?)
        // if there are more forms, use the more specific method to specify form path or component.
        getWicketTester().getLastRenderedPage().visitChildren(Form.class, new IVisitor<Form, Object>() {
            @Override public void component(Form form, IVisit<Object> visit) {
                getWicketTester().getFormTester(form).submit();
                visit.stop();
            }
        });
    }

}
