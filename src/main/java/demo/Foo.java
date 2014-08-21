package demo;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created with IntelliJ IDEA.
 * User: derek
 * Date: 2014-07-29
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
@WebService()
public class Foo {
  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  public static void main(String[] argv) {
    Object implementor = new Foo ();
    String address = "http://localhost:9000/Foo";
    Endpoint.publish(address, implementor);
  }
}