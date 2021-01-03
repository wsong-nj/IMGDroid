package tool.Analy.Antipattern;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class analysisIntent {
	 CallGraph cg;
   	public int b;
	 ArrayList<String> a= new ArrayList<String>();
	 public static List<SootClass> classesChain;
	 StringBuffer outter =new StringBuffer();
	  
	  
	public  analysisIntent(CallGraph cg,List<SootClass> classes){
		this.cg = cg;
		classesChain = classes;
		
	}
	public int anaIntent(List<SootClass> classes) {
		for (SootClass sc : classes) {
			try {
				for (SootMethod sm : sc.getMethods()) {
					if (sm.isConcrete() && !sm.isConstructor()&&!sm.getName().equals("startActivity")) {
						Body body = sm.retrieveActiveBody();
						for (Unit unit : body.getUnits()) {
							Stmt stmt = (Stmt) unit;
							if (stmt instanceof InvokeStmt && stmt.containsInvokeExpr()) {
								if (stmt.getInvokeExpr().getArgCount() > 0) {
									String methodName = stmt.getInvokeExpr().getMethod().getName();
									if (methodName.contains("startActivity")) {									
										
										for (Value param : stmt.getInvokeExpr().getArgs()) {
											if (param.getType().toString().equals("android.content.Intent")) {												
												List<Unit> preUnit = getAllPreviousUnit(unit, body);
											  if(preUnit != null) {
												 for(Unit u : preUnit) {
													 Stmt stm = (Stmt) u;
													 if ((stm instanceof InvokeStmt ||  stm instanceof AssignStmt)&& stm.containsInvokeExpr()) {
														 //System.out.println("stm锛�" + stm.getInvokeExpr().getMethod());
														if (stm.getInvokeExpr().getMethod().toString().contains("putParcelableArrayList") || stm.getInvokeExpr().getMethod().toString().contains("putExtra")){
                                                          //  System.out.println("-------passing data---------");
															 List<Value> vs=  stmt.getInvokeExpr().getArgs();
															 for(Value v:vs) {
                                                            if (v.getType().toString().equals("android.graphics.Bitmap")||v.getType().toString().contains("Drawable")||v.getType().toString().contains("BitmapDrawable")) {
                                                            	     b++;
                                                            	   // outter.append("loading  with androidSDK:");
                             										outter.append("sootmethod:"+sm);
                             										outter.append("unit:" + unit);
                             										outter.append("--------------------passing bitmap by Intent-------------");
                             										outter.append("\n");
                                                            		//System.out.println("---------passing bitmap by Intent--------");
                                                                          
                                                            } 
															 } 
													     }
														}
													} 
												 //Intent浣滀负鏂规硶鐨勫弬鏁颁紶鍏�
												// System.out.println("sm:"+ sm);
												 List<Type> p = sm.getParameterTypes();
												 for(Type pa:p) {
													 if(pa.toString().equals("android.content.Intent")) {
														 Iterator<Edge> edges = cg.edgesInto(sm);
															
															while(edges.hasNext()) {
																Edge e = edges.next();																
																SootMethod in =e.getSrc().method();
																//System.out.println("sootMethod:"+ in);
																if (in.isConcrete()) {
																	Body by = sm.retrieveActiveBody();
																	Iterator<Unit> iter = by.getUnits().snapshotIterator();
																	while (iter.hasNext()) {
																		final Unit u = iter.next();
																		Stmt st = (Stmt) unit;
																		if ((st instanceof InvokeStmt || st instanceof AssignStmt)
																				&& stmt.containsInvokeExpr()) {
																			if (st.getInvokeExpr().getMethod().toString().contains("putParcelableArrayList") || st.getInvokeExpr().getMethod().toString().contains("putExtra")){
					                                                            //System.out.println("-------passing data---------");
																				 List<Value> vs=  stmt.getInvokeExpr().getArgs();
																				 for(Value v:vs) {
					                                                            if (v.getType().toString().equals("android.graphics.Bitmap")) {   
					                                                            	 b++;
					                                                            	  
					                             										outter.append("sootmethod:"+sm);
					                             										outter.append("unit:" + unit);
					                             										outter.append("--------------------passing bitmap by Intent-------------");
					                             										outter.append("\n");
					                                                                          
					                                                            }  
																				 } 
																		     }
																		}
																	}
																}	
															}		
													 }
												 }
											}
													
												}
											}
												
										}
											
									}
								}

							}
						}

					}

				}catch (Exception e) {
				}
			} anaIntent1( classes);
			try {
				FileWriter fw = new FileWriter("./Aout.txt", true);
				fw.write(outter.toString());
			fw.close();
		       } catch (IOException e) {
			        e.printStackTrace();		
			 }
			  return b;
		}
     
   
	public int anaIntent1(List<SootClass> classes) {
		for (SootClass sc : classes) {
			try {
				for (SootMethod sm : sc.getMethods()) {
					if (sm.isConcrete() && !sm.isConstructor()) {
						Body body = sm.retrieveActiveBody();
						for (Unit unit : body.getUnits()) {
							Stmt stmt = (Stmt) unit;
							if (stmt instanceof InvokeStmt && stmt.containsInvokeExpr()) {
								if (stmt.getInvokeExpr().getArgCount() > 0) {
									String methodName = stmt.getInvokeExpr().getMethod().getName();
									
														 //System.out.println("stm锛�" + stm.getInvokeExpr().getMethod());
														if (methodName.toString().contains("putParcelableArrayList") || methodName.toString().contains("putExtra")){
                                                          //  System.out.println("-------passing data---------");
															 List<Value> vs=  stmt.getInvokeExpr().getArgs();
															 for(Value v:vs) {
                                                            if (v.getType().toString().equals("android.graphics.Bitmap")) {
                                                            	     b++;
                                                            	   // outter.append("loading  with androidSDK:");
                             										outter.append("sootmethod:"+sm);
                             										outter.append("unit:" + unit);
                             										outter.append("--------------------passing bitmap by Intent-------------");
                             										outter.append("\n");
                                                            		//System.out.println("---------passing bitmap by Intent--------");
                                                            }           
                                                            }               
													     }
														}
													} 
						                         }
					                        }
									}													

				}catch (Exception e) {
				}
			}
		return b; 
		}
		
	

	
	private List<Unit> getAllPreviousUnit(Unit unit1, Body body) {
		List<Unit> units = new ArrayList<>();
		for (Unit unit : body.getUnits()) {
			units.add(unit);
			if (unit.equals(unit1)) {
				break;
			}
		}
		List<Unit> list = new ArrayList<>();
		for (int i = units.size() - 1; i >= 0; i--) {
			list.add(units.get(i));
		}
		return list;

	}
}
