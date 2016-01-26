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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Editable panel.
 */
public class TransactionsWritablePanel extends Panel {

    private final PageableListView<Transaction> transactionsView;

    enum DateFilterEnum {
        TODAY(0),
        YESTERDAY(-1,0),
        LAST_10_DAYS(-10),
        LAST_30_DAYS(-30),
        All();

        private int to;
        private int from;

        DateFilterEnum(int from) {
            this(from,-1);
        }
        DateFilterEnum(int from, int to) {
            this.from = from;
            this.to = to;
        }

        DateFilterEnum() {
            this.from = -1;
            this.to = -1;
        }

        public LocalDate getFrom() {
            if (from ==-1) return null;
            return new LocalDate().plusDays(from);
        }
        public LocalDate getTo() {
            if (to ==-1) return null;
            return new LocalDate().plusDays(to);
        }

        @Override
        public String toString() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, super.toString());
        }
    };


    private final WebMarkupContainer detailsContainer;

    /** temporary variables.  delete after hooked up to real data source */
    private int ref = 1;
    private int msgNum = 1;
    private List<Transaction> filteredTransactions = Lists.newArrayList();
    /** ----------------------------------------------------- */


    private DateFilterEnum when = DateFilterEnum.All;
    private Transaction transaction = null;
    private String refNum;
    private String transId;
    private List<Transaction> transactions = createTransactions();

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
                .add(new TextField<String>("transId", new PropertyModel(this, "transId"))
                        .add(newFilteringBehavior()))
        );

        add(transactionsView = new PageableListView<Transaction>("transactions", getTransactionsModel(), 6) {
            @Override
            protected void populateItem(ListItem<Transaction> item) {
                final IModel<Transaction> model = item.getModel();
                item.add(new Label("ref", new PropertyModel(model, "referenceNumber")));
                AjaxLink link = new AjaxLink("trans") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        transaction = model.getObject();
                        target.add(detailsContainer);
                    }
                };
                link.add(new Label("label", new PropertyModel(model, "transactionId")));
                item.add(link);
                item.add(new Label("type", new PropertyModel(model, "type")));
                item.add(new Label("status", new PropertyModel(model, "status")));
                item.add(new Label("created", new PropertyModel(model, "created")));
            }

        });
        add(new PagingNavigation("navigator", transactionsView));

        detailsContainer = new WebMarkupContainer("detailsContainer");
        add(detailsContainer.setOutputMarkupId(true));
        detailsContainer.add(new ListView<Step>("details", getSteps()) {
            @Override
            protected void populateItem(ListItem<Step> item) {
                IModel<Step> model = item.getModel();
                item.add(new Label("name", new PropertyModel(model, "name")));
                item.add(new Label("status", new PropertyModel(model, "status")));
                item.add(new Label("msgs", new PropertyModel(model, "messages")));
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

    private IModel<? extends List<Step>> getSteps() {
        return new Model() {
            @Override
            public Serializable getObject() {
                ArrayList<Step> result = (transaction==null) ? new ArrayList<Step>() : (ArrayList<Step>) transaction.steps;
                System.out.println(result);
                return result;
            }
        };
    }

    private IModel<? extends List<? extends Transaction>> getTransactionsModel() {
        return new Model<ArrayList<Transaction>>() {
            @Override
            public ArrayList<Transaction> getObject() {
                return getTransactions(when.getFrom(), when.getTo(), transId, refNum);
            }
        };
    }

    // TODO : replace this with DB call.
    private ArrayList<Transaction> getTransactions(final LocalDate from, final LocalDate to, final String transId, final String refNum) {
        // stub : implement getTransactions(from, to, transId, refNum);
        // need to get total number of results.
        Predicate<Transaction> predicate = new Predicate<Transaction>() {
            @Override public boolean apply(Transaction transaction) {
                boolean after = (from==null) ? true : transaction.created.isAfter(from);
                boolean before = (to==null) ? true : transaction.created.isBefore(to);
                boolean tid = transId==null ? true : transaction.transactionId.toLowerCase().contains(transId.toLowerCase());
                boolean ref = refNum==null ? true : transaction.referenceNumber.equalsIgnoreCase(refNum);
                return after && before && tid && ref;
            }
        };
        return Lists.newArrayList(Iterables.filter(transactions, predicate));
    }

    private List<Transaction> createTransactions() {
        List<Transaction> result = Lists.newArrayList();
        for (int i=0; i<52; i++) {
            result.add(new Transaction());
        }
        return result;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize(); 
    }

    public class Transaction implements Serializable {
        public String referenceNumber;
        public String transactionId;
        public String type;
        public String status;
        public LocalDate created;
        public List<Step> steps = Lists.newArrayList();

        private List<String> types = Lists.newArrayList("Auto", "Hab", "Commercial");
        private List<String> statuses = Lists.newArrayList("Abandoned", "Success", "Failed");

        public Transaction() {
            int seed = (int) (40*Math.random());
            transactionId = UUID.randomUUID().toString();
            referenceNumber = "" + ref++;
            type = types.get(seed%types.size());
            status = statuses.get(seed%statuses.size());
            created = new LocalDate().minusDays(seed);

            for (int i=0;i<1+seed%4;i++) {
                steps.add(new Step());
            }
        }
    }

    public class Step implements Serializable {
        public String name;
        public String status;
        public List<String> messages = Lists.newArrayList();

        private List<String> names = Lists.newArrayList("persPolicyAddReq", "showPolicy", "submitPolicy", "showDetails");
        private List<String> statuses = Lists.newArrayList("Abandoned", "Success", "Failed");

        public Step() {
            int seed = (int) (100*Math.random());
            name = names.get(seed%names.size());
            for (int i =0; i< seed%4; i++) {
                messages.add("this is message " + msgNum++);
            }
            status = statuses.get(seed%statuses.size());
        }
    }

}
