
$(function() {

    $.widget( "brovada.sectionPanel", {

        options : { },

        _create: function() {
            var $panel = $('#'+this.options.markupId);

            var dependentFields = [];
            $.each(this.options.titleInputs,function(index,input) {
                var $inputField = $panel.find('[data-wf="'+input+'"]');
                dependentFields.push($inputField);
                if ($inputField.length==0) {
                    console.log('ERROR: cant find input field named ' + input + ".  your section panel tab updating may not work properly.");
                }
                // this will be refreshed via ajax. use delegate etc...
                $inputField.on("inputchange change", function(e) {
                    var $tabTitle = $panel.find('li.active a span');
                    var text = [];
                    $.each(dependentFields, function(i,f) {
                        text.push(f.val());
                    });
                    $tabTitle.text(text.join(' '));
                });
            });
        }

    });

});






