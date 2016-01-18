workflow.layout = function(config,$widget) {

    var init = function($widget, config) {
        if (!config.templateId) return;
        $template = $(document.getElementById(config.templateId));
        $model = $template.closest('template-model');
        applyTemplate($template, $model);
    }

    var update = function($components) {
        updateCss($components);
    }

     function updateCss($components) {
        $components.each(function(i,el) {
            var $el = $(el);
            var $templ = $el.prev('[data-template]');
            $el.addClass($templ.attr('class'));
            validateType($templ, $el);
        });
    }

    function applyTemplate($template, $model) {
        $template.find('[data-template]').each(function(i,target) {
            var id = t.getAttribute('data-template');
            var $source = $model.find('[data-wf="'+ id +'"]');
            var $target = $(target);

            // now copy the original into the template.
            if ($source.length>0) {  // really, there should only be one?  assert($dw.length=1)
                 $source.insertAfter($target);
                 $source.addClass($target.attr('class'));
                 validateType($source, $target);
            }
            else {
                console.log("WARNING : you have 'data-template=" + id + "' in your template but a matching 'data-wf=??' item cant be found in the form. (ignoring).");
                $(target).addClass('unresolved-template');
            }
        });
        validate($model);
    }

    function validate($model) {
        // at this point, *all* of the elements should be moved from $model DIV into the template DIV.
        // .: we can assume that any remaining ones do NOT have a matching template element   (<span data-template=xx>)
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
            console.log("Warning: your template uses different tag types for " + $b.attr('data-template') +
                ".\n " + $a.prop('tagName') + " != " + $b.prop('tagName'));
            // this may cause the template to look different when it's populated for realsy.  it's best to use the same types.
        }
    }

    return {
        init : init,
        update : update
    }

}

