
var easy = easy ? easy : {};

easy.feedback = (function() {

    var defaultOptions = {};

    var update = function(data) {
        var $feedback = $('#'+data.id);
        // remove errors from last time around....
        $('.popover.error').each(function(index,popover) {
            $(popover).prev().popover('hide');
        });
        // ...add new errors.
        $.each(data.messages, function(index,value) {
            addFeedback(value);
        });

        if (data.messages.length>0) {
            $feedback.find('.message').val('').hide();
        } else {
            $feedback.find('.message').val('').hide();
        }
    };

    function addFeedback(value) {
        var reporter = $('#' + value.reporter);
        reporter.popover({placement:'left',content:value.msg, trigger:'manual'}).popover('show');
        reporter.next('.popover').addClass(value.level.toLowerCase());
    }

    return {
        update: update
    }

})();
