

var easy = easy ? easy : {};


easy.phone = (function() {

    var defaultOptions = {
    };

    var init = function(options) {
        document.getElementById(options.id).phone = new phone($.extend(defaultOptions,options));
    }

    function phone(opts) {
        var options = opts;
        var $text = $('#'+opts.id);
        var lastValue = '';

        function requiresFormatting(value) {
            if (value && value.trim()!=lastValue && value.length>=3) {
                lastValue = value;
                return true;
            }
            return false;
        }

        // TODO : only call after 3 chars input??
        // also, cache to make sure value.trim() has changed since last call.
        $text.keyup(function(e) {
            var value = $text.val();
            if (requiresFormatting(value)) {
                var url = options.callback + "&text="+value;
                Wicket.Ajax.get({ u: url });
            }
        });

        var update = function(value) {
            $text.val(value);
        }

        return {
            update: update
        }
    }

    return {
        init : init
    }

});
