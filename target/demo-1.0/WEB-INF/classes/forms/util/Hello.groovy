package forms.util

class Hello implements IHello {

    Hello() {
    }

    @Override
    String greeting() {
        return "(hello from groovy land!)";
    }
}
