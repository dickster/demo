/**
 * Created by user on 2016-01-05.
 */

$(function() {

    $.widget( "brovada.type_ahead", {

//        options : { foo:'bar'},

        _create: function() {
            var opts = this.options;
            var $widget = $('#'+this.options.markupId);

            // Instantiate the Bloodhound suggestion engine
            var data = new Bloodhound({
                datumTokenizer: function (datum) {
                    // TODO : make this a parameter...
                    return Bloodhound.tokenizers.whitespace(datum.value);
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                remote: {
                    cache: opts.cache,
                    url: opts.url
                }
            });

            // Initialize the Bloodhound suggestion engine
            data.initialize();

            // Instantiate the Typeahead UI


        }

    });

});






