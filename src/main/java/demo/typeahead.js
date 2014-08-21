var easy = easy ? easy : {};

easy.typeahead = (function() {

    var defaultOptions = {
    };

    var init = function(options) {
        var widget = $('#'+options.id);
        widget.tah = tah($.extend(defaultOptions,options));
        return widget;
    }

    function tah(options) {
        var $text = $('#'+options.id);
        var source = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.obj.whitespace('car'),
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            local: [{ car:"Audi" }, { car:"Chevy" }, { car:"Ford" }, { car:"Honda" }, { car:"Volkswagen" }, { car:"Mazda" }, { car:"Hyundai" }, { car:"BMW" }, { car:"Vauxhall" }, { car:"Toyota" } ]
        });
        source.initialize();

        function getWordAtCursor(that) {
            // NOTE : this won't work on IE!
            var selection = that.$element.get(0).selectionStart;
            var text = that.query;
            var start = Math.min(selection,text.length);
            for (var from=start; from>=0; from--) {
                if (text.charAt(from)==' ') {
                    break;
                }
            }
            for (var to=start; to<text.length; to++) {
                if (text.charAt(to)==' ') {
                    break;
                }
            }
            return text.substring(from,to).trim();
        }

        function matcher(item) {
            //ignore non-alphanumeric keys...where contents don't change.
            var word=getWordAtCursor(this);
            // need to account for items with spaces in them.
            if (word.length===0) {
                return false;
            }
            return ~item.toLowerCase().indexOf(word.toLowerCase());
        }

        function updater(item) {
            var word = getWordAtCursor(this);
            return this.$element.get(0).value.replace(word,item);
        }

        $text.typeahead(
            {
            hint:true,
            highlight: true,
            minLength: 1
            },
            {
            name:'cars',
            displayKey : 'car',
            source: source.ttAdapter()
        });
    }

    return {
        init : init
    }

})();