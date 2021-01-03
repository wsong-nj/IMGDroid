package tool.Analy.Analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import com.event.EventHandler;
//import com.event.dataAnalysis.InterAnalysis;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.UnitBox;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.toolkits.graph.BriefUnitGraph;
//import tool.Analy.Metod.InterproAnaysis;
import tool.testForApks.testForApks;
//import tool.entryForAllApks.EntryForAll;

public class AnalysisRepeatLoading extends BasicAnalysis {
	public CallGraph callGraph = null;
	StringBuffer output;
	public String apkFileLocation;

	public AnalysisRepeatLoading(String apkFileLocation, CallGraph callGraph) {
		super();
		this.callGraph = callGraph;

	}

	/*
	 * public void Analyze() {// Start analysis
	 * 
	 * System.out.
	 * println("---------------------------Analysis Begin---------------------------"
	 * ); output.append("the apk:" + apkFileLocation + "\n");
	 * isImageAPI(classesChain);
	 * 
	 * System.out.
	 * println("---------------------------Analysis End---------------------------"
	 * ); }
	 */

	public void isRepeatLoading(List<SootClass> classesChain) {
		for (SootClass sc : classesChain) {
			try {
				for (SootMethod sm : sc.getMethods()) {
					if (sm.isConcrete()) {
						Body body = sm.retrieveActiveBody();
						Iterator<Unit> iter = body.getUnits().snapshotIterator();
						while (iter.hasNext()) {
							final Unit unit = iter.next();
							Stmt stmt = (Stmt) unit;
							if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt)
									&& stmt.containsInvokeExpr()) {
								SootMethod sd = stmt.getInvokeExpr().getMethod();
								 //System.out.println(stmt);
								String methodName = stmt.getInvokeExpr().getMethod().getName();
								String declaringClass = stmt.getInvokeExpr().getMethod().getDeclaringClass().getName();
								//List<Unit> units = new ArrayList<>();
					
								if(methodName.equals("decodeResource")
								|| methodName.equals("decodeFile")
								|| methodName.equals("decodeFileDescriptor")
								|| methodName.equals("decodeStream")
								|| methodName.equals("decodeByteArray")
								|| methodName.equals("decodeRegion")
								|| methodName.equals("creatFromPath")
								|| methodName.equals("creatFromStream")) {
									 System.out.println("unit:" + unit);
									 Edge e = callGraph.findEdge(unit, sd);
									 if(e.toString().contains("doInBackground")||e.toString().contains("doInBackground"))
									 System.out.println( e.toString());
									//System.out.println(callGraph.findEdge(unit, sd)); 
								 }
								
								
									
								

						}
					}
					//System.out.println();

				}
			}
			}

			catch (Exception e) {
				System.out.println("fail");
			}

		}
	}

	public void Analyze() {
		// TODO Auto-generated method stub
		System.out.println("---------------------------Analysis Begin---------------------------");
		// output.append("the apk:" + apkFileLocation + "\n");
		isRepeatLoading(classesChain);

		System.out.println("---------------------------Analysis End---------------------------");
	}

}
