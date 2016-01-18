// TODO : deprecated.  not used anymore....delete this!!!

var layoutDef = {};

//this can be a .js function too!
layoutDef['FORM-A-relayout'] = {

    sections : [
        {
            title : "name",
            rows : [
                   [{col:['first name','name.first'],css:'col-md-6'},
                       {col:['middle name', 'name.middle'], css:'col-md-3'}],
                   [{col:['last name', 'name.last'],css:'col-md-5'}],
                   [{col:['age', 'insured.age'],css:'col-md-2'},
                    {col:['insured.smokes'],css:'col-md-4'}],
                   [{col: ['address', 'insured.address'], css:'col-md-6'}],
                   [{col: ['address 2', 'insured.address2'], css:'col-md-6'}],
                   [{col:['next'], css:'col-md-3'}]
                ]
    }]
};


layoutDef['INFO_FORM'] = {

    sections : [
        {
            title : "client information",
            rows : [
                   [{col:['spacer', 'name.salutation'],css:'col-md-2'},
                       {col:['first name','name.first'],css:'col-md-3'},
                       {col:['middle name', 'name.middle'], css:'col-md-3'},
                       {col:['last name', 'name.last'],css:'col-md-4'}],
                    [{col:['address', 'insured.address'], css:'col-md-6'},
                     {col:['address 2', 'insured.address2'], css:'col-md-6'}],
                    [{col: ['contact info', 'insured.contact.email'], css:'col-md-4'}],
                    [{col:['age', 'insured.age'],css:'col-md-1'}],
                    [{col:['insured.smokes'],css:'col-md-4'}],
//                    // add vertical space here...?  put it in another section....?
                     [{col:['vehicle year', 'vehicle.year'],css:'col-md-2'},
                        {col:['vehicle type', 'vehicle.type'],css:'col-md-4'}],
                    [{col:['# of accidents', 'insured.accidents'],css:'col-md-1'}],
                    [{col:['what type of pop do you drink?', 'insured.drinks'],css:'col-md-3'},
                     {col:['drivers license', 'insured.driversLicense'],css:'col-md-4'}],

                   [{col:['next'], css:'col-md-3'}]
                ]
    }]
};


layoutDef['formA-3 (required field)'] = {

    sections : [
        {title : 'oops',
        rows : [
            [{col:['first name','name.first'],css:'col-md-3'},
                {col:['middle name', 'name.middle'], css:'col-md-3'},
                {col:['last name', 'name.last'], css:'col-md-3'}],
            [{col:['age', 'insured.age'],css:'col-md-1'}],
            [{col: ['occupation', 'insured.occupation'], css:'col-md-2'}],
            [{col:['next'], css:'col-md-4'}]
        ]

    }]

};

layoutDef['REFER_FORM'] = {
    sections : [
        {title : 'oops',
        rows : [
            [{col:['l1'],css:'col-md-12'}],
            [{col:['l2'],css:'col-md-12'}],
            [{col:['l3'],css:'col-md-12'}],
            [{col:['l4'],css:'col-md-12'}],
            [{col:['l5'],css:'col-md-12'}],
            [{col:['ok'],css:'col-md-3'}]
        ]

    }]

};
