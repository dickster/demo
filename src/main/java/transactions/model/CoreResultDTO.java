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

import java.util.Collection;
import java.util.Date;

/**
 * TODO: Class description.
 */
public class CoreResultDTO {
    private AdminApplicationDTO applicationId;
    private String category;
    private String code;
//    private Collection<CoreLoggingDTO> coreLoggingCollection;
    private Collection<CoreStepDTO> coreStepCollection;
    private Collection<CoreTransactionDTO> coreTransactionCollection;
    private Date creationDate;
    private String description;
    private Integer id;
    private Date modifiedDate;
    private String name;
//    private CoreOutcomeDTO outcomeId;
//    private Collection<UnderwriterTransactionDetailDTO> underwriterTransactionDetailCollection;
//    private Collection<UnderwriterWorkDTO> underwriterWorkCollection;
//    private Collection<UnderwriterWorkEventDTO> underwriterWorkEventCollection;


    public CoreResultDTO(String name) {
        this.name = name;
    }

    public AdminApplicationDTO getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(AdminApplicationDTO applicationId) {
        this.applicationId = applicationId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Collection<CoreStepDTO> getCoreStepCollection() {
        return coreStepCollection;
    }

    public void setCoreStepCollection(Collection<CoreStepDTO> coreStepCollection) {
        this.coreStepCollection = coreStepCollection;
    }

    public Collection<CoreTransactionDTO> getCoreTransactionCollection() {
        return coreTransactionCollection;
    }

    public void setCoreTransactionCollection(Collection<CoreTransactionDTO> coreTransactionCollection) {
        this.coreTransactionCollection = coreTransactionCollection;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
