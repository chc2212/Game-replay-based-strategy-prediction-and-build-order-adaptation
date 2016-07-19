import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IB1;  //knn
import weka.classifiers.rules.NNge;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;  //random comuttie
import weka.classifiers.trees.REPTree;   
import weka.classifiers.meta.Bagging;   //bagging
import weka.classifiers.meta.RotationForest;   //RotationForest
import weka.classifiers.meta.RandomCommittee;
import weka.core.Instance;
import weka.core.Instances;




public class BuildClassification {
	
	int numFolds = 10;

	public static void main(String[] args) {
		if (args.length == 0) {
			new BuildClassification("traces/scmPvP_Protoss_Mid.arff"); 
			return;
		}
		
		new BuildClassification(args[0]);
	}
	
	public BuildClassification(String file) {
        System.out.println("cycle" + "\t" + "Nnge" + "\t" + "Knn" + "\t" + "J48" + "\t" + "RuleSet" + "\t" + "bagging" + "\t" + "RandomComuttie" + "\t" + "RandomForest" + "\t" + "RorationForest");

       

        
		Classifier classifier1 = new NNge();
		Classifier classifier2 = new IB1(); // KNN
		Classifier classifier3 = new J48(); // Decision Tree
                RuleSet classifier4 = new RuleSet();
                Classifier classifier5 = new Bagging(); //bagging
                Classifier classifier6 = new RandomCommittee(); // randomcomuttie
                Classifier classifier7 = new RandomForest(); // Randomforest
                Classifier classifier8 = new RotationForest(); // RorationForest
            
       

                if(file.indexOf("Protoss")>0) classifier4.m_Species="Protoss";
                if(file.indexOf("Terran")>0) classifier4.m_Species="Terran";
                if(file.indexOf("Zerg")>0) classifier4.m_Species="Zerg";
         

		for (int cycle = 000; cycle <= 15000; cycle += 500) {		
			try {
				Instances data = new Instances(new BufferedReader(new FileReader(file)));
				data.setClassIndex(data.numAttributes() - 1);
                                // Attribute의 Index는 0부터 시작하는 것으로 보임
					
				double accuracy1 = test(classifier1, data, cycle, false, 0, 0);
				double accuracy2 = test(classifier2, data, cycle, false, 0, 0);
				double accuracy3 = test(classifier3, data, cycle, false, 0, 0);
                                double accuracy4 = test(classifier4, data, cycle, false, 0, 0);
                                 double accuracy5 = test(classifier5, data, cycle, false, 0, 0);
                                  double accuracy6 = test(classifier6, data, cycle, false, 0, 0);
                                   double accuracy7 = test(classifier7, data, cycle, false, 0, 0);
                                     double accuracy8 = test(classifier8, data, cycle, false, 0, 0);

		        System.out.println(cycle + "\t" + accuracy1 + "\t" + accuracy2 + "\t" + accuracy3 + "\t" + accuracy4 + "\t" + accuracy5 + "\t" + accuracy6 +  "\t" + accuracy7 +  "\t" + accuracy8);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}			
	}


	public double test(Classifier classifier, Instances data, int gameCycle, boolean translate, double noise, double missing) {
		
		int correct = 0;
		int tested = 0;

		try {
		
		for (int i=0; i<numFolds; i++) {
			Instances train = data.trainCV(numFolds, i);
			Instances test = data.testCV(numFolds, i);

			int[] value = new int[test.numAttributes()];
			int[] count = new int[test.numAttributes()];

			
			// transform the training data
			for (int index=0; index<train.numInstances(); index++) {
				Instance instance = train.instance(index);
				
				for (int attribute=0; attribute<test.numAttributes() - 1; attribute++) {
					if (instance.value(attribute) > 0) {
						value[attribute] += instance.value(attribute);
						count[attribute]++;
					}

					if (instance.value(attribute) > gameCycle) {
						instance.setValue(attribute, 0);
					}
				}
			}

			// transform the testing data
			for (int index=0; index<test.numInstances(); index++) {
				Instance instance = test.instance(index);
				
				for (int attribute=0; attribute<test.numAttributes() - 1; attribute++) {
					if (instance.value(attribute) > gameCycle) {
						instance.setValue(attribute, 0);
					}

					// add noise
					if (instance.value(attribute) > 0) {
						if (Math.random() < 0.5) {
							instance.setValue(attribute, instance.value(attribute) + Math.random()*noise);
						}
						else {
							instance.setValue(attribute, instance.value(attribute) - Math.random()*noise);
						}
					}
					
					// add missing attributes
					if (Math.random() < missing) {
						instance.setValue(attribute, 0);
					}
				}
			}

			tested += test.numInstances();
			correct += testClassifier(classifier, train, test);			
		}
		
		}
		catch (Exception e) {e.printStackTrace(); }

		return (double)correct/(double)tested;
	}
	
	public int testClassifier(Classifier classifier, Instances train, Instances test) {		
		int correct = 0;
		
		try {
			classifier.buildClassifier(train);
			
			for (int i = 0; i < test.numInstances(); i++) {
				double pred = classifier.classifyInstance(test.instance(i));

				if (pred == test.instance(i).classValue()) {
					correct++;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return correct;
	}
}
