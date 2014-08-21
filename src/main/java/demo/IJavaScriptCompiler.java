package demo;

import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import java.io.IOException;
import java.util.Map;

public interface IJavaScriptCompiler {

    byte[] compile(Map<String,IResourceStream> items) throws IOException, ResourceStreamNotFoundException;

}
