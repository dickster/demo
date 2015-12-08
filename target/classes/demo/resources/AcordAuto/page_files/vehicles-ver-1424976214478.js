// The persVehModelYear combo box uses this variable as its item list.
var persVehModelYear = {};
for (var i = new Date().getFullYear(); i > 1979 ; i--) {
    persVehModelYear[i.toString()] = i.toString();
}

/**
 * If the vin control or the year/make/model are populated then call the vehicle restful service.
 * If the call returns at least one matching vehicle then we override the various vehicle info 
 * control values with the values from the first matching vehicle.
 * 
 * @param {string} restServicePath The full http path to the restful service, minus any parameters. 
 * (Ex: http://localhost:8080/storage/rest/vehicle/vehicles).
 * @param {string} vinId The id of the VIN text box control.
 * @param {string} codeId The id of the code hidden field.
 * @param {string} yearId The id of the year text box control. 
 * @param {string} makeId The id of the make EasyJSComboBox component (the id of its wrapping div).
 * @param {string} modelId The id of the model EasyJSComboBox component (the id of its wrapping div).
 * @param {string} bodyTypeId The id of the Body Type hidden field.
 * @param {string} fuelTypeId The id of the Fuel Type hidden field.
 * @param {string} cylindersId The id of the Cylinders hidden field.
 * @returns {undefined}
 */
function loadVehicleValues(restServicePath, vinId, codeId, yearId, makeId, modelId, bodyTypeId, fuelTypeId, cylindersId) {

    easyHideAjaxIndicator('progress');

    // Get a reference to the controls for this vehicle.
    var vinTx = $('#' + vinId);
    var yearTF = $('#' + yearId + ' input[type="text"]');
    var makeTF = $('#' + makeId + ' input[type="text"]');
    var modelTF = $('#' + modelId + ' input[type="text"]');
    var codeHF = $('#' + codeId);
    var fuelHF = $('#' + fuelTypeId);
    var bodyHF = $('#' + bodyTypeId);
    var cylHF = $('#' + cylindersId);

    var vin = vinTx.val();
    var year = yearTF.val();
    var make = makeTF.val();
    var model = modelTF.val();

    var hasVin = vin.trim().length > 0;
    var hasYearMakeModel = (year.trim().length > 0) && (make.trim().length > 0) && (model.trim().length > 0);

    // If we have a non-empty VIN or nothing at all then blank out year, make, model.
    if (hasVin || !hasYearMakeModel) {
        setComboValue(yearId, "");
        setComboValue(makeId, "");
        setComboValue(modelId, "");        
    }

    // Blank out the hidden fields.  They will hopefully get new values after the ajax call.
    codeHF.val('');
    fuelHF.val('');
    bodyHF.val('');
    cylHF.val('');    
    
    // Disable/enable the year/make/model based on the VIN being populated.
    setState(yearTF, !hasVin);
    setState(makeTF, !hasVin);
    setState(modelTF, !hasVin);
    
    // If the Storage project is loaded and there was a value in the vin or year/make/model controls...
    if ((typeof Storage.hosts[STORAGE_REMOTE] === 'string') && (hasVin || hasYearMakeModel)) {

        var content = (hasVin) 
            ? '[{ "vin":"' + vin + '" }]' 
            : '[{ "year":"' + year + '", "make":"' + make + '", "model":"' + model + '" }]';
        
        // Make the call to the restful service.
        $.ajax({type: 'POST', data: content, dataType: 'json', url: restServicePath, processData: false, contentType: "application/json"})

        .done(function( data, textStatus, jqXHR ) {
    
            if ((data !== null) && (data.length > 0) && (data[0] !== null)) {
                
                // Get the values returned from the service.
                var ibcYear = data[0]["year"];
                var ibcMake = data[0]["make"];
                var ibcModel = data[0]["model"];
                var ibcCode = data[0]["code"];
                var ibcFuelType = data[0]["fuelType"];
                var ibcBodyType = data[0]["bodyType"];
                var ibcCylinders = data[0]["engineCylinders"];

                // Translate the IBC fuel type code to a CSIO code.
                switch (ibcFuelType) {
                    case 'G': ibcFuelType = '3'; break;
                    case 'D': ibcFuelType = '1'; break;
                    case 'V': ibcFuelType = '2'; break;
                    case 'N': ibcFuelType = '4'; break;
                    case 'X': ibcFuelType = 'Z'; break;
                    default: ibcFuelType = '';
                }
                
                if (ibcYear > 0) {
                    setComboValue(yearId, ibcYear);
                }
                if ((typeof ibcMake === 'string') && (ibcMake.length > 0)) {
                    setComboValue(makeId, ibcMake);
                }
                if ((typeof ibcModel === 'string') && (ibcModel.length > 0)) {
                    setComboValue(modelId, ibcModel);
                }
                if (ibcCode > 0) {
                    codeHF.val(ibcCode);
                }
                if ((typeof ibcFuelType === 'string') && (ibcFuelType.length > 0)) {
                    fuelHF.val('csio:' + ibcFuelType);
                }
                if ((typeof ibcBodyType === 'string') && (ibcBodyType.length > 0)) {
                    bodyHF.val('csio:' + ibcBodyType);
                }
                if (ibcCylinders > 0) {
                    cylHF.val(ibcCylinders);
                }
                
            }
            
        })            

        .fail(function( jqXHR, textStatus, errorThrown ) {
            $(Storage.errDlg).errorDlg('show', jqXHR.responseJSON);
        });

    }
    
};


/**
 * Sets the value and display value of an EasyJSComboBox component.
 * @param {type} comboId The id of the EasyJSComboBox component (the html id that the wrapping div will get).
 * @param {type} val The value to use for the EasyJSComboBox model.
 * @returns {undefined}
 */
function setComboValue(comboId, val) {    
    // An EasyJSComboBox has two child components.  One is a hidden
    // control that contains the model value.  The other is a text box
    // that contains the display value shown to the user.  We
    // need to set both of these.
    var valTextBox = $('#' + comboId + ' input[type="hidden"]');
    var textBox = $('#' + comboId + ' input[type="text"]');
    valTextBox.val(val);
    textBox.val(val);    
}
 
/**
 * Called from the onchange of the vehicle year and make controls,  
 * this function changes the content of the make and model drop downs.  
 * For example, when the user types 2001 in the year, we change the make and model
 * drop downs to only have values applicable for 2001.  When the user
 * selects a make, we change the models drop down to only have models
 * applicable to the selected make.
 * @param {String} comboId The id of the combo being loaded.
 * @param {String} valueId The id of the combo hidden value.
 * @param {String} divId The id of the DIV that gets shown for the combo.
 * @param {String} restServicePath The path to the restful service that 
 * will return the dynamic values.
 * @param {String} theme The class name to give to the drop down div.
 * @returns {undefined}
 */
function loadVehicleCombo(comboId, valueId, divId, restServicePath, theme) {
    
    easyHideAjaxIndicator('progress');
    
    // If the Storage project is loaded...
    if (typeof Storage.hosts[STORAGE_REMOTE] === 'string') {
        
        // Make the call to the restful service.
        $.ajax({dataType: "json", url: restServicePath, global: false})

        .done(function( data, textStatus, jqXHR ) {

            // If it doesn't exist yet, create a div with the results of the rest call.
            easyComboBoxInitialize(true, $('#' + comboId), valueId, divId, data, theme, false, false);

            // If the selected value of the combo isn't in the results then reset the selected combo value.
            var combo = $('#' + comboId);
            if ((combo !== null) && (combo.val() !== '') && ($.inArray(combo.val(), data) === -1)) {
                combo.val('');
            }

        })            

        .fail(function( jqXHR, textStatus, errorThrown ) { 
            $(Storage.errDlg).errorDlg('show', jqXHR.responseJSON);
        });

    }
};

function setState(control, isEnabled) {
    control.prop('disabled', !isEnabled);
    control.toggleClass('disabled', !isEnabled);
};
