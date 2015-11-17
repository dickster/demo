
var layoutDef = {};

//this can be a .js function too!
layoutDef['FORM-A-relayout'] = {

    rows : [
           [['first name','name.first'],['middle name', 'name.middle']],
           [['last name', 'name.last']],
           [['age', 'insured.age'], ['occupation', 'insured.occupation']],
           [['next']]
        ]
};

layoutDef['formA-3'] = {

    rows : [
           [ ['first name','name.first'],['middle name', 'name.middle'],['last name', 'name.last']],
           [['age', 'insured.age']],
           [['occupation', 'insured.occupation']],
           [['next']]
        ]
}
