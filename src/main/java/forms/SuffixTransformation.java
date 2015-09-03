package forms;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public class SuffixTransformation implements Transformation<String> {

    private boolean skipNulls = false;
    private boolean skipBlanks = false;
    private String suffix;
    private boolean caseSensitive = false;

    public SuffixTransformation(String suffix) {
        Preconditions.checkArgument(suffix!=null);
        this.suffix = suffix;
    }

    public SuffixTransformation skipNulls() {
        this.skipNulls = true;
        return this;
    }

    public SuffixTransformation skipBlanks() {
        this.skipBlanks = true;
        return this;
    }

    public SuffixTransformation caseSensitive() {
        this.caseSensitive = true;
        return this;
    }

    @Override
    public Object transform(String o) {
        if (skipNulls && o==null) return o;
        if (skipBlanks && StringUtils.isBlank(o)) return o;
        return o+suffix;
    }

    @Override
    public Object inverseTransform(String o) {
        if (o==null) return null;
        boolean matches = caseSensitive ?
                o.endsWith(suffix) :
                o.toUpperCase().endsWith(suffix.toUpperCase());
        if (matches) {
            return o.substring(0, o.length()-suffix.length());
        }
        // log warning...string didn't have suffix "this.suffix" on it.  leaving as is.
        return o;
    }
}
