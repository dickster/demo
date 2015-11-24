var workflow = function() {

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

        var initWidget = function(options) {
           var w = widget(options);
            w.addAttributes();
            w.initializePlugin();
        }

        function widget(opts)   {
            var options = opts;

            var addAttributes = function() {
                // note that this DOESN'T do properties like checked, selected, or disabled
                var id = options.markupId;
                var attributes = options.config.attributes;

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
                if (options.config.type=="FORM") {
                    layout(); // TODO : turn this into a plugin.
                    return;
                }
                if ( options.config.type=="ADDRESS") {
                    options.config.id = '#'+options.markupId;
                    easy.address.create(options.config);
                    return;
                }
                var config = options.config;
                var $widget = $('#'+options.markupId);
                var plugin = config.pluginName;
                if (!plugin) return;
                console.log('about to initialize widget ' + config.name + ' with plugin ' + plugin + ' and options ' + config.options);
                $widget[plugin](config.options);
            };

             var layout = function() {
                var $form = $('#'+options.markupId).find('form');
                var layout = layoutDef[options.formName];
                if (!layout) return;

                for (var i = 0; i < layout.rows.length; i++) {
                    var $row = $('<div class="row"></div>');
                    $form.append($row);
                    for (var j = 0; j < layout.rows[i].length; j++) {
                        var rowCss = rowsCss[layout.rows[i].length];
                        var $formGroup = $('<div class="form-group"></div>').addClass(rowCss);
                        $row.append($formGroup);
                        for (var k = 0; k<layout.rows[i][j].length; k++) {
                            var colName = layout.rows[i][j][k];
                            var $el = $('#'+options.nameToId[layout.rows[i][j][k]]);
                            $formGroup.append($el);
                        }
                    }
                }
                console.log("the form is ---> " + $form[0].outerHTML);
            };

            return {
                addAttributes : addAttributes,
                layout : layout,
                initializePlugin : initializePlugin
            }



        };



    return {
            initWidget : initWidget
        };



}();

