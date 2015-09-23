/**
 * Because we never know which inputs will be shown, often the shortcut keys for a given section are 
 * added to the section div at compile time.  This function moves the shortcut key down onto the first
 * visible input in the section.
 * @returns {undefined}
 */
function addShortcutKeys() {
    $("div[accesskey]").each(function(index) {
        var key = this.attributes['accesskey'].value;
        var $input = $("input:visible:first", this);
        if ($input.length > 0) {
            $input.attr('accesskey', key);
        }
    });
}