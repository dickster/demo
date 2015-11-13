package forms.mapping;

import forms.mapping.Transformation;

import javax.annotation.Nullable;

public class PredefinedTransformation {

    public static Transformation UPPERCASE = new Transformation() {

        @Override
        public @Nullable Object transform(Object s) {
            return s==null ? null : s.toString().toUpperCase();
        }

        @Override
        public Object inverseTransform(Object s) {
            return s; // no inversion. statelessly lost that ability.
        }
    };

}
