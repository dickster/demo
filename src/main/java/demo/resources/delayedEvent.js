
$(function() {

    $.widget( "brovada.delayedEvent", {

        defaultOptions : { },

        _create: function() {
            var opts = this.options;   // TODO : extend default options
            var dirty = false;
            var when = new Date();
            var $el = $('#'+opts.markupId);
            var poll;

            function maybeTrigger() {
                if (new Date()-when > opts.threshold) {
                    $el.trigger(opts.delayedEvent);
                }
                clearTimeout(poll);
                poll=null;
                dirty = false;
            }

            $el.on(opts.event, function(e) {
                when = new Date();
                dirty = true;
                if (poll) {
                    clearTimeout(poll);
                }
                poll = setTimeout(maybeTrigger, 250);
            });
        }

    });

});


