
var layoutDef = {};

//this can be a function too!
layoutDef['FORM-A-relayout'] = {
    colsPerRow : 3,
    rows : [
           [['first name','name.first'],['middle name', 'name.middle']],
           [['last name', 'name.last']],
           [['age', 'insured.age'], ['occupation', 'insured.occupation']],
           [['next']]
        ]
}
