package forms;
/**

Xconfig has options. put them in component meta data.
        PHASE 2:
        xVALIDATIONS, chained, reused. one off.  go back to same page with message?
        (error handling).
        NO BRANCHING!!!,
        xSPRING RE-USABLE BEHAVIORS.
        NULLSAFE MODEL via ASPECT???-fugit.
        xDISABLE BUTTON on CHANGE.
        xPAYMENT EXAMPLE.
        xCUSTOM THEME with logos/header/width/footer etc..
        xDYNAMIC SELECT VALUES via SPRING SERVICE.

        Input Data = GermanInsuranceObject.
        inludes Country : Canada/Mexico/USA.


        Salutation[mr,mrs...] Name (First, Middle, Last)
        address [google]
        contact [enter phone or email or website]  +contact-->phone
        drivers licence #
        favourite type of pop?
        type of vehicle
        age
        smoking
        year
        # of accidents
        snow tires?

        Form 1B:
        same as Form1A except different drivers license # validation.
        and different header?
        favourite type of soda?
        type of vehicle
        age
        smoking
        year
        # of accidents
        earthquake zone

        *both have multiple car/age/etc.. validations performed.
        Bind is primary button unless blah, then it changes to Refer (bind is disabled)

        Form 1C :
        we don not offer our services in Mexico at this time.


        Form 2:
        payment method.   CC, cash.    if CC, show images and cc info.
        credit card number.   (auto detect and call moneris when length is 8)
        expiry/security code.    ajax button.
        expiry dates are configurable (withOptions[springBean])  OptionProvider<Date>  look for beans of that class.
        pay everymonth/year/6 months.   (ajax calculates amount)
        submit button disabled until

        lay it out properly.
        add a new custom theme.  (Dereks' Toy Insurance Co.)
        NullSafeModel object.  just plop it in model.nullsafe and annotate the creators if need be. --otherwise it is newIntance()

        PHASE 3
        -------
        make a widget post custom event. e.g. WfPrintEvent.
        workflow will listen to said event. -either the wf or state(s) and react.
        YesNo widget. with dependent fields (optionally grouped in Div).
        template ajax handling? is it needed? how to avoid reflow?
        add icon formatter for label (TLBR, icon name)
        add link widget (config URL and parameters).
        mock out a session spring bean with request scoped spring bean with user info.

        maybe save config as json object?
        need a replacement for SoapUI.  use GSON?
        get groovy working again?  do i need it for states? workflows?
        fix validationAdapterFactory serialization issue. (don't use inner classes in @configuration file).
        make all configs call generic newInstance(id, this) constructor.
        make all variables NOT send in json by default.  you have to explicity include them via @IncludeConfigInJson  //boolean value default true



assuming we can leave classes alone.  for example, sectionpanel.

        ajax:
        1:if element has data-template beside it, copy css.  updateCss($el); <-- recursive.
        calls $.find('[data-template]') and calls updateCss() on each.
        2:if element contains template-source, the call layout().
        3:


 autocomplete (like how browsers handle auto-form fill out).
 e.g. type in insured, drivers info will be populated.



   **/