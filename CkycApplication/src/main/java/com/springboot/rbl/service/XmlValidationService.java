package com.springboot.rbl.service;

import org.springframework.stereotype.Service;

@Service
public class XmlValidationService {
	
    public void validate(String xml) {
        // XSD validation logic (already discussed)
    	
    	Schema schema = SchemaFactory.newInstance(
    			  XMLConstants.W3C_XML_SCHEMA_NS_URI)
    			  .newSchema(new File("ckyc.xsd"));

    			Validator validator = schema.newValidator();
    			validator.validate(new StreamSource(new StringReader(xml)));

    }

}
