package tool.Analy.Antipattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import soot.Body;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.infoflow.solver.cfg.InfoflowCFG;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.MHGDominatorsFinder;
import soot.toolkits.graph.MHGPostDominatorsFinder;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

public class LoopDecode {
	static CallGraph callGraph;
	SootMethod m;
	static List<SootMethod> allMethods = new ArrayList<SootMethod>();
	public static List<SootClass> classesChain;
	public InfoflowCFG info;
	private Unit unit;
	 StringBuffer outter =new StringBuffer();
	public int p;
	public int g;
	public int a;
	ArrayList<String> A =new ArrayList<String>();
	public int I;
	public int l;
	
	public int loopDecoding(List<SootClass> classes) {
		for (SootClass sc : classes) {
			for (SootMethod sm : sc.getMethods()) {
				if (sm.isConcrete()) {
					if (sm.getName().equals("getView") ||sm.getName().equals("onDraw")||sm.getName().equals("getChildView")||sm.getName().equals("getGroupView") ) {
						//outter.append("\n");
						//outter.append("mainMethod:" + sm+"\n");
						//System.out.println();
						//System.out.println("mainMethod:" + sm);
						List<Unit> au =getAllUnit(sm,50);
						if(au != null)
						  l=  anaLooding(sm,au);
					}	
			}	
		}
	} return l;
}

	
	public int anaLooding(SootMethod sm,List<Unit> fu) {
		 JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG();
		 info = new InfoflowCFG(icfg);
		Iterator<Unit> it = fu.iterator();
		try {
		while (it.hasNext()) {
			Unit u1 = it.next();
			Stmt stmt = (Stmt) u1;
			
			if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {				
				//SootMethod s = stmt.getInvokeExpr().getMethod();
				if(isLoadingPic(sm,u1)==1) {
					System.out.println("-------");
					List<Unit> ad = getAllDominatorsOfUnit(sm,u1);
					
					//	((Object) ad).getUnit().toString.contains("unbindService");
						if(ad==null) {
							System.out.println("--------------Looploading----------------");
						     a++;
						} 
						else {
							for(int i=0;i<ad.size();i++) {
								Unit ui =ad.get(i);
								if (!ui.equals(u1)) {
									Stmt st = (Stmt) unit;
									if ((st instanceof InvokeStmt || st instanceof AssignStmt) && st.containsInvokeExpr()) {
									    if(st.getInvokeExpr().getMethod().getName().equals("put")&&st.getInvokeExpr().getMethod().getDeclaringClass().equals ("android.util.LruCache"))
										System.out.println("-----------no antipantern:" + unit);
										A.add("unit"+unit);
		 						
							        }
						        }
					        }if(A.isEmpty()) {
					        	a++;
					        }
					    }
//					
				}
				
				if(isLoadingPic(sm,u1)==2) {
					List<Unit> ad = getAllDominatorsOfUnit( sm,u1);
					//	((Object) ad).getUnit().toString.contains("unbindService");
						if(ad==null) 
							System.out.println("--------------Looploading----------------");
						else  
							for(int i=0;i<ad.size();i++) {
								Unit ui =ad.get(i);
								if (!ui.equals(u1)) {
									Stmt st = (Stmt) unit;
									if ((st instanceof InvokeStmt || st instanceof AssignStmt) && st.containsInvokeExpr()) {
										System.out.println("pd"+ ui);
									    if(st.getInvokeExpr().getMethod().getName().equals("skipMemoryCache")||(st.getInvokeExpr().getMethod().getName().equals ("diskCacheStrategy")&&st.getInvokeExpr().getArg(0).toString().contains("NONE")))
										System.out.println("-----------loopdecode:" + unit);
									    g++;
										
								
							        }
		 				        }
					        }
					    }
					
				}if(isLoadingPic(sm,u1)==3) {
					List<Unit> ad = getAllDominatorsOfUnit( sm,u1);
					//	((Object) ad).getUnit().toString.contains("unbindService");
						if(ad==null) 
							System.out.println("--------------Looploading----------------");
						else  
							for(int i=0;i<ad.size();i++) {
								Unit ui =ad.get(i);
								if (!ui.equals(u1)) {
									Stmt st = (Stmt) unit;
									if ((st instanceof InvokeStmt || st instanceof AssignStmt) && st.containsInvokeExpr()) {
										System.out.println("pd"+ ui);
										
										    if((st.getInvokeExpr().getMethod().getName().equals("NO_CACHE")&&st.getInvokeExpr().getMethod().getDeclaringClass().getName().contains("com.squareup.picasso.MemoryPolicy"))
										    		||(st.getInvokeExpr().getMethod().getName().equals ("networkPolicy")&&st.getInvokeExpr().getArg(0).toString().contains("NO_CACHE")))
											System.out.println("-----------loopdecode:" + unit);
										    p++;
											
									
								        
							        }
										
								
							        }
		 				        }
					        }if(isLoadingPic(sm,u1)==4) {
//								System.out.println("-------------------4444444------------------");
								System.out.println("u1:"+ u1);

//							    //Stmt st = (Stmt)u1;
//							  //  SootMethod mo = st.getInvokeExpr().getMethod();
////							    Iterator<Edge> edges = callGraph.edgesInto(s);
////								while(edges.hasNext()) {
////									Edge e = edges.next();
////									
////									SootMethod in =e.getSrc().method();
////									System.out.println("in:"+ in);
////							  
						           Body b= sm.retrieveActiveBody();
							       UnitGraph unitGraph = new BriefUnitGraph(b); 
								
								   MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
								  MHGDominatorsFinder<Unit>  df = new  MHGDominatorsFinder(unitGraph);
////							    
							
								  //Stmt stm = (Stmt) unit;
								    Value value = null;
							        for(Value v :  stmt.getInvokeExpr().getArgs()) {
							        	   if(v.getType().toString().contains("DisplayImageOptions")){
								    		   System.out.println("-------------DisplayImageOptions---------");
								    		   value = v;
								    	   	   if(value != null) {
								    	   		List<Unit> t = df.getDominators(u1);
								    			for(int i=0;i<t.size();i++) {
								    				Unit d = t.get(i);
								    			//	System.out.println("pd unit:"+unit);
								    				Stmt tt = (Stmt)d;
								    				List<Unit> pd = new ArrayList<Unit>();
								    				if(!(stmt instanceof InvokeStmt)&& stmt instanceof AssignStmt) {	
								    					if (getLeftOP(d) != null && getLeftOP(d).toString().equals(value.toString())) {
								    						List<Unit> op = pdFinder.getDominators(u1);
								    						for(int j =0;j<op.size();j++) {
								    							Unit n = op.get(j);		    			    			
								    			    			Stmt m = (Stmt)n;
								    							if((m instanceof InvokeStmt || m instanceof AssignStmt)
																		&& m.containsInvokeExpr()) {
																	if((m.getInvokeExpr().getMethod().equals("cacheInMemory")&&m.getInvokeExpr().getArg(0).toString().equals("0"))){
																		System.out.println("------- loopdecode-------");
																		I++;
																	
																			
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
					    
				
			//	System.out.println("succs:" + u1);	
				
			

		//return pds;
	
		}catch(Exception e) {
			System.out.println("exception:"+e);
		}	return I+a+g+p;
	}
		
		
		public int isLoadingPic(SootMethod m,Unit u) {
			
			Stmt stm = (Stmt) u;
			//System.out.println(u);
			SootMethod sm = stm.getInvokeExpr().getMethod();
			String methodName = sm.getName();
			String declaringClass = sm.getDeclaringClass().getName();
			if (methodName.equals("decodeFile") || methodName.equals("decodeFileDescriptor")
					|| methodName.equals("decodeStream") || methodName.equals("decodeByteArray")) {
				//System.out.println();
				System.out.println("mainMethod:" + m);
				System.out.println("loading SDK：" + u);
					
				return 1;				   					
			} else {	
				if (methodName.equals("load") &&declaringClass.contains("com.bumptech.glide.RequestManager")) {					 
					//System.out.println("sootmethod:"+m);	
					System.out.println("mainMethod:" + m);
					System.out.println("loading with Glide：" + u);
				
					
					return 2;
				}else {
					if (methodName.equals("load") &&declaringClass.equals("com.squareup.picasso.Picasso")) {
						//System.out.println("sootmethod:"+m);	
						System.out.println("mainMethod:" + m);
						System.out.println("loading with Picasso：" + u);
						return 3;
					}else {
						if(methodName.equals("displayImage") && declaringClass.contains("com.nostra13.universalimageloader")) {
							//System.out.println("sootmethod:"+m);	
							System.out.println("mainMethod:" + m);
							System.out.println("loading with imageloader：" + u);
						   return 4;
						}  else return 0;
					}
						
				}
			}
			

}								
				

		public List<Unit> getAllDominatorsOfUnit(SootMethod sm,Unit u1){
			List<Unit> doms = new ArrayList<Unit>();
			try {
			if(!sm.hasActiveBody())
				return null;
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
				 List<Unit> appendfu = getAllUnit(s,count);
				 if(appendfu!=null)
				      fu.addAll(appendfu);
			}	
		
		}	
		return fu;
	}		
  
	public Value getLeftOP(Unit unit) {
		Value leftop = null;
		Stmt stmt = (Stmt) unit;
		if (stmt instanceof AssignStmt) {
			leftop = ((AssignStmt) stmt).getLeftOp();
		} else if (stmt instanceof IdentityStmt) {
			leftop = ((IdentityStmt) stmt).getLeftOp();
		} else if (stmt instanceof InvokeStmt) {
			List<ValueBox> ValueBoxList = unit.getUseAndDefBoxes();
			if (ValueBoxList.size() > 2) {
				leftop = ValueBoxList.get(ValueBoxList.size() - 2).getValue();
			}
		}
		return leftop;
	}
}
