package ru.com.ma.domain;

import org.apache.commons.lang3.StringUtils;

public class MessageFilter {

    private String term;


    public MessageFilter() {
    }

    public MessageFilter(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Boolean isEmpty(){
        return StringUtils.isEmpty(term);
    }
}
