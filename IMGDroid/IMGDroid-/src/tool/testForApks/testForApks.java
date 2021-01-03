package tool.testForApks;

import java.io.IOException;
import java.util.Collections;

import org.xmlpull.v1.XmlPullParserException;

import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.solver.cfg.InfoflowCFG;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.options.Options;
import tool.Analy.Analysis.AnalysisRepeatLoading;


public class testForApks {
	public static CallGraph callGraph;
	public static InfoflowCFG info ;
	public final static String androidPlatformLocation = "E:\\android SDK\\android-sdk-windows\\platforms";
	public final static String apkFileLocation  ="C:\\Users\\11142\\Desktop\\APKs\\Glide\\Mi-Video.apk";
	public static void main(String[] args) throws IOException, XmlPullParserException {
		String param[] = { "-android-jars", androidPlatformLocation, "-process-dir", apkFileLocation };
		initSoot(param);
		AnalysisRepeatLoading analysis = new AnalysisRepeatLoading(apkFileLocation, callGraph);
		analysis.Analyze();

	}
	public static void initSoot(String[] param) throws IOException, XmlPullParserException {
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
		Scene.v().loadNecessaryClasses();	// Load necessary classes
        CHATransformer.v().transform(); //Call graph
        callGraph=Scene.v().getCallGraph();
        JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG(true,true);
		info = new InfoflowCFG(icfg);
		System.out.println("cg size:"+callGraph.size());
        Scene.v().addBasicClass("java.io.BufferedReader",SootClass.HIERARCHY);
		Scene.v().addBasicClass("java.lang.StringBuilder",SootClass.BODIES);
		Scene.v().addBasicClass("java.util.HashSet",SootClass.BODIES);
		Scene.v().addBasicClass("android.content.Intent",SootClass.BODIES);
		Scene.v().addBasicClass("java.io.PrintStream",SootClass.SIGNATURES);
        Scene.v().addBasicClass("java.lang.System",SootClass.SIGNATURES); 
        Scene.v().addBasicClass("com.app.test.CallBack",SootClass.BODIES);		
        Scene.v().addBasicClass("java.io.Serializable",SootClass.SIGNATURES);
        Scene.v().addBasicClass("java.io.Serializable",SootClass.BODIES);
        Scene.v().addBasicClass("android.graphics.PointF",SootClass.SIGNATURES);
        Scene.v().addBasicClass("android.graphics.PointF",SootClass.BODIES);
        Scene.v().addBasicClass("org.reflections.Reflections",SootClass.HIERARCHY);
        Scene.v().addBasicClass("org.reflections.scanners.Scanner",SootClass.HIERARCHY);
        Scene.v().addBasicClass("org.reflections.scanners.SubTypesScanner",SootClass.HIERARCHY);
        Scene.v().addBasicClass("java.lang.ThreadGroup",SootClass.SIGNATURES);
        Scene.v().addBasicClass("com.ironsource.mobilcore.OfferwallManager",SootClass.HIERARCHY);
        Scene.v().addBasicClass("bolts.WebViewAppLinkResolver$2",SootClass.HIERARCHY);
        Scene.v().addBasicClass("com.ironsource.mobilcore.BaseFlowBasedAdUnit",SootClass.HIERARCHY);
        Scene.v().addBasicClass("android.annotation.TargetApi",SootClass.SIGNATURES);
        Scene.v().addBasicClass("com.outfit7.engine.Recorder$VideoGenerator$CacheMgr",SootClass.HIERARCHY);
        Scene.v().addBasicClass("com.alibaba.motu.crashreporter.handler.CrashThreadMsg$",SootClass.HIERARCHY);
        Scene.v().addBasicClass("java.lang.Cloneable",SootClass.HIERARCHY);
        Scene.v().addBasicClass("org.apache.http.util.EncodingUtils",SootClass.SIGNATURES);
        Scene.v().addBasicClass("org.apache.http.protocol.HttpRequestHandlerRegistry",SootClass.SIGNATURES);
        Scene.v().addBasicClass("org.apache.commons.logging.Log",SootClass.SIGNATURES);
        Scene.v().addBasicClass("org.apache.http.params.HttpProtocolParamBean",SootClass.SIGNATURES);
        Scene.v().addBasicClass("org.apache.http.protocol.RequestExpectContinue",SootClass.SIGNATURES);
        Scene.v().loadClassAndSupport("Constants");	
      //  PackageManager
	}
}
