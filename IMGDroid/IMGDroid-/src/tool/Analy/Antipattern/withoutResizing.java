package tool.Analy.Antipattern;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import android.analysis.BasicAnalysis;
import soot.Body;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.jimple.infoflow.solver.cfg.InfoflowCFG;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.MHGDominatorsFinder;
import soot.toolkits.graph.MHGPostDominatorsFinder;
import soot.toolkits.graph.UnitGraph;

public class withoutResizing extends tool.Analy.Analysis.BasicAnalysis{
	
	static CallGraph cg;
    public static List<SootClass> classesChain;
    StringBuffer outter =new StringBuffer();
    int GWR;
    int PWR;
    int FWR;
    int AWR;
    int UWR;
    int Total;
    ArrayList<String> a =new ArrayList<String>();
    ArrayList<String> b =new ArrayList<String>();
    ArrayList<String> c =new ArrayList<String>();
    ArrayList<String> d =new ArrayList<String>();
    ArrayList<String> e =new ArrayList<String>();
     int AWR1;
	 int awr;
	 int UWR1;
	private int num1;
	private int num2;
	 int WR;
//    public withoutResizing(CallGraph cg, List<SootClass> classes) {
//  		super(cg, classes);
//  		// TODO Auto-generated constructor stub
//  	}
 

	public int isResizing(List<SootClass> classes) {
		for (SootClass sc : classes) {
		//if(sc.getName().equals("com.newsblur.util.UIUtils"))	
		//{System.out.println(sc.getName());}
			try {
				for (SootMethod sm : sc.getMethods()) {
					
					if (sm.isConcrete()) {
						//System.out.println(sm.getDeclaringClass().getName());
						Body body = sm.retrieveActiveBody();
						UnitGraph unitGraph = new ExceptionalUnitGraph(body);
						MHGPostDominatorsFinder<Unit> pdFinder = new MHGPostDominatorsFinder<Unit>(unitGraph);
						MHGDominatorsFinder<Unit>  df = new  MHGDominatorsFinder(unitGraph);
						Iterator<Unit> iter = body.getUnits().snapshotIterator();
						while (iter.hasNext()) {
							final Unit unit = iter.next();
							Stmt stmt = (Stmt) unit;
							if ((stmt instanceof InvokeStmt || stmt instanceof AssignStmt)
									&& stmt.containsInvokeExpr()) {
								SootMethod sd = stmt.getInvokeExpr().getMethod();
								String methodName = sd.getName();
								String declaringClass = sd.getDeclaringClass().getName();
								
								if(methodName.equals("load") && declaringClass.contains("com.bumptech.glide")&&(!sm.getDeclaringClass().getName().contains("com.bumptech.glide"))){
												outter.append("---------Glide-------" + "\n");
									//				outter.append("unit" +unit+ "\n");
//													System.out.println("---------Glide-------" );
//													System.out.println("sm" +sm);
//													System.out.println("unit" +unit);
												  List<Unit> pd = pdFinder.getDominators(unit);
												     if(!pd.isEmpty()) {
													   for(Unit tem :pd) {
														Stmt stm = (Stmt) tem;
														if((stm instanceof InvokeStmt || stm instanceof AssignStmt)
																&& stm.containsInvokeExpr()) {
															if(stm.getInvokeExpr().getMethod().getName().equals("override") ) {
															//	outter.append("---------overrride-------"+ "\n");
															//	System.out.println("---------overrride-------");
																//Value value = null;
																List<Unit> preUnit = getAllPreviousUnit(tem, body);
																  if(preUnit != null) {
																	 for(Unit u : preUnit) {
																		 Stmt s = (Stmt) u;
																		 if ((s instanceof InvokeStmt ||  s instanceof AssignStmt)&& s.containsInvokeExpr()||s instanceof AssignStmt) {
																			 //System.out.println("stm锛�" + stm.getInvokeExpr().getMethod());
																			if (u.toString().contains("Target.SIZE_ORIGINAL")){
																//for (Value param : stm.getInvokeExpr().getArgs()) {
																	//getDef();
																//	if (param.toString().equals("Target.SIZE_ORIGINAL")) {
																	outter.append("loading  with Glide:");
																	outter.append("sootmethod:"+sm);
																	outter.append("unit锛�" + unit);
																	outter.append("--------------------G  without resizing--------------");
																	outter.append("\n");
																	GWR++;
																		//outter.append("---------G  without resizing-------"+ "\n");  
																	}    
																}
																
															}
															
														}
														
													}
													
												}
											}	
									    }			
									}
								if(methodName.equals("load") && declaringClass.contains("com.squareup.picasso")&&!sm.getDeclaringClass().getName().contains("com.squareup.picasso")){
									outter.append("---------picasso-------" + "\n");
									 List<Unit> pd = pdFinder.getDominators(unit);
									 if(!pd.isEmpty()) {
										for(Unit tem :pd) {
											Stmt stm = (Stmt) tem;
											if((stm instanceof InvokeStmt || stm instanceof AssignStmt)
													&& stm.containsInvokeExpr()) {
												//System.out.println("stm:"+stm.getInvokeExpr().getMethod().getName());
												if(stm.getInvokeExpr().getMethod().getName().equals("resize") || stm.getInvokeExpr().getMethod().getName().equals("resizeDimen")
														||stm.getInvokeExpr().getMethod().getName().equals("fit")) {
												
													//outter.append("---------P resizing-------"+ "\n");
													//System.out.println("---------p resizing-------" +sm);
													a.add("stm"+ stm);
													break;
														
												}
												
											}
											
										}if(a.isEmpty()) {
											outter.append("loading  with picasso:");
											outter.append("sootmethod:"+sm);
											outter.append("unit:" + unit);
											outter.append("-------------------- without resizing--------------");
											outter.append("\n");
										//	System.out.println("---------without resizing----------");
											PWR++;
										}
										
									}
									
										
									}			
									
									if(isFresco(methodName,declaringClass)) {
											if(methodName.equals("newBuilderWithSource") && declaringClass.equals(
													"com.facebook.imagepipeline.request.ImageRequest")){
							                     outter.append("---------Fresco-------"+ "\n");
												 List<Unit> pd = pdFinder.getDominators(unit);
												 if(!pd.isEmpty()) {
													for(Unit tem :pd) {
														Stmt stm = (Stmt) tem;
														if((stm instanceof InvokeStmt || stm instanceof AssignStmt)
																&& stm.containsInvokeExpr()) {
															if(stm.getInvokeExpr().getMethod().getName().equals("setResizeOptions")||stm.getInvokeExpr().getMethod().getName().equals("setDownsampleEnabled")){
															
															//	outter.append("---------F resizing-------"+ "\n");
															//	System.out.println("---------F resizing-------" +sm);
																
																b.add("stm"+ stm+ "\n");
																	
															}
															
														}
														
													}if(b.isEmpty()) {
														outter.append("loading  with fresco:");
														outter.append("sootmethod:"+sm);
														outter.append("unit:" + unit);
														outter.append("-------------------- without resizing--------------");
														outter.append("\n");
														FWR++;
													}
													
												}
												
													
												}	
											
										}
									if(methodName.equals("setImageURI")
											|| methodName.equals("creatFromPath")
											|| methodName.equals("creatFromStream")) {
									  if(!sm.method().getName().equals("setImageURI")) {
										outter.append("loading  with SDK:");
										outter.append("sootmethod:"+sm);
										outter.append("unit:" + unit);
										outter.append("-------------------- without resizing with setimageURl--------------");
										outter.append("\n");
										WR++;
										}	 
									}
                                       
                                              AWR=isResizeByAndroid(df,unit,methodName,sm);
									          
									         UWR= isResizeByI(df,pdFinder, unit,methodName, declaringClass,sm);
									
									
								
									}
							}
						}
					}	
				
		  

			}catch(Exception E) {
			//  System.out.println("-----"+E);
		  }
		
		}	try {
			FileWriter fw = new FileWriter("./Wout.txt", true);
			fw.write(outter.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return GWR+PWR+AWR+UWR+FWR+WR;
	}

	
	

	   
		
	public int isResizeByAndroid(MHGDominatorsFinder<Unit>  df,Unit unit,String methodName,SootMethod sm) {
		if(!sm.getDeclaringClass().getName().contains("com.bumptech.glide")
				||!sm.getDeclaringClass().getName().contains("com.squareup.picasso")
				||!sm.getDeclaringClass().getName().contains("com.nostra13.universalimageloader")){
			if(methodName.equals("decodeResource")
					|| methodName.equals("decodeFile")
					|| methodName.equals("decodeFileDescriptor")
					|| methodName.equals("decodeStream")
					|| methodName.equals("decodeByteArray")
					|| methodName.equals("decodeRegion")) {
				    
				 
				  
				
				
			   //    outter.append("---------using androidSDK-------" +sm+ "\n");
			    //   outter.append("unit:" +unit+ "\n");
				//System.out.println("---------using androidSDK-------");
				//System.out.println("sm"+sm);
				//System.out.println("unit:" +unit);
				
			        Stmt st =(Stmt)unit;
			        Value value = null;
			        List<Value> l = st.getInvokeExpr().getArgs();			    			   
			        for(Value v : l) {
			    	   
			    	  if(v!=null) {  
				       // System.out.println("------" + v. toString());
				        if (v. getType(). toString().contains("android.graphics.BitmapFactory")){
				        //	outter.append("-------------BitmapFactory$Options---------"+ "\n");
				        //	System.out.println("-------------BitmapFactory$Options---------");
				        	d.add("option"+ v. getType(). toString());
			    		   value = v;
			    	   	   if(value != null) {
			    	          awr =  getAnalyWValue(df, unit ,value,sm);
			    	   	   }
			       
			       
		             
				        
				        
			        } 
	              }
			}  if(d.isEmpty()) {
				if(sm.getDeclaringClass().getName().contains("com.bumptech.glide")
						||sm.getDeclaringClass().getName().contains("com.squareup.picasso")
						||sm.getDeclaringClass().getName().contains("com.nostra13.universalimageloader")){
					return 0;
				}
//				System.out.println("---------using androidSDK-------");
//				System.out.println("sm"+sm);
//				System.out.println("unit:" +unit);
//				System.out.println("---------without resizing of option----------");
				outter.append("loading  with androidSDK:");
				outter.append("sootmethod:"+sm);
				outter.append("unit:" + unit);
				outter.append("--------------------without resizing of option-------------");
				outter.append("\n");
				AWR1++; 
			}	
//			        
		  }
		}return awr+ AWR1;
		
	}
	
	public int getAnalyWValue(MHGDominatorsFinder<Unit>  df,Unit unit,Value value,SootMethod sm){
		
		  try {
			List<Unit> tmp = df.getDominators(unit);
			for(int i=0;i<tmp.size();i++) {
				Unit u = tmp.get(i);
			//	System.out.println("pd unit:"+unit);
				Stmt stmt = (Stmt)u;
		//	List<Unit> pds = new ArrayList<Unit>();
				if(((stmt instanceof InvokeStmt || stmt instanceof AssignStmt)
						&& stmt.containsInvokeExpr())||stmt instanceof AssignStmt) {	
					//System.out.println("unit:"+ u);
									
					 if ((getLeftOP(u) != null && getLeftOP(u).toString().contains(value.toString()))&&(getLeftOP(u).toString().contains("inJustDecodeBounds")||getLeftOP(u).toString().contains("inScaled")||getLeftOP(u).toString().contains("inDensity"))) {
						//outter.append("--------injustdecodebounds -------"+ "\n");
							//System.out.println("--------injustdecodebounds -------");
						c.add("stmt"+stmt);

						break;
					 }
					 //閫掑綊鏌ユ壘
//					 else {
//						 if((stmt instanceof InvokeStmt || stmt instanceof AssignStmt)
//									&& stmt.containsInvokeExpr()) {
//						   if(stmt.getInvokeExpr().getMethod().isConcrete()) {
//							   //System.out.println("stmt:"+stmt);
//						     
//							 Body b= stmt.getInvokeExpr().getMethod().retrieveActiveBody();
//							
//							 
//							 Iterator<Unit> iter = b.getUnits().snapshotIterator();
//							 while (iter.hasNext()) {
//								final Unit uni = iter.next();
//								Stmt stm = (Stmt) uni;
//								if(( stm instanceof AssignStmt
//										&& stm.containsInvokeExpr())||stm instanceof AssignStmt) {	
//									if ((getLeftOP(uni) != null) &&(getLeftOP(uni).toString().contains("inJustDecodeBounds")||getLeftOP(uni).toString().contains("inScaled")||getLeftOP(u).toString().contains("inDensity"))) {										
//										//	outter.append("-----------inJustDecodeBounds----------- " + "\n");
//										System.out.println("--------injustdecodebounds -------");
//											c.add("stmt"+stm);
//
//											break;																
//										
//								}
////															
//								}
//							
//							 } 
//						 }	 
//						 }
//						
//					}
				 	
												
				}
			}if(c.isEmpty()) {
				//  int a=anainvokeM( sm);
				  List<Type> a = sm.getParameterTypes();
			       for(Type p:a) {
			    	  
			          if(p!=null && p.toString().contains("android.graphics.BitmapFactory")) {
			        	//  System.out.println("锛�    option is parameter");
			        	  c.add(p.toString());
			        	  break;
			          }	 
			    	 } if(c.isEmpty()) {
			    		 if(sm.getDeclaringClass().getName().contains("com.bumptech.glide")
									||sm.getDeclaringClass().getName().contains("com.squareup.picasso")
									||sm.getDeclaringClass().getName().contains("com.nostra13.universalimageloader")){
								return 0;
							}
//			    		 System.out.println("---------using androidSDK-------");
//							System.out.println("sm"+sm);
//							System.out.println("unit:" +unit);
//			    		 System.out.println("---------without resizing ----------");
			    		 outter.append("loading  with androidSDK:");
							outter.append("sootmethod:"+sm);
							outter.append("unit:" + unit);
							outter.append("--------------------without resizing -------------");
							outter.append("\n");
			    		 num1++;
			    	 }else num1=anainvokeM( sm);
				
				
			}
			for(int j=0;j<tmp.size();j++) {
				Unit u = tmp.get(j);
			//	System.out.println("pd unit:"+unit);
				Stmt stmt = (Stmt)u;
			//	List<Unit> pds = new ArrayList<Unit>();
				if(!(stmt instanceof InvokeStmt)&& stmt instanceof AssignStmt) {	
					if (getLeftOP(u) != null && getLeftOP(u).toString().contains(value.toString())&&getLeftOP(u).toString().contains("inSampleSize")) {										
						//	outter.append("-----------inSampleSize----------- " );
						//System.out.println("--------inSampleSize -------");
							break;																
						
					}											
				}
			}
			
			System.out.println("  " );
			
		  }catch(Exception E) {
			  System.out.println("-----"+E);
		  }
		   return num1;	
			
			
		}
			
	
	
    
	public boolean isFresco(String methodName,String declaringClass) {
		if(methodName.equals("initialize") && declaringClass.contains("com.facebook.fresco")) {
			outter.append("---------Fresco-------" + "\n");
			return true;
		}else 
			return false;
		
		
	}


	public int isResizeByI(MHGDominatorsFinder<Unit>  df,MHGPostDominatorsFinder<Unit> pdFinder,Unit unit,String methodName,String declaringClass,SootMethod sm) {
		if(methodName.equals("displayImage") && declaringClass.contains("com.nostra13.universalimageloader")&&!sm.getDeclaringClass().getName().contains("com.nostra13.universalimageloader")) {
	        // outter.append("---------using Imageloader-------" + sm+ "\n");
			// outter.append("unit:" +unit);
			System.out.println("---------using Imageloader-------" + "\n"+sm);
			System.out.println("unit:" +unit);
			
	        Stmt st =(Stmt)unit;
	        Value value = null;
	        for(Value v :  st.getInvokeExpr().getArgs()) {
	        	   if(v.getType().toString().contains("DisplayImageOptions")){
	        		 outter.append("-------------DisplayImageOptions---------"+"\n");
	        			System.out.println("---------DisplayImageOptions-------" + sm);
		    		   value = v;
		    	   	   if(value != null) {
		    	   		List<Unit> tmp = df.getDominators(unit);
		    			for(int i=0;i<tmp.size();i++) {
		    				Unit u = tmp.get(i);
		    			//	System.out.println("pd unit:"+unit);
		    				Stmt stmt = (Stmt)u;
		    				List<Unit> pds = new ArrayList<Unit>();
//		    				if(!(stmt instanceof InvokeStmt)&& stmt instanceof AssignStmt) {	
//		    					if (getLeftOP(u) != null && getLeftOP(u).toString().contains(value.toString())) {
//		    						List<Unit> op = pdFinder.getDominators(u);
//		    						for(int j =0;j<op.size();j++) {
//		    							Unit t = op.get(j);		    			    			
//		    			    			Stmt stm = (Stmt)t;
		    							if((stmt instanceof InvokeStmt || stmt instanceof AssignStmt)
												&& stmt.containsInvokeExpr()) {
										//	if(stm.getInvokeExpr().getMethod().getName().equals("imageScaleType")){
												//outter.append("--------------"+ unit+"\n");
		    							
											//	System.out.println(" unit:"+u);
												if(stmt.getInvokeExpr().getMethod().getDeclaringClass().toString().equals("com.nostra13.universalimageloader.core.assist.ImageScaleType")&&stmt.toString().contains("NONE")) {
											
												e.add("unit:"+u);		
												outter.append("loading  with Imageloader:");
												outter.append("sootmethod:"+sm);
												outter.append("unit：" + unit);
												outter.append("--------------------I  without resizing -------------");
												outter.append("");
												UWR1++;
													
											}
											
										}
		    							
		    							
		    						}
		    					if(e.isEmpty()) {
//		    						for(int i=0;i<tmp.size();i++) {
//		    		    				Unit u = tmp.get(i);
//		    		    			//	System.out.println("pd unit:"+unit);
//		    		    				Stmt stmt = (Stmt)u;
//		    		    				List<Unit> pds = new ArrayList<Unit>();
////		    		    				if(!(stmt instanceof InvokeStmt)&& stmt instanceof AssignStmt) {	
////		    		    					if (getLeftOP(u) != null && getLeftOP(u).toString().contains(value.toString())) {
////		    		    						List<Unit> op = pdFinder.getDominators(u);
////		    		    						for(int j =0;j<op.size();j++) {
////		    		    							Unit t = op.get(j);		    			    			
////		    		    			    			Stmt stm = (Stmt)t;
//		    		    							if((stmt instanceof InvokeStmt || stmt instanceof AssignStmt)
//		    												&& stmt.containsInvokeExpr()) {
//		    										//	if(stm.getInvokeExpr().getMethod().getName().equals("imageScaleType")){
//		    												//outter.append("--------------"+ unit+"\n");
//		    		    								if (getLeftOP(u) != null && getLeftOP(u).toString().contains(value.toString())) {
		    						                  SootClass c=sm.method().getDeclaringClass();
		    		    									for(SootMethod m:c.getMethods()) {
		    		    										
		    		    										if(m.isConcrete()) {
		    		    											if(m.getName().equals("<init>")) {
		    		    												Body body = m.retrieveActiveBody();
		    		    												UnitGraph unitGraph = new ExceptionalUnitGraph(body);
		    		    												MHGPostDominatorsFinder<Unit> pd = new MHGPostDominatorsFinder<Unit>(unitGraph);
		    		    												Iterator<Unit> iter = body.getUnits().snapshotIterator();
		    		    												while (iter.hasNext()) {
		    		    													final Unit unit1 = iter.next();
		    		    													Stmt stmt1 = (Stmt) unit1;
		    		    													if ( stmt1 instanceof AssignStmt) {
		    		    														//if(unit1.toString().contains("new com.nostra13.universalimageloader.core.DisplayImageOptions$Builder")) {
//		    		    															System.out.println("*********"+unit1);
//		    		    															Value v1 =((AssignStmt) stmt1).getLeftOp();
//		    		    															List<Unit> ps=pd.getDominators(unit1);
//		    		    															for(Unit p:ps) {
//		    		    																Stmt s= (Stmt)p;
		    		    																
		    		    																    if (getRightOP(unit1) != null && getRightOP(unit1).toString().contains("NONE")) {
		    		    																    	//if (getRightOP(unit1) != null && getRightOP(unit1).toString().contains("IN_SAMPLE_POWER_OF_2")) {
		    		    																    	  // System.out.println(" unit:"+s.getInvokeExpr().getMethod());
		    		    																    	if(getRightOP(unit1).getType().toString().contains("com.nostra13.universalimageloader.core.assist.ImageScaleType")) {
		    		    																    	 //  if(s.getInvokeExpr().getMethod().getName().toString().equals("cacheInMemory") &&s.getInvokeExpr().getArg(0).toString().equals("1")) {
		    		    																    		  
		    		    																    		   System.out.println("**********"+unit1);
		    		    																    	   outter.append("loading  with Imageloader:");
		    		  		    												                   outter.append("sootmethod:"+sm);
		    		  		    												                   outter.append("unit:" + unit1);
		    		  		    												                   outter.append("--------------------I  without resizing -------------");
		    		  		    												                   outter.append("");
		    		  		    												                   UWR1++;
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

			System.out.println(" ");
		}return UWR1;
		
		
	}  
	public int anainvokeM(SootMethod sm) {
//	       List<Type> a = sm.getParameterTypes();
//	       for(Type p:a) {
	    	 try {  
//	          if(p!=null && p.toString().contains("android.graphics.BitmapFactory")) {

	        	  Iterator<Edge> edges = cg.edgesInto(sm);
	       
	        		while(edges.hasNext()) {
	        			Edge e = edges.next();			        			
	        			SootMethod in =e.getSrc().method();  
	        			if(in ==null) {
	        				System.out.println("in is null");
	        			}else {
	        				
	        				Body body = in.retrieveActiveBody();
							Iterator<Unit> iter = body.getUnits().snapshotIterator();
							while (iter.hasNext()) {
								final Unit uni = iter.next();
								Stmt stm = (Stmt) uni;
								if(!(stm instanceof InvokeStmt)&& stm instanceof AssignStmt) {	
									if (getLeftOP(uni) != null &&getLeftOP(uni).toString().contains("inJustDecodeBounds")) {										
											//System.out.println("-----------inJustDecodeBounds----------- " );
											return 0;																
										
									}											
								}
							}outter.append("loading  with androidSDK:");
							outter.append("sootmethod:"+sm);
							//outter.append("unit:" + uni);
							outter.append("--------------------without resizing-------------");
							outter.append("\n");
							 num2++;
	        				
	        				
	        			}			        			
	        		}
	          
	    	 }catch(Exception e) {
	    		// System.out.println("exception" + e);
	    	 } return num2;
	       } 
		
	

}
