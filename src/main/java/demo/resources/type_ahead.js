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
            var data = new Bloodhound({
                datumTokenizer: function (datum) {
                    // TODO : make this a parameter...
                    return Bloodhound.tokenizers.whitespace(datum.value);
                },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                remote: {
                    cache: false,  // TODO : make this a parameter.  true by default.
                    url: url
//                    filter: function (movies) {
//                        // Map the remote source JSON array to a JavaScript object array
//                        return $.map(movies.results, function (movie) {
//                            return {
//                                value: movie.original_title
//                            };
//                        });
//                    }
                }
            });

            // Initialize the Bloodhound suggestion engine
            data.initialize();

            // Instantiate the Typeahead UI
            $widget.typeahead({
                source: function(query, cb) {
                    data.search(query, cb, cb);
                }
//                templates: {
//                    empty: '<div class="empty-message">No matches.</div>'
//                }
            });
               // .bind('typeahead:selected', select);

        }

    });

});






