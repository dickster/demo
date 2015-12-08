
var easy = easy ? easy : {};


easy.textarea = (function() {

    var defaultOptions = {
        rows:'3'
    };

    var init = function(options) {
        var ta = new textArea(options);
        $('#'+options.id).textarea = ta;
    }

    function textArea(opts) {
        var options = $.extend(defaultOptions,opts);
        var id = options.id;
        var $parent = $('#'+id);
        var $area = $parent.find('textarea').first();
        var $placeholder = $('<textarea class="form-control" rows="1" style="z-index:-1;resize:none;overflow:none;"/>').insertAfter($area);

//        function cursorPos(el) {
//            var pos = 0;
//            if('selectionStart' in el) {
//                pos = el.selectionStart;
//            } else if('selection' in document) {
//                el.focus();
//                var Sel = document.selection.createRange();
//                var SelLength = document.selection.createRange().text.length;
//                Sel.moveStart('character', -el.value.length);
//                pos = Sel.text.length - SelLength;
//            }
//            return pos;
//        }

        $parent.textarea = arguments.callee;

        $area.css({ 'z-index':'222',resize:'none' });
        $area.attr('rows', options.rows);

        if (options.floating) {
            $area.css({ position:'absolute', top:'0', left:'0' });
        }

        var view = function() {
            $placeholder.val($area.val()).show();
            $area.hide();
        };

        var edit = function() {
            $area.show();
            $area.focus();
            if (!options.floating) {
                $placeholder.hide();
            }

            // TODO : set proper selection spot.
        };

        $area.blur(function(e) {
            view();
        });
        $placeholder.focus(function(e) {
            edit();
        });

        view();

        return {
            view : view,
            hello : function() { alert('hi');},
            edit : edit
        }

    }

    return {
        init : init
    }

});
