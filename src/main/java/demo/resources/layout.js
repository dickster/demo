var easy = easy ? easy : {};
easy.layout= (function() {

    var rowsCss = [
        null, //"can't have 0 colsPerRow!!!",
        "col-md-12",
        "col-md-6",
        "col-md-4",
        "col-md-3",
        "col-md-2",
        "col-md-2",
        "col-md-1",
        "col-md-1",
        "col-md-1",
        "col-md-1",
        "col-md-1",
        "col-md-1"
        ];

    var init = function(options) {
        //doLayout($.extend(defaults, options));
        // TODO : add default options later.
        doLayout(options);
    }

    function doLayout(opts) {
        var $form = $('#'+opts.id).find('form');

        for (var name in opts.nameToCss) {
            console.log('widget ' + name + '/' +opts.nameToId[name] + ' --> ' + opts.nameToCss[name]);
            var css = opts.nameToCss[name];
            $('#'+opts.nameToId[name]).addClass(css);
        }

        var layout = layoutDef[opts.name];
        if (!layout) return;
        var rowCss = rowsCss[layout.colsPerRow];

        for (var i = 0; i < layout.rows.length; i++) {
            var $row = $('<div class="row"></div>');
            $form.append($row);
            for (var j = 0; j < layout.rows[i].length; j++) {
                var $formGroup = $('<div class="col-md-6 form-group"></div>');
                $row.append($formGroup);
                for (var k = 0; k<layout.rows[i][j].length; k++) {
                    var colName = layout.rows[i][j][k];
                    var $el = $('#'+opts.nameToId[layout.rows[i][j][k]]);
                    $formGroup.append($el);
                }
            }
        }
        console.log("the form is ---> " + $form[0].outerHTML);
    }

    return {
        init: init
    };

})();







