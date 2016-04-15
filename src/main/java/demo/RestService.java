package demo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;

public interface RestService {

    public InputStream doRest(String url);

}
