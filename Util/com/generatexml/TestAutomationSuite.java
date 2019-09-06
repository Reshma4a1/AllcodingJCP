package com.generatexml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="suite")
public class TestAutomationSuite {
	 @JacksonXmlProperty(isAttribute=true)
	 private String name;
		
	 @JacksonXmlProperty(isAttribute = true)
	 private boolean parallel;
	 
	 @JacksonXmlProperty(localName = "test")
	 @JacksonXmlElementWrapper(useWrapping = false)
	 private List<Test> tests;
	 
	 public TestAutomationSuite(String name){
		 this.name =  name;
		 this.parallel=false;
		 this.tests = new ArrayList<Test>();
	 }
	 
	 public void addTest(String testname,String verbose,String paramName,String paramValue,String className) {
	     Test test = new Test(testname,verbose);
	     test.addParam(paramName, paramValue);
	     Pattern.compile(",").splitAsStream(className).forEach(test::addClass);
	     this.tests.add(test);
	 }
}

class Test{
	 @JacksonXmlProperty(isAttribute = true)
     private String name;
     
     @JacksonXmlProperty(isAttribute=true)
     private String verbose;
     
     @JacksonXmlProperty(localName = "parameter")
     private TestParameter param;

     @JacksonXmlProperty(localName = "classes")
     private TestClasses klasses;
     
     public Test(String name,String verbose){
         this.name = name;
         this.verbose=verbose;
         klasses = new TestClasses();
     }

     public void addParam(String name, String value) {
         param = new TestParameter(name, value);
     }

     public void addClass(String name) {
         klasses.addClasses(name);
     }
}

class TestParameter{
	@JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String value;

    public TestParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

class TestClasses{
    @JacksonXmlProperty(localName = "class")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TestClass> testClasseList;
    
    public TestClasses(){
        this.testClasseList = new ArrayList<TestClass>();
    }
    
    public void addClasses(String name) {
        this.testClasseList.add(new TestClass(name));
    }
}
class TestClass{
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    TestClass(String testClassName) {
        this.name = testClassName;
    }
}