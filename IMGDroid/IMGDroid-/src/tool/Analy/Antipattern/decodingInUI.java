 package tool.Analy.Antipattern;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import soot.jimple.toolkits.callgraph.Targets;
import soot.toolkits.graph.BriefUnitGraph;

public class decodingInUI {
	  StringBuffer outter =new StringBuffer();
	  static CallGraph cg;
	  public static List<SootClass> classesChain;
	  ArrayList<String> a= new ArrayList<String>();
	  public int b;
	  
	  
	public decodingInUI(){
		//this.cg = cg;
	//	classesChain = classes;
		
	}
	
	public int isDecodingInUI(List<SootClass> classes) {
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
								
								if(methodName.equals("decodeResource")
										|| methodName.equals("decodeFile")
										|| methodName.equals("decodeFileDescriptor")
										|| methodName.equals("decodeStream")
										|| methodName.equals("decodeByteArray")
										|| methodName.equals("decodeRegion")
										|| methodName.equals("creatFromPath")
										|| methodName.equals("creatFromStream")
										||methodName.equals("setImageURI")
										|| methodName.equals("creatFromPath")
										|| methodName.equals("creatFromStream")) {
									
									if(sm.getDeclaringClass().toString().contains("com.squareup.picasso")||sm.getDeclaringClass().toString().contains("com.bumptech.glide")||sm.getDeclaringClass().toString().contains("com.nostra13.universalimageloader")) {
										break;
									}
									if(sm.getName().contains("doInBackground")) {
								      //  System.out.println("----------------doinbackground-----------");
								      //  System.out.println(sm + "  -->  " + sd);
								        a.add("in"+sm);
								        return 0;
								      
								    	}else {
								    	//	"java.lang.Thread"
								    		if(sm.getName().equals("run")) {
								    	       // System.out.println("----------------doinbackground-----------");
								    	      //  System.out.println(sm + "  -->  " + sd);
								    	        a.add("in"+sm);
								    	        return 0;
								    	    	}else {
								    	    		if(sm.getName().contains("onHandleIntent")&&sm.getDeclaringClass().toString().contains("IntentService")) {
								    	    	       // System.out.println("----------------doinbackground-----------");
								    	    	      //  System.out.println(sm + "  -->  " + sd);
								    	    	        a.add("in"+sm);
								    	    	        return 0;
								    	    	    	}else {
								//	System.out.println("sm:"+sm.toString());
								//	System.out.println("********unit of decoding:*********"+ unit);
									//Stmt stmt = (Stmt) unit;
									isDeUI(sm,cg,5);
									if(a.isEmpty()) {
										outter.append("loading  with androidSDK:");
										outter.append("sootmethod:"+sm);
										outter.append("unit:" + unit);
										outter.append("--------------------decoding in ui -------------");
										outter.append("\n");
										
										b++;
										
									}
								//	getPreInvokeOfUnit(body,unit,methodName);
									System.out.println("      ");
																		
									//	String sourceMethod =e.getSrc().method().getName();
								    	    	    	}
								    	    	    	}   	  	
									
								}
							 }

								if(methodName.equals("setImageURI")) {
									//System.out.println("unit:"+unit);
									if(sm.method().getName().equals("setImageURI")) {
										isDeUI(sm,cg,5);
									}else {
									if(sm.getName().contains("doInBackground")) {
								      //  System.out.println("----------------doinbackground-----------");
								      //  System.out.println(sm + "  -->  " + sd);
								        a.add("in"+sm);
								        return 0;
								      
								    	}else {
								    	//	"java.lang.Thread"
								    		if(sm.getName().equals("run")) {
								    	       // System.out.println("----------------doinbackground-----------");
								    	      //  System.out.println(sm + "  -->  " + sd);
								    	        a.add("in"+sm);
								    	        return 0;
								    	    	}else {
								    	    		if(sm.getName().contains("onHandleIntent")&&sm.getDeclaringClass().toString().contains("IntentService")) {
								    	    	       // System.out.println("----------------doinbackground-----------");
								    	    	      //  System.out.println(sm + "  -->  " + sd);
								    	    	        a.add("in"+sm);
								    	    	        return 0;
								    	    	    	}else {
								//	System.out.println("sm:"+sm.toString());
								//	System.out.println("********unit of decoding:*********"+ unit);
									//Stmt stmt = (Stmt) unit;
									isDeUI(sm,cg,5);
									if(a.isEmpty()) {
										outter.append("loading  with androidSDK:");
										outter.append("sootmethod:"+sm);
										outter.append("unit��" + unit);
										outter.append("--------------------decoding in ui -------------");
										outter.append("\n");
										
										b++;
										
									}
								//	getPreInvokeOfUnit(body,unit,methodName);
									System.out.println("      ");
																		
									//	String sourceMethod =e.getSrc().method().getName();
								    	    	    	}
								    	    	    	}   	  	
									
								}
							  }		
							}
		                    	   if(methodName.equals("get") && declaringClass.contains("com.squareup.picasso")){
		                    		   if( sm.toString().contains("com.squareup.picasso")) break;
		                    		   if(sm.getName().contains("doInBackground")) {
//									        System.out.println("----------------doinbackground-----------");
//									        System.out.println(sm + "  -->  " + sd);
									        a.add("in"+sm);
									        return 0;
									      
									    	}else {
									    	//	"java.lang.Thread"
									    		if(sm.getName().equals("run")) {
//									    	        System.out.println("----------------doinbackground-----------");
//									    	        System.out.println(sm + "  -->  " + sd);
									    	        a.add("in"+sm);
									    	        return 0;
									    	    	}else {
									    	    		if(sm.getName().contains("onHandleIntent")&&sm.getDeclaringClass().toString().contains("IntentService")) {
//									    	    	        System.out.println("----------------doinbackground-----------");
//									    	    	        System.out.println(sm + "  -->  " + sd);
									    	    	        a.add("in"+sm);
									    	    	        return 0;
									    	    	    	}else {
//										System.out.println("sm:"+sm.toString());
//										System.out.println("********unit of decoding:*********"+ unit);
										
										isDeUI(sm,cg,15);
										if(a.isEmpty()) {
											b++;
											outter.append("loading  with Picasso:");
											outter.append("sootmethod:"+sm);
											outter.append("unit:" + unit);
											outter.append("--------------------decoding in ui -------------");
											outter.append("\n");
											
										}
									
										System.out.println("      ");
																			
										
									    	 	}
									    	}   	  	
										
									}
		                    		 //  System.out.println("sm:"+sm);
		                    		 //  System.out.println("********unit of decoding:*********"+ unit);
		                    		   
								      //  isDeUI(sm,cg,5);
			                     }
		                     					    						    								  																				
						  }
	
							
		 }
					}
					}
			}
			catch(Exception e) {
				System.out.println("Exception" + e);
				
			}
		}try {
			FileWriter fw = new FileWriter("./Uout.txt", true);
			fw.write(outter.toString());
		fw.close();
	} catch (IOException e) {
		e.printStackTrace();		
		}
		return b;

	}
	

private List<String> getPreInvokeOfUnit(Body body, Unit unit, String methodName) {
	BriefUnitGraph graph = new BriefUnitGraph(body);
	List<Unit> units = new ArrayList<>();
	List<String> list = new ArrayList<>();
//	if(methodName.equals("decodeResource")
//			|| methodName.equals("decodeFile")
//			|| methodName.equals("decodeFileDescriptor")
//			|| methodName.equals("decodeStream")
//			|| methodName.equals("decodeByteArray")
//			|| methodName.equals("decodeRegion")
//			|| methodName.equals("creatFromPath")
//			|| methodName.equals("creatFromStream")) {
//		System.out.println("********unit of decoding:*********"+ unit);
		Stmt stmt = (Stmt) unit;
		Iterator<Edge> edges = cg.edgesInto(stmt.getInvokeExpr().getMethod());
		try {
			while(edges.hasNext()) {
				Edge e = edges.next();
				//MethodOrMethodContext s = e.getSrc();
				String sourceMethod =e.getSrc().method().getName(); 
				System.out.println("source:"+ sourceMethod);
				list.add(sourceMethod);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return list;
	


	//return null;

}
   
@SuppressWarnings("unlikely-arg-type")
public void isDeUI(SootMethod sd,CallGraph cg,int c) {
	 
 if(c<=0) {
	return;
 }	 
 try {
	c--;//System.out.println("c:"+c);
	Iterator<Edge> edges = cg.edgesInto(sd);
//	while(edges.hasNext()==false) {
//		System.out.println("*************caller is null***********");
//	}


	while(edges.hasNext()) {
		Edge e = edges.next();
		//System.out.println("----------------------");
		
		SootMethod in =e.getSrc().method();
		//System.out.println("in:"+ e.getSrc().method());
		//list.add(sourceMethod);
	
	
  // Iterator<MethodOrMethodContext> inTargets = new Targets(cg.edgesInto(sd));
 //  if(inTargets != null) {
	
	 //SootMethod in = (SootMethod) inTargets.next();
     if (in == null) {
            System.out.println("in is null");
     }
 	   //  outter.append("source:"+in.toString()+"\n");
    	if(in.getName().contains("doInBackground")) {
//        System.out.println("----------------doinbackground-----------");
//        System.out.println(in + "  -->  " + sd);
        a.add("in"+in);
        return ;
      
    	}else {
    	//	"java.lang.Thread"
    		if(in.getName().equals("run")) {
//    	        System.out.println("----------------doinbackground-----------");
//    	        System.out.println(in + "  -->  " + sd);
    	        a.add("in"+in);
    	        return ;
    	    	}else {
    	    		if(in.getName().contains("onHandleIntent")&&in.getDeclaringClass().toString().contains("IntentService")) {
//    	    	        System.out.println("----------------doinbackground-----------");
//    	    	        System.out.println(in + "  -->  " + sd);
    	    	        a.add("in"+in);
    	    	        return ;
    	    	    	}
    	    		else {
    	    	    		//System.out.println("----------no innewthread------------");
    	    	    		//System.out.println("\n");
//    	    	    		System.out.println("--------recursion------------");
    	    	    	    isDeUI(in,cg,c); 
    	    	    	}  
    	    	}
    		
    	} 
    		
    
    	
//  }
	}
	//else System.out.println("-------null------");
   
}catch(Exception e) {
	 System.out.println("exception"+ e);
}
 

// else System.out.println("next"+"\n");
//   return;
}

}
