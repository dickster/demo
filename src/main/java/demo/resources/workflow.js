
var wf = function() {

    var url = "?";

    var pushHistory = function() {
        var state = $('#state').val();
        window.history.pushState({name:state, time:new Date()}, "ignored title", state );
    }

    var init = function() {
        // TODO : check this back/history stuff...it used to work but doesn't anymore.
        window.onpopstate = function(event) {
            var u = url + "&state=" + window.history.state.name;
            Wicket.Ajax.get({u:u});
        };

        var state = $('#state').val();
        history.replaceState({name:state,time:new Date()}, "huh", state);
    }


    // idea!  for dependents, use html to group them.
    // specify data-dep=="??".  that's the onchange val() of element that controls visibility toggling of slave.
    //  <div class="dependent">
    //      <input class="master">
    //      <input class="slave" data-dep="true">
    //      <input class="slave" data-dep="false">
    // </div>
    //   OR
    // ----slaves can be divs.
    //  <div class="dependent">
    //      <input class="master">
    //      <div><class="slave" data-dep="apple"><input .../> <label..../> <span.../> etc.. </div>
    //      <div><class="slave" data-dep="orange"><input .../> <label..../> <span.../> etc.. </div>
    // </div>

    // .js = $(".dependent").each(function(i,v) {
    //     var $slaves = $(this).find('.slave');
    //     $(this).find('.master').change(function(e) {
    //          var val = $(this).val();
    //          $slaves.each(function(i,v) {
    //              $(v).toggle(val===$(v).attr('data-dep'));
    //          }
    // }
    var widget = function(config) {
        var w = _widget(config);
        w.addDependents();
        w.initializePlugin();
    }

        //  ----------- WIDGET obj ----------------

        function _widget(conf) {
            var config= conf;
            var $widget = $('#'+config.options.markupId);
            var $component = config.selector ? $widget.find(config.selector) : $widget;
            //TODO : assert component.size()==1

            // TODO : make sure this works if elements are replaced by ajax (i.e. the event handler still is attached).
            var addDependents = function() {
                if (!config.dependents) return;
                // add change listener...show dependents when true.  (& trigger change on this?).
                $component.on('change', function(e) {
                    var val = $component.val();
                    for (var key in config.dependents){
                        for (var i=0;i<config.dependents[key].length;i++){
                            var d = config.dependents[key][i];
                            $('[data-wf="'+d+'"]').toggle(val===key);

                            // TODO : stop using 'data-wf'.   @Deprecate it!!!!

                            // TODO : clear value when you hide the value.
                            // if (val!=key) clearInput($('[data-wf="'+d+'"]'));
                        }
                        // TODO : should use text version which is...
                        // $widget.find('option[value="'+$widget.val()+'"]').text()
                    }
                });
                $widget.trigger('change');
            };

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
                    $component[config.pluginName](config.options);
                }
                catch (err) {
                    console.log("Error : plugin [" + config.pluginName + "] failed. -->  " +  err);
                }

            };

            return {
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

