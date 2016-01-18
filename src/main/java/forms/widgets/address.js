/**
 * requires.... 
 *  <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script> 
 */


// NOTE : where should i package this???
var easy = easy ? easy : {};
easy.address = (function() {

    var create = function(options) {
        var widget = autoCompleteAddress(options);
        $(options.id).data('address',widget);
        return widget;
    }


    function autoCompleteAddress(opts) {
        var defaults = {
            // any default options like country, city, etc... pass 'em here.
        };
        var options = $.extend(defaults, opts);
        var $address = $(options.id);
        var $text = $address.find('.txt');
        var $lat = $address.children('.lat');
        var $lng = $address.children('.lng');
        var $street_address = $address.children('.street-address');
        var $city = $address.children('.city');
        var $country = $address.children('.country');
        var $postalCode = $address.children('.postal-code');
        var $state = $address.children('.state');
        var currentInput;

        function getAddresses(request, response) {
            new google.maps.Geocoder().geocode({'address': request.term, region:'CA' }, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results.length == 0) {
                        response($.map(results, function(value, i) {
                            return value.formatted_address;
                        }));
                    } else if (results.length > 1) {
                        if (options.latitude&&options.longitude) {
                            results = results.sort(function(a,b) {return delta(a,b)})
                        }
                        response(results);
                    }
                    else {
                        response(results);
                    }
                } else {
                    response([]);
                }
            });

        };

        function extractFromGeoCode(components, type) {
            for (var i = 0; i < components.length; i++) {
                for (var j = 0; j < components[i].types.length; j++) {
                    if (components[i].types[j] == type)
                        return components[i].long_name;
                }
            }
            return "";
        }

        function updateNonAddress(textInput) {
            update(textInput);
        }

        function toRad(x) {
            return x * Math.PI / 180;
        }

        function delta(a,b) {
            a.distance = a.distance ? a.distance : distance(a);
            b.distance = b.distance ? b.distance : distance(b);
            return a.distance-b.distance;
        }

        function distance(pt) {
            if (!options.latitude || !options.longitude) {
                throw('you need to specify location bias');
            }
            var lat1 = pt.geometry.location.lat();
            var lng1 = pt.geometry.location.lng();
            var lat2 = options.latitude;
            var lng2 = options.longitude;

            var R = 6371; // km
            var x1 = lat2-lat1;
            var dLat = toRad(x1);
            var x2 = lng2-lng1;
            var dLon = toRad(x2);
            var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                    Math.sin(dLon/2) * Math.sin(dLon/2);
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            var d = R * c;
            console.log('dist ' + lat1 + ' , ' + lng1 + ' = ' + d);
            return d;
        }

        function updateAddress(item) {
            update(item.formatted_address, item.geometry.location, item.address_components);
        }

        function update(formattedAddress, latLng, addressInfo) {
            $text.val(formattedAddress);
            if (latLng) {
                $lat.val(latLng.lat());
                $lng.val(latLng.lng());
            } else { 
                $lat.val(null);
                $lng.val(null);
            }

            // note: fire change event just in case someone's listening to hidden fields.
            if (addressInfo) {
                $street_address.val(extractFromGeoCode(addressInfo, 'street_number') + ' ' + extractFromGeoCode(addressInfo, 'route')).change();
                $city.val(extractFromGeoCode(addressInfo, 'locality')).change();
                $country.val(extractFromGeoCode(addressInfo, 'country')).change();
                $postalCode.val(extractFromGeoCode(addressInfo, 'postal_code')).change();
                $state.val(extractFromGeoCode(addressInfo, 'administrative_area_level_1')).change();
            } else {
                $street_address.val(null).change();
                $city.val(null).change();
                $country.val(null).change();
                $postalCode.val(null).change();
                $state.val(null).change();
            }

            currentInput = $text.val();
        }

        var textOptions = {
            delay: 500,
            minLength: 1,
            source: function(request, response) {
                getAddresses(request, response);
            },
            select: function(event, ui) {
                updateAddress(ui.item);
                return false;
            },
            change: function(a, b) {
                // address is NOT a selected value, just some random input so we'll remove any gps, postal code, country etc... values.
                if (currentInput != $text.val()) {
                    updateNonAddress($text.val());
                }
                return false;
            }
        };

        if ($text.size() > 0) {
            $text.autocomplete(textOptions).data('ui-autocomplete')._renderItem = function(ul, item) {
                    return $("<li></li>")
                            .data("item.autocomplete", item)
                            .append("<a>" + item.formatted_address + "</a>")
                            .appendTo(ul);
                };
        }

        return {
            // no exposed API for autoComplete widget yet.
        };

    }

    return {
        create: create
    };

})();




