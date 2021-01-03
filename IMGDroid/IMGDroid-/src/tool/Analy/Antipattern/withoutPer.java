package tool.Analy.Antipattern;


import java.io.File;
	import java.io.FilenameFilter;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.List;

	import org.xmlpull.v1.XmlPullParserException;

	import soot.jimple.infoflow.InfoflowConfiguration.AliasingAlgorithm;
	import soot.jimple.infoflow.InfoflowConfiguration.CallgraphAlgorithm;
	import soot.jimple.infoflow.InfoflowConfiguration.CodeEliminationMode;
	import soot.jimple.infoflow.InfoflowConfiguration.ImplicitFlowMode;
	import soot.jimple.infoflow.android.SetupApplication;
	import soot.jimple.infoflow.handlers.RecordTainPropagationHandler;
	import soot.jimple.infoflow.results.InfoflowResults;
	import soot.jimple.infoflow.slice.Constants;
	import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
public class withoutPer {
	

//		public static void main(String[] args) throws IOException, XmlPullParserException {
//			// TODO Auto-generated method stub
//			String androidJars = "E:\\android SDK\\android-sdk-windows\\platforms";
//			String resultPath = "E:\\workspace\\test";
//			// String apkName = "org.schabi.newpipe_770";
//			String apkDirPath = "C:\\Users\\11142\\Desktop\\APKs\\testapk";
//			// String apkFullPath = apkDirPath + File.separator + apkName + ".apk";
//			// Constants.targetPackageName = "com.ductb.animemusic";
//			startAnalysis(apkDirPath, androidJars, resultPath);
//		}

		public void startAnalysis(String apkFilePath, String androidJars, String resultPath)
				throws IOException, XmlPullParserException {

			long start = System.currentTimeMillis();

			List<String> apkFiles = new ArrayList<String>();
			File apkFile = new File(apkFilePath);
			if (apkFile.isDirectory()) {
				String[] dirFiles = apkFile.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return (name.endsWith(".apk"));
					}
				});
				for (String fileName : dirFiles)
					apkFiles.add(apkFilePath + File.separator + fileName);
			} else {
				// apk is a file so grab the extension
				String extension = apkFile.getName().substring(apkFile.getName().lastIndexOf("."));
				if (extension.equalsIgnoreCase(".apk"))
					apkFiles.add(apkFilePath);
				else {
					System.err.println("Invalid input file format: " + extension);
					return;
				}

			}

			for (final String filePath : apkFiles) {
				String apk = new File(filePath).getName();
				// **************************************************
				// Constants.apkName = apk.substring(0, apk.lastIndexOf("."));
				// Constants.sliceResultDirName = resultPath + File.separator +
				// Constants.apkName + File.separator + "slice";
				System.out.println("Analyzing file " + filePath + "...");
				// System.out.println("result dir :" + Constants.sliceResultDirName);
				// Run the analysis
				analyzeAPKFile(filePath, androidJars);
			}
			long end = System.currentTimeMillis();
			System.out.println("总计用时 : " + (end - start) / 1000.0);
		}

		public static InfoflowResults analyzeAPKFile(String fileName, String androidJars)
				throws IOException, XmlPullParserException {
			System.out.println("Loading Android.jar files from " + androidJars);
			SetupApplication setupApplication = new SetupApplication(androidJars, fileName);

			// Find the taint wrapper file
			File taintWrapperFile = new File("EasyTaintWrapperSource.txt");
			if (!taintWrapperFile.exists())
				taintWrapperFile = new File("./EasyTaintWrapperSource.txt");
			setupApplication.setTaintWrapper(new EasyTaintWrapper(taintWrapperFile));

			// Configure the analysis
			setupApplication.getConfig().setCallgraphAlgorithm(CallgraphAlgorithm.SPARK);
			setupApplication.getConfig().setMergeDexFiles(true);
			setupApplication.getConfig().setImplicitFlowMode(ImplicitFlowMode.NoImplicitFlows);
			setupApplication.getConfig().setStopAfterFirstFlow(false);
			setupApplication.getConfig().setCallgraphAlgorithm(CallgraphAlgorithm.AutomaticSelection);
			setupApplication.getConfig().setCodeEliminationMode(CodeEliminationMode.RemoveSideEffectFreeCode);
			setupApplication.getConfig().setAliasingAlgorithm(AliasingAlgorithm.FlowSensitive);
			setupApplication.getConfig().setEnableArrayTracking(true);
			setupApplication.getConfig().setEnableExceptionTracking(false);
			setupApplication.getConfig().setEnableStaticFieldTracking(false);
			setupApplication.getConfig().getAnalysisFileConfig().setAdditionalClasspath("./lib/android-support-v4.jar");
			setupApplication.getConfig().setWriteOutputFiles(true);
			setupApplication.getConfig().setOnlyICFG(false);
			setupApplication.setTaintPropagationHandler(new RecordTainPropagationHandler());

			String sourceAndSinksFile;
			if (new File("./SourcesAndSinks/" + Constants.apkName + ".txt").exists()) {
				sourceAndSinksFile = "./SourcesAndSinks/" + Constants.apkName + ".txt";
			} else
				sourceAndSinksFile = "./SourcesAndSinks/SourcesAndSinks.txt";

			System.out.println("using " + sourceAndSinksFile);
			InfoflowResults result = setupApplication.runInfoflow(sourceAndSinksFile);
			// System.out.println(setupApplication.getIcfg().allNonCallEndNodes());
			return result;
		}

	


}
