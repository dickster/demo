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
public class AdminApplicationDTO {
//    private Collection<AdminPermissionDTO> adminPermissionCollection;
    private Collection<CoreResultDTO> coreResultCollection;
//    private Collection<CoreTransactionTypeDTO> coreTransactionTypeCollection;
    private Date creationDate;
    private Integer id;
    private Date modifiedDate;
    private String name;

    public Collection<CoreResultDTO> getCoreResultCollection() {
        return coreResultCollection;
    }

    public void setCoreResultCollection(Collection<CoreResultDTO> coreResultCollection) {
        this.coreResultCollection = coreResultCollection;
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
