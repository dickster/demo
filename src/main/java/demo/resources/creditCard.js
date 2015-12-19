
$(function() {

    $.widget( "brovada.creditCard", {

        defaultOptions : { },

        _create: function() {
            var opts = this.options;   // TODO : extend default options
            var $el = $('#'+opts.markupId);

            function callback(result) {
                var $submitButtons = $el.parents('form').find('.btn-submit');
                if (!result.valid) {
                    $submitButtons.attr('disabled', 'disabled');
                }
                else {
                    $submitButtons.removeAttr('disabled');
                }
                showMsg($(this), result);
            }

            function showMsg(el, result) {
                // TODO : make this work on a time delay.
                el.next('.cc-msg').remove();
                var colorCss = result.valid ? 'alert-info' : 'alert-danger';
                var button = $('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
                var msg = $('<div class="cc-msg alert  alert-dismissible ' +colorCss+ ' "></div >');
                msg.insertAfter(el);
                if (result.valid) {
                    msg.text('Recognized as a ' + result.card_type.name);
                }
                else {
                    msg.text('This is not a valid credit card number');
                }
                msg.append(button);

            }


            // TODO : add list of credit cards to support. for now we'll support all of them.
            $el.validateCreditCard(callback);
        }

    });

});




//
//4000 0000 0000 0002
//4026 0000 0000 0002
//5018 0000 0009
//5100 0000 0000 0008
//6011 0000 0000 0004