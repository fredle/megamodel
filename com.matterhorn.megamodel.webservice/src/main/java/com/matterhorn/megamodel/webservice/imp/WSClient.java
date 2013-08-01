package com.matterhorn.megamodel.webservice.imp;


import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.matterhorn.megamodel.api.webservices.MegaModelMistMethodsWS;

public class WSClient {

    private static final QName SERVICE_NAME
        = new QName("http://megamodel.matterhorninvestment.com/megamodel", "MegaModelService");
 

    public static void main (String[] args) throws Exception {
    	
        String endpointAddress = "http://dell67.matterhorn.int:8080/cxf/mist";
        Service service = Service.create(new URL(endpointAddress +"?wsdl"), SERVICE_NAME);
        MegaModelMistMethodsWS port = service.getPort(MegaModelMistMethodsWS.class);
        
        long resp = port.returnOne();
        System.out.println("The number " + resp);
      
    }
}