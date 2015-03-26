
$.widget( "brovada.textField", {

    _create: function() {
        var foobar = this.options.someValue;
        this.element.addClass( "blah" ).text( foobar );
    },

    publicMethod : function() {
        // do something...
    },

    value: function( value ) {
        // No value passed, act as a getter.
        if ( value === undefined ) {
            return this.options.value;
            // Value passed, act as a setter.
        } else {
            this.options.value = value;
            this.element.text( value );
        }

    },

    _setOption: function( key, value ) {
        this.options[ key ] = value;
        this._update();
    },

    _update : function() {
        _trigger("update", null, {someOption:'s5d'} /*<--options object */)
        // do stuff here....
    }

});


var easyForm = (function() {

    var init = function(widgets) {
        $.each(widgets, function(index,widget) {
            widget.id;
            widget.pluginType;
            widget.options;
            var $w = $(widget.id);
            if ($w.length) {
                var $plugin = $w.attr(widget.pluginType);
                if ($plugin.length) {
                    $plugin.call(widget.options);
                } else {
                    throw "can't find plugin [" + widget.pluginType + "] for widget [" + widget.id + "]";
                }
            } else {
                throw "can't find widget [" + widget.id + "]";
            }
        });
    }

    return {
        init:init
    }

})();

