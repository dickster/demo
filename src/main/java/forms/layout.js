var easy = easy ? easy : {};
easy.layout= (function() {

//    rows: ["foo", "bar", "hello".]
//
//        OR
//
//    rows : [ {id:"foo", css:"col-log-3"}, ]

//    var defaults = {sections:
//        [{id:'nameSection', label:'INFO',
//            rows:[
//                [ {components:['firstNameLabel','firstName'],class:'col-lg-3'},
//                    {components:['middleNameLabel','middleName'],class:'col-lg-3'},
//                    {components:['lastNameLabel','lastName'],class:'col-lg-3'},
//                    {components:['compInd'],class:'col-lg-3'}
//                ]
//            ]
//        },
//            {id:'addressSection', label:'ADDRESS',
//                rows:
//                    [
//                        [  {components:['addrLabel', 'addr'],class:'col-lg-6'},
//                            {components:['addr2Label','addr2'],class:'col-lg-3'}
//                        ],
//                        [
//                            {components:['cityLabel','city'],class:'col-lg-3'},
//                            {components:['provLabel','prov'],class:'col-lg-3'},
//                            {components:['postalCodeLabel','postalCode'],class:'col-lg-3'}
//                        ],
//                        [
//                            {components:['countryLabel','country'],class:'col-lg-3'}
//                        ],
//                        [
//                            {components:['relationshipLabel','relationship'],class:'col-lg-3'}
//                        ]
//                    ]
//
//            }
//
//        ]};


    var init = function(options) {
        doLayout($.extend(defaults, options));
    }


    function doLayout(opts) {
        $(opts.sections).each(function(index) {
            var $oldContent = $('#'+this.id);
            var $content = $('<div class="reformatted-content"></div>').appendTo($('#'+this.id));
            $content.append('<h4>'+this.label+'</h4>')
            $(this.rows).each(function(rowsIndex) {
                var $row = $('<div class="row"></div>').appendTo($content);
                $(this).each(function(rowIndex) {
                    var $col = $('<div class='+this.class+'></div>').appendTo($row);
                    $(this.components).each(function(compIndex){
                        var $component = $('#'+this);
                        $component.appendTo($col);
                    });
                })
            });
        });

    }

    return {
        init: init
    };

})();




