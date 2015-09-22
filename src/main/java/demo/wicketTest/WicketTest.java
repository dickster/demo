package demo.wicketTest;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.loader.IStringResourceLoader;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.junit.Before;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withTypeAssignableTo;

public abstract class WicketTest<T extends WicketHarness,F extends Component> implements IFixtureFactory<F> {
	
	private static final String CLASS = "class";

    protected BrovadaWicketTester wicketTester;
	private T harness;
	private String path;
	private BrovadaWicketTestContext testContext;
	
	
	@Before
	public void setUp() throws Exception {
		initialize();
        setPath(getPathBase());
	}
	
	public BrovadaWicketTester createWicketTesterForRunner() {
		return initialize();
	}

    public WebApplication createApp() {
		return createApp(createTestInjector());
	}
	
	protected ComponentTestInjector createTestInjector() {
		return ComponentTestInjector.make();
	}

    public WebApplication createApp(ComponentTestInjector injector) {
        return new BrovadaTestableApp(injector);
    }

    protected void verifyMocks(Object... mocks) {
    	for (Object mock:mocks) { 
    		verify(mock);
    	}
    }
    
	public void initializeForRunner(BrovadaWicketTester wicketTester, BrovadaWicketTestContext context) {
		this.wicketTester = wicketTester;
    	testContext = context;
    }
    
    protected BrovadaWicketTestContext getTestContext() {
    	return testContext;
    }

    protected TestContextHandler getContextHandler() {
        //override this to handle context stuff specifically.
        return new BrovadaTestContextHandler();
    }

	public BrovadaWicketTester initialize() {
		if (wicketTester==null) {
			ComponentTestInjector injector = createTestInjector();
		//	wicketTester = new BrovadaWicketTester(createApp(injector), injector);
			Application.get().getResourceSettings().getStringResourceLoaders().add(new IStringResourceLoader() {
	            @Override
	            public String loadStringResource(Class<?> clazz, String key, Locale locale, String style, String variation) {
	                return key;
	            }

	            @Override
	            public String loadStringResource(Component component, String key, Locale locale, String style, String variation) {
	                return key;
	            }
	        });
		}
		if (testContext!=null) { 
			getContextHandler().initializeContext(getWicketTester().getSession(), testContext);
		}		
		return wicketTester;
	}	

	public abstract F createFixture(String id);

	protected T createHarness() {
        Class<T> harnessClass = getHarnessClass();
        return makeHarness(harnessClass, null, path);
    }

    private <H extends WicketHarness> H makeHarness(Class<H> harnessClass, WicketHarness parent, String path) {
        H instance = null;
        try {
            instance = harnessClass.newInstance();
            instance.withPath(path).withWicketTester(wicketTester);
            return inject(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(parent==null ?
                "can't create harness @ " + path + " of type " + harnessClass.getSimpleName() :
                "can't create harness @ " + path + " of type " + harnessClass.getSimpleName() + " in parent harness " + parent.getClass().getSimpleName());
    }

    private final <H extends WicketHarness> H inject(H parent) {
        Set<Field> harnesses = getAllFields(parent.getClass(), withAnnotation(Harness.class), withTypeAssignableTo(WicketHarness.class));
        for (Field harnessField:harnesses) {
            injectField(harnessField, parent);
        }
        return parent;
    }

    private WicketHarness injectField(Field harnessField, WicketHarness parent) {
        Preconditions.checkArgument(harnessField.getAnnotation(Harness.class)!=null, "must have @"+Harness.class.getSimpleName() + " annotated field for harnesses");
        Harness annotation = harnessField.getAnnotation(Harness.class);
        String path = parent.getPathFor(annotation.value());
        Class<? extends WicketHarness> type = (Class<T>) harnessField.getType();
        WicketHarness harness = makeHarness(type, parent, path);
        harnessField.setAccessible(true);
        try {
            harnessField.set(parent, harness);
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return harness;
    }


    protected abstract Class<T> getHarnessClass();

    public WicketTest<T,F> wire(Object bean, String fieldName) {
		// wire up bean to all fields with given name.  
		wicketTester.wire(fieldName, bean);
		return this;
	}

	public void renderFixture() {
        renderFixture(this);
    }

	public void renderFixture(IFixtureFactory<F> factory) {
        renderFixture(factory.createFixture(getFixtureId()));
    }

    protected String getPathBase() {
        return getFixtureId();
    }

    protected String getFixtureId() {
        return "";
    }

    public abstract void renderFixture(F fixture);

	protected T getHarness() {
        // page must be rendered first for harness to work!
		if (harness ==null) {
			harness = createHarness();
		}
		return harness;
	}			

	// use this if you expect the class to be *ONLY* the expected value, as opposed to having it 
	//   be one of many classes.   typically you'll use assertClassContains() instead.
	protected void assertClassExactly(String expected, Component component) {
		assertAttributeEquals(CLASS, expected, component);
	}
	
	protected void assertClassContains(String expected, Component component) {
		assertAttributeContains(CLASS, expected, component);
	}
	
	protected void assertInDocument(String expected) {
		String document = wicketTester.getLastResponse().getDocument();
		assertTrue(document.indexOf(expected) != -1);
	}
	
	/**
	 *  CAVEAT : note that these attribute testing (specifically the TagTester) code has bugs. 
	 * if you try to get the component after an ajax event it won't find it.
	 * @see #assertAttributeContains(String, String, org.apache.wicket.Component)
	 */
	protected void assertAttributeEquals(String attribute, String expected, Component component) {
		TagTester tagTester = getWicketTester().getTagByWicketId(component.getId());
		String actual = tagTester.getAttribute(attribute);
		assertEquals(expected, actual);
	}

	/**
	 * @see #assertAttributeEquals(String, String, org.apache.wicket.Component) for bug notes
	 */
	protected void assertAttributeContains(String attribute, String expected, Component component) {
		TagTester x = getWicketTester().getTagByWicketId(component.getId());
		String actual = x.getAttribute(attribute);
		assertNotNull(actual);
		assertTrue(actual.contains(expected));
	}
	
	protected String getPath() {
		checkNotNull(path);
		return path;
	}

    protected void setPath(String... paths) {
        String path = Joiner.on(":").join(paths);
        setPath(path);
    }

	protected void setPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            if (!path.endsWith(":")) {
                path = path+":";
            }
        }
		this.path = path;
	}

	protected void assertInvisible(Component component) {
		assertNull("component should not be visible", component);
	}
	
	protected void assertInvisible(String path) {
		assertInvisible(getHarness().get(path));
	}

	protected void assertErrorMessages(String[] messages) {
		getWicketTester().assertErrorMessages(messages);
	}
	
	protected void assertVisible(Component component) {
		assertNotNull(component);
	}
	
	protected void assertVisible(String path) {
		assertVisible(getHarness().get(path));
	}

    protected void assertComponentOnAjaxResponse(Component c) {
        getWicketTester().assertComponentOnAjaxResponse(c);
    }

	protected void assertChecked(Boolean isChecked, CheckBox checkBox) {
		Assert.assertEquals(isChecked, checkBox.getModelObject());
	}
	
	protected void assertModelValue(Component component, Object expected) {
        assertEquals(expected, component.getDefaultModelObject());
	}	

	protected void assertListView(ListView<List<?>> listView, List<?> expected ) {
		assertEquals(expected, listView.getList());
	}
	
	protected void assertDropDownChoices(List<? extends Serializable> expected, DropDownChoice<? extends Serializable> actual) {
		assertEquals(expected, actual.getChoices());
	}	
	
	protected void assertComponent(Component component, Class<? extends Component> clazz) {
		assertNotNull("can't find component ", component);
		assertTrue("expected component class " + clazz.getSimpleName() + " but was " + component.getClass(), clazz.isAssignableFrom(component.getClass()));
	}

	protected void assertLabel(Label label, String expectedLabelText) {
		assertEquals(expectedLabelText, label.getDefaultModelObjectAsString());
	}
	
	protected void assertRenderedPage(Class<? extends Page> clazz) {
		assertComponent(getWicketTester().getLastRenderedPage(), clazz);
	}

    protected void debugComponentTrees() {
        getWicketTester().debugComponentTrees();
    }

    protected void dump() {
        // just an alias for this method.
        debugComponentTrees();
    }

	protected void assertContainsPropertyValue(MarkupContainer container, String value) {
		final List<String> values = new ArrayList<String>();
		
		container.visitChildren(new IVisitor<Component, Void>() {
            @Override
            public void component(Component component, IVisit<Void> visit) {
                IModel<?> defaultModel = component.getDefaultModel();
                if (defaultModel instanceof PropertyModel) {
                    PropertyModel<?> propertyModel = (PropertyModel<?>) defaultModel;
                    Class<?> klass = propertyModel.getObject().getClass();
                    try {
                        Method declaredMethod = klass.getDeclaredMethod(propertyModel.getPropertyGetter().getName(), new Class[0]);
                        Object result = declaredMethod.invoke(propertyModel.getObject(), new Object[0]);
                        values.add((String) result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
		
		assertTrue("\"" + value + "\" not found", values.contains(value));
	}

    //@Override
	public BrovadaWicketTester getWicketTester() {
		Preconditions.checkNotNull(wicketTester, "you must call initializeApplication() in setup method (@Before) in order for test to run.");
		return wicketTester;
	}

	public <M> M wire(Class<M> typeToMock, String fieldName) {
		M mock = createMock(typeToMock);
		wire(mock, fieldName);
		return mock;
	}	

	protected <M> M wire(Class<M> typeToMock) {
		M mock = createMock(typeToMock);
		String fieldName = mock.getClass().getSimpleName();
		// note : classname will have all that mock gobbledygook appended. let's strip it off. 
		//   e.g.    User$$EnhancerByCGLIB  -->  User
        if (fieldName.indexOf("$$EnhancerByCGLIB")>=0) {
		    fieldName = fieldName.substring(0, fieldName.indexOf("$$EnhancerByCGLIB"));
        }
		fieldName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, fieldName);
		wire(mock,fieldName);
		return mock;
	}


	/*	@Override
		public void dumpFailureInfo(Description description, Failure failure) throws Exception {
			StringWriter sw = new StringWriter();
			try {
				PrintWriter pw = new PrintWriter(sw);
				pw.println("");
				pw.println("################################################################################");		
				collectFailureInfo(pw, description, failure);
				pw.println("################################################################################");
				pw.flush();
				LOGGER.error(sw.toString());
			} finally {
				sw.close();
			}		
		}
	*/	
	/*	protected void collectFailureInfo(PrintWriter pw, Description description, Failure failure) throws IOException {
			pw.println("##### FAILURE Wicket Test:" + description + " #####");
			if(failure != null) {
				pw.println("FAILURE: " + failure.getMessage());
			}
			
			String fileContents = getHarness().getLastRenderedPageAsHtml();
			File dumpedFile = dumpFile(description, fileContents);
			pw.println("\nRendered Html File =====> " + dumpedFile.getAbsolutePath() + "\n");
//			pw.println(fileContents);				
		}*/
		
	/*	private File dumpFile(Description description, String dump) throws IOException {
			String shortDescription=description.getDisplayName().substring(0,description.getDisplayName().indexOf("("));
			File file = File.createTempFile(shortDescription+System.currentTimeMillis(),".html" );
			FileOutputStream out = new FileOutputStream(file);
			try {
				out.write(dump.getBytes("UTF-8"));    	
				out.flush();
			} finally {
				out.close();
			}
			return file;
		}*/
	

	public static class BrovadaWicketTester extends WicketTester {
		private ComponentTestInjector injector;
        private Map<Form, FormTester> formTesters = Maps.newHashMap();

		public BrovadaWicketTester(WebApplication app, ComponentTestInjector injector) {
			super(app);
			this.injector = injector;			
		}

        public void wire(String fieldName, Object bean) {
            injector.wire(fieldName, bean);
        }

        public FormTester getFormTester(Component component) {
            Preconditions.checkArgument(component!=null);
            Component c = component;
            while (c!=null) {
                if (c instanceof Form) {
                    return getFormTester((Form) c);
                }
                c = c.getParent();
            }
            throw new IllegalArgumentException("component has no parent form: " + component.getId());
        }

        public FormTester getFormTester(Form form) {
            FormTester formTester = formTesters.get(form);
            if (formTester==null) {
                formTester = newFormTester(form.getPageRelativePath());
                formTesters.put(form, formTester);
            }
            return formTester;
        }

        private final void submit(FormTester formTester) {
            // CAVEATE : can only submit once per form tester.  need to recreate if you want to do it again.
            submit(formTester);
            formTesters.values().remove(formTester);
        }

    }

}
