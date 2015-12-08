var autoSurcharge = {
    'csio\:SURAV' : 'Antique Vehicle',
    'csio\:SURBU' : 'Business Use',
    'csio\:SURCA' : 'Prior Policy Cancellation',
    'csio\:SURCC' : 'Claims for Occasional Driver (class 06/05)',
    'csio\:SURCD' : 'Convictions for Occasional Driver (class 06/05)',
    'csio\:SURCE' : 'Commercial Exposure',
    'csio\:SURCL' : 'Claims',
    'csio\:SURCM' : 'No. of Comprehensive Claims',
    'csio\:SURCN' : 'Convictions',
    'csio\:SURCO' : 'Conviction/Claim for an Occasional Driver (use preferred codes: SURCC and SURCD)',
    'csio\:SURCP' : 'Car Pooling',
    'csio\:SURER' : 'Explosive/Radioactive Material',
    'csio\:SURFA' : 'Facility ',
    'csio\:SURFC' : 'Filing Certificate',
    'csio\:SURHK' : 'High Kilometres Driven',
    'csio\:SURHP' : 'High Performance',
    'csio\:SURIH' : 'Inconsistent Insurance History',
    'csio\:SURLS' : 'License Suspension',
    'csio\:SURLV' : 'Leased Vehicle',
    'csio\:SURMC' : 'Miscellaneous',
    'csio\:SURML' : 'Mono-line Policy',
    'csio\:SURMO' : 'Multiple Operators',
    'csio\:SURNP' : 'Non Payment',
    'csio\:SUROE' : 'Oversize Engine',
    'csio\:SUROF' : 'Facility (Occasional Driver)',
    'csio\:SUROP' : 'Out of Province',
    'csio\:SURPN' : 'No Previous Insurance',
    'csio\:SURUS' : 'U.S. & Non-Canadian Exposure'
};

// The list of static surcharges (only used when coverages section is in readonly mode).
var autoSurchargeStatic = {};

// This is only used when the coverage list is displayed readonly.
var autoSurchargeReadonly = $.extend({}, autoSurcharge, autoSurchargeStatic);
