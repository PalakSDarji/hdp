package com.hadippa.model;

/**
 * Created by HP on 02-08-2016.
 */
public class CityModel {


    /**
     * id : 32
     * name : Vadodara
     * country_id : 1
     * country_name : India
     * discovery_enabled : 0
     * has_new_ad_format : 0
     * is_state : 0
     * state_id : 0
     * state_name :
     * state_code :
     */

    private int id;
    private String name;
    private int country_id;
    private String country_name;
    private int discovery_enabled;
    private int has_new_ad_format;
    private int is_state;
    private int state_id;
    private String state_name;
    private String state_code;
    private long lastSearchedDate;

    public long getLastSearchedDate() {
        return lastSearchedDate;
    }

    public void setLastSearchedDate(long lastSearchedDate) {
        this.lastSearchedDate = lastSearchedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public int getDiscovery_enabled() {
        return discovery_enabled;
    }

    public void setDiscovery_enabled(int discovery_enabled) {
        this.discovery_enabled = discovery_enabled;
    }

    public int getHas_new_ad_format() {
        return has_new_ad_format;
    }

    public void setHas_new_ad_format(int has_new_ad_format) {
        this.has_new_ad_format = has_new_ad_format;
    }

    public int getIs_state() {
        return is_state;
    }

    public void setIs_state(int is_state) {
        this.is_state = is_state;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }
}
