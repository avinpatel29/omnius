package com.omnius.api;

import java.math.BigDecimal;
import java.util.Map;

public class Response {

    private float duration;
    private boolean success;
    private String error_message;
    private int status_code;
    private Map<String, Object> payload;

    private BigDecimal size;
    private long from;
    private BigDecimal limit;

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    private long page;
    private Map<String, Object> items;
    private long total;


    public float getDuration() {
        return duration;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError_message() {
        return error_message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public long getFrom() {
        return from;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public long getPage() {
        return page;
    }

    public Map<String, Object> getItems() {
        return items;
    }

    public long getTotal() {
        return total;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public void setItems(Map<String, Object> items) {
        this.items = items;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
