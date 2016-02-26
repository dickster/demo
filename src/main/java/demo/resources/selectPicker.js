
var easy = easy ? easy : {};

easy.selectPicker = (function() {

    var init = function(id, opts) {
        // TODO : add options elements here if they are specified in parameter.
        var $select = $('#'+id);

        if (opts.data && window[opts.data]) {
            var data = window[opts.data];
            $.each(data, function(key,value) {
                    $select.append($("<option></option>")
                            .attr("value",key)
                            .text(value));
                });
        };
        // only if not multiple
        if (!$select.attr('multiple')) {
            var placeholder = $select.find('option')[0];
            placeholder.setAttribute('disabled','');
            if (opts.title) {
                $(placeholder).text(opts.title);
            }
        }


        $('#'+id).selectpicker(opts);
    };

    return {
        init: init
    };
})();




