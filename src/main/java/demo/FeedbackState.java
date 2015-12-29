package demo;

import forms.widgets.HasCss;

public enum FeedbackState implements HasCss {
    VOID(),
    LOADING("fa fa-refresh fa-spin text-info"),
    HAS_ERROR("fa fa-times-circle text-warning"),
    HAS_WARNING("fa fa-exclamation-circle text-warning"),
    HAS_INFO("fa fa-info-circle text-success");

    private String css;

    FeedbackState() {
        this.css = null;
    }
    FeedbackState(String css) {
        this.css = css;
    }

    public String getCss() {
        return css;
    }
};