package com.reqres.api;

import testframework.PropertiesManager;

public class ReqresApi extends BaseApiService {
    public ReqresApi(String subPath) {
        super(subPath, PropertiesManager.getConfigProperties().getProperty("reqres.baseUrl"));
    }
}
