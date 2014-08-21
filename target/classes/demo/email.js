
easy.email = (function() {


    var defaultOptions = {
        providers: ['gmail.com', 'yahoo.com', 'hotmail.com', 'brovada.com']
    };


    var init = function(options) {
        $('#'+options.id).email = new email($.extend(defaultOptions,options));
    };

    function email(opts) {
        var $input =$('#'+opts.id);
        var options = opts;

        function getHints(term) {
            var index = term.indexOf('@');
            if (index==-1) {
                return [];
            }
            return {
                values: options.providers,
                term:index==term.length ? '' : term.substr(index+1)
            };
        }

        $input
            // don't navigate away from the field on tab when selecting an item
            .bind( 'keydown', function( event ) {
                if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).autocomplete( "instance" ).menu.active ) {
                    event.preventDefault();
                }
            })
            .addClass('form-control')
            .autocomplete({
                minLength: 1,
                source: function( request, response ) {
                    // delegate back to autocomplete, but extract the last term
                    var hints = getHints(request.term);
                    response( $.ui.autocomplete.filter(
                        hints.values, hints.term ) );
                },
                focus: function() {
                    // prevent value inserted on focus
                    return false;
                },
                select: function( event, ui ) {
                    var index = this.value.indexOf('@');
                    // assert index!=-1.
                    this.value = this.value.substr(0,index) + '@' + ui.item.value;
                    return false;
                }
            });

        return {
        }

    }

    return {
        init : init
    };

});
