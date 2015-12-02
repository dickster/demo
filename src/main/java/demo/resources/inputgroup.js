
$(function() {

    $.widget( "brovada.inputGroup", {

        options : { foo:'bar'},

        _create: function() {
            if (!this.options.prefix && !this.options.suffix) {
                return;
            }
            var tf = $('#'+this.options.markupId);
            tf.wrap("<div class='input-group'></div>");
            if (this.options.prefix) {
                $( "<span class='input-group-addon'>"+this.options.prefix+"</span>").insertBefore(tf);
            }
            if (this.options.suffix) {
                $( "<span class='input-group-addon'>"+this.options.suffix+"</span>").insertAfter(tf);
            }
        }

    });

});


