package com.reqres.api;

public class BaseApiService {
    private final String servicePath;
    private final String serviceUrl;

    protected BaseApiService(String path, String baseUrl) {
        this.servicePath = path;
        this.serviceUrl = baseUrl;
    }

    protected String getServicePath() {
        return servicePath;
    }

    protected String getServiceUrl() {
        return serviceUrl;
    }
}