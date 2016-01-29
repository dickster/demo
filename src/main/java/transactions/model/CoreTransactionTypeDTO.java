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
public class CoreTransactionTypeDTO {
    private AdminApplicationDTO applicationId;
    private Collection<CoreTransactionDTO> coreTransactionCollection;
    private Date creationDate;
    private Integer id;
    private Date modifiedDate;
    private String name;

    public CoreTransactionTypeDTO(String name) {
        this.name = name;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */



    
    public AdminApplicationDTO getApplicationId() {
        return this.applicationId;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public Collection<CoreTransactionDTO> getCoreTransactionCollection() {
        return this.coreTransactionCollection;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public Date getCreationDate() {
        Date retDate = null;

        if (this.creationDate != null) {
            retDate = new Date(this.creationDate.getTime());
        }

        return retDate;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public Integer getId() {
        return this.id;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public Date getModifiedDate() {
        Date retDate = null;

        if (this.modifiedDate != null) {
            retDate = new Date(this.modifiedDate.getTime());
        }

        return retDate;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public String getName() {
        return this.name;
    }

    /**
     * TODO: Description for method.
     * @param applicationId TODO: applicationId description.
     */
    
    public void setApplicationId(AdminApplicationDTO applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * TODO: Description for method.
     * @param coreTransactionCollection TODO: coreTransactionCollection description.
     */
    
    public void setCoreTransactionCollection(Collection coreTransactionCollection) {
        this.coreTransactionCollection = coreTransactionCollection;
    }

    /**
     * TODO: Description for method.
     * @param creationDate TODO: creationDate description.
     */
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = null;

        if (creationDate != null) {
            this.creationDate = new Date(creationDate.getTime());
        }
    }

    /**
     * TODO: Description for method.
     * @param id TODO: id description.
     */
    
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * TODO: Description for method.
     * @param modifiedDate TODO: modifiedDate description.
     */
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = null;

        if (modifiedDate != null) {
            this.modifiedDate = new Date(modifiedDate.getTime());
        }
    }

    /**
     * TODO: Description for method.
     * @param name TODO: name description.
     */
    
    public void setName(String name) {
        this.name = name;
    }
}
