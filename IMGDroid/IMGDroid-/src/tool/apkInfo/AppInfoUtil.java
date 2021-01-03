package tool.apkInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.manifest.ProcessManifest;

import soot.Body;
import soot.G;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.options.Options;

/**
 * 统计app相关信息：代码行数，方法数， 类数
 * @author dell
 *
 */
public class AppInfoUtil {
	
	static String apkFileLocation;
	static String androidPlatformLocation;
	static String apkName;
	static String filePath;
	
	static Map<String, Integer> stmtNUmsMap = new HashMap<>();
	static Map<String, Integer> classNumsMap = new HashMap<>();
	static Map<String, Integer> methodNumsMap = new HashMap<>();
	static Map<String, Integer> actNumsMap = new HashMap<>();
	
	public static void main(String[] args) {
		apkFileLocation = "f://a.apk"; //apk路径
		androidPlatformLocation = "E:\\android\\sdk\\platforms";
		apkName = "a"; //apk名字
		filePath = "f://"; //结果保存路径
		initSoot();
		ProcessManifest processMan = new ProcessManifest();
		processMan.loadManifestFile(apkFileLocation);
    	countAppInfo(apkName, processMan);
    	saveResult(filePath, false);
	}
	
	//apkName:apk名字
	//processMainfest:解析清单文件
	public static void countAppInfo(String apkName, ProcessManifest processMainfest) {
		System.out.println("record app stmtNums, methodNums, classNums info...");
		String packageName = processMainfest.getPackageName();
		//有些app清单文件中解析出来的包名不对
		if(apkName.equals("gitrrgbkinolog_12"))
			packageName = "git.rrigby.kinolog";
		if(apkName.equals("orgmaterialosicons_10"))
			packageName = "com.afollestad";
		if(apkName.equals("AnimeMusic_v292_apkpurecom"))
			packageName = "com.ductb.animemusic";
		if(apkName.equals("CBCNews_v422_apkpurecom"))
			packageName = "ca.cbc.android";
		if(apkName.equals("WallpapersforiPhoneX_v13_apkpurecom"))
			packageName = "com.yarolegovich.slidingrootnav";
		
		int stmtNums = 0, methodNums = 0, classNums = 0, actNums = 0;
		for(SootClass sootClass : Scene.v().getClasses()) {
			if(!sootClass.getName().startsWith(packageName))
				continue;
			classNums++;
			List<SootMethod> sootMethods = sootClass.getMethods();
			methodNums += sootMethods.size();
			for(int i = 0; i < sootMethods.size(); i++) {
				SootMethod sootMethod = sootMethods.get(i);
				if(!sootMethod.isConcrete())
					continue;
				
				Body body = sootMethod.retrieveActiveBody();
				if(body == null)
					continue;
				if(body.getUnits() != null)
					stmtNums += body.getUnits().size();
			}
		}
		
		//activity数量
		for(String act : processMainfest.activityList) {
			if(act.startsWith(packageName))
				actNums++;
		}
		stmtNUmsMap.put(apkName, stmtNums);
		classNumsMap.put(apkName, classNums);
		methodNumsMap.put(apkName, methodNums);
		actNumsMap.put(apkName, actNums);
		
		String ret = "apk:" + apkName + "\tstmtNums:" + stmtNums + "\tclassNums:" + classNums + "\tmethodNums:" + methodNums + 
				"\tact:" + actNums + "\n";
		//FileUtil.saveResult(filePath, Collections.singleton(ret), "appInfo.txt", append);
		System.out.println("record app info finished!");
	}

	public static void saveResult(String filePath, boolean append) {
		StringBuilder sb = new StringBuilder();
		int totalStmtNums = 0, totalClassNums = 0, totalMethodNums = 0, totalActNums = 0;
		int stmtNums = 0, methodNums = 0, classNums = 0, actNums = 0;
		for(String apkName : stmtNUmsMap.keySet()) {
			stmtNums = stmtNUmsMap.get(apkName);
			methodNums = methodNumsMap.get(apkName);
			classNums = classNumsMap.get(apkName);
			actNums = actNumsMap.get(apkName);
			totalStmtNums += stmtNums;
			totalClassNums += classNums;
			totalMethodNums += methodNums;
			totalActNums += actNums;
			String ret = "apk:" + apkName + "\tstmtNums:" + stmtNums + "\tclassNums:" + classNums + "\tmethodNums:" + methodNums + 
					"\tact:" + actNums + "\n";
			sb.append(ret + "\n");
		}
		int size = stmtNUmsMap.keySet().size();
		String aver = "averStmtNums: " + (double)totalStmtNums / size + "\taverClassNums: " + (double)totalClassNums / size + "\tmethodNums: " + 
				(double)totalMethodNums / size + "\taverActNums: " + (double)totalActNums / size + "\n";
		sb.append(aver);
		FileUtil.saveResult(filePath, Collections.singleton(sb.toString()), "appInfo.txt", append);
	}
	
	public static void initSoot() {
		G.reset();
		Options.v().set_allow_phantom_refs(true);
		Options.v().set_prepend_classpath(true);
		Options.v().set_whole_program(true);
		Options.v().set_debug(true);
		Options.v().set_process_dir(Collections.singletonList(apkFileLocation));
		Options.v().set_no_bodies_for_excluded(true);
		Options.v().set_keep_line_number(true);
		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_output_format(Options.output_format_dex);
		Options.v().set_process_multiple_dex(true);
		Options.v().set_force_overwrite(true);
		Options.v().set_android_api_version(22);
		Options.v().set_android_jars(androidPlatformLocation);

		Scene.v().loadNecessaryClasses();
		Scene.v().loadBasicClasses();
		
		/*SetupApplication app = null;
		app = new SetupApplication(Constants.androidPlatformLocation,
				Constants.apkFileLocation());
		app.constructCallgraph();*/
		//new LibraryClassPatcher().patchLibraries();
		
		PackManager.v().runPacks();
	}
}
