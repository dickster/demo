// This is needed for screens that do not trigger the E_LOADED event (ex: quote).
$( document ).ready(function() {initBilling();});

// If we are on the desktop page and we load a new policy then we have to reset the billing controls.
if (typeof E_LOADED !== 'undefined') {
    $(document).on(E_LOADED, initBilling);
}

/**
 * Initializes the billing summary section contents.
 * @returns {undefined}
 */
function initBilling() {
    $('.persPolicyBilling-easy-section').each(function() {
        var $billCombo = getBillCombo(this);
        var $payTextbox = getPaymentAmountTextbox(this);
        var $billMethod = $('.persPolicyBillingMethodCd-easy-combo-box-value', this);
        updateSummary(this, $billCombo, $payTextbox);
        showHideControls(this, $billMethod);
    });
}

/**
 * Fires when the Payment Amount textbox gets updated.  Bound from AbstractBillingPanel.
 * @param {HTMLElement} textbox The Payment Amount textbox.
 * @returns {undefined}
 */
function onPaymentAmountChanged(textbox) {
    var $sectionDiv = $(textbox).closest('.persPolicyBilling-easy-section');
    updateSummary($sectionDiv, getBillCombo($sectionDiv), $(textbox));
}

/**
 * Shows/hides other billing controls depending on the new billing method selected.
 * Bound from AbstractBillingPanel.
 * @param {jQuery} $valueTextbox The persPolicyBillingMethodCd text element containing the csio code.
 * @param {jQuery} $textTextbox The persPolicyBillingMethodCd text element containing the display value.
 * @returns {undefined} Nothing.
 */
function onBillingMethodChanged($textTextbox, $valueTextbox) {
    var $sectionDiv = $valueTextbox.closest('.persPolicyBilling-easy-section');
    showHideControls($sectionDiv, $valueTextbox);
    updateSummary($sectionDiv, $textTextbox, getPaymentAmountTextbox($sectionDiv));        
}

/**
 * If the billing method is Agency/Broker Billed then we hide the other billing controls.
 * @param {jQuery} $sectionDiv The billing section div.
 * @param {jQuery} $valueTextbox The text box that holds the billing method csio code.
 * @returns {undefined}
 */
function showHideControls($sectionDiv, $valueTextbox) {
    var isAgency = ($valueTextbox.val() === 'csio:A');
    $('.persPolicyPaymentPlanCd-easy-combo-box', $sectionDiv).toggle(!isAgency);
    $('.persPolicyCommercialName-easy-text-field', $sectionDiv).toggle(!isAgency);
    $('.persPolicyCurrentTermAmt-easy-text-field', $sectionDiv).toggle(!isAgency);
    $('.persPolicyDayMonthDue-easy-text-field', $sectionDiv).toggle(!isAgency);
    $('.persPolicyMethodPaymentCd-easy-combo-box', $sectionDiv).toggle(!isAgency);
    $('.persPolicyAccountNumberId-easy-text-field', $sectionDiv).toggle(!isAgency);
    $('.persPolicyCreditCardExpirationDt-easy-calendar', $sectionDiv).toggle(!isAgency);
    $('.persPolicyBankId-easy-text-field', $sectionDiv).toggle(!isAgency);
    $('.persPolicyBranchId-easy-text-field', $sectionDiv).toggle(!isAgency);
    $('.persPolicyBillingDownPayment-easy-section', $sectionDiv).toggle(!isAgency);
}

/**
 * Retrieves the Billing Method combo in a given Billing section.
 * @param {jQuery} $sectionDiv The Billing section DIV element.
 * @returns {jQuery} The Billing Method select control.
 */
function getBillCombo($sectionDiv) {
    return $('.persPolicyBillingMethodCd-easy-combo-box-input', $sectionDiv);
}

/**
 * Retrieves the Payment Amount textbox in a given Billing section.
 * @param {jQuery} $sectionDiv The Billing section DIV element.
 * @returns {jQuery} The Payment Amount textbox control.
 */
function getPaymentAmountTextbox($sectionDiv) {
    return $('.persPolicyCurrentTermAmt-easy-text-field-input', $sectionDiv);
}

/**
 * Updates the Summary section that gets displayed when the section is collapsed.
 * @param {jQuery} $billSection The Billing section div that parents the billing controls.
 * @param {jQuery} $billCombo The Billing Method combo control.
 * @param {jQuery} $amountTextbox The Payment Amount text box.
 * @returns {undefined}
 */
function updateSummary($billSection, $billCombo, $amountTextbox) {
    var billMethod = (($billCombo !== null) && (typeof $billCombo.val() !== 'undefined')) ? $billCombo.val() : "";
    var amount = (($amountTextbox !== null) && (typeof $amountTextbox.val() !== 'undefined')) ? $amountTextbox.val() : "";
    var sep = (((billMethod.length > 0) && (amount.length > 0)) ? " : " : "");
    var summary = billMethod + sep + ((amount.length > 0) ? amount : "");
    $('.easy-section-summary', $billSection).html(summary);    
}