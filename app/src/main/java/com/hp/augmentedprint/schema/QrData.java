package com.hp.augmentedprint.schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 24/4/18 by aparna .
 */
public class QrData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("project_name")
    @Expose
    private String projectName;
    @SerializedName("project_number")
    @Expose
    private Integer projectNumber;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("orginization_name")
    @Expose
    private String orginizationName;
    @SerializedName("orginization_description")
    @Expose
    private String orginizationDescription;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("bulding_name")
    @Expose
    private String buldingName;
    @SerializedName("project_address")
    @Expose
    private String projectAddress;
    @SerializedName("project_status")
    @Expose
    private String projectStatus;
    @SerializedName("project_issue_date")
    @Expose
    private String projectIssueDate;
    @SerializedName("initial_investment")
    @Expose
    private Integer initialInvestment;
    @SerializedName("download_link")
    @Expose
    private String downloadLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrginizationName() {
        return orginizationName;
    }

    public void setOrginizationName(String orginizationName) {
        this.orginizationName = orginizationName;
    }

    public String getOrginizationDescription() {
        return orginizationDescription;
    }

    public void setOrginizationDescription(String orginizationDescription) {
        this.orginizationDescription = orginizationDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBuldingName() {
        return buldingName;
    }

    public void setBuldingName(String buldingName) {
        this.buldingName = buldingName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectIssueDate() {
        return projectIssueDate;
    }

    public void setProjectIssueDate(String projectIssueDate) {
        this.projectIssueDate = projectIssueDate;
    }

    public Integer getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(Integer initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}
