package demo;

public enum FeedbackState {
    VOID(""),
    LOADING("fa fa-refresh fa-spin"),
    HAS_ERROR("fa fa-times-circle red"),
    HAS_WARNING("fa fa-exclamation-circle orange"),
    HAS_INFO("fa fa-info-circle yellow");

    private String css;

    FeedbackState(String css) {
        this.css = css;
    }

    public String getCss() {
        return css;
    }
};