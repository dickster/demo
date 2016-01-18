package forms.behavior;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

import java.util.List;
import java.util.Set;

public class VehicleTypeAheadBehavior extends TypeAheadBehavior {

    // silly example that gets
    public VehicleTypeAheadBehavior() {
        super();
    }

    @Override
    protected String getJson() {
        List<String> types = Lists.newArrayList("ford", "gm", "toyota",
                "lexus", "hyundai",
                "chrysler", "jeep",
                "amc", "honda", "mercedes",
                "volkswagen", "bmw", "rolls royce",
                "fiat", "ferrarri", "subaru"
                );

        Set<Result> results = Sets.newHashSet();
        for (String r:types) {
            results.add(new Result(r));
        }
        return new Gson().toJson(results);
    }

}
