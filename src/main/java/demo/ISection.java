package demo;

// TODO : create spring beans that implement this (& other methods)
//  & optionally delegate from SectionPanel.
public interface ISection {
    public String getTooltip();
    public String getName();
    public String getIconCss();
    public Integer getOrdinal();
    public String getHref();

}
