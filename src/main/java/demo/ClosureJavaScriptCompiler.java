package demo;

import com.google.common.collect.Lists;
import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ClosureJavaScriptCompiler implements IJavaScriptCompiler {

    // for now i cache this in memory. may want to save in file if gets too big.
    private byte[] bytes;

    @Override
    public byte[] compile(Map<String,IResourceStream> resources) throws IOException, ResourceStreamNotFoundException {
        if (bytes==null) {
System.out.println("compiling " + resources.keySet());
            Compiler compiler = new Compiler();
            compiler.disableThreads();

            CompilerOptions options = new CompilerOptions();
            CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

            List<SourceFile> sourceFiles = Lists.newArrayList();
            for (String name : resources.keySet()) {
                IResourceStream stream = resources.get(name);
                sourceFiles.add(SourceFile.fromInputStream(name, stream.getInputStream()));
            }

            List<SourceFile> externs = Lists.newArrayList();

            Result result = compiler.compile(externs, sourceFiles, options);
            if (!result.success) {
                //??  what to do here???
            }

            bytes = compiler.toSource().getBytes();
            if (bytes.length>100000) {
                // TODO : use logger here...
                System.out.println("the javascript bundle is getting large  (" + bytes.length + " bytes).  you may want to cache it on filesystem instead of memory. ");
            }
        }
        return bytes;
    }
}
