
package com.rapid.goshop.vo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Result {

    @Expose
    private List<Address_component> address_components = new ArrayList<Address_component>();
    @Expose
    private String formatted_address;
    @Expose
    private Geometry geometry;
    @Expose
    private List<String> types = new ArrayList<String>();

    public List<Address_component> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<Address_component> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
