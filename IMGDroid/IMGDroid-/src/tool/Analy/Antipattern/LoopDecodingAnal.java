package tool.Analy.Antipattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
//import android.analysis.BasicAnalysis;
import heros.InterproceduralCFG;
import soot.Body;
import soot.Local;
import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.infoflow.InfoflowManager;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.data.Abstraction;
import soot.jimple.infoflow.solver.cfg.IInfoflowCFG;
import soot.jimple.infoflow.solver.cfg.InfoflowCFG;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.Sources;
import soot.jimple.toolkits.callgraph.Targets;
import soot.jimple.toolkits.ide.DefaultJimpleIFDSTabulationProblem;
import soot.jimple.toolkits.ide.icfg.BiDiInterproceduralCFG;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.options.Options;
import soot.toolkits.graph.BriefBlockGraph;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.MHGDominatorsFinder;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;
//import tool.Analy.tags.MethodTag;
//import tool.Analy.util.LocalAnalysis;
//import tool.testForApks.InterMethodAnal;
import soot.toolkits.graph.MHGPostDominatorsFinder;

public  class LoopDecodingAnal {
//	public LoopDecodingAnal(BiDiInterproceduralCFG<Unit, SootMethod> icfg) {
//		super(manager.getICFG());
//		this.manager = manager;
//	}




	//protected   InterproceduralCFG<Unit, SootMethod> delegate;

//	public final static String jarPath = "D:\\android-sdk-windows\\platforms";
	static CallGraph callGraph;
	SootMethod m;
	static List<SootMethod> allMethods = new ArrayList<SootMethod>();
	public static List<SootClass> classesChain;
	public InfoflowCFG info;
	private Unit unit;
	

	
	
	  public LoopDecodingAnal(InfoflowCFG info) { 
		  this.info = info; 
	  
	  }
	 
//	public IInfoflowCFG interproceduralCFG() {
//		return (IInfoflowCFG) super.interproceduralCFG();
//	}
	

	public void loopDecoding(List<SootClass> classes) {
		for (SootClass sc : classes) {
			for (SootMethod sm : sc.getMethods()) {
				if (sm.isConcrete()) {
					if (sm.getName().equals("getView") ||sm.getName().equals("onDraw")||sm.getName().equals("getChildView")||sm.getName().equals("getGroupView") ) {
						System.out.println();
						System.out.println("mainMethod:" + sm);
						List<Unit> au =getAllUnit(sm,50);
						if(au != null)
						anaLooding(sm,au);
					}	
			}	
		}
	}
}

	
	/**
	 * get all post-dominators of an unit in the interproceduralCFG
	 * 
	 * @param sm SootMethod of the unit
	 * @param u  unit
	 * @return
	 */
	public List<Unit> getPostDominatorsOfUnit(SootMethod sm, Unit u) {
	 List<Unit> pds = new ArrayList<Unit>();
	 if(sm.retrieveActiveBody()!=null) {
		Body b = sm.retrieveActiveBody();
		// UnitGraph unitGraph = new ExceptionalUnitGraph(b);
		UnitGraph unitGraph = new BriefUnitGraph(b);
		MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
		
		List<Unit> tmp = pdFinder.getDominators(u);
		pds.addAll(tmp);
		for (int i = 0; i < tmp.size(); i++) {
			Unit unit = tmp.get(i);
			// System.out.println("pd unit:"+unit);
			if (!u.equals(unit)) {
				Stmt stmt = (Stmt) unit;
				if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {
					System.out.println("pd unit:" + unit);
					// System.out.println("callees:"+info.getCalleesOfCallAt(unit));
//					List<SootMethod> callees = new ArrayList<SootMethod>();
//					callees.addAll(info.getCalleesOfCallAt(unit));
//					for(int j=0;j<callees.size();j++) {
//						SootMethod s = callees.get(j);
//						if(!s.getName().equals("hasNext")) {
//							List<Unit> appendpds = getPostDominatorsOfFirstUnit(s,50);
//							if(appendpds!=null)
//								pds.addAll(appendpds);
//						}
//					}
					SootMethod s = stmt.getInvokeExpr().getMethod();
					List<Unit> appendpds = getPostDominatorsOfFirstUnit(s);
					if (appendpds != null)
						pds.addAll(appendpds);
				}
			}
		}
	 }
		// System.out.println("callers of sm:"+info.getCallersOf(sm));
		return pds;
	
}

	/**
	 * get post-dominators of the first unit in the sm, because they must be executed in sm.
	 * @param sm SootMethod
	 * @param count Maximum depth of the recursive method
	 * @return
	 */
	public List<Unit> getPostDominatorsOfFirstUnit(SootMethod sm){
		if(sm == null || !sm.isConcrete())
			return null;
		if(sm.getDeclaringClass().getName().startsWith("java") || sm.getDeclaringClass().getName().startsWith("android"))
			return null;
		System.out.println("SootMethod is:"+sm);
		List<Unit> pds = new ArrayList<Unit>();
		List<Unit> tmp = null;
		Body b = sm.retrieveActiveBody();
		UnitGraph unitGraph = new ExceptionalUnitGraph(b);
		MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
		Unit firstUnit = b.getUnits().getFirst();
		tmp = pdFinder.getDominators(firstUnit);
		pds.addAll(tmp);
		for(int i=0;i<tmp.size();i++) {
			Unit unit = tmp.get(i);
		//	System.out.println("pd unit:"+unit);
			Stmt stmt = (Stmt)unit;
			if(stmt.containsInvokeExpr()) {
				SootMethod s = stmt.getInvokeExpr().getMethod();
				if(s.getSignature().equals(sm.getSignature()))
					return null;
				List<Unit> appendpds = getPostDominatorsOfFirstUnit(s);
				if(appendpds!=null)
					pds.addAll(appendpds);
				
			}
		}
		return pds;
	}
	
	

	public List<Unit> getAllDominatorsOfUnit(SootMethod sm,Unit u1){
			List<Unit> doms = new ArrayList<Unit>();
			try {
	//		if(!sm.hasActiveBody()) 
	//			return null;
				
			Body b = sm.retrieveActiveBody();
		//	Unit u = b.getUnits().getFirst();
			UnitGraph unitGraph = new BriefUnitGraph(b);  //don't consider exception edges
			MHGDominatorsFinder<Unit> domFinder = new MHGDominatorsFinder<Unit>(unitGraph);
			List<Unit> tmp = domFinder.getDominators(u1);
			doms.addAll(tmp);
			for(int i=0;i<tmp.size();i++) {
				Unit unit = tmp.get(i);
				if(!u1.equals(unit)) {
					Stmt stmt = (Stmt)unit;
					if((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {					
						SootMethod s = stmt.getInvokeExpr().getMethod();
						Set<String> ignoredMethods = new HashSet<String>();
						ignoredMethods.add(s.getSignature());
						List<Unit> appendpds = getMBEUnit(s,ignoredMethods);
						if(appendpds!=null)
							doms.addAll(appendpds);
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doms;
	}

	
	
	/**
	 * Recursively get units that must be executed through getting post-dominators of the first unit in the sm
	 * @param sm SootMethod 
	 * @param ignoredMethods the signatures of methods that don't need to search.
	 * @return
	 */
	public List<Unit> getMBEUnit(SootMethod sm,Set<String> ignoredMethods){
		if(sm == null || !sm.isConcrete())
			return null;
		//if(sm.getDeclaringClass().getName().startsWith("java")   //usually don't need units in these classes
			//	|| sm.getDeclaringClass().getName().startsWith("android"))
		//	return null;

		List<Unit> pds = new ArrayList<Unit>();
		List<Unit> tmp = null;
		Body b = sm.retrieveActiveBody();
		UnitGraph unitGraph = new BriefUnitGraph(b);  //don't consider exception edges
		MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
		unitGraph = null;
		Unit firstUnit = b.getUnits().getFirst();
		tmp = pdFinder.getDominators(firstUnit);
		pds.addAll(tmp);
		for(int i=0;i<tmp.size();i++) {
			Unit unit = tmp.get(i);
			Stmt stmt = (Stmt)unit;
			if((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {
				SootMethod s = stmt.getInvokeExpr().getMethod();
				if(ignoredMethods.contains(s.getSignature()))
					return null;
				ignoredMethods.add(s.getSignature());
				List<Unit> appendpds = getMBEUnit(s,ignoredMethods);
				if(appendpds!=null)
					pds.addAll(appendpds);
				
			}
		}
		return pds;
	}

	public void  anaLooding(SootMethod sm,List<Unit> fu) {
		 JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG();
 		 info = new InfoflowCFG(icfg);
		Iterator<Unit> it = fu.iterator();
		try {
		while (it.hasNext()) {
			Unit u1 = it.next();
			Stmt stmt = (Stmt) u1;
			
			if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {				
				//SootMethod s = stmt.getInvokeExpr().getMethod()
				//System.out.println(u1);
				isLoadingPic(u1,sm);
				
			//	System.out.println("succs:" + u1);				


		//return pds;
	}
		}
		}catch(Exception e) {
			System.out.println("exception:"+e);
		}	
	}
	public int isLoadingPic(Unit u,SootMethod sm) {
							
				Stmt stm = (Stmt) u;
				SootMethod sm1 = stm.getInvokeExpr().getMethod();
				String methodName = sm1.getName();
				String declaringClass = sm1.getDeclaringClass().getName();
				if (methodName.equals("decodeFile") || methodName.equals("decodeFileDescriptor")
						|| methodName.equals("decodeStream") || methodName.equals("decodeByteArray")) {
	      			//System.out.println("sootmethod:"+m);	
					System.out.println("loading SDK：" + u);
				    SootMethod m=info.getMethodOf(u);
					
					List<Unit> ad = getAllDominatorsOfUnit(m,u);
						
				//	((Object) ad).getUnit().toString.contains("unbindService");
					if(ad==null) 
						System.out.println("--------------Looploading----------------");
					else {
						for(int i=0;i<ad.size();i++) {
							Unit ui =ad.get(i);
							if (!ui.equals(u)) {
								Stmt s = (Stmt) unit;
								if ((s instanceof InvokeStmt || s instanceof AssignStmt) && s.containsInvokeExpr()) {
								    if(s.getInvokeExpr().getMethod().getName().equals("put")&&s.getInvokeExpr().getMethod().getDeclaringClass().equals ("android.util.LruCache"))
									System.out.println("-----------no antipantern:" + unit);
									
							
						        }
					        }
				        }
				    }
					return 1;				   					
				} else {	
					if (methodName.equals("load") &&declaringClass.contains("com.bumptech.glide.RequestManager")) {					 
						System.out.println("sootmethod:"+m);	
						System.out.println("loading with Glide：" + u);
						List<Unit> ad = getAllDominatorsOfUnit( m,u);
					//	((Object) ad).getUnit().toString.contains("unbindService");
						if(ad==null) 
							System.out.println("--------------Looploading----------------");
						else {
							for(int i=0;i<ad.size();i++) {
								Unit ui =ad.get(i);
								if (!ui.equals(u)) {
									Stmt s = (Stmt) unit;
									if ((s instanceof InvokeStmt || s instanceof AssignStmt) && s.containsInvokeExpr()) {
									    if(s.getInvokeExpr().getMethod().getName().equals("skipMemoryCache")||(s.getInvokeExpr().getMethod().getName().equals ("diskCacheStrategy")&&stm.getInvokeExpr().getArg(0).toString().contains("NONE")))
										System.out.println("-----------loopdecode:" + unit);
										
								
							        }
		 				        }
					        }
					    }
						
						return 2;
					}else {
						if (methodName.equals("load") &&declaringClass.equals("com.squareup.picasso.Picasso")) {
							System.out.println("sootmethod:"+m);	
							System.out.println("loading with Picasso：" + u);
							return 3;
						}else {
							if(methodName.equals("displayImage") && declaringClass.contains("com.nostra13.universalimageloader")) {
								System.out.println("sootmethod:"+m);	
								System.out.println("loading with imageloader：" + u);
							   return 4;
							}  else return 0;
						}
							
					}
				}
				

	}								
					
	

	/**
	 * ʹ��callgraph���sootMethod���������з���
	 */
	public static List<SootMethod> getTargetsMethods(SootMethod sootMethod) {
		List<SootMethod> sMethods = new ArrayList<SootMethod>();
		Iterator<MethodOrMethodContext> targets = new Targets(callGraph.edgesOutOf(sootMethod));
		while (targets.hasNext()) {
			SootMethod m = (SootMethod) targets.next();
			sMethods.add(m);
		}
		return sMethods;
	}

	public static List<SootMethod> getSourcesMethods(SootMethod sootMethod) {
		List<SootMethod> sMethods = new ArrayList<SootMethod>();
		Iterator<MethodOrMethodContext> sources = new Targets(callGraph.edgesInto(sootMethod));
		while (sources.hasNext()) {
			SootMethod m = (SootMethod) sources.next();
			sMethods.add(m);

		}

		return sMethods;
	}

	public static Iterator<Edge> getSourceEdges(SootMethod sootMethod) {
		return callGraph.edgesInto(sootMethod);
	}

	public static void getTargetEdges(SootMethod sootMethod) {
		Iterator<Edge> iterator = callGraph.edgesOutOf(sootMethod);
		while (iterator.hasNext()) {
			Edge edge = iterator.next();
			System.out.println("edge:" + edge);
		}
	}

	public static ArrayList<SootMethod> getAllPreviousMethods(SootMethod sm) {
		ArrayList<SootMethod> preMethods = new ArrayList<SootMethod>();
		Iterator<MethodOrMethodContext> iterator = callGraph.sourceMethods();
		List<SootMethod> temp = new ArrayList<SootMethod>();
		while (iterator.hasNext()) {
			SootMethod smMethod = (SootMethod) iterator.next();
			if (!smMethod.getSignature().startsWith("<android") && !smMethod.getSignature().startsWith("<java")
					&& !smMethod.getSignature().contains("<init>"))
				temp.add(smMethod);
		}
		getAllMethos(temp);
		for (int j = 0; j < allMethods.size(); j++) {
			SootMethod smMethod = allMethods.get(j);
			if (smMethod.toString().contains(sm.toString())) {

				if (!preMethods.contains(smMethod)) {
					preMethods.add(smMethod);
				}

				for (int i = 0; i < preMethods.size(); i++) {
					Iterator<MethodOrMethodContext> sources = new Sources(callGraph.edgesInto(preMethods.get(i)));
					// System.out.println("source:"+sources);//Iterator it = callGraph.edgesInto(m);
					while (sources.hasNext()) {
						SootMethod sourceMethod = (SootMethod) sources.next();
						System.out.println("sootMethod:" + sourceMethod);
						if (sourceMethod.getSignature().startsWith("<android"))
							continue;
						if (!preMethods.contains(sourceMethod) && !sourceMethod.getName().equals("dummyMainMethod")) {
							preMethods.add(sourceMethod);
						}
					}
				}

				break;
			}
		}

		return preMethods;
	}



	

	public static void getAllMethos(List<SootMethod> list1) {
		allMethods.addAll(list1);
		List<SootMethod> temp = new ArrayList<SootMethod>();
		for (int i = 0; i < list1.size(); i++) {
			Iterator<MethodOrMethodContext> iterator = new Targets(callGraph.edgesOutOf(list1.get(i)));
			while (iterator.hasNext()) {
				SootMethod sMethod = (SootMethod) iterator.next();
				if (!sMethod.getSignature().startsWith("<android") && !sMethod.getSignature().startsWith("<java")
						&& !sMethod.getSignature().contains("<init>"))
					temp.add(sMethod);

			}
		}
		try {
			if (!temp.isEmpty()) {
				getAllMethos(temp);
			}
		} catch (Exception e) {
		}
		
	

	}
	
	public List<Unit> getAllUnit(SootMethod sm,int count ){
		//JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG();
		//info = new InfoflowCFG(icfg);
	  if(count<=0) {
		  return null;
	  }	 
	    count--;
		if (sm == null || !sm.isConcrete())
			return null;
		if (sm.getDeclaringClass().getName().startsWith("java")
			|| sm.getDeclaringClass().getName().startsWith("android"))
			return null;
		
		List<Unit> fu = new ArrayList<Unit>();
//		List<Unit> tmp = null;
     	Body b = sm.retrieveActiveBody();
     	Chain<Unit> u = b.getUnits();
        fu.addAll(u);     
//		UnitGraph unitGraph = new BriefUnitGraph(b);
		Iterator<Unit> it = u.iterator();
		while (it.hasNext()) {
			Unit u1 = it.next();
			Stmt stmt = (Stmt) u1;
			
			if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {
				if (stmt.getInvokeExpr().getMethod().getDeclaringClass().getName().startsWith("java")|| stmt.getInvokeExpr().getMethod().getDeclaringClass().getName().startsWith("android"))					
					continue;
				SootMethod s = stmt.getInvokeExpr().getMethod();			
				if (s.getSignature().equals(sm.getSignature()))
					return null;
				// int x = isLoadingPic(u1,sm);
				
					  List<Unit> appendfu = getAllUnit(s,count);
					  if(appendfu!=null)
					  fu.addAll(appendfu);
				
//					if(x==1) {                                  //判断加载图片方式
//						System.out.println("-------------------111111111------------------");
//						Iterator<Edge> edges = callGraph.edgesInto(s);
//						while(edges.hasNext()) {
//							Edge e = edges.next();
//							
//							SootMethod in =e.getSrc().method();
//							System.out.println("in:"+ in);
//							
//							
//						List<Unit> ad = getAllDominatorsOfUnit(in,u1);
//							
//					//	((Object) ad).getUnit().toString.contains("unbindService");
//						if(ad==null) 
//							System.out.println("--------------Looploading----------------");
//						else {
//							for(int i=0;i<ad.size();i++) {
//								Unit ui =ad.get(i);
//								if (!ui.equals(u1)) {
//									Stmt stm = (Stmt) unit;
//									if ((stm instanceof InvokeStmt || stm instanceof AssignStmt) && stm.containsInvokeExpr()) {
//									    if(stm.getInvokeExpr().getMethod().getName().equals("put")&&stm.getInvokeExpr().getMethod().getDeclaringClass().equals ("android.util.LruCache"))
//										System.out.println("-----------no antipantern:" + unit);
//										
//								
//							        }
//						        }
//					        }
//					    }
//						break;
//						}	
//					}else {if(x==2) { 
//						System.out.println("-------------------22222222------------------");
//						List<Unit> ad = getAllDominatorsOfUnit( sm,u1);
//					//	((Object) ad).getUnit().toString.contains("unbindService");
//						if(ad==null) 
//							System.out.println("--------------Looploading----------------");
//						else {
//							for(int i=0;i<ad.size();i++) {
//								Unit ui =ad.get(i);
//								if (!ui.equals(u1)) {
//									Stmt stm = (Stmt) unit;
//									if ((stm instanceof InvokeStmt || stm instanceof AssignStmt) && stm.containsInvokeExpr()) {
//									    if(stm.getInvokeExpr().getMethod().getName().equals("skipMemoryCache")||(stm.getInvokeExpr().getMethod().getName().equals ("diskCacheStrategy")&&stm.getInvokeExpr().getArg(0).toString().contains("NONE")))
//										System.out.println("-----------loopdecode:" + unit);
//										
//								
//							        }
//						        }
//					        }
//					    }
//						
//						
//						
//					}else {if(x==3) {
//						System.out.println("-------------------33333333------------------");
//						List<Unit> ad = getAllDominatorsOfUnit( sm,u1);
//					//	((Object) ad).getUnit().toString.contains("unbindService");
//						if(ad==null) 
//							System.out.println("--------------Looploading----------------");
//						else {
//							for(int i=0;i<ad.size();i++) {
//								Unit ui =ad.get(i);
//								if (!ui.equals(u1)) {
//									Stmt stm = (Stmt) unit;
//									if ((stm instanceof InvokeStmt || stm instanceof AssignStmt) && stm.containsInvokeExpr()) {
//									    if((stm.getInvokeExpr().getMethod().getName().equals("memoryPolicy")&&stm.getInvokeExpr().getArg(0).toString().contains("NO_CACHE"))
//									    		||(stm.getInvokeExpr().getMethod().getName().equals ("networkPolicy")&&stm.getInvokeExpr().getArg(0).toString().contains("NO_CACHE")))
//										System.out.println("-----------loopdecode:" + unit);
//										
//								
//							        }
//						        }
//					        }
//					    }
//						
//					}else {
//						if(x==4) {
//							System.out.println("-------------------4444444------------------");
//							System.out.println("u1:"+ u1);
//							List<Unit> pres = info.getPredsOf(u1);
//							if(pres.isEmpty()) {
//								System.out.println("null");
//							}
//							for(Unit pre:pres) {
//								System.out.println("pre:"+ pre);
//							}
//						    //Stmt st = (Stmt)u1;
//						  //  SootMethod mo = st.getInvokeExpr().getMethod();
////						    Iterator<Edge> edges = callGraph.edgesInto(s);
////							while(edges.hasNext()) {
////								Edge e = edges.next();
////								
////								SootMethod in =e.getSrc().method();
////								System.out.println("in:"+ in);
////						  
////						       Body b= in.retrieveActiveBody();
////						       UnitGraph unitGraph = new BriefUnitGraph(b); 
////						    
////							
////							   MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
////							  MHGDominatorsFinder<Unit>  df = new  MHGDominatorsFinder(unitGraph);
////							  //Stmt stm = (Stmt) unit;
////							    Value value = null;
////						        for(Value v :  stmt.getInvokeExpr().getArgs()) {
////						        	   if(v.getType().toString().contains("DisplayImageOptions")){
////							    		   System.out.println("-------------DisplayImageOptions---------");
////							    		   value = v;
////							    	   	   if(value != null) {
////							    	   		List<Unit> t = df.getDominators(u1);
////							    			for(int i=0;i<t.size();i++) {
////							    				Unit d = t.get(i);
////							    			//	System.out.println("pd unit:"+unit);
////							    				Stmt tt = (Stmt)d;
////							    				List<Unit> pd = new ArrayList<Unit>();
////							    				if(!(stmt instanceof InvokeStmt)&& stmt instanceof AssignStmt) {	
////							    					if (getLeftOP(d) != null && getLeftOP(d).toString().equals(value.toString())) {
////							    						List<Unit> op = pdFinder.getDominators(u1);
////							    						for(int j =0;j<op.size();j++) {
////							    							Unit n = op.get(j);		    			    			
////							    			    			Stmt m = (Stmt)n;
////							    							if((m instanceof InvokeStmt || m instanceof AssignStmt)
////																	&& m.containsInvokeExpr()) {
////																if((m.getInvokeExpr().getMethod().equals("cacheOnDisk")&&m.getInvokeExpr().getArg(0).equals(1))||(m.getInvokeExpr().getMethod().equals("cacheInMemory")&&m.getInvokeExpr().getArg(0).equals(1))){
////																	System.out.println("-------no loopdecode-------");
////																
////																		
////																}else System.out.println("------- loopdecode-------");
////																
////															}
////							    							
////							    							
////							    						}
////							    					}
////							    												
////							    				}
////							    			}
////							    	   	   }
////							       
////							       
////						             }
////						    	   
////						       }
////						
////							
////							
////						}
//					}		
//					 
//				}
						
					}
						
					}
					
					
					
					
			
				
			      
			
			
		return fu ;
		
		
	
	  
	} 
}
