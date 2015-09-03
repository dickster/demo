package forms;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class ColConfig implements Serializable {

    private List<String> components;
    private String colClass;


    public ColConfig(String colClass) {
        this.colClass = colClass;
    }

    public ColConfig withComponents(String... ids) {
        this.components = Lists.newArrayList(ids);
        return this;
    }

//                                                            .withDefaultCols().
//    layout(new Section("foo").withRow(new Row(id1, id2, id3).withCols(a, b, c)))


    //1: calculate # of cols in row and use appropriate css.
    //2: use given col css.
    //

    //var defaults = {sections:
//            [{id:'nameSection', label:'INFO',
//            rows:[
//                  { ids:['id1','id2', 'id3','id4'],css:{'id1':'col-md-3', 'id2':''},
//                  { ids:['id1','id2', 'id3','id4'],css:{'id1':'col-md-3', 'id2':''},
//             ]

//        [ {'firstNameLabel','firstName'],class:'col-lg-3'},
//        {components:['middleNameLabel','middleName'],class:'col-lg-3'},
//        {components:['lastNameLabel','lastName'],class:'col-lg-3'},
//        {components:['compInd'],class:'col-lg-3'}
//        ]
//        ]
//    },
//    {id:'addressSection', label:'ADDRESS',
//            rows:
//        [
//        [  {components:['addrLabel', 'addr'],class:'col-lg-6'},
//        {components:['addr2Label','addr2'],class:'col-lg-3'}
//        ],
//        [
//        {components:['cityLabel','city'],class:'col-lg-3'},
//        {components:['provLabel','prov'],class:'col-lg-3'},
//        {components:['postalCodeLabel','postalCode'],class:'col-lg-3'}
//        ],
//        [
//        {components:['countryLabel','country'],class:'col-lg-3'}
//        ],
//        [
//        {components:['relationshipLabel','relationship'],class:'col-lg-3'}
//        ]
//        ]
//
//    }
//
//    ]};
//


}
