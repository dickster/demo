
var wf = function() {

    var url = "?";

    var pushHistory = function() {
        var state = $('#state').val();
        window.history.pushState({name:state, time:new Date()}, "ignored title", state );
    }

    var init = function() {
        Wicket.Event.subscribe('/ajax/call/complete', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {
            // at the end of all ajax calls, relayout components.
            var $components = $(jqXHR.responseXML.documentElement.children).filter("component").map(
                function(i,e) {
                    return document.getElementById(e.id);
                })
            wf.layout.update($components);
        });

        window.onpopstate = function(event) {
            var u = url + "&state=" + window.history.state.name;
            Wicket.Ajax.get({u:u});
        };

        var state = $('#state').val();
        history.replaceState({name:state,time:new Date()}, "huh", state);
    }

    var widget = function(config) {
        var w = _widget(config);
        w.addDependents();
        w.initializePlugin();
        w.layout();
    }

        //  ----------- WIDGET obj ----------------

        function _widget(conf) {
            var config= conf;
            var $widget = $('#'+config.options.markupId);
            // this will typically be the widget itself.  but it may be a wrapping container.
            var $containedWidget = $widget.closest('[data-wf]');

            var addDependents = function() {
                if (!config.dependents) return;
                // add change listener...show dependents when true.  (& trigger change on this?).
                $widget.on('change', function(e) {
                    var val = $widget.val();
                    for (var key in config.dependents){
                        for (var i=0;i<config.dependents[key].length;i++){
                            var d = config.dependents[key][i];
                            $('[data-wf="'+d+'"]').toggle(val===key);
                            // TODO : clear value when you hide the value.
                            // if (val!=key) clearInput($('[data-wf="'+d+'"]'));
                        }
                        // TODO : should use text version which is...
                        // $widget.find('option[value="'+$widget.val()+'"]').text()
                    }
                });
                $widget.trigger('change');
            };

            var layout = function() {
                wf.layout.init($widget, config.options);
            }

            var applyPlugin = function() {
                // THESE ARE HACKS AND NEED TO BE WRITTEN AS JQUERY UI PLUGINS.
                // e.g. $('#foo').address();
                if ( config.type=="ADDRESS") {
                    config.id = '#'+config.options.markupId;
                    easy.address.create(config);
                    return;
                }
                //-----------------------------------------------------------------

                if (!config.pluginName) return;

                console.log('about to initialize widget ' + config.id + ' with plugin ' + config.pluginName + ' and options ' + JSON.stringify(config.options));
                try {
                    $widget[config.pluginName](config.options);
                }
                catch (err) {
                    console.log("error launching plugin " + config.pluginName + err);
                }

            };

            return {
                layout : layout,
                addDependents : addDependents,
                initializePlugin : applyPlugin
            }


        }; // ------- /widget ---------



    return {
        widget : widget,
        init : init,
        pushHistory : pushHistory
    };

}();

