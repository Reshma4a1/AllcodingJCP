package com.generatexml;

import java.io.File;
import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.xml.ser.XmlSerializerProvider;
import com.fasterxml.jackson.dataformat.xml.util.StaxUtil;

public class DynamicTestGenerator{
	private final Fillo fillo;
	private final String filePath;
	private Connection connection;
	
	public DynamicTestGenerator(String filePath) {
	        fillo = new Fillo();
	        this.filePath = filePath;
	}
	        
	public void getTests(String query,String outPutXMLFile) {
        try {
            connection = fillo.getConnection(this.filePath);
            Recordset recordset = connection.executeQuery(query);
            this.createSuite(recordset,outPutXMLFile);
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
	
	private void createSuite(Recordset recordset,String outPutXMLFile) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        final String dtd = "<!DOCTYPE suite SYSTEM "+"\"http:"+"//testng.org/testng-1.0.dtd"+"\">";
        final JacksonXmlDTD.DtdXmlSerializerProvider serializerProvider = new JacksonXmlDTD.DtdXmlSerializerProvider(
                (XmlSerializerProvider) xmlMapper.getSerializerProvider(),
                xmlMapper.getSerializationConfig(),
                xmlMapper.getSerializerFactory(),
                dtd);
        xmlMapper.setSerializerProvider(serializerProvider);
        TestAutomationSuite suite = new TestAutomationSuite("AutomationSuite");
        try {
            while (recordset.next()) {
                String testName = recordset.getField("TestCaseDescription");
                String className = recordset.getField("ClassName");
                String param = "Data";
                String paramValue = recordset.getField("TestData");
                String testVerbose = recordset.getField("TestVerbose");
                suite.addTest(testName,testVerbose,param,paramValue,className);
            }
            xmlMapper.writeValue(new File(outPutXMLFile),suite);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recordset.close();
        }
    }
}


class JacksonXmlDTD {
		@SuppressWarnings("serial")
		static class DtdXmlSerializerProvider extends XmlSerializerProvider{
	        private final String dtd;
	        public DtdXmlSerializerProvider(
	                final XmlSerializerProvider src,
	                final SerializationConfig config,
	                final SerializerFactory jsf,
	                final String dtd) {
	            super(src, config, jsf);
	            this.dtd = dtd;
	        }
	        @Override
	        protected void _initWithRootName(final ToXmlGenerator xgen, final QName rootName)
	                throws IOException {
	            super._initWithRootName(xgen, rootName);
	            try {
	                xgen.getStaxWriter().writeDTD(dtd);
	            } catch (final XMLStreamException e) {
	                StaxUtil.throwXmlAsIOException(e);
	            }
	        }
	        @Override
	        public DefaultSerializerProvider createInstance(
	                final SerializationConfig config, final SerializerFactory jsf) {
	            return new DtdXmlSerializerProvider(this, config, jsf, dtd);
	        }
	    }
}