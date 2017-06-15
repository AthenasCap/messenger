package com.hizzit.messenger.business.messagehub.control;
import javax.ws.rs.QueryParam;

/**
 * To out source Parameters for MessageFilters Class
 * @author jan
 */
public class MessageFilterBean {
    private @QueryParam("year") int year;
    private @QueryParam("start") int start;
    private @QueryParam("size") int size;

    public MessageFilterBean() {
    }

    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    } 
}
