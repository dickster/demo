/**
 * Created by user on 2016-01-05.
 */

$(function() {

    $.widget( "brovada.type_ahead", {

        options : { foo:'bar'},

        _create: function() {
            var url = this.options.url;
            var $widget = $('#'+this.options.markupId);

            // Instantiate the Bloodhound suggestion engine
            var movies = new Bloodhound({
                datumTokenizer: function (datum) {
                    // TODO : make this a parameter...
                    return Bloodhound.tokenizers.whitespace(datum.value);
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                remote: {
                    url: url,
                    filter: function (movies) {
                        // Map the remote source JSON array to a JavaScript object array
                        return $.map(movies.results, function (movie) {
                            return {
                                value: movie.original_title // TODO : make this a parameter.
                            };
                        });
                    }
                }
            });

            // Initialize the Bloodhound suggestion engine
            movies.initialize();

            // Instantiate the Typeahead UI
            $widget.typeahead(null, {
                    displayKey: 'value',   // TODO : make this a parameter.
                    source: movies.ttAdapter()
            });

        }

    });

});






