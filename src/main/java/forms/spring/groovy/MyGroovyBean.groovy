package forms.spring.groovy

import forms.util.ITest
import org.springframework.stereotype.Service;

@Service
class MyGroovyBean implements ITest {
    @Override
    void hello() {
        println("hello there");
    }
}
