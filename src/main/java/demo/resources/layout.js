var easy = easy ? easy : {};
easy.layout= (function() {


    var foo =
    {
        "id":"form21",

        "layout":{
            "nameToId":{
                "2":"component24",
                "1":"component25",
                "first name":"component26",
                "FORM-A":"content23",
                "middle name":"component28",
                "name.first":"component27",
                "next":"component2c",
                "name.last":"component2b",
                "last name":"component2a",
                "name.middle":"component29"
            },
            "nameToCss":{
                "2":"form_group",
                "1":"form_group",
                "first name":"",
                "FORM-A":"form",
                "middle name":"",
                "name.first":"form-control",
                "next":"btn btn-primary",
                "name.last":"form-control",
                "last name":"",
                "name.middle":"form-control"
            }},"widgetOptions":{}};

    var init = function(options) {
        //doLayout($.extend(defaults, options));
        // TODO : add default options later.
        doLayout(options);
    }

    function doLayout(opts) {
        var layout = opts.layout;
        for (var name in layout.nameToCss) {
            console.log('css for ' + name + ' is ' + css);
            var css = layout.nameToCss[name];
            $('#'+layout.nameToId[name]).addClass(css);

            console.log(layout.nameToId[name] + ' --> ' + $('#'+layout.nameToId[name]));
        }
    }

    return {
        init: init
    };

})();







