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
            w.addAttributes();
            w.initializePlugin();
        }

        function widget(conf) {
            var config= conf;
            var addAttributes = function() {
                // note that this DOESN'T do properties like checked, selected, or disabled
                var id = config.markupId;
                var attributes = config.attributes;

                if (!attributes) {
                    return;
                }
                // TODO : need to handle appending attributes for ones that exist.
                for (var attr in attributes) {
                    var value = attributes[attr];
                    document.getElementById(id).setAttribute(attr, value);
                }
            };

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
                if (config.type=="CHECKBOX") {
                    layoutCheckBox();
                }
                if ( config.type=="ADDRESS") {
                    config.id = '#'+config.markupId;
                    easy.address.create(config);
                    return;
                }
                if (config.type=="SECTION") {
                    ez.sectionPanel.init(config);
                    return;
                }

                if (!config.pluginName) return;

                var $widget = $('#'+config.markupId);
                console.log('about to initialize widget ' + config.name + ' with plugin ' + config.pluginName + ' and options ' + config);
                try {
                    $widget[config.pluginName](config.options);
                }
                catch (err) {
                    console.log("error launching plugin " + config.pluginName + err);
                }
            };

            var layout = function() {
                var $form = $('#'+config.markupId).find('form');
                var layout = layoutDef[config.name];
                if (!layout) return;

                for (var i = 0; i < layout.rows.length; i++) {
                    var $row = $('<div class="row"></div>');
                    $form.append($row);
                    for (var j = 0; j < layout.rows[i].length; j++) {
//                        var rowCss = rowsCss[layout.rows[i].length];
                        var colClass = layout.rows[i][j].css;
                        var $formGroup = $('<div></div>').addClass(colClass);
                        $row.append($formGroup);
                        for (var k = 0; k<layout.rows[i][j].col.length; k++) {
                            var colName = layout.rows[i][j].col[k];
                            var $el = $( '#' + config.idToMarkupId[colName]);
                            $formGroup.append($el);
                        }
                    }
                }
                console.log("the form is ---> " + $form[0].outerHTML);
            };

            function layoutCheckBox() {
                $('#'+config.markupId).wrap("<div class='checkbox'></div>");
                $('#'+config.markupId).wrap("<label>" + config.label + "</label>");
            }

            return {
                addAttributes : addAttributes,
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

