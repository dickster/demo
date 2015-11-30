
var layoutDef = {};

//this can be a .js function too!
layoutDef['FORM-A-relayout'] = {

    rows : [
               [{col:['first name','name.first'],css:'col-md-6'},
                   {col:['middle name', 'name.middle'], css:'col-md-3'}],
               [{col:['last name', 'name.last'],css:'col-md-5'}],
               [{col:['age', 'insured.age'],css:'col-md-4'},
                   {col: ['occupation', 'insured.occupation'], css:'col-md-2'}],
               [{col:['next'], css:'col-md-3'}]
            ]
};

layoutDef['formA-3 (required field)'] = {

    rows : [
        [{col:['first name','name.first'],css:'col-md-3'},
            {col:['middle name', 'name.middle'], css:'col-md-3'},
            {col:['last name', 'name.last'], css:'col-md-3'}],
        [{col:['age', 'insured.age'],css:'col-md-1'}],
        [{col: ['occupation', 'insured.occupation'], css:'col-md-2'}],
        [{col:['next'], css:'col-md-4'}]
    ]

}
