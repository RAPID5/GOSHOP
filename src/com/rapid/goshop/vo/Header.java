
package com.rapid.goshop.vo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Header {

    @SerializedName("API_Version")
    @Expose
    private String aPI_Version;
    @SerializedName("API_Name")
    @Expose
    private String aPI_Name;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("MessageID")
    @Expose
    private String messageID;
    @SerializedName("Content_Type")
    @Expose
    private String content_Type;

    public String getAPI_Version() {
        return aPI_Version;
    }

    public void setAPI_Version(String aPI_Version) {
        this.aPI_Version = aPI_Version;
    }

    public String getAPI_Name() {
        return aPI_Name;
    }

    public void setAPI_Name(String aPI_Name) {
        this.aPI_Name = aPI_Name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getContent_Type() {
        return content_Type;
    }

    public void setContent_Type(String content_Type) {
        this.content_Type = content_Type;
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
