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

            function moveTargetIntoTemplate(source, target) {
                if (target.length>0) {
                    target.insertAfter(source);
                    copyAttributes(source, target);
                    source.attr('data-wf-rendered',true).hide();
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
                    if ('id'=== a.name || 'style'=== a.name || 'type'=== a.name) continue;
                    dest.setAttribute(a.name,a.value);
                }
                var sourceType = src.nodeName;
                var destType = dest.nodeName;
                if (!(sourceType===destType)) {
                    console.log('WARNING : for "' +src.getAttribute('data-wf') +'" the template has a type of ' + sourceType + ' but the form is using ' + destType + '.   ' +
                        'It is recommended that you use the same types to avoid styling inconsistencies.');
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

