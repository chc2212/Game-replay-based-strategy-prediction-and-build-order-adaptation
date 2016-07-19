

import weka.classifiers.Classifier;
import weka.classifiers.UpdateableClassifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.core.TechnicalInformation;
import weka.core.TechnicalInformationHandler;
import weka.core.Utils;
import weka.core.Capabilities.Capability;
import weka.core.TechnicalInformation.Field;
import weka.core.TechnicalInformation.Type;

import java.util.Enumeration;

public class RuleSet
  extends Classifier 
  implements UpdateableClassifier, TechnicalInformationHandler {

  /** for serialization */
  static final long serialVersionUID = -6152184127304895851L;
  
  /** The training instances used for classification. */
  private Instances m_Train;

    /** The minimum values for numeric attributes. */
  private double [] m_MinArray;

  /** The maximum values for numeric attributes. */
  private double [] m_MaxArray;

  public String m_Species;

  /**
   * Returns a string describing classifier
   * @return a description suitable for
   * displaying in the explorer/experimenter gui
   */

  public String globalInfo() {

    return "Nearest-neighbour classifier. Uses normalized Euclidean distance to " 
      + "find the training instance closest to the given test instance, and predicts "
      + "the same class as this training instance. If multiple instances have "
      + "the same (smallest) distance to the test instance, the first one found is "
      + "used.\n\n"
      + "For more information, see \n\n"
      + getTechnicalInformation().toString();
  }

  /**
   * Returns an instance of a TechnicalInformation object, containing 
   * detailed information about the technical background of this class,
   * e.g., paper reference or book this class is based on.
   * 
   * @return the technical information about this class
   */
  public TechnicalInformation getTechnicalInformation() {
    TechnicalInformation 	result;
    
    result = new TechnicalInformation(Type.ARTICLE);
    result.setValue(Field.AUTHOR, "D. Aha and D. Kibler");
    result.setValue(Field.YEAR, "1991");
    result.setValue(Field.TITLE, "Instance-based learning algorithms");
    result.setValue(Field.JOURNAL, "Machine Learning");
    result.setValue(Field.VOLUME, "6");
    result.setValue(Field.PAGES, "37-66");
    
    return result;
  }

  /**
   * Returns default capabilities of the classifier.
   *
   * @return      the capabilities of this classifier
   */
  public Capabilities getCapabilities() {
    Capabilities result = super.getCapabilities();
    result.disableAll();

    // attributes
    result.enable(Capability.NOMINAL_ATTRIBUTES);
    result.enable(Capability.NUMERIC_ATTRIBUTES);
    result.enable(Capability.DATE_ATTRIBUTES);
    result.enable(Capability.MISSING_VALUES);

    // class
    result.enable(Capability.NOMINAL_CLASS);
    result.enable(Capability.MISSING_CLASS_VALUES);

    // instances
    result.setMinimumNumberInstances(0);
    
    return result;
  }

  /**
   * Generates the classifier.
   *
   * @param instances set of instances serving as training data 
   * @throws Exception if the classifier has not been generated successfully
   */
  public void buildClassifier(Instances instances) throws Exception {
   
    // can classifier handle the data?
    getCapabilities().testWithFail(instances);

    // remove instances with missing class
    instances = new Instances(instances);
    instances.deleteWithMissingClass();
    
    m_Train = new Instances(instances, 0, instances.numInstances());

    m_MinArray = new double [m_Train.numAttributes()];
    m_MaxArray = new double [m_Train.numAttributes()];
    for (int i = 0; i < m_Train.numAttributes(); i++) {
      m_MinArray[i] = m_MaxArray[i] = Double.NaN;
    }
    Enumeration enu = m_Train.enumerateInstances();
    while (enu.hasMoreElements()) {
      updateMinMax((Instance) enu.nextElement());
    }
     
  }

  /**
   * Updates the classifier.
   *
   * @param instance the instance to be put into the classifier
   * @throws Exception if the instance could not be included successfully
   */
  public void updateClassifier(Instance instance) throws Exception {
  
    if (m_Train.equalHeaders(instance.dataset()) == false) {
      throw new Exception("Incompatible instance types");
    }
    if (instance.classIsMissing()) {
      return;
    }
    m_Train.add(instance);
    updateMinMax(instance);
  }

  /**
   * Classifies the given test instance.
   *
   * @param instance the instance to be classified
   * @return the predicted class for the instance 
   * @throws Exception if the instance can't be classified
   */
  public double classifyInstance(Instance instance) throws Exception {
    
    if (m_Train.numInstances() == 0) {
      throw new Exception("No training instances!");
    }

    /* IB1 */
    /*
    double distance, minDistance = Double.MAX_VALUE, classValue = 0;
    updateMinMax(instance);
    Enumeration enu = m_Train.enumerateInstances();
    while (enu.hasMoreElements()) {
      Instance trainInstance = (Instance) enu.nextElement();
      if (!trainInstance.classIsMissing()) {
	distance = distance(instance, trainInstance);
	if (distance < minDistance) {
	  minDistance = distance;
	  classValue = trainInstance.classValue();
	}
      }
    }
    */
    double classValue = 0;
     int max=0;
         for(int i=0;i<instance.numAttributes()-1;i++) {
       if((int)instance.value(i)>max) max=(int)instance.value(i);
    }

    if(m_Species.equals("Protoss")){
        
        
        
        
        
        
int ProtossPylon;
if((int)instance.value(0)==0) ProtossPylon=max+1; else ProtossPylon=(int)instance.value(0);
int ProtossSecondPylon;
if((int)instance.value(1)==0) ProtossSecondPylon=max+1; else ProtossSecondPylon=(int)instance.value(1);
int ProtossFirstGas;
if((int)instance.value(2)==0) ProtossFirstGas=max+1; else ProtossFirstGas=(int)instance.value(2);
int ProtossSecondGas;
if((int)instance.value(3)==0) ProtossSecondGas=max+1; else ProtossSecondGas=(int)instance.value(3);
int ProtossFirstExpansion;
if((int)instance.value(4)==0) ProtossFirstExpansion=max+1; else ProtossFirstExpansion=(int)instance.value(4);
int ProtossSecondExpansion;
if((int)instance.value(5)==0) ProtossSecondExpansion=max+1; else ProtossSecondExpansion=(int)instance.value(5);
int ProtossThirdExpansion;
if((int)instance.value(6)==0) ProtossThirdExpansion=max+1; else ProtossThirdExpansion=(int)instance.value(6);
int ProtossFourthExpansion;
if((int)instance.value(7)==0) ProtossFourthExpansion=max+1; else ProtossFourthExpansion=(int)instance.value(7);
int ProtossGateway;
if((int)instance.value(8)==0) ProtossGateway=max+1; else ProtossGateway=(int)instance.value(8);
int ProtossSecondGatway;
if((int)instance.value(9)==0) ProtossSecondGatway=max+1; else ProtossSecondGatway=(int)instance.value(9);
int ProtossThirdGatway;
if((int)instance.value(10)==0) ProtossThirdGatway=max+1; else ProtossThirdGatway=(int)instance.value(10);
int ProtossFourthGatway;
if((int)instance.value(11)==0) ProtossFourthGatway=max+1; else ProtossFourthGatway=(int)instance.value(11);
int ProtossCore;
if((int)instance.value(12)==0) ProtossCore=max+1; else ProtossCore=(int)instance.value(12);
int ProtossZealot;
if((int)instance.value(13)==0) ProtossZealot=max+1; else ProtossZealot=(int)instance.value(13);
int ProtossGoon;
if((int)instance.value(14)==0) ProtossGoon=max+1; else ProtossGoon=(int)instance.value(14);
int ProtossRange;
if((int)instance.value(15)==0) ProtossRange=max+1; else ProtossRange=(int)instance.value(15);
int ProtossForge;
if((int)instance.value(16)==0) ProtossForge=max+1; else ProtossForge=(int)instance.value(16);
int ProtossCannon;
if((int)instance.value(17)==0) ProtossCannon=max+1; else ProtossCannon=(int)instance.value(17);
int ProtossGroundWeapons1;
if((int)instance.value(18)==0) ProtossGroundWeapons1=max+1; else ProtossGroundWeapons1=(int)instance.value(18);
int ProtossGroundArmor1;
if((int)instance.value(19)==0) ProtossGroundArmor1=max+1; else ProtossGroundArmor1=(int)instance.value(19);
int ProtossShields1;
if((int)instance.value(20)==0) ProtossShields1=max+1; else ProtossShields1=(int)instance.value(20);
int ProtossGroundWeapons2;
if((int)instance.value(21)==0) ProtossGroundWeapons2=max+1; else ProtossGroundWeapons2=(int)instance.value(21);
int ProtossGroundArmor2;
if((int)instance.value(22)==0) ProtossGroundArmor2=max+1; else ProtossGroundArmor2=(int)instance.value(22);
int ProtossShields2;
if((int)instance.value(23)==0) ProtossShields2=max+1; else ProtossShields2=(int)instance.value(23);
int ProtossCitadel;
if((int)instance.value(24)==0) ProtossCitadel=max+1; else ProtossCitadel=(int)instance.value(24);
int ProtossLegs;
if((int)instance.value(25)==0) ProtossLegs=max+1; else ProtossLegs=(int)instance.value(25);
int ProtossArchives;
if((int)instance.value(26)==0) ProtossArchives=max+1; else ProtossArchives=(int)instance.value(26);
int ProtossTemplar;
if((int)instance.value(27)==0) ProtossTemplar=max+1; else ProtossTemplar=(int)instance.value(27);
int ProtossArchon;
if((int)instance.value(28)==0) ProtossArchon=max+1; else ProtossArchon=(int)instance.value(28);
int ProtossStorm;
if((int)instance.value(29)==0) ProtossStorm=max+1; else ProtossStorm=(int)instance.value(29);
int ProtossDarkTemplar;
if((int)instance.value(30)==0) ProtossDarkTemplar=max+1; else ProtossDarkTemplar=(int)instance.value(30);
int ProtossDarkArchon;
if((int)instance.value(31)==0) ProtossDarkArchon=max+1; else ProtossDarkArchon=(int)instance.value(31);
int ProtossMaelstorm;
if((int)instance.value(32)==0) ProtossMaelstorm=max+1; else ProtossMaelstorm=(int)instance.value(32);
int ProtossRoboBay;
if((int)instance.value(33)==0) ProtossRoboBay=max+1; else ProtossRoboBay=(int)instance.value(33);
int ProtossShuttle;
if((int)instance.value(34)==0) ProtossShuttle=max+1; else ProtossShuttle=(int)instance.value(34);
int ProtossShuttleSpeed;
if((int)instance.value(35)==0) ProtossShuttleSpeed=max+1; else ProtossShuttleSpeed=(int)instance.value(35);
int ProtossRoboSupport;
if((int)instance.value(36)==0) ProtossRoboSupport=max+1; else ProtossRoboSupport=(int)instance.value(36);
int ProtossReavor;
if((int)instance.value(37)==0) ProtossReavor=max+1; else ProtossReavor=(int)instance.value(37);
int ProtossReavorDamage;
if((int)instance.value(38)==0) ProtossReavorDamage=max+1; else ProtossReavorDamage=(int)instance.value(38);
int ProtossReavorCapacity;
if((int)instance.value(39)==0) ProtossReavorCapacity=max+1; else ProtossReavorCapacity=(int)instance.value(39);
int ProtossObservory;
if((int)instance.value(40)==0) ProtossObservory=max+1; else ProtossObservory=(int)instance.value(40);
int ProtossObs;
if((int)instance.value(41)==0) ProtossObs=max+1; else ProtossObs=(int)instance.value(41);
int ProtossStargate;
if((int)instance.value(42)==0) ProtossStargate=max+1; else ProtossStargate=(int)instance.value(42);
int ProtossCorsair;
if((int)instance.value(43)==0) ProtossCorsair=max+1; else ProtossCorsair=(int)instance.value(43);
int ProtossDisruptionWeb;
if((int)instance.value(44)==0) ProtossDisruptionWeb=max+1; else ProtossDisruptionWeb=(int)instance.value(44);
int ProtossFleetBeason;
if((int)instance.value(45)==0) ProtossFleetBeason=max+1; else ProtossFleetBeason=(int)instance.value(45);
int ProtossCarrier;
if((int)instance.value(46)==0) ProtossCarrier=max+1; else ProtossCarrier=(int)instance.value(46);
int ProtossCarrierCapacity;
if((int)instance.value(47)==0) ProtossCarrierCapacity=max+1; else ProtossCarrierCapacity=(int)instance.value(47);
int ProtossTribunal;
if((int)instance.value(48)==0) ProtossTribunal=max+1; else ProtossTribunal=(int)instance.value(48);
int ProtossArbitor;
if((int)instance.value(49)==0) ProtossArbitor=max+1; else ProtossArbitor=(int)instance.value(49);
int ProtossStatis;
if((int)instance.value(50)==0) ProtossStatis=max+1; else ProtossStatis=(int)instance.value(50);
int ProtossRecall;
if((int)instance.value(51)==0) ProtossRecall=max+1; else ProtossRecall=(int)instance.value(51);
int ProtossAirWeapons1;
if((int)instance.value(52)==0) ProtossAirWeapons1=max+1; else ProtossAirWeapons1=(int)instance.value(52);
int ProtossAirArmor1;
if((int)instance.value(53)==0) ProtossAirArmor1=max+1; else ProtossAirArmor1=(int)instance.value(53);
int ProtossAirWeapons2;
if((int)instance.value(54)==0) ProtossAirWeapons2=max+1; else ProtossAirWeapons2=(int)instance.value(54);
int ProtossAirArmor2;
if((int)instance.value(55)==0) ProtossAirArmor2=max+1; else ProtossAirArmor2=(int)instance.value(55);

   // RuleSet by Ben Weber
/*
if (ProtossCitadel < ProtossStargate && ProtossCitadel < ProtossFirstExpansion && ProtossCitadel < ProtossRoboBay) {
			 if (ProtossLegs <ProtossArchives) { classValue=0; } // then "Fast Legs"
		     else if (ProtossArchives < ProtossLegs) { classValue=1; }  // then "Fast DT"
			 else {
				classValue=6;	// then "unknown"
			}
		}
		else if (ProtossStargate<ProtossCitadel && ProtossStargate < ProtossFirstExpansion && ProtossStargate < ProtossRoboBay) {
			    classValue=4; // then "Fast Air"
		}
        else if (ProtossFirstExpansion< ProtossCitadel && ProtossFirstExpansion < ProtossStargate && ProtossFirstExpansion < ProtossRoboBay) {
				classValue=5;	//then "Fast Expand"
		}
        else if (ProtossRoboBay < ProtossCitadel && ProtossRoboBay < ProtossStargate && ProtossRoboBay < ProtossFirstExpansion) {
			if (ProtossRoboSupport < ProtossObservory) {
				classValue=3;	// then "Reaver"
			}
			else if (ProtossObservory < ProtossRoboSupport) {
				classValue=2; // then "Standard"
			}
			else {
				classValue=6; //then "unknown"
			}
		}
		else {
			classValue=6;
		}
    

*/
  // RuleSet by Automatic Discovery Algorithm

if(ProtossFirstExpansion <= ProtossStargate){
	if(ProtossRoboBay <= ProtossFirstExpansion){
		if(ProtossCitadel <= ProtossRoboBay){
			if(ProtossLegs <= ProtossArchives){
				if(ProtossFourthExpansion <= ProtossLegs){
					classValue=6;
				}
				else{
					classValue=0;
				}
			}
			else{
				classValue=1;
			}
		}
		else{
			if(ProtossRoboSupport <= ProtossObservory){
				if(ProtossSecondExpansion <= ProtossRoboSupport){
					classValue=6;
				}
				else{
					classValue=3;
				}
			}
			else{
				classValue=2;
			}
		}
	}
	else{
		if(ProtossFirstExpansion <= ProtossCitadel){
			classValue=5;
		}
		else{
			if(ProtossLegs <= ProtossArchives){
				classValue=0;
			}
			else{
				classValue=1;
			}
		}
	}
}
else{
	if(ProtossCitadel <= ProtossStargate){
		if(ProtossLegs <= ProtossArchives){
			classValue=0;
		}
		else{
			classValue=1;
		}
	}
	else{
		if(ProtossRoboBay <= ProtossStargate){
			if(ProtossRoboSupport <= ProtossFirstExpansion){
				classValue=3;
			}
			else{
				classValue=2;
			}
		}
		else{
			classValue=4;
		}
	}
}

       

/*
//bot_vs_bot_enemy 오토룰셋
if(ProtossObs <= ProtossReavor){
	if(ProtossObs <= ProtossDarkTemplar){
		if(ProtossSecondGas <= ProtossArchives){
			if(ProtossCannon <= ProtossDarkTemplar){
				if(ProtossFirstGas <= ProtossCitadel){
					if(ProtossForge <= ProtossReavor){
						if(ProtossRoboBay <= ProtossRoboSupport){
							if(ProtossSecondGas <= ProtossObservory){
								if(ProtossObs <= ProtossFirstExpansion){
									if(ProtossRoboBay <= ProtossObservory){
										if(ProtossFirstGas <= ProtossObs){
											if(ProtossThirdGatway <= ProtossCitadel){
												if(ProtossSecondGas <= ProtossObs){
													if(ProtossCore <= ProtossPylon){
														classValue=5;
													}
													else{
														if(ProtossSecondGas <= ProtossZealot){
															classValue=6;
														}
														else{
															if(ProtossSecondGas <= ProtossFourthGatway){
																if(ProtossFirstGas <= ProtossGoon){
																	if(ProtossForge <= ProtossFirstGas){
																		if(ProtossSecondGatway <= ProtossGateway){
																			classValue=6;
																		}
																		else{
																			if(ProtossSecondGatway <= ProtossSecondPylon){
																				if(ProtossGateway <= ProtossZealot){
																					if(ProtossGateway <= ProtossPylon){
																						classValue=5;
																					}
																					else{
																						classValue=6;
																					}
																				}
																				else{
																					classValue=5;
																				}
																			}
																			else{
																				classValue=5;
																			}
																		}
																	}
																	else{
																		classValue=6;
																	}
																}
																else{
																	classValue=5;
																}
															}
															else{
																classValue=6;
															}
														}
													}
												}
												else{
													if(ProtossSecondPylon <= ProtossCannon){
														classValue=5;
													}
													else{
														classValue=2;
													}
												}
											}
											else{
												classValue=6;
											}
										}
										else{
											if(ProtossSecondPylon <= ProtossGateway){
												classValue=2;
											}
											else{
												classValue=1;
											}
										}
									}
									else{
										classValue=1;
									}
								}
								else{
									if(ProtossSecondGas <= ProtossDarkTemplar){
										classValue=5;
									}
									else{
										classValue=1;
									}
								}
							}
							else{
								if(ProtossSecondPylon <= ProtossPylon){
									classValue=1;
								}
								else{
									if(ProtossFourthExpansion <= ProtossThirdExpansion){
										if(ProtossRoboBay <= ProtossSecondPylon){
											classValue=5;
										}
										else{
											classValue=2;
										}
									}
									else{
										classValue=5;
									}
								}
							}
						}
						else{
							classValue=2;
						}
					}
					else{
						classValue=2;
					}
				}
				else{
					classValue=1;
				}
			}
			else{
				classValue=1;
			}
		}
		else{
			if(ProtossForge <= ProtossFourthGatway){
				classValue=1;
			}
			else{
				classValue=5;
			}
		}
	}
	else{
		classValue=1;
	}
}
else{
	classValue=3;
}

*/
//bot_vs_bot_self 오토룰셋
/*
if(ProtossCitadel <= ProtossFirstExpansion){
	if(ProtossThirdExpansion <= ProtossArchives){
		if(ProtossSecondGatway <= ProtossRoboSupport){
			if(ProtossCitadel <= ProtossObservory){
				classValue=6;
			}
			else{
				classValue=2;
			}
		}
		else{
			classValue=3;
		}
	}
	else{
		classValue=1;
	}
}
else{
	if(ProtossFirstExpansion <= ProtossRoboBay){
		classValue=5;
	}
	else{
		if(ProtossSecondGas <= ProtossShuttle){
			classValue=2;
		}
		else{
			classValue=3;
		}
	}
}
*/

//호철 enemy룰셋으로 구한 것
/*
if(ProtossObs <= ProtossDarkTemplar){
	if(ProtossLegs <= ProtossGoon){
		if(ProtossSecondGas <= ProtossCitadel){
			if(ProtossSecondGas <= ProtossFirstExpansion){
				if(ProtossPylon <= ProtossGateway){
					if(ProtossSecondGas <= ProtossCore){
						if(ProtossSecondGas <= ProtossFirstGas){
							if(ProtossFirstGas <= ProtossPylon){
								classValue=6;
							}
							else{
								if(ProtossSecondGatway <= ProtossSecondPylon){
									classValue=5;
								}
								else{
									if(ProtossSecondGatway <= ProtossZealot){
										classValue=6;
									}
									else{
										classValue=5;
									}
								}
							}
						}
						else{
							classValue=6;
						}
					}
					else{
						classValue=5;
					}
				}
				else{
					classValue=6;
				}
			}
			else{
				classValue=5;
			}
		}
		else{
			classValue=0;
		}
	}
	else{
		if(ProtossObs <= ProtossReavor){
			if(ProtossObs <= ProtossLegs){
				if(ProtossThirdExpansion <= ProtossObs){
					if(ProtossSecondGas <= ProtossArchives){
						if(ProtossSecondGas <= ProtossShuttle){
							if(ProtossThirdExpansion <= ProtossSecondGatway){
								if(ProtossPylon <= ProtossZealot){
									classValue=3;
								}
								else{
									classValue=5;
								}
							}
							else{
								if(ProtossThirdGatway <= ProtossFirstExpansion){
									if(ProtossThirdGatway <= ProtossCannon){
										if(ProtossFirstExpansion <= ProtossCore){
											classValue=6;
										}
										else{
											if(ProtossFirstExpansion <= ProtossRoboBay){
												if(ProtossSecondExpansion <= ProtossFirstExpansion){
													if(ProtossRange <= ProtossForge){
														if(ProtossSecondGatway <= ProtossGateway){
															if(ProtossFirstGas <= ProtossCore){
																classValue=5;
															}
															else{
																classValue=6;
															}
														}
														else{
															classValue=6;
														}
													}
													else{
														classValue=5;
													}
												}
												else{
													classValue=5;
												}
											}
											else{
												classValue=2;
											}
										}
									}
									else{
										classValue=2;
									}
								}
								else{
									classValue=5;
								}
							}
						}
						else{
							classValue=3;
						}
					}
					else{
						classValue=1;
					}
				}
				else{
					if(ProtossGoon <= ProtossCannon){
						if(ProtossFourthGatway <= ProtossObs){
							if(ProtossThirdGatway <= ProtossShuttle){
								if(ProtossShuttle <= ProtossReavor){
									if(ProtossGroundArmor1 <= ProtossFourthExpansion){
										classValue=5;
									}
									else{
										if(ProtossSecondGatway <= ProtossCore){
											classValue=2;
										}
										else{
											classValue=5;
										}
									}
								}
								else{
									classValue=2;
								}
							}
							else{
								classValue=3;
							}
						}
						else{
							if(ProtossRange <= ProtossShuttleSpeed){
								if(ProtossCitadel <= ProtossObs){
									if(ProtossFourthExpansion <= ProtossThirdExpansion){
										classValue=2;
									}
									else{
										if(ProtossSecondGas <= ProtossTemplar){
											classValue=5;
										}
										else{
											classValue=1;
										}
									}
								}
								else{
									if(ProtossSecondGas <= ProtossFirstGas){
										if(ProtossPylon <= ProtossSecondGatway){
											classValue=2;
										}
										else{
											classValue=6;
										}
									}
									else{
										if(ProtossFirstExpansion <= ProtossObs){
											if(ProtossFirstGas <= ProtossRoboSupport){
												if(ProtossFirstExpansion <= ProtossRoboSupport){
													if(ProtossObservory <= ProtossShuttleSpeed){
														if(ProtossStorm <= ProtossShields2){
															if(ProtossFourthExpansion <= ProtossGroundArmor2){
																if(ProtossFirstExpansion <= ProtossFirstGas){
																	classValue=5;
																}
																else{
																	if(ProtossFirstGas <= ProtossThirdGatway){
																		if(ProtossTemplar <= ProtossArchon){
																			if(ProtossGateway <= ProtossPylon){
																				if(ProtossSecondGatway <= ProtossSecondExpansion){
																					if(ProtossArchives <= ProtossGroundWeapons1){
																						if(ProtossCore <= ProtossPylon){
																							classValue=2;
																						}
																						else{
																							if(ProtossThirdGatway <= ProtossReavor){
																								classValue=5;
																							}
																							else{
																								classValue=2;
																							}
																						}
																					}
																					else{
																						if(ProtossFirstGas <= ProtossPylon){
																							classValue=5;
																						}
																						else{
																							classValue=2;
																						}
																					}
																				}
																				else{
																					classValue=2;
																				}
																			}
																			else{
																				classValue=2;
																			}
																		}
																		else{
																			classValue=5;
																		}
																	}
																	else{
																		classValue=2;
																	}
																}
															}
															else{
																classValue=2;
															}
														}
														else{
															classValue=5;
														}
													}
													else{
														if(ProtossThirdExpansion <= ProtossLegs){
															classValue=3;
														}
														else{
															classValue=2;
														}
													}
												}
												else{
													classValue=3;
												}
											}
											else{
												classValue=3;
											}
										}
										else{
											if(ProtossFirstGas <= ProtossLegs){
												if(ProtossFourthGatway <= ProtossReavor){
													if(ProtossRoboBay <= ProtossReavor){
														classValue=2;
													}
													else{
														if(ProtossSecondExpansion <= ProtossArchives){
															classValue=2;
														}
														else{
															classValue=5;
														}
													}
												}
												else{
													if(ProtossCore <= ProtossGroundWeapons1){
														if(ProtossRoboBay <= ProtossArchon){
															if(ProtossSecondGatway <= ProtossLegs){
																if(ProtossThirdGatway <= ProtossObs){
																	if(ProtossTemplar <= ProtossForge){
																		classValue=3;
																	}
																	else{
																		classValue=2;
																	}
																}
																else{
																	if(ProtossShuttleSpeed <= ProtossReavor){
																		if(ProtossStorm <= ProtossFourthGatway){
																			classValue=3;
																		}
																		else{
																			if(ProtossFourthExpansion <= ProtossGroundArmor1){
																				classValue=2;
																			}
																			else{
																				classValue=3;
																			}
																		}
																	}
																	else{
																		if(ProtossObs <= ProtossRoboSupport){
																			if(ProtossSecondExpansion <= ProtossGroundWeapons2){
																				classValue=2;
																			}
																			else{
																				if(ProtossSecondGas <= ProtossForge){
																					classValue=3;
																				}
																				else{
																					classValue=2;
																				}
																			}
																		}
																		else{
																			if(ProtossShuttleSpeed <= ProtossGroundWeapons1){
																				if(ProtossFirstExpansion <= ProtossReavor){
																					classValue=2;
																				}
																				else{
																					if(ProtossShuttle <= ProtossRoboSupport){
																						classValue=2;
																					}
																					else{
																						classValue=3;
																					}
																				}
																			}
																			else{
																				classValue=2;
																			}
																		}
																	}
																}
															}
															else{
																classValue=3;
															}
														}
														else{
															classValue=2;
														}
													}
													else{
														classValue=3;
													}
												}
											}
											else{
												if(ProtossFirstGas <= ProtossForge){
													classValue=5;
												}
												else{
													classValue=2;
												}
											}
										}
									}
								}
							}
							else{
								if(ProtossForge <= ProtossGroundWeapons1){
									classValue=3;
								}
								else{
									classValue=2;
								}
							}
						}
					}
					else{
						if(ProtossCannon <= ProtossCitadel){
							classValue=5;
						}
						else{
							classValue=1;
						}
					}
				}
			}
			else{
				if(ProtossFirstExpansion <= ProtossArchon){
					if(ProtossFourthGatway <= ProtossGroundArmor1){
						if(ProtossDarkTemplar <= ProtossFirstExpansion){
							classValue=0;
						}
						else{
							classValue=5;
						}
					}
					else{
						classValue=0;
					}
				}
				else{
					classValue=1;
				}
			}
		}
		else{
			if(ProtossShuttle <= ProtossFourthGatway){
				if(ProtossFirstGas <= ProtossFirstExpansion){
					if(ProtossRange <= ProtossFourthGatway){
						if(ProtossRange <= ProtossForge){
							if(ProtossFirstExpansion <= ProtossShuttle){
								if(ProtossThirdGatway <= ProtossFirstExpansion){
									classValue=2;
								}
								else{
									if(ProtossGateway <= ProtossZealot){
										if(ProtossSecondGas <= ProtossThirdGatway){
											classValue=3;
										}
										else{
											if(ProtossSecondGas <= ProtossObservory){
												classValue=3;
											}
											else{
												classValue=5;
											}
										}
									}
									else{
										classValue=5;
									}
								}
							}
							else{
								if(ProtossRoboSupport <= ProtossGroundWeapons2){
									classValue=3;
								}
								else{
									if(ProtossSecondGas <= ProtossSecondExpansion){
										classValue=2;
									}
									else{
										classValue=3;
									}
								}
							}
						}
						else{
							if(ProtossPylon <= ProtossGateway){
								classValue=5;
							}
							else{
								classValue=3;
							}
						}
					}
					else{
						classValue=2;
					}
				}
				else{
					classValue=5;
				}
			}
			else{
				if(ProtossThirdGatway <= ProtossZealot){
					classValue=3;
				}
				else{
					classValue=5;
				}
			}
		}
	}
}
else{
	if(ProtossCore <= ProtossRoboSupport){
		if(ProtossDarkTemplar <= ProtossRoboBay){
			if(ProtossGroundWeapons1 <= ProtossDarkTemplar){
				if(ProtossArchives <= ProtossDarkTemplar){
					classValue=0;
				}
				else{
					classValue=5;
				}
			}
			else{
				if(ProtossFirstExpansion <= ProtossDarkTemplar){
					if(ProtossFirstGas <= ProtossZealot){
						classValue=1;
					}
					else{
						if(ProtossFourthGatway <= ProtossThirdExpansion){
							classValue=5;
						}
						else{
							classValue=1;
						}
					}
				}
				else{
					classValue=1;
				}
			}
		}
		else{
			if(ProtossSecondGatway <= ProtossCore){
				classValue=5;
			}
			else{
				classValue=2;
			}
		}
	}
	else{
		classValue=3;
	}
}


*/

        
    }
    if(m_Species.equals("Terran")){
        
        
        int TerranDepot;
if((int)instance.value(0)==0) TerranDepot=max+1; else TerranDepot=(int)instance.value(0);
int TerranGas;
if((int)instance.value(1)==0) TerranGas=max+1; else TerranGas=(int)instance.value(1);
int TerranSecondGas;
if((int)instance.value(2)==0) TerranSecondGas=max+1; else TerranSecondGas=(int)instance.value(2);
int TerranExpansion;
if((int)instance.value(3)==0) TerranExpansion=max+1; else TerranExpansion=(int)instance.value(3);
int TerranSecondExpansion;
if((int)instance.value(4)==0) TerranSecondExpansion=max+1; else TerranSecondExpansion=(int)instance.value(4);
int TerranThirdExpansion;
if((int)instance.value(5)==0) TerranThirdExpansion=max+1; else TerranThirdExpansion=(int)instance.value(5);
int TerranFourthExpansion;
if((int)instance.value(6)==0) TerranFourthExpansion=max+1; else TerranFourthExpansion=(int)instance.value(6);
int TerranBarracks;
if((int)instance.value(7)==0) TerranBarracks=max+1; else TerranBarracks=(int)instance.value(7);
int TerranSecondBarracks;
if((int)instance.value(8)==0) TerranSecondBarracks=max+1; else TerranSecondBarracks=(int)instance.value(8);
int TerranThirdBarracks;
if((int)instance.value(9)==0) TerranThirdBarracks=max+1; else TerranThirdBarracks=(int)instance.value(9);
int TerranMarine;
if((int)instance.value(10)==0) TerranMarine=max+1; else TerranMarine=(int)instance.value(10);
int TerranMedic;
if((int)instance.value(11)==0) TerranMedic=max+1; else TerranMedic=(int)instance.value(11);
int TerranFirebat;
if((int)instance.value(12)==0) TerranFirebat=max+1; else TerranFirebat=(int)instance.value(12);
int TerranBunker;
if((int)instance.value(13)==0) TerranBunker=max+1; else TerranBunker=(int)instance.value(13);
int TerranAcademy;
if((int)instance.value(14)==0) TerranAcademy=max+1; else TerranAcademy=(int)instance.value(14);
int TerranStem;
if((int)instance.value(15)==0) TerranStem=max+1; else TerranStem=(int)instance.value(15);
int TerranComsat;
if((int)instance.value(16)==0) TerranComsat=max+1; else TerranComsat=(int)instance.value(16);
int TerranEbay;
if((int)instance.value(17)==0) TerranEbay=max+1; else TerranEbay=(int)instance.value(17);
int TerranTurret;
if((int)instance.value(18)==0) TerranTurret=max+1; else TerranTurret=(int)instance.value(18);
int TerranInfantryAttack1;
if((int)instance.value(19)==0) TerranInfantryAttack1=max+1; else TerranInfantryAttack1=(int)instance.value(19);
int TerranInfantryArmor1;
if((int)instance.value(20)==0) TerranInfantryArmor1=max+1; else TerranInfantryArmor1=(int)instance.value(20);
int TerranInfantryAttack2;
if((int)instance.value(21)==0) TerranInfantryAttack2=max+1; else TerranInfantryAttack2=(int)instance.value(21);
int TerranInfantryArmor2;
if((int)instance.value(22)==0) TerranInfantryArmor2=max+1; else TerranInfantryArmor2=(int)instance.value(22);
int TerranFactory;
if((int)instance.value(23)==0) TerranFactory=max+1; else TerranFactory=(int)instance.value(23);
int TerranSecondFactory;
if((int)instance.value(24)==0) TerranSecondFactory=max+1; else TerranSecondFactory=(int)instance.value(24);
int TerranThirdFactory;
if((int)instance.value(25)==0) TerranThirdFactory=max+1; else TerranThirdFactory=(int)instance.value(25);
int TerranMachineShop;
if((int)instance.value(26)==0) TerranMachineShop=max+1; else TerranMachineShop=(int)instance.value(26);
int TerranSiege;
if((int)instance.value(27)==0) TerranSiege=max+1; else TerranSiege=(int)instance.value(27);
int TerranMines;
if((int)instance.value(28)==0) TerranMines=max+1; else TerranMines=(int)instance.value(28);
int TerranVulture;
if((int)instance.value(29)==0) TerranVulture=max+1; else TerranVulture=(int)instance.value(29);
int TerranTank;
if((int)instance.value(30)==0) TerranTank=max+1; else TerranTank=(int)instance.value(30);
int TerranGoliath;
if((int)instance.value(31)==0) TerranGoliath=max+1; else TerranGoliath=(int)instance.value(31);
int TerranArmory;
if((int)instance.value(32)==0) TerranArmory=max+1; else TerranArmory=(int)instance.value(32);
int TerranSecondArmory;
if((int)instance.value(33)==0) TerranSecondArmory=max+1; else TerranSecondArmory=(int)instance.value(33);
int TerranMetalAttack1;
if((int)instance.value(34)==0) TerranMetalAttack1=max+1; else TerranMetalAttack1=(int)instance.value(34);
int TerranMetalArmor1;
if((int)instance.value(35)==0) TerranMetalArmor1=max+1; else TerranMetalArmor1=(int)instance.value(35);
int TerranMetalAttack2;
if((int)instance.value(36)==0) TerranMetalAttack2=max+1; else TerranMetalAttack2=(int)instance.value(36);
int TerranMetalArmor2;
if((int)instance.value(37)==0) TerranMetalArmor2=max+1; else TerranMetalArmor2=(int)instance.value(37);
int TerranStarport;
if((int)instance.value(38)==0) TerranStarport=max+1; else TerranStarport=(int)instance.value(38);
int TerranControlTower;
if((int)instance.value(39)==0) TerranControlTower=max+1; else TerranControlTower=(int)instance.value(39);
int TerranWraith;
if((int)instance.value(40)==0) TerranWraith=max+1; else TerranWraith=(int)instance.value(40);
int TerranCloak;
if((int)instance.value(41)==0) TerranCloak=max+1; else TerranCloak=(int)instance.value(41);
int TerranDropship;
if((int)instance.value(42)==0) TerranDropship=max+1; else TerranDropship=(int)instance.value(42);
int TerranValkyrie;
if((int)instance.value(43)==0) TerranValkyrie=max+1; else TerranValkyrie=(int)instance.value(43);
int TerranScienceFacility;
if((int)instance.value(44)==0) TerranScienceFacility=max+1; else TerranScienceFacility=(int)instance.value(44);
int TerranVessel;
if((int)instance.value(45)==0) TerranVessel=max+1; else TerranVessel=(int)instance.value(45);
int TerranGhost;
if((int)instance.value(46)==0) TerranGhost=max+1; else TerranGhost=(int)instance.value(46);
int TerranNuke;
if((int)instance.value(47)==0) TerranNuke=max+1; else TerranNuke=(int)instance.value(47);
int TerranBattlecruiser;
if((int)instance.value(48)==0) TerranBattlecruiser=max+1; else TerranBattlecruiser=(int)instance.value(48);
int TerranAirAttack;
if((int)instance.value(49)==0) TerranAirAttack=max+1; else TerranAirAttack=(int)instance.value(49);
int TerranAirArmor;
if((int)instance.value(50)==0) TerranAirArmor=max+1; else TerranAirArmor=(int)instance.value(50);


if(TerranFactory <= TerranSecondBarracks){
	if(TerranMachineShop <= TerranSecondFactory){
		if(TerranTank <= TerranVulture){
			if(TerranTank <= TerranStarport){
				if(TerranSecondExpansion <= TerranTank){
					classValue=6;
				}
				else{
					if(TerranVulture <= TerranExpansion){
						classValue=4;
					}
					else{
						classValue=3;
					}
				}
			}
			else{
				classValue=5;
			}
		}
		else{
			if(TerranVulture <= TerranStarport){
				classValue=2;
			}
			else{
				classValue=5;
			}
		}
	}
	else{
		if(TerranVulture <= TerranSecondFactory){
			if(TerranVulture <= TerranStarport){
				classValue=2;
			}
			else{
				classValue=5;
			}
		}
		else{
			if(TerranStarport <= TerranSecondFactory){
				classValue=5;
			}
			else{
				classValue=1;
			}
		}
	}
}
else{
	classValue=0;
}

        
        
        
        

    }
    if(m_Species.equals("Zerg")){
        
        int ZergPool;
if((int)instance.value(0)==0) ZergPool=max+1; else ZergPool=(int)instance.value(0);
int ZergOverlord;
if((int)instance.value(1)==0) ZergOverlord=max+1; else ZergOverlord=(int)instance.value(1);
int ZergZergling;
if((int)instance.value(2)==0) ZergZergling=max+1; else ZergZergling=(int)instance.value(2);
int ZergZerglingSpeed;
if((int)instance.value(3)==0) ZergZerglingSpeed=max+1; else ZergZerglingSpeed=(int)instance.value(3);
int ZergCrackling;
if((int)instance.value(4)==0) ZergCrackling=max+1; else ZergCrackling=(int)instance.value(4);
int ZergBurrow;
if((int)instance.value(5)==0) ZergBurrow=max+1; else ZergBurrow=(int)instance.value(5);
int ZergGas;
if((int)instance.value(6)==0) ZergGas=max+1; else ZergGas=(int)instance.value(6);
int ZergSecondGas;
if((int)instance.value(7)==0) ZergSecondGas=max+1; else ZergSecondGas=(int)instance.value(7);
int ZergSecondHatch;
if((int)instance.value(8)==0) ZergSecondHatch=max+1; else ZergSecondHatch=(int)instance.value(8);
int ZergThirdHatch;
if((int)instance.value(9)==0) ZergThirdHatch=max+1; else ZergThirdHatch=(int)instance.value(9);
int ZergFourthHatch;
if((int)instance.value(10)==0) ZergFourthHatch=max+1; else ZergFourthHatch=(int)instance.value(10);
int ZergFifthHatch;
if((int)instance.value(11)==0) ZergFifthHatch=max+1; else ZergFifthHatch=(int)instance.value(11);
int ZergSixthHatch;
if((int)instance.value(12)==0) ZergSixthHatch=max+1; else ZergSixthHatch=(int)instance.value(12);
int ZergSeventhHatch;
if((int)instance.value(13)==0) ZergSeventhHatch=max+1; else ZergSeventhHatch=(int)instance.value(13);
int ZergHydraDen;
if((int)instance.value(14)==0) ZergHydraDen=max+1; else ZergHydraDen=(int)instance.value(14);
int ZergHydra;
if((int)instance.value(15)==0) ZergHydra=max+1; else ZergHydra=(int)instance.value(15);
int ZergHydraRange;
if((int)instance.value(16)==0) ZergHydraRange=max+1; else ZergHydraRange=(int)instance.value(16);
int ZergHydraSpeed;
if((int)instance.value(17)==0) ZergHydraSpeed=max+1; else ZergHydraSpeed=(int)instance.value(17);
int ZergLurker;
if((int)instance.value(18)==0) ZergLurker=max+1; else ZergLurker=(int)instance.value(18);
int ZergLair;
if((int)instance.value(19)==0) ZergLair=max+1; else ZergLair=(int)instance.value(19);
int ZergSpire;
if((int)instance.value(20)==0) ZergSpire=max+1; else ZergSpire=(int)instance.value(20);
int ZergMutalisk;
if((int)instance.value(21)==0) ZergMutalisk=max+1; else ZergMutalisk=(int)instance.value(21);
int ZergScourge;
if((int)instance.value(22)==0) ZergScourge=max+1; else ZergScourge=(int)instance.value(22);
int ZergAirAttack;
if((int)instance.value(23)==0) ZergAirAttack=max+1; else ZergAirAttack=(int)instance.value(23);
int ZergAirArmor;
if((int)instance.value(24)==0) ZergAirArmor=max+1; else ZergAirArmor=(int)instance.value(24);
int ZergQueenNest;
if((int)instance.value(25)==0) ZergQueenNest=max+1; else ZergQueenNest=(int)instance.value(25);
int ZergQueen;
if((int)instance.value(26)==0) ZergQueen=max+1; else ZergQueen=(int)instance.value(26);
int ZergHive;
if((int)instance.value(27)==0) ZergHive=max+1; else ZergHive=(int)instance.value(27);
int ZergGreaterSpire;
if((int)instance.value(28)==0) ZergGreaterSpire=max+1; else ZergGreaterSpire=(int)instance.value(28);
int ZergGardian;
if((int)instance.value(29)==0) ZergGardian=max+1; else ZergGardian=(int)instance.value(29);
int ZergDevour;
if((int)instance.value(30)==0) ZergDevour=max+1; else ZergDevour=(int)instance.value(30);
int ZergUltraliskCavern;
if((int)instance.value(31)==0) ZergUltraliskCavern=max+1; else ZergUltraliskCavern=(int)instance.value(31);
int ZergUltralisk;
if((int)instance.value(32)==0) ZergUltralisk=max+1; else ZergUltralisk=(int)instance.value(32);
int ZergUltraliskArmor;
if((int)instance.value(33)==0) ZergUltraliskArmor=max+1; else ZergUltraliskArmor=(int)instance.value(33);
int ZergUltraliskSpeed;
if((int)instance.value(34)==0) ZergUltraliskSpeed=max+1; else ZergUltraliskSpeed=(int)instance.value(34);
int ZergDefilerMound;
if((int)instance.value(35)==0) ZergDefilerMound=max+1; else ZergDefilerMound=(int)instance.value(35);
int ZergDefiler;
if((int)instance.value(36)==0) ZergDefiler=max+1; else ZergDefiler=(int)instance.value(36);
int ZergConsume;
if((int)instance.value(37)==0) ZergConsume=max+1; else ZergConsume=(int)instance.value(37);
int ZergevoDen;
if((int)instance.value(38)==0) ZergevoDen=max+1; else ZergevoDen=(int)instance.value(38);
int ZergMeleeWeapons1;
if((int)instance.value(39)==0) ZergMeleeWeapons1=max+1; else ZergMeleeWeapons1=(int)instance.value(39);
int ZergRangeWeapons1;
if((int)instance.value(40)==0) ZergRangeWeapons1=max+1; else ZergRangeWeapons1=(int)instance.value(40);
int ZergGroundArmor1;
if((int)instance.value(41)==0) ZergGroundArmor1=max+1; else ZergGroundArmor1=(int)instance.value(41);
int ZergMeleeWeapons2;
if((int)instance.value(42)==0) ZergMeleeWeapons2=max+1; else ZergMeleeWeapons2=(int)instance.value(42);
int ZergRangeWeapons2;
if((int)instance.value(43)==0) ZergRangeWeapons2=max+1; else ZergRangeWeapons2=(int)instance.value(43);
int ZergGroundArmor2;
if((int)instance.value(44)==0) ZergGroundArmor2=max+1; else ZergGroundArmor2=(int)instance.value(44);
int ZergCreep;
if((int)instance.value(45)==0) ZergCreep=max+1; else ZergCreep=(int)instance.value(45);
int ZergSunken;
if((int)instance.value(46)==0) ZergSunken=max+1; else ZergSunken=(int)instance.value(46);
int ZergSpore;
if((int)instance.value(47)==0) ZergSpore=max+1; else ZergSpore=(int)instance.value(47);


if(ZergHydraDen <= ZergSpire){
	if(ZergCrackling <= ZergHydraDen){
		classValue=6;
	}
	else{
		if(ZergLair <= ZergThirdHatch){
			if(ZergLurker <= ZergThirdHatch){
				if(ZergHydraRange <= ZergLurker){
					classValue=2;
				}
				else{
					if(ZergHydraDen <= ZergLair){
						classValue=2;
					}
					else{
						classValue=5;
					}
				}
			}
			else{
				if(ZergHydraDen <= ZergLair){
					classValue=2;
				}
				else{
					classValue=3;
				}
			}
		}
		else{
			if(ZergThirdHatch <= ZergHydraDen){
				classValue=4;
			}
			else{
				classValue=2;
			}
		}
	}
}
else{
	if(ZergThirdHatch <= ZergLair){
		classValue=1;
	}
	else{
		classValue=0;
	}
}

        
        
        
        

    }

    return classValue;
  }

  /**
   * Returns a description of this classifier.
   *
   * @return a description of this classifier as a string.
   */
  public String toString() {

    return ("IB1 classifier");
  }

  /**
   * Calculates the distance between two instances
   *
   * @param first the first instance
   * @param second the second instance
   * @return the distance between the two given instances
   */          
  private double distance(Instance first, Instance second) {

    
    
    double diff, distance = 0;

    for(int i = 0; i < m_Train.numAttributes(); i++) { 
      if (i == m_Train.classIndex()) {
	continue;
      }
      if (m_Train.attribute(i).isNominal()) {

	// If attribute is nominal
	if (first.isMissing(i) || second.isMissing(i) ||
	    ((int)first.value(i) != (int)second.value(i))) {
	  distance += 1;
	}
      } else {
	
	// If attribute is numeric
	if (first.isMissing(i) || second.isMissing(i)){
	  if (first.isMissing(i) && second.isMissing(i)) {
	    diff = 1;
	  } else {
	    if (second.isMissing(i)) {
	      diff = norm(first.value(i), i);
	    } else {
	      diff = norm(second.value(i), i);
	    }
	    if (diff < 0.5) {
	      diff = 1.0 - diff;
	    }
	  }
	} else {
	  diff = norm(first.value(i), i) - norm(second.value(i), i);
	}
	distance += diff * diff;
      }
    }
    
    return distance;
  }
    
  /**
   * Normalizes a given value of a numeric attribute.
   *
   * @param x the value to be normalized
   * @param i the attribute's index
   * @return the normalized value
   */
  private double norm(double x,int i) {

    if (Double.isNaN(m_MinArray[i])
	|| Utils.eq(m_MaxArray[i], m_MinArray[i])) {
      return 0;
    } else {
      return (x - m_MinArray[i]) / (m_MaxArray[i] - m_MinArray[i]);
    }
  }

  /**
   * Updates the minimum and maximum values for all the attributes
   * based on a new instance.
   *
   * @param instance the new instance
   */
  private void updateMinMax(Instance instance) {
    
    for (int j = 0;j < m_Train.numAttributes(); j++) {
      if ((m_Train.attribute(j).isNumeric()) && (!instance.isMissing(j))) {
	if (Double.isNaN(m_MinArray[j])) {
	  m_MinArray[j] = instance.value(j);
	  m_MaxArray[j] = instance.value(j);
	} else {
	  if (instance.value(j) < m_MinArray[j]) {
	    m_MinArray[j] = instance.value(j);
	  } else {
	    if (instance.value(j) > m_MaxArray[j]) {
	      m_MaxArray[j] = instance.value(j);
	    }
	  }
	}
      }
    }
  }
  
  /**
   * Returns the revision string.
   * 
   * @return		the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 5525 $");
  }

  /**
   * Main method for testing this class.
   *
   * @param argv should contain command line arguments for evaluation
   * (see Evaluation).
   */
  public static void main(String [] argv) {
     runClassifier(new RuleSet(), argv);
  }
}
