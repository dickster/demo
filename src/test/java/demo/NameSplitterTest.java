package demo;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NameSplitterTest {

//    private NameSplitter splitter;

    List<String> names = Lists.newArrayList(
            "Mr. and Ms Joe Smith."
    );


    @Before
    public void setup() {
Iterable<String> tokens = Splitter.on(CharMatcher.anyOf("-.")).split("foo bar blah");
  //      splitter = new NameSplitter();
    }

    @Test
    public void testParseName() throws Exception {
        List<String> result;
        for (String name:names) {
    //        result = splitter.parseName(name);
      //      System.out.println(result);
        }
    }
}
