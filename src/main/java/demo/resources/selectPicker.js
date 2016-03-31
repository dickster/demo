
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
        // only if *not* multiple do we muck with placeholder
        if (!$select.attr('multiple')) {
            var placeholder = $select.find('option')[0];
            placeholder.setAttribute('disabled','');
            if (opts.title) {
                $(placeholder).text(opts.title);
            }
        }

        if (opts.keywordSearch) {
            $.each($select.find('option'), function(key, value) {
                $(this).attr('data-tokens', key);
            });
        }

        if (opts.keysDisplayed) {
            // may need to swap key & val() display order?
            $.each($select.find('option'), function(key, value) {
                $(this).attr('data-subtext', key);
            });
        }


        $('#'+id).selectpicker(opts);
    };

    return {
        init: init
    };
})();




