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

        var init = function(options) {
            var f = form(options);
            f.layout();
            f.addAttributes();
            f.initializePlugins();
            return form;
        };

        function form(opts) {
            // TODO : $.extend(defaultOptions);
            var options = opts;

            var addAttributes = function() {
                // note that this DOESN'T do properties like checked, selected, or disabled
                for (var id in options.idToData) {
                    var config = options.idToData[id];
                    addAttributesFor(id, config.attributes);
                }
            };

            var initializePlugins = function() {
                for (var id in options.idToData) {
                    var config = options.idToData[id];
                    var $widget = $('#'+id);
                    var plugin = config.pluginName;
                    if (!plugin) continue;
                    console.log('about to initialize widget ' + name + ' with plugin ' + plugin + ' and options ' + config.options);
                    $widget[plugin](config.options);
                }
            };

            var layout = function() {
                var $form = $('#'+options.formId).find('form');
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

            function addAttributesFor(id, attributes) {
                if (!id || !attributes) {
                    return;
                }
                // TODO : need to handle appending attributes for ones that exist.
                for (var attr in attributes) {
                    var value = attributes[attr];
                    document.getElementById(id).setAttribute(attr, value);
                }

            }

            return {
                layout: layout,
                addAttributes : addAttributes,
                initializePlugins : initializePlugins
            }

        };



    return {
            init: init
        };



}();

