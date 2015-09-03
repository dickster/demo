package forms;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import java.util.Map;

public class MapTransformation implements Transformation {

    private final BiMap<String, String> map;

    public MapTransformation(Map<String, String> map) {
        this.map = ImmutableBiMap.copyOf(map);
    }

    @Override
    public Object transform(Object o) {
        return map.get(o);
    }

    @Override
    public Object inverseTransform(Object o) {
        return map.inverse().get(o);
    }
}
