
easy.autocomplete = (function() {


    var defaultOptions = {
    };


    var init = function(options) {
        $('#'+options.id).autocomplete = new autocomplete($.extend(defaultOptions,options));
    };

    function autocomplete(opts) {
        var options = opts;
        var $input = $('#'+opts.id);
        var $overlay = $('<span class="vin-overlay">HELLO THERE</span>');
        var oldTerm;


        // TODO : this map should include year bitmasks to show what years are available.
        var manufacturers = {
            'Ford':["Mustang", "Thunderbird", "Fairlane", "Edsel"],
            'GMC':["Sonic", "Volt", "Cadillac"],
            'Chrysler':["Jeep", "Caravan", "PickupTruck"],
            'Toyota':["Prius", "Camry", "Corolla"],
            'Honda':["Civic", "Accord", "Prelude", "Fit" ],
            'Hyundai':["Sonata"],
            'Volkswagen':["Beetle", "Rabbit", "Golf", "Jetta"],
            'Mercedes':["435W"],
            'Kia':["Soul"],
            'Smart':["ForTwo"],
            'Fiat':["Spider", "500C"]
        };

        // this should be global?
        var years = [];

        function split( val ) {
            return val.split(' ');
        }
        function extractLast( term ) {
            return split( term ).pop();
        }
        function endsWith(t,s) {
            return t.length >= s.length && t.substr(t.length - s.length) == s;
        }
        function isPossibleChangedVin(term) {
            return term.trim().length==17 && term!=oldTerm;
        }
        function getHints(term) {
            // parse...VIN, leave it...
            //         1-3 digits --> matching years.
            //         space after year ---> all manufacturers
            //         alphanumeric after year  --> matching manufacturers.   //TODO : allow for spaces in manufacturer
            //         space after manufacturer --> matching models.   // TODO : allow for year filtering.

            // if it ends with space...return the next list of possibilities.
            // e.g.   '1998 Ford ' will return all ford models
            var endsWithSpace = endsWith(term, ' ');
            var tokens = split(term.trim());
            if (tokens.length==0 || !tokens[0].match('^\\d+$')) {
                // overlay extracted VIN here!!!!
                if (isPossibleChangedVin(term)) {
                    oldTerm = term;
                    $input.trigger({type:'vinput',text:term});
                }
                return [];
            } else if (tokens.length==1 && !endsWithSpace) {
                return years;
            } else if ((tokens.length==2 &&!endsWithSpace) || (tokens.length==1 && endsWithSpace)) {
                return Object.keys(manufacturers);
            } else if (tokens.length==3 || (tokens.length==2 && endsWithSpace)) {
                var make = tokens[1];  // TODO : join tokens 1...tokens.length-1
                return manufacturers[make];
            }
        }

        $overlay.insertAfter($input);

        var to = new Date().getFullYear();
        var from = to - 25;
        for (i=0; i<=25; i++) {
            years[i] = (to-(25-i)).toString();
        }

        $input
            // don't navigate away from the field on tab when selecting an item
            .bind( 'keydown', function( event ) {
                if ( event.keyCode === $.ui.keyCode.TAB &&
                    $( this ).autocomplete( "instance" ).menu.active ) {
                    event.preventDefault();
                }
            })
            .bind( 'vinput', function(event) {
                // do ajax call here....
                $overlay.toggle();
            })
           .addClass('form-control')
            .autocomplete({
                minLength: 1,
                source: function( request, response ) {
                    // delegate back to autocomplete, but extract the last term
                    response( $.ui.autocomplete.filter(
                        getHints(request.term), extractLast( request.term ) ) );
                },
                focus: function() {
                    // prevent value inserted on focus
                    return false;
                },
                select: function( event, ui ) {
                    var terms = split( this.value );
                    terms.pop();
                    terms.push( ui.item.value );
                    this.value = terms.join(' ');
                    return false;
                }
            });

        return {
            // autocomplete functions....
        }

    }

    return {
        init : init
    };

});
