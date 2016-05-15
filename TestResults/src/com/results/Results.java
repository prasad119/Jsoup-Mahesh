package com.results;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Results {

	private static boolean bFileCreated=false;
    private static String baseFileName="base.html";
    private static String resultantFileName="";
    private static Elements dom;
    public static String FunctionName="";
    public static String sTestCaseName="";
    private static Elements TestCaseName;
    private static Elements IteratorName;
    private static String Iterator;
	public static void logResult(String status){
	
		
		//check file creted or not
		if(!bFileCreated)
		createFile();
		//create or update Functionality
		createOrUpdteFunctionality();
		//create or update TestCase
		createOrUpdateTestCase(status);
	    //create or update iterator
		createOrUpdateIterator(status);
	}


	//create file
	private static void createFile()
	{
		Document doc = null;
		try {
			doc = Jsoup.parse(new File(baseFileName), "utf-8");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Elements dom = doc.children();
		BufferedWriter bw = null;
		try {
			resultantFileName="Report"+currenDate().replace(":","").replace(" ","")+".html";
			new File(resultantFileName).createNewFile();
			bw = new BufferedWriter(new FileWriter(resultantFileName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			bw.write(dom.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	     editFile();
		bFileCreated=true;
	}
	//open the file in edit mode
	private static void  editFile(){
		Document doc=null;
		try {
			doc = Jsoup.parse(new File(baseFileName), "utf-8");
		    dom=doc.children();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
	}
	//get current date
	private static String currenDate(){
		SimpleDateFormat formate=new SimpleDateFormat("DD:mm:yyyy HH:mm:ss");
		Calendar cal=Calendar.getInstance();
	    return formate.format(cal.getTime());
		
	}
	//create or check new Functionality
	private static void createOrUpdteFunctionality(){
		if(!checkFunctionalityExist()){
			int SNo=dom.select("table#mainTable tbody tr").size();
			dom.select("table#mainTable tbody").append("<tr id='"+FunctionName+"'><td>"+SNo+"</td><td>"+FunctionName+"</td><td class=\"panel\" id='"+FunctionName+"Pass' >Tp</td><td class=\"panel\" id='"+FunctionName+"Fail' >TF</td><td class=\"panel\" id='"+FunctionName+"Total' >TotalTestCase</td></tr>");
			dom.select("body").append("<div class=\"status\" align=\"center\" style=\"display:none\" name=\""+FunctionName+"Pass\"><h1><font face=\"Calibri\" color=\"grey\" align=\"center\">"
					+ ""+FunctionName+" Pass Test Summary</font></h1><table border=\"2px\" width=\"20%\" height=\"15%\"><tbody><tr><th>SNO</th><th>TestCaseName</th><th>Status</th><th>Start Time</th><th>End Time</th></tr></tbody></table></div>"
					+ "<div class=\"status\" align=\"center\" style=\"display:none\" name=\""+FunctionName+"Fail\"><h1><font face=\"Calibri\" color=\"grey\" align=\"center\">"+FunctionName+" Fail Test Summary</font></h1>"
                    +"<table border=\"2px\" width=\"20%\" height=\"15%\"><tbody><tr><th>SNO</th><th>TestCaseName</th><th>Status</th><th>Start Time</th><th>End Time</th></tr></tbody></table>"  					
							+ "</div><div class=\"status\" align=\"center\" style=\"display:none\" name=\""+FunctionName+"Total\"><h1><font face=\"Calibri\" color=\"grey\" align=\"center\">"+FunctionName+" Total Test Summary</font></h1>"
									+ "<table border=\"2px\" width=\"20%\" height=\"15%\"><tbody><tr><th>SNO</th><th>TestCaseName</th><th>Status</th><th>Start Time</th><th>End Time</th></tr></tbody></table></div>");
		}
		
	}
	//check functionality exist or not
	private static boolean checkFunctionalityExist(){
 		Elements functionality=dom.select("#"+FunctionName+"");
 		int functionality_count=functionality.size();
 		if(functionality_count>0){
 			return true;
 		}
 		return false;
	}
	//create or check TestCase
    private static void createOrUpdateTestCase(String status){
    	if(!checkTestCase()){
        int testCaseNo=dom.select("div[name='"+FunctionName+"Total']>table>tbody>tr").size();
    	dom.select("div[name='"+FunctionName+"Total']>table>tbody").append("<tr class=\"TestCase\" id='"+FunctionName+"Total"+sTestCaseName+"'><td>"+testCaseNo+"</td><td>"+sTestCaseName+"</td><td>"+status+"</td><td>"+currenDate()+"</td><td>"+currenDate()+"</td></tr>");
    	int tc_Status_count=dom.select("div[name='"+FunctionName+""+status+"']>table>tbody>tr").size();
    	dom.select("div[name='"+FunctionName+""+status+"']>table>tbody").append("<tr class=\"TestCase\" id='"+FunctionName+"status"+sTestCaseName+"'><td>"+tc_Status_count+"</td><td>"+sTestCaseName+"</td><td>"+status+"</td><td>"+currenDate()+"</td><td>"+currenDate()+"</td></tr>");
    	}
    }
    //check testCase exist or not
    private static boolean checkTestCase(){
    		TestCaseName=dom.select("div[name='"+FunctionName+"Total']>table>tbody>tr[id='"+FunctionName+"Total"+sTestCaseName+"']");	    	
    	int TestCase_count=TestCaseName.size();
    	System.out.println(TestCase_count);
 		if(TestCase_count>0){
 			return true;
 		}
 		return false;
    }
    //create or update Iterator
    private static void createOrUpdateIterator(String status){
    	if(!checkIterator()){
            int testCaseNo=dom.select("div[name='"+FunctionName+"Total']>table>tbody>tr").size();
        	dom.select("div[name='"+FunctionName+"Total']>table>tbody").append("<tr style=\"display:none\" name='"+FunctionName+"Total"+sTestCaseName+"' class=\"itr\" id='"+FunctionName+"Total"+sTestCaseName+Iterator+"'><td colspan=\"5\" align=\"center\" ><div>iterator:"+Iterator+"</div>"
        			+ "<table style=\"display:none\" name=\""+FunctionName+"Total"+sTestCaseName+Iterator+"\"  border=\"1.5px\" width=\"15%\" height=\"15%\">"
        					+ "<tr><th>SNO</th><th>TestStep</th><th>Description</th><th>Status</th><th>TimeStamp</th></tr>");
        	int tc_Status_count=dom.select("div[name='"+FunctionName+""+status+"']>table>tbody>tr").size();
        	dom.select("div[name='"+FunctionName+""+status+"']>table>tbody").append("<tr style=\"display:none\" name='"+FunctionName+status+sTestCaseName+"' class=\"itr\" id='"+FunctionName+status+sTestCaseName+Iterator+"'><td colspan=\"5\" align=\"center\" ><div>iterator:"+Iterator+"</div>"
        			+ "<table style=\"display:none\" name=\""+FunctionName+"Total"+sTestCaseName+Iterator+"\"  border=\"1.5px\" width=\"15%\" height=\"15%\">"
					+ "<tr><th>SNO</th><th>TestStep</th><th>Description</th><th>Status</th><th>TimeStamp</th></tr>");
        	}
        	
    		BufferedWriter bw = null;
    		try {
    			bw = new BufferedWriter(new FileWriter(resultantFileName));
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    		try {
    			bw.write(dom.toString());
    			bw.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 		

    }
    private static boolean checkIterator(){
    	IteratorName=dom.select("div[name='"+FunctionName+"Total']>table>tbody>tr[id='"+FunctionName+"Total"+sTestCaseName+Iterator+"']");	    	
	int TestCase_count=TestCaseName.size();
	System.out.println(TestCase_count);
		if(TestCase_count>0){
			return true;
		}
		return false;
    }
	public static void main(String[] args) throws Exception {

		FunctionName="Role1";
		sTestCaseName="tc_001_login";
		Iterator="0";
		logResult("Fail");
		FunctionName="Role1";
		
		sTestCaseName="tc_001_login";
		Iterator="1";
		logResult("Pass");
		FunctionName="Role1";
		sTestCaseName="tc_003_login";
		logResult("Pass");
		FunctionName="Role1";
		sTestCaseName="tc_004_login";
		logResult("Pass");
		
		FunctionName="Role2";
		sTestCaseName="Role2_tc_001_login";
		logResult("Fail");
		FunctionName="Role2";
		sTestCaseName="Role2_tc_002_login";
		logResult("Pass");
		FunctionName="Role2";
		sTestCaseName="Role2_tc_003_login";
		logResult("Pass");
		FunctionName="Role2";
		sTestCaseName="Role2_tc_004_login";
		logResult("Pass");


		
//		}
		
		
		
		
	}
}
