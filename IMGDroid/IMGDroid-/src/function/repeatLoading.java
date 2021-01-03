package function;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import soot.Body;
import soot.MethodOrMethodContext;
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
import soot.jimple.toolkits.callgraph.Sources;
import soot.jimple.toolkits.callgraph.Targets;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.MHGDominatorsFinder;
import soot.toolkits.graph.MHGPostDominatorsFinder;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

public class repeatLoading {

        
		static CallGraph callGraph;
		SootMethod m;
		static List<SootMethod> allMethods = new ArrayList<SootMethod>();
		public static List<SootClass> classesChain;
		public InfoflowCFG info;
		private Unit unit;
		private int num;
		public ArrayList<Object> a=new ArrayList<Object>();
		public int b;
		 StringBuffer outter =new StringBuffer();
		 ArrayList<String> e =new ArrayList<String>();



		public int loopDecoding(List<SootClass> classes) {
			for (SootClass sc : classes) {
				for (SootMethod sm : sc.getMethods()) {
					if (sm.isConcrete()) {
						if (sm.getName().equals("getView") ||sm.getName().equals("onDraw")||sm.getName().equals("getChildView")||sm.getName().equals("getGroupView") ) {
							//System.out.println();
							//System.out.println("mainMethod:" + sm);
						    getAllUnit(sm);
						   // System.out.println("b:"+ b);
//							if(au != null)
//							anaLooding(sm,au);
						}	
				}	
			}
		}  try {
			FileWriter fw = new FileWriter("./Rout.txt", true);
			fw.write(outter.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			return b;
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
						continue;
					ignoredMethods.add(s.getSignature());
					List<Unit> appendpds = getMBEUnit(s,ignoredMethods);
					if(appendpds!=null)
						pds.addAll(appendpds);
					
				}
			}
			return pds;
		}

		
		public int isLoadingPic(Unit u,SootMethod sm) {
				try {
					
					Stmt stm = (Stmt) u;
					if((stm instanceof InvokeStmt || stm instanceof AssignStmt) && stm.containsInvokeExpr()) {
					SootMethod sm1 = stm.getInvokeExpr().getMethod();
					String methodName = sm1.getName();
					String declaringClass = sm1.getDeclaringClass().getName();
					//娴ｈ法鏁ndroid native API
					if (methodName.equals("decodeFile") || methodName.equals("decodeFileDescriptor")
							|| methodName.equals("decodeStream") || methodName.equals("decodeByteArray")
							|| methodName.equals("creatFromPath")
							|| methodName.equals("creatFromStream")) {
		      			//System.out.println("sootmethod:"+m);	
						//System.out.println("loading SDK:" + u);
					   // SootMethod m=info.getMethodOf(u);
						
						List<Unit> ad = getAllDominatorsOfUnit(sm,u);
							
					//	((Object) ad).getUnit().toString.contains("unbindService");
						if(ad==null) 
							System.out.println("--------------Looploading----------------");
						else {
							for(int i=0;i<ad.size();i++) {
								Unit ui =ad.get(i);
								if (!ui.equals(u)) {
									Stmt s = (Stmt) unit;
									if ((s instanceof InvokeStmt || s instanceof AssignStmt) && s.containsInvokeExpr()) {
									    if(s.getInvokeExpr().getMethod().getName().equals("put")&&s.getInvokeExpr().getMethod().getDeclaringClass().equals ("android.util.LruCache")) {
										//System.out.println("-----------no antipantern:" + unit);
									    a.add("unit"+unit);
									    }   
										
								
							        }
						        }
					        }
							if(a.isEmpty()) {
								b++;
					    		//System.out.println("loading SDK閿涳拷" );
					    		//System.out.println("sootmethod:"+sm);	
					    		//System.out.println("unit閿涳拷" + u);
					    		//System.out.println("--------------------repeat loading--------------");
								outter.append("loading SDK:");
								outter.append("sootmethod:"+sm);
								outter.append("unit:" + u);
								outter.append("--------------------repeat loading--------------");
								outter.append("\n");
						
					        	
					        }
					    }
						return 1;				   					
					} else {
						if (methodName.equals("load") &&declaringClass.contains("com.bumptech.glide.RequestManager")) {					 
							//System.out.println("sootmethod:"+m);	
							
							List<Unit> ad = getAllDominatorsOfUnit( sm,u);
						//	((Object) ad).getUnit().toString.contains("unbindService");
							if(ad==null) 
								System.out.println("--------------Looploading----------------");
							else {
								for(int i=0;i<ad.size();i++) {
									Unit ui =ad.get(i);
									if (!ui.equals(u)) {
										Stmt s = (Stmt) unit;
										if ((s instanceof InvokeStmt || s instanceof AssignStmt) && s.containsInvokeExpr()) {
										    if(s.getInvokeExpr().getMethod().getName().equals("skipMemoryCache")||(s.getInvokeExpr().getMethod().getName().equals ("diskCacheStrategy")&&stm.getInvokeExpr().getArg(0).toString().contains("NONE"))) {
										//	System.out.println("-----------loopdecode:" + unit);
										    	b++;
//										    System.out.println("loading with Glide閿涳拷" );
//								    		System.out.println("sootmethod:"+sm);	
//								    		System.out.println("unit閿涳拷" + u);
//								    		System.out.println("--------------------repeat loading--------------");	
								    		outter.append("loading  with Glide:");
											outter.append("sootmethod:"+sm);
											outter.append("unit:" + u);
											outter.append("--------------------repeat loading--------------");
											outter.append("\n");
								        	
										    }	
									
								        }
			 				        }
						        }
						    }
							
							return 2;
						}else {	
							if (methodName.equals("load") &&declaringClass.equals("com.squareup.picasso.Picasso")) {
								//System.out.println("sootmethod:"+sm);	
								//System.out.println("loading with Picasso閿涳拷" + u);
								List<Unit> ad = getAllDominatorsOfUnit( sm,u);
								//	((Object) ad).getUnit().toString.contains("unbindService");
									if(ad==null) 
										System.out.println("--------------Looploading----------------");
									else  
										for(int i=0;i<ad.size();i++) {
											Unit ui =ad.get(i);
											if (!ui.equals(u)) {
												Stmt st = (Stmt) unit;
												if ((st instanceof InvokeStmt || st instanceof AssignStmt) && st.containsInvokeExpr()) {
													System.out.println("pd"+ ui);
													
													    if((st.getInvokeExpr().getMethod().getName().equals("NO_CACHE")&&st.getInvokeExpr().getMethod().getDeclaringClass().getName().contains("com.squareup.picasso.MemoryPolicy"))
													    		||(st.getInvokeExpr().getMethod().getName().equals ("networkPolicy")&&st.getInvokeExpr().getArg(0).toString().contains("NO_CACHE"))) {
														//stem.out.println("-----------loopdecode:" + unit);
													       	b++;
//													    System.out.println("loading with picasso閿涳拷" );
//											    		System.out.println("sootmethod:"+sm);	
//											    		System.out.println("unit閿涳拷" + u);
//											    		System.out.println("--------------------repeat loading--------------");	
													       	outter.append("loading  with picasso:");
															outter.append("sootmethod:"+sm);
															outter.append("unit锛�" + u);
															outter.append("--------------------repeat loading--------------");
															outter.append("\n");    
													    }
												
											        
										        }
													
											
										        }
					 				        }
								return 3;
							}else {
								if(methodName.equals("displayImage") && declaringClass.contains("com.nostra13.universalimageloader")) {
									//System.out.println("sootmethod:"+sm);	
								   //	System.out.println("loading with imageloader閿涳拷" + u);
									  
							           Body by= sm.retrieveActiveBody();
								       UnitGraph unitGraph = new BriefUnitGraph(by); 
									
									   MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
									  MHGDominatorsFinder<Unit>  df = new  MHGDominatorsFinder(unitGraph);
////								    
								
									  //Stmt stm = (Stmt) unit;
									    Value value = null;
								        for(Value v :  stm.getInvokeExpr().getArgs()) {
								        	   if(v.getType().toString().contains("DisplayImageOptions")){
									    		  // System.out.println("-------------DisplayImageOptions---------");
									    		   value = v;
									    	   	   if(value != null) {
									    	   		List<Unit> t = df.getDominators(u);
									    			for(int i=0;i<t.size();i++) {
									    				Unit d = t.get(i);
									    			//	System.out.println("pd unit:"+unit);
									    				Stmt tt = (Stmt)d;
									    				List<Unit> pd = new ArrayList<Unit>();
									    				if(!(tt instanceof InvokeStmt)&& tt instanceof AssignStmt) {	
									    					if (getLeftOP(d) != null && getLeftOP(d).toString().equals(value.toString())) {
									    						List<Unit> op = pdFinder.getDominators(d);
									    						for(int j =0;j<op.size();j++) {
									    							Unit n = op.get(j);		    			    			
									    			    			Stmt m = (Stmt)n;
									    							if((m instanceof InvokeStmt || m instanceof AssignStmt)
																			&& m.containsInvokeExpr()) {
																		if((m.getInvokeExpr().getMethod().equals("cacheInMemory")&&m.getInvokeExpr().getArg(0).toString().equals("0"))){
																			System.out.println("------- loopdecode-------");
																		   	b++;
																		   	e.add("unit:"+u);
//																		   	System.out.println("loading with Imageloader閿涳拷" );
//																    		System.out.println("sootmethod:"+sm);	
//																    		System.out.println("unit閿涳拷" + u);
//																    		System.out.println("--------------------repeat loading--------------");	
																    		outter.append("loading  with Imageloader:");
																			outter.append("sootmethod:"+sm);
																			outter.append("unit:" + u);
																			outter.append("--------------------repeat loading--------------");
																			outter.append("\n");
																        	
																		
																				
																		}
																		
																	}
									    							
									    							
									    						}
									    					}
									    												
									    				}
									    			}if(e.isEmpty()) {
							    						for(int i=0;i<t.size();i++) {
							    		    				Unit u1 = t.get(i);
							    		    			//	System.out.println("pd unit:"+unit);
							    		    				Stmt stmt = (Stmt)u1;
							    		    				List<Unit> pds = new ArrayList<Unit>();
//							    		    				if(!(stmt instanceof InvokeStmt)&& stmt instanceof AssignStmt) {	
//							    		    					if (getLeftOP(u) != null && getLeftOP(u).toString().contains(value.toString())) {
//							    		    						List<Unit> op = pdFinder.getDominators(u);
//							    		    						for(int j =0;j<op.size();j++) {
//							    		    							Unit t = op.get(j);		    			    			
//							    		    			    			Stmt stm = (Stmt)t;
							    		    							if(stmt instanceof AssignStmt)
							    												 {
							    		    								//System.out.println("-----------"+u1);
							    										//	if(stm.getInvokeExpr().getMethod().getName().equals("imageScaleType")){
							    												//outter.append("--------------"+ unit+"\n");
							    		    								if (getLeftOP(u1) != null && getLeftOP(u1).toString().contains(value.toString())) {
							    		    									//System.out.println("==================="+u1);
							    		    									SootClass c=sm.method().getDeclaringClass();
							    		    									//System.out.println("-----------"+c);
							    		    									for(SootMethod m:c.getMethods()) {
							    		    										
							    		    										if(m.isConcrete()) {
							    		    											//System.out.println("-----------"+m);
							    		    											if(m.getName().equals("<init>")) {
							    		    												Body body = m.retrieveActiveBody();
							    		    												UnitGraph unitGraph1 = new ExceptionalUnitGraph(body);
							    		    												MHGPostDominatorsFinder<Unit> pd = new MHGPostDominatorsFinder<Unit>(unitGraph1);
							    		    												Iterator<Unit> iter = body.getUnits().snapshotIterator();
							    		    												while (iter.hasNext()) {
							    		    													final Unit unit1 = iter.next();
							    		    													Stmt stmt1 = (Stmt) unit1;
							    		    													if((stmt1 instanceof InvokeStmt || stmt1 instanceof AssignStmt)
				    		    					    												&& stmt1.containsInvokeExpr()) {
//							    		    														System.out
//																											.println("===unit:"+unit1);
							    		    														if(unit1.toString().contains("com.nostra13.universalimageloader.core.DisplayImageOptions$Builder")) {
							    		    															//System.out.println("*********"+unit1);
//							    		    															Value v1 =((AssignStmt) stmt1).getLeftOp();
//							    		    															List<Unit> ps=pd.getDominators(unit1);
//							    		    															for(Unit p:ps) {
//							    		    																Stmt s= (Stmt)p;
//							    		    																if((s instanceof InvokeStmt || s instanceof AssignStmt)
//							    		    					    												&& s.containsInvokeExpr()) {
//							    		    																    if (getLeftOP(p) != null && getLeftOP(p).toString().equals(v1.toString())) {
//							    		    																    	  System.out.println("--------------- unit:"+s.getInvokeExpr().getMethod());
							    		    																    	//if(s.getInvokeExpr().getMethod().getDeclaringClass().toString().equals("com.nostra13.universalimageloader.core.assist.ImageScaleType")&&stmt.toString().contains("NONE")) {
							    		    																    	 if(stmt1.getInvokeExpr().getMethod().getName().toString().equals("cacheInMemory") &&stmt1.getInvokeExpr().getArg(0).toString().equals("0 ")) {
							    		    																    		  
							    		    																    		   System.out.println("**********"+unit1);
							    		    																    	   outter.append("loading  with Imageloader:");
							    		  		    												                   outter.append("sootmethod:"+sm);
							    		  		    												                   outter.append("unit:" + unit1);
							    		  		    												                   outter.append("--------------------I repeat loading -------------");
							    		  		    												                   outter.append("");
							    		  		    												                   b++;
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
								
								   return 4;
								}  
							}
								
						}
					}
					}	
					
				}catch(Exception ex) {
					System.out.println(ex);
				}	
				
				return 0;
					

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
		
		public void getAllUnit(SootMethod sm ){
		
			if (sm == null || !sm.isConcrete())
				return;
			if (sm.getDeclaringClass().getName().startsWith("java"))
			
				return;
			
			List<Unit> fu = new ArrayList<Unit>();
//			List<Unit> tmp = null;
	     	Body b = sm.retrieveActiveBody();
	     	Chain<Unit> u = b.getUnits();
	     	
			Iterator<Unit> it = u.iterator();
			while (it.hasNext()) {
				Unit u1 = it.next();
				Stmt stmt = (Stmt) u1;
				
				if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {
					if (stmt.getInvokeExpr().getMethod().getDeclaringClass().getName().startsWith("java"))					
						continue;
					SootMethod s = stmt.getInvokeExpr().getMethod();			
					if (s.getSignature().equals(sm.getSignature()))
						continue;
//					String methodName = s.getName();
//					String declaringClass = s.getDeclaringClass().getName();
//					if (methodName.equals("decodeFile") || methodName.equals("decodeFileDescriptor")
//							|| methodName.equals("decodeStream") || methodName.equals("decodeByteArray")) {
//		      			//System.out.println("sootmethod:"+m);	
//						System.out.println("loading SDK閿涳拷" + u1);
//						return;
//					}
				
					num= isLoadingPic(u1,sm);
					
					if(num!=0) {
						return ;
					}
//							
						}
							
						}
			
			Iterator<Unit> i = u.iterator();
			while (i.hasNext()) {
				Unit u1 = i.next();
				Stmt stmt = (Stmt) u1;
				
				if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt) && stmt.containsInvokeExpr()) {
					if (stmt.getInvokeExpr().getMethod().getDeclaringClass().getName().startsWith("java"))					
						continue;
					SootMethod s = stmt.getInvokeExpr().getMethod();
					if (s== null || !s.isConcrete())
						continue;
					if (s.getSignature().equals(sm.getSignature()))
						return;
					Body b1 = s.retrieveActiveBody();
			     	Chain<Unit> un = b1.getUnits();
			  
					Iterator<Unit> it1 = un.iterator();
					while (it1.hasNext()) {
						Unit u11 = it1.next();
						Stmt stmt1 = (Stmt) u11;
						if((stmt1 instanceof InvokeStmt || stmt1 instanceof AssignStmt) && stmt1.containsInvokeExpr()) {
//						  String methodName = stmt1.getInvokeExpr().getMethod().getName();
//						  String declaringClass = stmt1.getInvokeExpr().getMethod().getDeclaringClass().getName();
//						  //System.out.println("u11"+u11);
//					  	if (methodName.equals("decodeFile") || methodName.equals("decodeFileDescriptor")
//								|| methodName.equals("decodeStream") || methodName.equals("decodeByteArray")) {
//			      			//System.out.println("sootmethod:"+m);	
//							System.out.println("loading SDK閿涳拷" + u11);
//							return;
							num= isLoadingPic(u11,s);
							if(num!=0) {
								return ;
							}
						else {
							SootMethod s1 = stmt1.getInvokeExpr().getMethod();
							if (s1== null || !s1.isConcrete())
								continue;
							if (s1.getSignature().equals(sm.getSignature()))
							continue;
							Body b2 = s1.retrieveActiveBody();
					     	Chain<Unit> un1 = b2.getUnits();

							Iterator<Unit> it2 = un1.iterator();
							while (it2.hasNext()) {
								Unit u12 = it2.next();
								Stmt stmt2 = (Stmt) u12;
							num= isLoadingPic(u12,s1);
							if(num!=0) {
								
								return;
							}else {
								if((stmt2 instanceof InvokeStmt || stmt2 instanceof AssignStmt) && stmt2.containsInvokeExpr()) {
								SootMethod s2 = stmt2.getInvokeExpr().getMethod();
								if (s2== null || !s2.isConcrete())
									continue;
								if (s2.getSignature().equals(sm.getSignature()))
								continue;
								Body b3 = s2.retrieveActiveBody();
						     	Chain<Unit> un2 = b3.getUnits();

								Iterator<Unit> it3 = un2.iterator();
								while (it3.hasNext()) {
									Unit u13 = it3.next();
									Stmt stmt3 = (Stmt) u13;
								num= isLoadingPic(u13,s2);
								if(num!=0) {
									
									return;
								}else {
									if((stmt3 instanceof InvokeStmt || stmt3 instanceof AssignStmt) && stmt3.containsInvokeExpr()) {
										SootMethod s3 = stmt3.getInvokeExpr().getMethod();
										if (s3== null || !s3.isConcrete())
											continue;
										if (s3.getSignature().equals(sm.getSignature()))
										continue;
										Body b4 = s3.retrieveActiveBody();
								     	Chain<Unit> un3 = b4.getUnits();

										Iterator<Unit> it4 = un3.iterator();
										while (it4.hasNext()) {
											Unit u14 = it4.next();
											Stmt stmt4 = (Stmt) u14;
										num= isLoadingPic(u14,s3);
										if(num!=0) {
											
											return;
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
					
				
		
			
			
		
		  
		
		
		public List<Unit> getAllDominatorsOfUnit(SootMethod sm,Unit u1){
			List<Unit> doms = new ArrayList<Unit>();
			try {
//			if (sm== null || !sm.isConcrete())
//				break;
			if(!sm.hasActiveBody())
				return null;
			Body b = sm.retrieveActiveBody();
		//	Unit u = b.getUnits().getFirst();
			UnitGraph unitGraph = new BriefUnitGraph(b);  //don't consider exception edges
			MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
			List<Unit> tmp = pdFinder.getDominators(u1);
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
