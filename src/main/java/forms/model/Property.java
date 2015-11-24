package forms.model;


public class Property {
    private String ognl;
    private String parent;
    private String child;
    private boolean array = false;

    public Property(String ognl) {
        this.ognl = ognl;
        this.parent = calculateParent(ognl);
        this.child = calculateChild(ognl);
    }

    private String calculateChild(String ognl) {
        // check for [] which means array.
        int index = ognl.lastIndexOf(".");
        String child = index==-1 ?
                ognl :
                ognl.substring(index+1);
        if (child.endsWith("[]")) {
            array = true;
            return child.replace("[]","");
        } else {
            return child;
        }
    }

    private String calculateParent(String ognl) {
        int index = ognl.lastIndexOf(".");
        return index!=-1
                ? ognl.substring(0, index)
                : null;
    }

    public String getParentProperty() {
        return parent;
    }
    public String getChildProperty() {
        return child;
    }
    public boolean isArray() {
        return false;
    }

}
