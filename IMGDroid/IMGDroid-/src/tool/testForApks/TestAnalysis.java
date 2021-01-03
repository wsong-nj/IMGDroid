package tool.testForApks;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//import function.demo1;
import function.repeatLoading;
import soot.Body;
import soot.G;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.Stmt;
import soot.jimple.infoflow.solver.cfg.InfoflowCFG;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.options.Options;
import soot.util.Chain;
import tool.Analy.Antipattern.LoopDecode;
import tool.Analy.Antipattern.LoopDecodingAnal;
//import tool.Analy.Antipattern.LoopDecodingAnal;
import tool.Analy.Antipattern.analysisIntent;
//import tool.Analy.Antipattern.anySD;
import tool.Analy.Antipattern.decodingInUI;
//import tool.Analy.Antipattern.exloopdecoding;
//import tool.Analy.Antipattern.reachableAnalysis;
import tool.Analy.Antipattern.withoutResizing;
import tool.Analy.Metod.InterMethodAnaly;
import tool.Analy.Metod.cgReachability;

public class TestAnalysis {
	static CallGraph cg;
	static InfoflowCFG info;
	static List<SootClass> classes;
	public int w;
	public int l;
	public int r;
	public int a;
	public int u;

	public TestAnalysis(InfoflowCFG info, CallGraph cg) {
		this.info = info;
		this.cg = cg;

	}

	// public static void main(String[] args) {
	public void test() throws IOException {
		// String androidPlatform = "E:\\android SDK\\android-sdk-windows\\platforms";
		// String apk = "C:\\Users\\11142\\Desktop\\APKs\\QKSMS(jb51.net).apk";
		// String[] param = {"-android-jars", androidPlatform, "-process-dir", apk};
		// initSoot(param);
		classes = resolveAllClasses(Scene.v().getClasses());
		// demo1 d= new demo1();
		// d.isSD(classes);
		// exloopdecoding el =new exloopdecoding(info);
		// el.exlood(classes);

		// ystem.out.println("");Detecting Local Image Decoding without Permission

		
		  System.out.println("-------Detecting Image Decoding Without Resizing -------"
		  ); withoutResizing re =new withoutResizing(); w=re.isResizing(classes);
		  System.out.println("Detecting Image Passing by Intent"); 
		  
		  analysisIntent ai =
		  new analysisIntent(cg,classes); a=ai.anaIntent(classes);
		  
		  System.out.println("Detecting Repeated Decoding Without Caching");
		  repeatLoading rp = new repeatLoading(); r=rp.loopDecoding(classes);
		  
		  
		  
		  
		  System.out.println("Detecting Image Decoding in UI Thread"); decodingInUI ui
		  = new decodingInUI(); u=ui.isDecodingInUI(classes);
		 

		//cgReachability cr = new cgReachability();
		//cr.findRoad(classes);

		// anaP a =new anaP();
		// a.pd(classes);
		// anySD sd= new anySD(cg,classes);
		// sd.isSD(classes);
		// test t =new test();
		// t.pd(classes);

		// LoopDecodingAnal ld = new LoopDecodingAnal(info);
		// ld.loopDecoding(classes);
		// reachableAnalysis ra= new reachableAnalysis();
	}

	public static void initSoot(String[] param) {
		/*
		 * Params args[0,1]: APK file location args[2,3]: Path of Android platform
		 */
		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_output_format(Options.output_format_jimple);
		Options.v().set_process_multiple_dex(true);
		Options.v().set_output_dir("JimpleOutput");
		Options.v().set_keep_line_number(true);
		Options.v().set_prepend_classpath(true);
		Options.v().set_allow_phantom_refs(true);
		Options.v().set_android_jars(param[1]);
		Options.v().set_process_dir(Collections.singletonList(param[3]));
		Options.v().set_whole_program(true);
		Options.v().set_force_overwrite(true);
		Scene.v().loadNecessaryClasses(); // Load necessary classes
		CHATransformer.v().transform(); // Call graph
		cg = Scene.v().getCallGraph();
		JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG(true, true);
		info = new InfoflowCFG(icfg);

		System.out.println("callgraph size:" + cg.size());

		Scene.v().addBasicClass("java.io.BufferedReader", SootClass.HIERARCHY);
		Scene.v().addBasicClass("java.lang.StringBuilder", SootClass.BODIES);
		Scene.v().addBasicClass("java.util.HashSet", SootClass.BODIES);
		Scene.v().addBasicClass("android.content.Intent", SootClass.BODIES);
		Scene.v().addBasicClass("java.io.PrintStream", SootClass.SIGNATURES);
		Scene.v().addBasicClass("java.lang.System", SootClass.SIGNATURES);
		Scene.v().addBasicClass("com.app.test.CallBack", SootClass.BODIES);
		Scene.v().addBasicClass("java.io.Serializable", SootClass.SIGNATURES);
		Scene.v().addBasicClass("java.io.Serializable", SootClass.BODIES);
		Scene.v().addBasicClass("android.graphics.PointF", SootClass.SIGNATURES);
		Scene.v().addBasicClass("android.graphics.PointF", SootClass.BODIES);
		Scene.v().addBasicClass("org.reflections.Reflections", SootClass.HIERARCHY);
		Scene.v().addBasicClass("org.reflections.scanners.Scanner", SootClass.HIERARCHY);
		Scene.v().addBasicClass("org.reflections.scanners.SubTypesScanner", SootClass.HIERARCHY);
		Scene.v().addBasicClass("java.lang.ThreadGroup", SootClass.SIGNATURES);
		Scene.v().addBasicClass("com.ironsource.mobilcore.OfferwallManager", SootClass.HIERARCHY);
		Scene.v().addBasicClass("bolts.WebViewAppLinkResolver$2", SootClass.HIERARCHY);
		Scene.v().addBasicClass("com.ironsource.mobilcore.BaseFlowBasedAdUnit", SootClass.HIERARCHY);
		Scene.v().addBasicClass("android.annotation.TargetApi", SootClass.SIGNATURES);
		Scene.v().addBasicClass("com.outfit7.engine.Recorder$VideoGenerator$CacheMgr", SootClass.HIERARCHY);
		Scene.v().addBasicClass("com.alibaba.motu.crashreporter.handler.CrashThreadMsg$", SootClass.HIERARCHY);
		Scene.v().addBasicClass("java.lang.Cloneable", SootClass.HIERARCHY);
		Scene.v().addBasicClass("org.apache.http.util.EncodingUtils", SootClass.SIGNATURES);
		Scene.v().addBasicClass("org.apache.http.protocol.HttpRequestHandlerRegistry", SootClass.SIGNATURES);
		Scene.v().addBasicClass("org.apache.commons.logging.Log", SootClass.SIGNATURES);
		Scene.v().addBasicClass("org.apache.http.params.HttpProtocolParamBean", SootClass.SIGNATURES);
		Scene.v().addBasicClass("org.apache.http.protocol.RequestExpectContinue", SootClass.SIGNATURES);
		Scene.v().loadClassAndSupport("Constants");

		// PackManager.v().runPacks();
	}

	public static List<SootClass> resolveAllClasses(Chain<SootClass> chain) {
		List<SootClass> allClasses = new ArrayList<SootClass>();
		for (SootClass s : chain) {
			if (s.isConcrete()) {
				if (!s.getName().startsWith("android") && !s.getName().startsWith("java")) {
					allClasses.add(s);
				}
			}
		}
		return allClasses;
	}

}
