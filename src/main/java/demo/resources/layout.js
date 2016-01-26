wf.layout = function() {

    var init = function($widget, options) {
        if (!options.templateId) return;
        var $template = $(document.getElementById(options.templateId));
        var $model = $template.prev('.template-model');
        applyTemplate($template, $model);
    }

    var update = function($components) {
        updateCss($components);
    }

    function updateCss($components) {
        $components.each(function(i,el) {
            var $el = $(el);
            var $templ = $el.prev('[data-tmpl]');
            $el.addClass($templ.attr('class'));
            validateType($templ, $el);
        });
    }

    function applyTemplate($template, $model) {
        // REMINDER : all data-wf values should be unique!
        $model.find('> [data-wf]').each(function(i,source) {
            var id = source.getAttribute('data-wf');
            var $source = $(source);
            var $target = $template.find('[data-tmpl="'+ id +'"]');

            // now copy the original into the template.
            if ($source.length>0) {  // really, there should only be one?  assert($dw.length=1)
                 $source.insertAfter($target);
                 $target.hide();
                 $source.addClass($target.attr('class'));
                 validateType($source, $target);
            }
            else {
                console.log("WARNING : you have 'data-tmpl=" + id + "' in your template but a matching 'data-wf=??' item cant be found in the form. (ignoring).");
                $(target).addClass('unresolved-template');
            }
        });
        validate($model, $template);
    }

    // TODO : only do this in debug mode
    function validate($model, $template) {
        // at this point, *all* of the elements should be moved from $model DIV into the template DIV.
        // .: we can assume that any remaining ones do NOT have a matching template element   (<span data-tmpl=xx>)
        // we will log this and add a class to each to aid in debugging.
        var hasErrors = false;
        $model.find('[data-wf]').each(function(index, data) {
                hasErrors = true;
                var msg = "WARNING: '" + data.getAttribute('data-wf') + "' is in the form but not in template";
                $(data)
                    .attr('data-toggle','tooltip')
                    .attr('title', msg)
                    .addClass('not-in-template');
            }
        );
        if (hasErrors) {
            $model.addClass('hasErrors');
        }
        // also, if there are any visible template elements left, that means they haven't been matched up
        // with the model. (if they were, they would have been hidden when resolved).
        // .: we will mark them as invalid to help debugging.
        var hasErrors = false;
        $template.find('[data-tmpl]:visible').each(function(index, tmpl) {
                var msg = "WARNING: '" + tmpl.getAttribute('data-tmpl') + "' exists in the template but is not in the form.";
                $(tmpl)
                    .attr('data-toggle','tooltip')
                    .attr('data-placement','right')
                    .attr('title', msg)
                    .addClass('unreferenced');
            }
        );
        $('[data-toggle="tooltip"]').tooltip();
    }

    function validateType($a, $b) {
        // TODO : only do this in debug mode??
        if ($a.prop('tagName')!=$b.prop('tagName')) {
            console.log("Warning: your template uses different tag types for " + $b.attr('data-tmpl') +
                ".\n " + $a.prop('tagName') + " != " + $b.prop('tagName'));
            // this may cause the template to look different when it's populated for realsy.  it's best to use the same types.
        }
    }

    return {
        init : init,
        update : update
    }

}();

