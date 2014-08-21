
// NOTE : where should i package this???
var easy = easy ? easy : {};

easy.datelabel = (function() {

    var defaults = { timeago:{} };

    var init = function(opts) {
        var options = $.extend(defaults, opts);
        $.timeago.settings.allowFuture = true;
        var el = $('#'+options.id).attr('title', options.isoDate);
        el.timeago();
    }

    return {
        init : init
    }
});
