/*
 * Copyright (c) brovada Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * brovada Technologies, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with brovada.
 */
package transactions;


import com.google.common.base.CaseFormat;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.LocalDate;
import transactions.model.*;

import java.io.Serializable;
import java.util.*;


/**
 * Editable panel.
 */
public class TransactionsWritablePanel extends Panel {

    private final PageableListView<CoreTransactionDTO> transactionsView;

    enum DateFilterEnum {
        TODAY("Today", 0),
        YESTERDAY("Yesterday", -1,0),
        LAST_10_DAYS("Last 10 Days", -10),
        LAST_30_DAYS("Last 30 Days", -30),
        All("All");

        private Integer to;
        private Integer from;
        private String label;

        DateFilterEnum(String label, int from) {
            this(label, from, null);
        }

        DateFilterEnum(String label) {
            this(label, null, null);
        }

        DateFilterEnum(String label, Integer from, Integer to) {
            this.label = label;
            this.from = from;
            this.to = to;
        }

        public LocalDate getFrom() {
            if (from == null) return null;
            return new LocalDate().plusDays(from);
        }
        public LocalDate getTo() {
            if (to ==null) return null;
            return new LocalDate().plusDays(to);
        }

        @Override
        public String toString() {
            return label!=null ? label :
                    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, super.toString());
        }
    };


    private final WebMarkupContainer detailsContainer;

    private DateFilterEnum when = DateFilterEnum.All;
    private CoreTransactionDTO transaction = null;
    private String refNum;
    private Integer transId;
    private List<CoreTransactionDTO> transactions = createTransactions();

    /**
     * DOCUMENT: Constructs ...
     *
     * @param id DOCUMENT: id description.
     */
    public TransactionsWritablePanel(final String id) {
        super(id);
        setVersioned(true);
        setOutputMarkupId(true);

        add(new Form("toolbar")
                .add(new DropDownChoice<DateFilterEnum>("when", new PropertyModel(this, "when"), Lists.newArrayList(DateFilterEnum.values()))
                        .add(newFilteringBehavior()))
                .add(new TextField<String>("refNum", new PropertyModel(this, "refNum"))
                        .add(newFilteringBehavior()))
                .add(new TextField<Integer>("transId", new PropertyModel(this, "transId"))
                        .add(newFilteringBehavior()))
        );

        add(transactionsView = new PageableListView<CoreTransactionDTO>("transactions", getTransactionsModel(), 6) {
            @Override
            protected void populateItem(ListItem<CoreTransactionDTO> item) {
                final IModel<CoreTransactionDTO> model = item.getModel();
                item.add(new Label("id", new PropertyModel(model, "id")));
                AjaxLink link = new AjaxLink("ref") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        transaction = model.getObject();
                        target.add(detailsContainer);
                    }
                };
                link.add(new Label("label", new PropertyModel(model, "transactionReferenceCode")));
                item.add(link);
                item.add(new Label("type", new PropertyModel(model, "transactionTypeId.id")));
                item.add(new Label("status", new PropertyModel(model, "status")));
                item.add(new Label("created", new PropertyModel(model, "creationDate")));
            }

        });
        add(new PagingNavigation("navigator", transactionsView));

        detailsContainer = new WebMarkupContainer("detailsContainer");
        add(detailsContainer.setOutputMarkupId(true));
        detailsContainer.add(new ListView<CoreStepDTO>("details", getStepsModel()) {
            @Override
            protected void populateItem(ListItem<CoreStepDTO> item) {
                IModel<CoreStepDTO> model = item.getModel();
                item.add(new Label("name", new PropertyModel(model, "operation")));
                item.add(new Label("status", new PropertyModel(model, "resultId.name")));
                item.add(new Label("msgs", new PropertyModel(model, "coreMessageCollection")));
            }
        });
    }

    private AjaxFormComponentUpdatingBehavior newFilteringBehavior() {
        return new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(TransactionsWritablePanel.this);
            }
        };
    }

    // replace this with step model...
    private IModel<? extends List<CoreStepDTO>> getStepsModel() {
        return new Model() {
            @Override
            public Serializable getObject() {
                ArrayList<CoreStepDTO> result = (transaction==null) ? new ArrayList<CoreStepDTO>() : Lists.newArrayList(transaction.getCoreStepCollection());
                System.out.println(result);
                return result;
            }
        };
    }

    // replace this with
    private IModel<? extends List<? extends CoreTransactionDTO>> getTransactionsModel() {
        return new Model<ArrayList<CoreTransactionDTO>>() {
            @Override
            public ArrayList<CoreTransactionDTO> getObject() {
                return getTransactions(when.getFrom(), when.getTo(), transId, refNum);
            }
        };
    }

    // TODO : replace this with DB call.
    private ArrayList<CoreTransactionDTO> getTransactions(final LocalDate from, final LocalDate to, final Integer transId, final String refNum) {
        // stub : implement getTransactions(from, to, transId, refNum);
        // need to get total number of results.
        Predicate<CoreTransactionDTO> predicate = new Predicate<CoreTransactionDTO>() {
            @Override public boolean apply(CoreTransactionDTO transaction) {
                boolean after = (from==null) ? true : transaction.getCreationDate().after(from.toDate());
                boolean before = (to==null) ? true : transaction.getCreationDate().before(to.toDate());
                boolean tid = transId==null ? true : transaction.getTransactionTypeId().getId().equals(transId);
                boolean ref = refNum==null ? true : transaction.getTransactionReferenceCode().contains(refNum);
                return after && before && tid && ref;
            }
        };
        return Lists.newArrayList(Iterables.filter(transactions, predicate));
    }

    private List<CoreTransactionDTO> createTransactions() {
        List<CoreTransactionDTO> result = Lists.newArrayList();
        for (int i=0; i<52; i++) {
            result.add(new CoreTransactionDTO());
        }
        return result;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize(); 
    }

}
