package tool.Result;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import tool.Analy.Antipattern.withoutPer;


   
public class testall {
	public static void main(String[] args) {
		//String androidPlatform = "D:\\android-sdk-windows\\platforms";
		//String FileDirectory = "F:\\qcs\\linshi";
		String androidPlatform = "E:\\android SDK\\android-sdk-windows\\platforms";
		String FileDirectory = "C:\\Users\\11142\\Desktop\\APKs\\testapk";
		String resultPath = "./test";
		String[] arg = { FileDirectory,androidPlatform };
		EntryForAll entryForAll = new EntryForAll(arg);
		ArrayList<String> files = entryForAll.getApkFilespath();
		try {
			entryForAll.AnalyzeAll(files); 
			withoutPer wp=new withoutPer();
//			 TestApp tp =new TestApp();
	         wp.startAnalysis(FileDirectory, androidPlatform, resultPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	} 

}
