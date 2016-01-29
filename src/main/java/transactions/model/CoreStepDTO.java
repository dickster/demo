/*
 * Copyright (c) brovada Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * brovada Technologies, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with brovada.
 */

package transactions.model;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * TODO: Class description.
 */
public class CoreStepDTO {
//    private Collection<CoreAttachmentDTO> coreAttachmentCollection;
//    private Collection<CoreLoggingDTO> coreLoggingCollection;
    private Collection<CoreMessageDTO> coreMessageCollection = Lists.newArrayList();
    private Collection<CoreMessageDTO> coreMessageCollection1 = Lists.newArrayList();
    private Date creationDate;
    private Integer id;
    private String level;
    private Date modifiedDate;
    private String operation;
    private CoreResultDTO resultId;
    private CoreTransactionDTO transactionId;

    private static int msgNum = 1;

    private List<String> names = Lists.newArrayList("persPolicyAddReq", "showPolicy", "submitPolicy", "showDetails");
    private List<String> levels = Lists.newArrayList("Abandoned", "Success", "Failed");

    public CoreStepDTO() {
        int seed = (int) (100*Math.random());
        operation = names.get(seed%names.size());

        String result = seed % 2 == 0 ? "rejected" : "ok";
        resultId = new CoreResultDTO(result);

        for (int i =0; i< seed%4; i++) {
            String msg = "this is msg " + (msgNum++);
            coreMessageCollection.add(new CoreMessageDTO(msg));
        }
        level = levels.get(seed % levels.size());
    }

    public Collection<CoreMessageDTO> getCoreMessageCollection() {
        return coreMessageCollection;
    }

    public void setCoreMessageCollection(Collection<CoreMessageDTO> coreMessageCollection) {
        this.coreMessageCollection = coreMessageCollection;
    }

    public Collection<CoreMessageDTO> getCoreMessageCollection1() {
        return coreMessageCollection1;
    }

    public void setCoreMessageCollection1(Collection<CoreMessageDTO> coreMessageCollection1) {
        this.coreMessageCollection1 = coreMessageCollection1;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public CoreResultDTO getResultId() {
        return resultId;
    }

    public void setResultId(CoreResultDTO resultId) {
        this.resultId = resultId;
    }

    public CoreTransactionDTO getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(CoreTransactionDTO transactionId) {
        this.transactionId = transactionId;
    }
}
