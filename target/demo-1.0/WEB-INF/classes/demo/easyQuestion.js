
var easy = easy ? easy : {};


easy.question = (function() {

    var defaultOptions = {
    };


    var init = function(options) {
        $('#'+options.id).textarea = new question($.extend(defaultOptions,options));
    }

    function question(opts) {
        var options = opts;
        var $question = $('#'+opts.id);
        var $text = $question.find('textarea');
        var $checkbox = $question.find('input[type=checkbox]');

        $text.toggle($checkbox[0].checked);

        $checkbox.change(function(e) {
            $text.toggle(this.checked);
        });

        return {
        }

    }

    return {
        init : init
    }

});
