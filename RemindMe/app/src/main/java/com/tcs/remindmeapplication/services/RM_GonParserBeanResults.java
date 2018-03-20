package com.tcs.remindmeapplication.services;

import java.io.Serializable;

/**
 * Created by AVS on 21-Oct-15.
 */
public class RM_GonParserBeanResults implements Serializable {

    RM_GonParserBeanGeometry geometry=new RM_GonParserBeanGeometry();


    public RM_GonParserBeanGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(RM_GonParserBeanGeometry geometry) {
        this.geometry = geometry;
    }









}
