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
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * TODO: Class description.
 */
public class CoreTransactionDTO {
    //    private CoreProductDTO sourceProductId;
//    private AdminOrganizationDTO targetOrganizationId;
//    private CoreProductDTO targetProductId;
//    private UnderwriterTransactionDetailDTO underwriterTransactionDetail;
//    private AdminUserDTO userId;
//    private Collection<CoreAttachmentDTO> coreAttachmentCollection;
    //private Collection<CoreLoggingDTO> coreLoggingCollection;
//    private Collection<CoreMessageDTO> coreMessageCollection;
    private Collection<CoreStepDTO> coreStepCollection = Lists.newArrayList();
    private Date creationDate;
    private Integer id;
    private Date modifiedDate;
    private Integer parentTransactionId;
    private CoreResultDTO resultId;
    private AdminOrganizationDTO sourceOrganizationId;
    private String transactionReferenceCode;
    private CoreTransactionTypeDTO transactionTypeId;

    private String status;

    private static int ID=1;

    public CoreTransactionDTO() {
        super();

        List<String> types = Lists.newArrayList("Auto", "Hab", "Commercial");
        List<String> statuses = Lists.newArrayList("Abandoned", "Success", "Failed");

        int seed = (int) (40*Math.random());
        transactionReferenceCode = UUID.randomUUID().toString();
        id = ID++;
        transactionTypeId = new CoreTransactionTypeDTO(types.get(seed%(types.size())));
        status = statuses.get(seed%statuses.size());
        creationDate = new LocalDate().minusDays(seed).toDate();

        for (int i=0;i<1+seed%4;i++) {
            coreStepCollection.add(new CoreStepDTO());
        }
    }

    public String getStatus() {
        return status;
    }


    //    public class Transaction implements Serializable {
//        public String referenceNumber;
//        public String transactionId;
//        public String type;
//        public String status;
//        public LocalDate created;
//        public List<CoreStepDTO> steps = Lists.newArrayList();
//
//        private List<String> types = Lists.newArrayList("Auto", "Hab", "Commercial");
//        private List<String> statuses = Lists.newArrayList("Abandoned", "Success", "Failed");
//
//        public Transaction() {
//        }
//    }



    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Collection<CoreStepDTO> getCoreStepCollection() {
        return coreStepCollection;
    }

    public void setCoreStepCollection(Collection<CoreStepDTO> coreStepCollection) {
        this.coreStepCollection = coreStepCollection;
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

    public CoreResultDTO getResultId() {
        return resultId;
    }

    public void setResultId(CoreResultDTO resultId) {
        this.resultId = resultId;
    }

    public String getTransactionReferenceCode() {
        return transactionReferenceCode;
    }

    public void setTransactionReferenceCode(String transactionReferenceCode) {
        this.transactionReferenceCode = transactionReferenceCode;
    }

    public CoreTransactionTypeDTO getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(CoreTransactionTypeDTO transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }
}
