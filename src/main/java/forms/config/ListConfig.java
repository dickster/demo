package forms.config;

import forms.WidgetTypeEnum;
import forms.widgets.OrderedList;

import javax.annotation.Nonnull;


public class ListConfig extends GroupConfig<OrderedList> {

    public ListConfig(@Nonnull String name) {
        super(name, WidgetTypeEnum.LIST);
        withCss("list-container");
    }

    @Override
    public OrderedList create(String id) {
        // change to class.newInstance()...
        return new OrderedList(id, this);
    }
}
