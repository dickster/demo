package forms;


public interface IPropertyHolder {

    Object get(String property);
    <T> T set(String property, T value);
    Object safeGet(String child);
}
