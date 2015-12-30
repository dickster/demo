// TODO : rename this to shorten strings...make js output smaller.

var workflow = function() {

        var url = "?";

        var pushHistory = function() {
            var state = $('#state').val();
            window.history.pushState({name:state, time:new Date()}, "ignored title", state );
        }

        var init = function() {
            window.onpopstate = function(event) {
                var u = url + "&state=" + window.history.state.name;
                Wicket.Ajax.get({u:u});
            };
            var state = $('#state').val();
            history.replaceState({name:state,time:new Date()}, "huh", state);
        }

        var initWidget = function(config) {
            var w = widget(config);
            w.initializePlugin();
            if (config.type=="FORM") {
                w.layout();  // <-- TODO : move this outside of widget.
            }
            // TODO : if ajax, update layout for widget only...do this by intercepting Wicket.AJAX.event.
        }

    function widget(conf) {
            var config= conf;
            var $widget = $('#'+config.markupId);

            var initializePlugin = function() {
                // THESE ARE HACKS AND WILL BE WRITTEN AS JQUERY UI PLUGINS.
                if ( config.type=="ADDRESS") {
                    config.id = '#'+config.markupId;
                    easy.address.create(config);
                    return;
                }

                if (!config.pluginName) return;

                config.options.markupId = config.markupId;
                console.log('about to initialize widget ' + config.id + ' with plugin ' + config.pluginName + ' and options ' + JSON.stringify(config.options));
                try {
                    $widget[config.pluginName](config.options);
                }
                catch (err) {
                    console.log("error launching plugin " + config.pluginName + err);
                }

                // TODO : all widgets should update layouts.

            };

            function validateTemplateElement($t, $original) {
                // TODO : only do this in debug mode??
                if ($t.prop('tagName')!=$original.prop('tagName')) {
                    console.log("Warning: your template uses different tag types for " + $t.attr('data-template') +
                        ".  One is a " + $t.prop('tagName') + " while the other is " + $original.prop('tagName'));
                    // this may cause the template to look different when it's populated for realsy.  it's best to use the same types.
                }
            }

            var template = function($template_source, $template_data) {
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
        // if ajax, only do the component.

            var layoutWithTemplate = function() {
                // the debug button we'll always move over. no need to include it in template.
//                t.prepend(form.find('.btn-debug'));
                var hasTemplates = false;
                var $component = $('#'+config.markupId);
                $component.find('.template-source').each(function(index,tmpl) {
                    var $template = $(tmpl);
                    var $data = $template.prev('.template-data');
                    template($template, $data);
                    hasTemplates = true;
                });
                return hasTemplates;
            }

            return {
                layout : layoutWithTemplate,
                initializePlugin : initializePlugin
            }

        };


    return {
            initWidget : initWidget,
            init : init,
            pushHistory : pushHistory
        };



    var rowsCss = [
        null, //"can't have zero colsPerRow!!!",
        "col-md-12",
        "col-md-6",
        "col-md-4",
        "col-md-3",
        "col-md-2",
        "col-md-2",
        "col-md-1",
        "col-md-1",
        "col-md-1",
        "col-md-1",
        "col-md-1",
        "col-md-1"
    ];


}();

