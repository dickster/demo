

// TODO : rename this to shorten strings...make js output smaller.
// e.g. wf.init(); instead of workflow.initWidget();

var workflow = function() {

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
                });
            workflow.layout.updateTemplateCss($components);
        });

        window.onpopstate = function(event) {
            var u = url + "&state=" + window.history.state.name;
            Wicket.Ajax.get({u:u});
        };
        //CAVEAT : it is assumed that this will be called after all .initWidget() calls for a regular page load.
        // if not, it may impact the layout...it's best to do layout after all elements have sorted themselves out.

        updateLayout($('body'));

        var state = $('#state').val();
        history.replaceState({name:state,time:new Date()}, "huh", state);
    }

    var initWidget = function(config) {
        var w = widget(config);
        w.addDependents();
        w.initializePlugin();
    }



        function widget(conf) {
            var config= conf;
            var $widget = $('#'+config.options.markupId);

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

            var initializePlugin = function() {
                // THESE ARE HACKS AND WILL BE WRITTEN AS JQUERY UI PLUGINS.
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

                workflow.layout.init($widget, config);
            };

            return {
                addDependents : addDependents,
                initializePlugin : initializePlugin
            }


        }; // ------- /widget ---------



    return {
        initWidget : initWidget,
        init : init,
        pushHistory : pushHistory
    };

}();

