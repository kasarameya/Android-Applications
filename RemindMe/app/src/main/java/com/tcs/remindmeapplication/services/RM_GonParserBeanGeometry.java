package com.tcs.remindmeapplication.services;

import java.io.Serializable;

/**
 * Created by AVS on 21-Oct-15.
 */
public class RM_GonParserBeanGeometry implements Serializable {

    Location location=new Location();




    public Location getLocations() {
        return location;
    }

    public void setLocations(Location location) {
        this.location = location;
    }

    public class Location implements Serializable
    {

        String lat;
        String lng;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }



}
