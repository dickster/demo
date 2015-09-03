
// NOTE : where should i package this???
var easy = easy ? easy : {};
easy.selectPicker = (function() {

    var init = function(options) {
        $('.selectpicker').selectpicker();
    };

    return {
        init: init
    };
})();




