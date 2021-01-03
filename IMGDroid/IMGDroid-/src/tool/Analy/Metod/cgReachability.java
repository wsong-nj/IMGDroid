package tool.Analy.Metod;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.util.Chain;

public class cgReachability {
	  static CallGraph cg;
	  static List<SootClass> classes;
	
	public  void findRoad(List<SootClass> classes) {
		for (SootClass sc : classes) {
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
								String methodName = sd.getName();
								String declaringClass = sd.getDeclaringClass().getName();
								cg=Scene.v().getCallGraph();
								
								if(methodName.equals("a")&&(declaringClass.equals("com.byox.drawview.views.DrawView"))
										) {
									System.out.println("find!");
									Iterator<Edge> edges = cg.edgesInto(sd);
									
									while(edges.hasNext()) {
										System.out.println("!");
										Edge e = edges.next();
										SootMethod in =e.getSrc().method();
										System.out.println("in:"+ e.getSrc().method());
										isDeUI(in,cg,5);
									}
								
								      //  System.out.println("----------------doinbackground-----------");
								      //  System.out.println(sm + "  -->  " + sd);
								      
								      
								    	
							 }

								
			                     }
		                     					    						    								  																				
						  }
	
							
		 }
					}
					
			}
	
			catch(Exception e) {
				System.out.println("Exception" + e);
				
			}
		}

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
	
	public void isDeUI(SootMethod sd,CallGraph cg,int c) {
		 
		 if(c<=0) {
			return;
		 }	 
		 try {
			c--;//System.out.println("c:"+c);
			Iterator<Edge> edges = cg.edgesInto(sd);
//			while(edges.hasNext()==false) {
//				System.out.println("*************caller is null***********");
//			}


			while(edges.hasNext()) {
				Edge e = edges.next();
				//System.out.println("----------------------");
				
				SootMethod in =e.getSrc().method();
				System.out.println("in:"+ e.getSrc().method());
				//list.add(sourceMethod);
			
			
		  // Iterator<MethodOrMethodContext> inTargets = new Targets(cg.edgesInto(sd));
		 //  if(inTargets != null) {
			
			 //SootMethod in = (SootMethod) inTargets.next();
		     if (in == null) {
		            System.out.println("in is null");
		     }
		 	  else 
		    	    	    	    isDeUI(in,cg,c); 
		    	    	    	}  

		   
		}catch(Exception e) {
			 System.out.println("exception"+ e);
		}
		 

		// else System.out.println("next"+"\n");
		//   return;
		}

}
