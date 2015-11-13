package forms.mapping;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import java.util.Map;

public class MapTransformation implements Transformation<String> {

    private final BiMap<String, String> map;

    public MapTransformation(Map<String, String> map) {
        this.map = ImmutableBiMap.copyOf(map);
    }

    @Override
    public String transform(String o) {
        return map.get(o);
    }

    @Override
    public String inverseTransform(String o) {
        return map.inverse().get(o);
    }
}
