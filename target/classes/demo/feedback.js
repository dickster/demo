
var easy = easy ? easy : {};

easy.feedback = (function() {

    var defaultOptions = {};

    var update = function(data) {
        var $feedback = $('#'+data.id);
        // remove errors from last time around...
        $('.popover.error').each(function(index,popover) {
            $(popover).remove();
        });
        // ...add new errors.
        $.each(data.messages, function(index,value) {
            addFeedback(value);
        });
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
