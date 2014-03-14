
package com.rapid.goshop.vo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Summary {

    @SerializedName("PageNo")
    @Expose
    private String pageNo;
    @SerializedName("PageSize")
    @Expose
    private String pageSize;
    @SerializedName("TotalPages")
    @Expose
    private String totalPages;
    @SerializedName("TotalRetailers")
    @Expose
    private String totalRetailers;
    @SerializedName("TotalStores")
    @Expose
    private String totalStores;
    @SerializedName("NearestResult")
    @Expose
    private String nearestResult;
    @SerializedName("FarthestResult")
    @Expose
    private String farthestResult;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRetailers() {
        return totalRetailers;
    }

    public void setTotalRetailers(String totalRetailers) {
        this.totalRetailers = totalRetailers;
    }

    public String getTotalStores() {
        return totalStores;
    }

    public void setTotalStores(String totalStores) {
        this.totalStores = totalStores;
    }

    public String getNearestResult() {
        return nearestResult;
    }

    public void setNearestResult(String nearestResult) {
        this.nearestResult = nearestResult;
    }

    public String getFarthestResult() {
        return farthestResult;
    }

    public void setFarthestResult(String farthestResult) {
        this.farthestResult = farthestResult;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

}
