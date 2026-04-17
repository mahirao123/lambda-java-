package com.springboot.rbl.service;

import org.springframework.stereotype.Service;

@Service
public class SoapClientService {

	public String sendSigned(byte[] signedXml) {
	    try {
	        SOAPMessage msg = MessageFactory.newInstance().createMessage();
	        msg.getSOAPBody().addTextNode(
	            Base64.getEncoder().encodeToString(signedXml)
	        );

	        SOAPConnection conn =
	            SOAPConnectionFactory.newInstance().createConnection();

	        conn.call(msg, "https://cersai.gov.in/ckyc");

	        return "CKYC Submitted with DSC";
	    } catch (Exception e) {
	        return "SOAP Error: " + e.getMessage();
	    }
	}

}
