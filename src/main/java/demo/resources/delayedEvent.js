
$(function() {

    $.widget( "brovada.delayedEvent", {

        options : { },

        _create: function() {
            var opts = this.options;
            var dirty = false;
            var when = new Date();
            var $el = $('#'+opts.markupId);
            var poll;

            function maybeTrigger() {
                if (new Date()-when>300) {  // TODO : use options.threshold
                    console.log('triggering delayed event');
                    $el.trigger(opts.delayedEvent);
                }
                clearTimeout(poll);
                dirty = false;
            }

            $el.on(opts.event, function(e) {
                console.log('changed....');
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


