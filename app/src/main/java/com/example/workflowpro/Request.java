package com.example.workflowpro;

public class Request {

    String requestAsset;
    String requestCategory;
    String requestCreator;
    String requestDate;
    String requestDescription;
    String requestId;
    byte[] requestImage;
    String requestLocation;
    String requestPriority;
    String requestState;
    String requestTeam;
    String requestTimeStamp;
    String requestTitle;

    String requestWorkers;

    public Request() {
    }

    public Request(String requestAsset, String requestCategory, String requestCreator, String requestDate, String requestDescription, String requestId, String requestLocation, String requestPriority, String requestState, String requestTeam, String requestTimeStamp, String requestTitle, String requestWorkers) {
        this.requestAsset = requestAsset;
        this.requestCategory = requestCategory;
        this.requestCreator = requestCreator;
        this.requestDate = requestDate;
        this.requestDescription = requestDescription;
        this.requestId = requestId;
        this.requestLocation = requestLocation;
        this.requestPriority = requestPriority;
        this.requestState = requestState;
        this.requestTeam = requestTeam;
        this.requestTimeStamp = requestTimeStamp;
        this.requestTitle = requestTitle;
        this.requestWorkers = requestWorkers;
    }

    public Request(String requestAsset, String requestCategory, String requestCreator, String requestDate, String requestDescription, String requestId, byte[] requestImage, String requestLocation, String requestPriority, String requestState, String requestTeam, String requestTimeStamp, String requestTitle, String requestWorkers) {
        this.requestAsset = requestAsset;
        this.requestCategory = requestCategory;
        this.requestCreator = requestCreator;
        this.requestDate = requestDate;
        this.requestDescription = requestDescription;
        this.requestId = requestId;
        this.requestImage = requestImage;
        this.requestLocation = requestLocation;
        this.requestPriority = requestPriority;
        this.requestState = requestState;
        this.requestTeam = requestTeam;
        this.requestTimeStamp = requestTimeStamp;
        this.requestTitle = requestTitle;
        this.requestWorkers = requestWorkers;
    }

    public String getRequestAsset() {
        return requestAsset;
    }

    public void setRequestAsset(String requestAsset) {
        this.requestAsset = requestAsset;
    }

    public String getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(String requestCategory) {
        this.requestCategory = requestCategory;
    }

    public String getRequestCreator() {
        return requestCreator;
    }

    public void setRequestCreator(String requestCreator) {
        this.requestCreator = requestCreator;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public byte[] getRequestImage() {
        return requestImage;
    }

    public void setRequestImage(byte[] requestImage) {
        this.requestImage = requestImage;
    }

    public String getRequestLocation() {
        return requestLocation;
    }

    public void setRequestLocation(String requestLocation) {
        this.requestLocation = requestLocation;
    }

    public String getRequestPriority() {
        return requestPriority;
    }

    public void setRequestPriority(String requestPriority) {
        this.requestPriority = requestPriority;
    }

    public String getRequestState() {
        return requestState;
    }

    public void setRequestState(String requestState) {
        this.requestState = requestState;
    }

    public String getRequestTeam() {
        return requestTeam;
    }

    public void setRequestTeam(String requestTeam) {
        this.requestTeam = requestTeam;
    }

    public String getRequestTimeStamp() {
        return requestTimeStamp;
    }

    public void setRequestTimeStamp(String requestTimeStamp) {
        this.requestTimeStamp = requestTimeStamp;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestWorkers() {
        return requestWorkers;
    }

    public void setRequestWorkers(String requestWorkers) {
        this.requestWorkers = requestWorkers;
    }
}
