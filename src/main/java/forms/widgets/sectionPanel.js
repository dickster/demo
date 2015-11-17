
var easy = easy ? easy : {};


easy.sectionPanel = (function() {

    var defaultOptions = {
        emptyText:'\u00a0',
        expand : false,
        header : {
            minWidth : '7em',
            paddingRight: '50px',
            maxWidth : '110em'
        }
    };

    var init = function(options) {
        document.getElementById(options.id).tabPanel = tabPanel($.extend(defaultOptions,options));
    }

    var expandAll = function() {
        $('#'+options.id + ' .tab-panels').show();
    }

    var collapseAll = function() {
        $('#'+options.id + ' .tab-panels').hide();
    }


    function tabPanel(options) {
        var id = options.id;
        var $tabPanel = $('#'+id);
        var options = options;
        var current = options.current;
        var $panels = $('#'+options.id + ' .tab-panels');
        var $header = $tabPanel.find('.tab-header');
        var $status = $tabPanel.find('.tab-status i');

        var selectTab = function(index) {
            // deselect current...
            $tabPanel.find('li.current').removeClass('active');
            // ...select new one.
            $tabPanel.find(' ul li').eq(index).addClass('active');
        };

        var selectLastTab = function() {
            selectTab($tabPanel.find('ul li').length-1);
        };

        var selectFirstTab = function() {
            selectTab(0);
        };

        var setStatus = function(status) {
            $status.removeClass().addClass(status);
            if (status) {
                $panels.show();
            }
        };


        function updatePopover(li,text,init) {
            if (init) {
                li.popover({
                    trigger: 'hover',
                    'placement': 'bottom'
                });
            }
            // HACK...scrollwidth doesn't work for li so i hack it to use child and add fudge factor. FIX THIS.
            var popoverState = li.find('span').outerWidth()+36 <= li[0].scrollWidth ? 'disable' : 'enable';
            li.popover(popoverState);
            li.attr('data-content', text);
        }

        function updatePopovers() {
            $.each($tabPanel.find('.tab-list li'),function(index,li) {
                var $tab = $(li);
                updatePopover($tab,$tab.find('a span').text(),true);
            });
        }

        function updateAddTooltips() {
            if (!options.addTooltip) return;
            $tabPanel.find('.tab-add')
                .attr('data-toggle','tooltip')
                .attr('title',options.addTooltip)
                .tooltip({placement:'right'});
        }

        function layout() {
            var $tabList = $tabPanel.find('ul');
            var width = $tabPanel.find('.tab-row').width() - $header.width() - 32;  // - size of status icon at end?
            var $tabs = $tabPanel.find('ul li');
            // how many tabs...how much room for each??
            var tabCount  = $tabs.length;
            var size = Math.floor(width/(tabCount+1))+'px';

            if (!options.canAdd) {
                var $tabRow = $tabPanel.find('.tab-row');
                $tabRow.addClass('tab-fixed');
            }
            if (options.expand) {
                $panels.show();
            }

            $tabs.css({minWidth:size,maxWidth:size});
            // TODO : if > maxTabs, throw the excess in another <ul class="extra-tabs"><li>blah</li></ul>
            // have minTazbWidth option.
            $tabPanel.find('.tab-header').css(options.header);
            $.each(options.titleInputs,function(index,input) {
                if(!input) return;
                var $input = $(input);
                var $link = $($tabPanel.find('li .tab-name')[index]);
                // only show popover if name is too long to fit in tab.
                $input.keypress(function() {
                    setTimeout(function() {
                        var text = $input.val();
                        var span = $link.find('span');
                        if (text) {
                            span.removeClass('tab-empty').text(text);
                        } else {
                            span.addClass('tab-empty').text(options.emptyText);
                        }
                        updatePopover($link.parent(), $input.val());
                    },50);
                });
                // chrome doesn't fire backspace key events? why.  this hack covers up that flaw...ugly.
                $input.keyup(function(e) {
                    if (!e.charCode) {
                        $(this).trigger('keypress', e);
                    }
                });
            });

        }


        $('.tabs').tab();

//        if (options.collapsed) {
//            $panels.hide();
//        }

//        $header.click(function(e) {
//            $panels.toggle();
//        });


//        layout();

//        updateAddTooltips();

        selectTab(options.current);

//        updatePopovers();

//        $tabPanel.find(' ul li').click(function(e) {
//            selectTab($(this).index());
//            e.preventDefault();
//        });


        return {
            setStatus : setStatus,
            selectFirstTab : selectFirstTab,
            selectLastTab: selectLastTab
        }

    }

    return {
        init : init,
        expandAll : expandAll,
        collapseAll : collapseAll
    }



});
