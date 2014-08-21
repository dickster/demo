
var easy = easy ? easy : {};

easy.name = (function() {

    var names = [];

    var create = function(options) {
        var widget = name(options);
        widget.init();
        return $('#'+options.id).data('nameWidget',widget);
    }

    function unquoted(text) {
        if (!text) return '';
        return text.replace(/"/g, "");
    }

    function quoted(input) {
        if (!input.value) return '';
        var text = input.value.trim();
        text = text.indexOf(' ')!=-1 ? '"' + text + '"' : text;
        if (text.length>0) text = text + ' ';
        return text;
    }

    function name(opts) {
        var defaults = {popover:{html:true,placement:'auto right',trigger:'manual',title:'Did you mean...?'}};
        var options = $.extend(defaults, opts);
        var $name = $('#'+opts.id);
        var $dialog;
        var $parentForm = $name.closest('form');

        function popover() {
            return $name.next('.popover');
        }

        function updateFromDialog(nameInputs) {
            $name.val(quoted(nameInputs[0]) + quoted(nameInputs[1]) + quoted(nameInputs[2]));
        }

        function didYouMean(name) {
            $name.popover('show');
            var nameInputs = popover().find('.popover-content input');
            nameInputs[0].value = unquoted(name.first);
            nameInputs[1].value = unquoted(name.middle);
            nameInputs[2].value = unquoted(name.last);
            updateFromDialog(nameInputs);
            nameInputs.keyup(function() {
                setTimeout(function() { updateFromDialog(nameInputs); }, 50 );  // keep polling when input text might change.
            });
        }

        function update() {
            var formattedText = [];
            $dialog.find('input').each(function() {
                var text = $(this).val().toString().trim();
                if (text.split(" ").length>1 && text.toString().substring(0, 1)!=='"') {
                    formattedText.push('"' + text.replace('"','').trim() + '"');
                } else {
                    formattedText.push(text.replace('"','').trim());
                }
            });
            $name.val(formattedText.join(' '));
        }

        function validateByAjax() {
            var url = options.callback + "&text="+$name.val();
            Wicket.Ajax.get({ u: url });
        }

        var init = function() {
            $name.blur(function(e) {
                validateByAjax();
            });
            $name.popover({
                html : true,
                placement : 'auto left',
                trigger : 'manual',
                title: 'Did you mean...',
                // TODO : refactor this so it is part of EasyName.html.  make it a fragment with customizable labels/title.
                content: '<form class="did-you-mean"><label>First</label><input type="text"/><label>Middle</label><input type="text"/><label>Last</label><input type="text"/><a>Ok</a></form>'
            });
            names.push(this);
            if (names.length==1) {   // first time thru add listener, only need to do it once.
                $('body').on('click', function (e) {
                    var popover = $(e.target).parents('.popover');
                    $.each(names,function(index,value) {
                        value.maybeHidePopover(popover);
                    });
                });
            }
        };

        var maybeHidePopover = function(clickedPopover) {
            var myPopover = $name.next('.popover');
            if(clickedPopover.length>0 && myPopover.length>0 & clickedPopover[0]===myPopover[0]) {
                // don't hide unless we know it's not the one we are associated with.
            } else {
                $name.popover('hide');
            }
        };

        return {
            init : init,
            maybeHidePopover : maybeHidePopover,
            didYouMean : didYouMean
        };
    }

    return {
        create: create
    };

})();




