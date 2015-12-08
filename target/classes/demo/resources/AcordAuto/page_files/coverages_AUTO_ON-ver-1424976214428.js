// This is needed for screens that do not trigger the E_LOADED event (ex: quote).
$( document ).ready(function() {initAUTOCoverages();});

// If we are on the desktop page and we load a new policy then we have to reset the hab controls.
if (typeof E_LOADED !== 'undefined') {
    $(document).on(E_LOADED, initAUTOCoverages);
}

function initAUTOCoverages() {
    // CoveragePanelBuilder.addLimitOrDeductibleControl() will set a default value for COL & CMP,
    // but if the policy has SP or AP then we don't want to use those default values, so we fire
    // the onchange to default other coverages appropriately.  See JIRA NEX-1386.
    $('div.coverage-row div.coverage-col-limit select[name$="_Deductible_csio:SP"]').each(
        function() {
            this.onchange();
        }
    );
    $('div.coverage-row div.coverage-col-limit select[name$="_Deductible_csio:AP"]').each(
        function() {
            this.onchange();
        }
    );
}

function onCoverageValueChanged(select, coverageCd, riskId) {
    
    var text = select.options[select.selectedIndex].text;
    var value = select.value;
    var form = $(select).closest('form');
    
    switch (coverageCd) {
        
        // Set the limits of other coverages to match the new TPBI limit.
        case 'csio:TPBI' : 
            
            // Set the text of the labels that the user sees.
            $('#' + riskId + '_Limit_csio\\:TPPD_Label', form).text(text);
            $('#' + riskId + '_Limit_csio\\:44_Label', form).text(text);
            $('#' + riskId + '_Include_csio\\:UA_Label', form).text((text === '') ? exclude : include);
            
            // Set the value of the hidden inputs that get submitted to the server.
            $('#' + riskId + '_Limit_csio\\:TPPD', form).val(value);
            $('#' + riskId + '_Limit_csio\\:44', form).val(value);
            $('#' + riskId + '_Include_csio\\:UA', form).val((value === '') ? exclude : include);
            
            break;
            
        // If the user chose a non-empty deductible for All Perils then blank out Collision, Comprehensive and Specified Perils.
        case 'csio:AP' :
            if (value !== '') {
                $('#' + riskId + '_Deductible_csio\\:COL', form).val(null);
                $('#' + riskId + '_Deductible_csio\\:CMP', form).val(null);
                $('#' + riskId + '_Deductible_csio\\:SP', form).val(null);
            }
            break;
        
        // If the user chose a non-empty deductible for Collision then blank out All Perils.
        case 'csio:COL' :
            if (value !== '') {
                $('#' + riskId + '_Deductible_csio\\:AP', form).val(null);
            }
            break;
        
        // If the user chose a non-empty deductible for Comprehensive then blank out All Perils and Specified Perils.
        case 'csio:CMP' :
            if (value !== '') {
                $('#' + riskId + '_Deductible_csio\\:AP', form).val(null);
                $('#' + riskId + '_Deductible_csio\\:SP', form).val(null);
            }
            break;
        
        // If the user chose a non-empty deductible for Specified Perils then blank out All Perils and Comprehensive.
        case 'csio:SP' :
            if (value !== '') {
                $('#' + riskId + '_Deductible_csio\\:AP', form).val(null);
                $('#' + riskId + '_Deductible_csio\\:CMP', form).val(null);
            }
            break;        
            
    }
    
}