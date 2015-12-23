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
        }

        function widget(conf) {
            var config= conf;

            function get(id) {
                return $("[data-wf='"+id+"']");
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
                if (config.type=="CHECKBOX") {
                    layoutCheckBox();
                }
                if ( config.type=="ADDRESS") {
                    config.id = '#'+config.markupId;
                    easy.address.create(config);
                    return;
                }
//                if (config.type=="SECTION") {
//                    ez.sectionPanel.init(config);
//                    return;
//                }

                if (!config.pluginName) return;

                var $widget = $('#'+config.markupId);
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

            var layoutWithTemplate = function() {
                var form = $(document).find('form .raw-content');//get(config.id).find('form');
                // TODO : maybe i need to clone this?
                var t = $('#template');
                t.show();
                t.find('[data-wf]').each(function(i,v) {
                    var templateElement = $(v);
                    var id=templateElement.attr('data-wf');
                    // TODO : copy all css classes and attributes.
                    var replaceWithThis = form.find('[data-wf="'+ id +'"]');
                    if (replaceWithThis.length>0) {
                        replaceWithThis.insertAfter(templateElement);
                        templateElement.attr('data-wf-rendered',true).hide();
                    }
                    else {
                        console.log("WARNING : you have " + id + " in your template but can't find it in your form. ignoring this field.");
                        templateElement.addClass('undefined');
                    }
                });
                t.prepend(form.find('[data-wf="refresh"]'));

                // TODO : copy attributes from tempateSource-->target
                var untemplatedIds = '';
                form.find('[data-wf]').each(function(i,v) {
                    untemplatedIds = $(v).attr('data-wf') + ',' + untemplatedIds;
                    $(v).addClass('untemplated');
                });
                if (untemplatedIds.length>0) {
                    console.log("WARNING: you have stuff in your form that you haven't included in your template --> " + untemplatedIds)
                }
                //form.hide();  // form should be empty at this point.
            }

            function copyAttributes(source, destination) {
                for (i = 0; i < source.attributes.length; i++) {
                    var a = source.attributes[i];
//                    skip "style" and "id".
// TODO : make sure tag type is the same <input?>   <div etc...>
                    destination.attr(a.name, a.value);
                }
            }

            var layoutDefault = function (form) {
                form.css('width:300px');
                //form.find('[data-wf]').each(function(i,e) {
                //    $(e).wrap('<div class="form-group"></div>')
                //        .wrap('<div class="row"></div>')
                //        .wrap('<div class="col-md-4"></div>');
                //});
            };

            var layout = function() {
                var $form = get(config.id).find('form');
                layoutWithTemplate($form);
                return;
                var layout = layoutDef[config.id];
                if (!layout) {
                    layoutDefault($form);
                    return;
                }

                for (var section = 0; section<layout.sections.length; section++) {
                    var $sec = $('<fieldset></fieldset>');
                    $form.append($sec);
                    layoutSection(layout.sections[section], $sec);
                }
//                console.log("the form is ---> " + $form[0].outerHTML);
            };

            function layoutSection(section, parent) {
                for (var row = 0; row < section.rows.length; row++) {
                    var $row = $('<div class="row"></div>');
                    $row.wrap('<div class="form-group"></div>');
                    parent.append($row.parent());
                    layoutRow(section.rows[row], $row);
                }
            }

            function layoutRow(row, parent) {
                for (var col = 0; col < row.length; col++) {
                    var colClass = row[col].css;
                    var $formGroup = $('<div></div>').addClass(colClass);
                    parent.append($formGroup);
                    layoutColGroup($formGroup, row[col]);
                }
            }

            function layoutColGroup(parent, colGroup) {
                for (var c = 0; c<colGroup.col.length; c++) {
                    var colName = colGroup.col[c];
                    var $el = get(colName);
                    parent.append($el);
                }
            }

            function layoutCheckBox() {   // do this in behaviour??? or else ajax will muck it up with duplicated html.
                var $cb = get(config.id);
                // if not already wrapped then... { } else leave it...
                // should just write a panel checkbox class styled like this.
//                $cb.wrap("<div class='checkbox'></div>");
//                $cb.wrap("<label>" + config.label + "</label>");
            }

            return {
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

