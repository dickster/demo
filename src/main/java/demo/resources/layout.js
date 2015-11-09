var easy = easy ? easy : {};
easy.layout= (function() {

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
        //doLayout($.extend(defaults, options));
        // TODO : add default options later.
        doLayout(options);
    }

    function doLayout(opts) {
//need to move form?
        var $oldContent = $('#'+opts.id);
        var $content = $('<div class="reformatted-content"></div>').appendTo($oldContent);

        $(opts.layout.sections).each(function(index) {

            if (this.label) $content.append('<h4>'+this.label+'</h4>');

            $(this.rows).each(function(rowsIndex) {
                var $row = $('<div class="form-group"></div>').appendTo($content);
                for (col in this) {
                    alert(this[col]);
                }
//
//                $(this).each(function(rowIndex) {
//                    var $col = $('<div class='+this.class+'></div>').appendTo($row);
//                    $(this.cols).each(function(compIndex){
//                        var $component = $('#'+this);
//                        $component.appendTo($col);
//                    });
//                })
            });
        });

    }

    return {
        init: init
    };

})();




