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
public class AdminOrganizationDTO {
    private String addrCity;
    private String addrPostalcodeZipcode;
    private String addrProvinceState;
    private String addrStreet;
    private String addrStreet2;
    private Collection<AdminOrganizationDTO> adminOrganizationCollection;
    private Collection<CoreTransactionDTO> coreTransactionCollection;
    private Collection<CoreTransactionDTO> coreTransactionCollection1;
    private Date creationDate;
    private Integer id;
    private Boolean isdeleted;
    private Date modifiedDate;
    private String name;
    private AdminOrganizationDTO parentOrganizationId;

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrPostalcodeZipcode() {
        return addrPostalcodeZipcode;
    }

    public void setAddrPostalcodeZipcode(String addrPostalcodeZipcode) {
        this.addrPostalcodeZipcode = addrPostalcodeZipcode;
    }

    public String getAddrProvinceState() {
        return addrProvinceState;
    }

    public void setAddrProvinceState(String addrProvinceState) {
        this.addrProvinceState = addrProvinceState;
    }

    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    public String getAddrStreet2() {
        return addrStreet2;
    }

    public void setAddrStreet2(String addrStreet2) {
        this.addrStreet2 = addrStreet2;
    }

    public Collection<AdminOrganizationDTO> getAdminOrganizationCollection() {
        return adminOrganizationCollection;
    }

    public void setAdminOrganizationCollection(Collection<AdminOrganizationDTO> adminOrganizationCollection) {
        this.adminOrganizationCollection = adminOrganizationCollection;
    }

    public Collection<CoreTransactionDTO> getCoreTransactionCollection() {
        return coreTransactionCollection;
    }

    public void setCoreTransactionCollection(Collection<CoreTransactionDTO> coreTransactionCollection) {
        this.coreTransactionCollection = coreTransactionCollection;
    }

    public Collection<CoreTransactionDTO> getCoreTransactionCollection1() {
        return coreTransactionCollection1;
    }

    public void setCoreTransactionCollection1(Collection<CoreTransactionDTO> coreTransactionCollection1) {
        this.coreTransactionCollection1 = coreTransactionCollection1;
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

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
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

    public AdminOrganizationDTO getParentOrganizationId() {
        return parentOrganizationId;
    }

    public void setParentOrganizationId(AdminOrganizationDTO parentOrganizationId) {
        this.parentOrganizationId = parentOrganizationId;
    }
}
