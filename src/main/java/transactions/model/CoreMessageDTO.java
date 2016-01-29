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

import java.util.Date;
import java.util.List;

/**
 * TODO: Class description.
 */
public class CoreMessageDTO  {
    private String category;
    private Date creationDate;
    private CoreStepDTO flowId;
    private Integer id;
    private String level;
    private Date modifiedDate;
    private Integer number;
    private CoreStepDTO stepId;
    private CoreTransactionDTO transactionId;
    private String value;
    private Boolean visible;

    public CoreMessageDTO(String value, String level, String category) {
        this.value = value;
        this.level = level;
        this.category = category;
    }

    public CoreMessageDTO(String msg) {
        int seed = (int) (100*Math.random());
        List<String> names = Lists.newArrayList("persPolicyAddReq", "showPolicy", "submitPolicy", "showDetails");
        List<String> levels = Lists.newArrayList("Abandoned", "Success", "Failed");
        this.level = levels.get(seed%(levels.size()));
        this.value = msg;
        this.category = "CATEGORY_" + seed;
    }

    @Override
    public String toString() {
        return String.format("%s : %s  (%s)", level, value, category);
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    public String getCategory() {
        return this.category;
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
    
    public CoreStepDTO getFlowId() {
        return this.flowId;
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
    
    public String getLevel() {
        return this.level;
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
    
    public Integer getNumber() {
        return this.number;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public CoreStepDTO getStepId() {
        return this.stepId;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public CoreTransactionDTO getTransactionId() {
        return this.transactionId;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public String getValue() {
        return this.value;
    }

    /**
     * TODO: Description for method.
     * @return TODO: description.
     */
    
    public Boolean getVisible() {
        return this.visible;
    }

    /**
     * TODO: Description for method.
     * @param category TODO: category description.
     */
    
    public void setCategory(String category) {
        this.category = category;
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
     * @param flowId TODO: flowId description.
     */
    
    public void setFlowId(CoreStepDTO flowId) {
        this.flowId = flowId;
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
     * @param level TODO: level description.
     */
    
    public void setLevel(String level) {
        this.level = level;
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
     * @param number TODO: number description.
     */
    
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * TODO: Description for method.
     * @param stepId TODO: stepId description.
     */
    
    public void setStepId(CoreStepDTO stepId) {
        this.stepId = stepId;
    }

    /**
     * TODO: Description for method.
     * @param transactionId TODO: transactionId description.
     */
    
    public void setTransactionId(CoreTransactionDTO transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * TODO: Description for method.
     * @param value TODO: value description.
     */
    
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * TODO: Description for method.
     * @param visible TODO: visible description.
     */
    
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
