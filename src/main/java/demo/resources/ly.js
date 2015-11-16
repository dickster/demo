
var rowsCss = ["col-md-12","col-md-6","col-md-4","col-md-3","col-md-2","col-md-2","col-md-1","col-md-1","col-md-1","col-md-1","col-md-1","col-md-1" ];


function doLayout(opts) {
    var $form = $('#'+opts.id);
    var layout = opts.layout;
    var rowCss = rowsCss[layout.colsPerRow];

    for (var i = 0; i < layout.groups.length; i++){
        var $formGroup = $('<div class="col-md-6 form-group"></div>');
        $formGroup.append($form);
        for (var j = 0; j < layout.groups[i].length; j++){
            var $el = $('#'+opts.nameToId[layout.groups[i][j]]);
            $formGroup.append(el);
        }
    }
}