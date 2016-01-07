

// TODO : rename this to shorten strings...make js output smaller.
// e.g. wf.init(); instead of workflow.initWidget();

var workflow = function() {

    var url = "?";

    var pushHistory = function() {
        var state = $('#state').val();
        window.history.pushState({name:state, time:new Date()}, "ignored title", state );
    }

    function validateTemplateElement($t, $original) {
        // TODO : only do this in debug mode??
        if ($t.prop('tagName')!=$original.prop('tagName')) {
            console.log("Warning: your template uses different tag types for " + $t.attr('data-template') +
                ".  One is a " + $t.prop('tagName') + " while the other is " + $original.prop('tagName'));
            // this may cause the template to look different when it's populated for realsy.  it's best to use the same types.
        }
    }

    // TODO : copy classes from template data to source!
    var template = function($template_source, $template_data) {
        // find matching data-tempate= X =data-wf values.   copy the data-wf element over next to the template.
        $template_source.find('[data-template]').each(function(i,t) {
            var id = t.getAttribute('data-template');
            var $original = $template_data.find('[data-wf="'+ id +'"]');
            var $t = $(t);
            // now copy the original into the template.
            if ($original.length>0) {
                $original.insertAfter($t);  // TODO : does it copy if it already exists?  does it overwrite existing?
                $original.addClass($t.attr('class'));
                validateTemplateElement($t, $original);
            }
            else {
                console.log("WARNING : you have " + id + " in your template but can't find it in your form. it will be ignored.");
                $t.addClass('unreferenced-template');
            }
        });

        // just for debugging reasons, mark any unused elements as "untemplated".
        $template_data.find('[data-wf]').each(function(index, data) {
            console.log("WARNING: " + data.getAttribute('data-wf') + " is not in template");
            $(data).addClass('not-in-template');
        });

    }

    // only do this once per request....  if form, do layout.
    // if ajax, only do the component.  make sure you do this last.   push this onto queue.

    var updateLayout = function($components) {
        updateCss($components);
        layoutWithTemplate($components);
        console.log('updating layout for ' + $components.attr('id') + ' : ' + $components.attr('data-wf'));
    }

    var updateCss = function($components) {
        $components.each(function(i,el) {
            var $el = $(el);
            var clss = $el.prev('[data-template]').attr('class');
            $el.addClass(clss);
        });
    }

    var layoutWithTemplate = function($el) {
        var hasTemplates = false;
        $el.find('.template-source').each(function(index,tmpl) {
            var $template = $(tmpl);
            var $data = $template.prev('.template-data');
            template($template, $data);
            hasTemplates = true;
        });
        return hasTemplates;
    }

    var init = function() {
        Wicket.Event.subscribe('/ajax/call/complete', function(jqEvent, attributes, jqXHR, errorThrown, textStatus) {
            // look at attributes.  get id.  layout($elWithId);
            var $components = $(jqXHR.responseXML.documentElement.children).filter("component").map(
                function(i,e) {
                    return document.getElementById(e.id);
                });
            updateLayout($components);
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

                // TODO : all widgets should update layouts.

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

