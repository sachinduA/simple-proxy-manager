package com.proxymanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;



@Service
public class ProxyManager {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest servletRequest;

    @Value("${redirectApi}")
    String redirectApi;


    public HttpMethod methodSelection(){
        String reqMethod = servletRequest.getMethod();
        switch (reqMethod){
            case "GET" : return ( HttpMethod.GET);
            case "POST": return ( HttpMethod.POST);
            case "PUT" :return ( HttpMethod.PUT);
            case "DELETE" :return ( HttpMethod.DELETE);
        }
        return null;
   }


    //parse headers to map
   public MultiValueMap<String, String> getHeadersInfo() {

       MultiValueMap<String, String> requestHeaders = new HttpHeaders();

       Enumeration<String> headersNames = servletRequest.getHeaderNames();

       String value;
       String key;

       while (headersNames.hasMoreElements()) {
           key = headersNames.nextElement();
           value = servletRequest.getHeader(key);
           requestHeaders.put(key, Collections.singletonList(value));
       }
       return requestHeaders;

   }


    public String requestManager(String requestObject) {

        // pass the requestHeaders to HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(getHeadersInfo());
        HttpEntity<String> entity = new HttpEntity<>(requestObject, headers);

        String urlPath = servletRequest.getRequestURI();

        StringBuffer reader = servletRequest.getRequestURL();
        Logger logger = LoggerFactory.getLogger("Connecting to");
        logger.info(" " + reader);

        return restTemplate.exchange(redirectApi + urlPath, methodSelection(), entity, String.class).getBody();
    }

}

