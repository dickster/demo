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
                 $source.addClass($target.attr('class'));
                 validateType($source, $target);
            }
            else {
                console.log("WARNING : you have 'data-tmpl=" + id + "' in your template but a matching 'data-wf=??' item cant be found in the form. (ignoring).");
                $(target).addClass('unresolved-template');
            }
        });
        validate($model);
    }

    function validate($model) {
        // at this point, *all* of the elements should be moved from $model DIV into the template DIV.
        // .: we can assume that any remaining ones do NOT have a matching template element   (<span data-tmpl=xx>)
        // we will log this and add a class to each to aid in debugging.
        $model.find('[data-wf]').each(function(index, data) {
                console.log("WARNING: " + data.getAttribute('data-wf') + " is not in template");
                $(data).addClass('not-in-template');
            }
        );
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

