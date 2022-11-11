package com.proxymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyInvoker {

    private ProxyManager proxyManager;

    @Autowired
    public void proxyManager(ProxyManager proxyManager){
        this.proxyManager = proxyManager;
    }

    @RequestMapping(value = "/**", produces = "application/json")
    public String requestHandler(@RequestBody(required = false) String requestObject){
        return proxyManager.requestManager(requestObject);

    }

}
