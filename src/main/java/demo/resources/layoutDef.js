
var layoutDef = {};

//"idToMarkupId":
//{
//    "INFO_FORM":"content21",

//    "name.salutation":"el4",
//    "name.first":"el6",XXX
//    "first name":"el5",
//    "middle name":"el7",
//    "name.middle":"el8",
//    "last name":"el9",
//    "name.last":"ela",
//    "insured.smokes":"eld",
//    "address":"ele"
//    "address 2":"el10",
//    "insured.address":"elf",
//    "insured.address2":"el11",
//    "age":"elb",
//    "insured.age":"elc",
//    "vehicle.year":"el19",
//    "vehicle year":"el18",
//    "vehicle type":"el16",
//    "vehicle.type":"el17",
//    "insured.contact.email":"el13",
//    "contact info":"el12",
//    "how many accidents have you had in the last 5 years?":"el1a",
//    "insured.accidents":"el1b",
//    "what type of pop do you drink?":"el1c",
//    "insured.drinks":"el1d",
//    "insured.driversLicense":"el15",
//    "drivers license":"el14",

//   "next":"el1e",
//    "REFRESH":"refresh20",
//}

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
                   [{col:['spacer', 'name.salutation'],css:'col-md-1'},
                       {col:['first name','name.first'],css:'col-md-3'},
                       {col:['middle name', 'name.middle'], css:'col-md-3'},
                       {col:['last name', 'name.last'],css:'col-md-5'}],
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
