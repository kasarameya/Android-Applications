package com.tcs.remindmeapplication.services;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by AVS on 21-Oct-15.
 */
public class RM_GonParserBeanObject implements Serializable{


    ArrayList<RM_GonParserBeanResults> results=new ArrayList<>();
    String status;
    //String next_page_token;

   /* public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }*/



    public ArrayList<RM_GonParserBeanResults> getResults() {
        return results;
    }

    /*public ArrayList<Html_attributions> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(ArrayList<Html_attributions> html_attributions) {
        this.html_attributions = html_attributions;
    }*/

    public void setResults(ArrayList<RM_GonParserBeanResults> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
