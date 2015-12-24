// TODO : rename this to shorten strings...make js output smaller.

var workflow = function() {

        var url = "?";

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
            if (config.isAjax) {
                w.updateLayout();
            }
        }

        function widget(conf) {
            var config= conf;
            var $widget = $('#'+config.markupId);

            var updateLayout = function() {
                // should assert that previous node is the template?
                copyAttributes($widget.prev(), $widget);
            }

            var initializePlugin = function() {
                // THESE ARE HACKS AND WILL BE WRITTEN AS JQUERY UI PLUGINS.
                if (config.type=="FORM") {
                    try {
                        url = config.url;  // required for history support.
                        layout(); // TODO : turn this into a plugin.
                        return;
                    } catch (err) {
                        console.log("can't layout form.  maybe your layout definition is wrong?");
                    }
                }
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
            };

            // TODO : layout a single widget...look only for specific data-wf-idsdata-

            function moveTargetIntoTemplate(source, target) {
                if (target.length>0) {
                    target.insertAfter(source);
                    copyAttributes(source, target);
                    source.attr('data-wf-template','').hide();
                }
                else {
                    console.log("WARNING : you have " + source.attr('data-wf') + " in your template but can't find it in your form. it will be ignored.");
                    source.addClass('undefined');
                }
            }

            var layoutWithTemplate = function() {
                var form = $(document).find('form .raw-content');//get(config.id).find('form');
                // TODO : maybe i need to clone this?
                var t = $('form .template');
                t.find('[data-wf]').each(function(i,v) {
                    var source = $(v);
                    var id=source.attr('data-wf');
                    // TODO : copy all css classes and attributes.
                    var target = form.find('[data-wf="'+ id +'"]');
                    moveTargetIntoTemplate(source, target);
                });
                // the debug button we'll always move over. no need to include it in template.
                t.prepend(form.find('.btn-debug'));

                var untemplatedIds = '';
                form.find('[data-wf]').each(function(i,v) {
                    var $el = $(v);
                    var dataWf = $el.attr('data-wf');
                    untemplatedIds = dataWf + ',' + untemplatedIds;
                    $el.addClass('untemplated').attr('placeholder',dataWf + ': is not in template');
                });
                if (untemplatedIds.length>0) {
                    console.log("WARNING: you have stuff in your form that you haven't included in your template --> " + untemplatedIds);
                }
            }

            function copyAttributes(source, destination) {
                var src = source.get(0);
                var dest = destination.get(0);
                for (i = 0; i < src.attributes.length; i++) {
                    var a = src.attributes[i];
                    // skip some key attributes that might adversely affect wicket or framework...copy the rest.
                    if ('id'=== a.name || 'style'=== a.name || 'type'=== a.name || 'data-wf-template'=== a.name) continue;
                    dest.setAttribute(a.name,a.value);
                }
                var sourceType = src.nodeName;
                var destType = dest.nodeName;
                if (!(sourceType===destType)) {
                    console.log('WARNING : for "' +src.getAttribute('data-wf') +'" the template has a type of ' + sourceType + ' but the form is using ' + destType + '.   ' +
                        'It is recommended that you use the same types to avoid styling inconsistencies.');
                }
            }

            var layout = function() {
                layoutWithTemplate();
                return;
            };

            return {
                updateLayout : updateLayout,
                layout : layout,
                initializePlugin : initializePlugin
            }

        };


    return {
            initWidget : initWidget,
            init : init,
            pushHistory : pushHistory
        };

}();

