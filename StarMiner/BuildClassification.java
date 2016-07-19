import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IB1;
import weka.classifiers.rules.NNge;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;


public class BuildClassification {
	
	int numFolds = 10;

	public static void main(String[] args) {
		if (args.length == 0) {
			new BuildClassification("traces/scmPvT_Protoss_Mid.arff");
			return;
		}
		
		new BuildClassification(args[0]);
	}
	
	public BuildClassification(String file) {
        System.out.println("cycle" + "\t" + "Classifier 1" + "\t" + "Classifier 2" + "\t" + "Classifier 3");
		
		Classifier classifier1 = new NNge();
		Classifier classifier2 = new IB1();
		Classifier classifier3 = new J48();

		for (int cycle = 000; cycle <= 15000; cycle += 500) {		
			try {
				Instances data = new Instances(new BufferedReader(new FileReader(file)));
				data.setClassIndex(data.numAttributes() - 1);
					
				double accuracy1 = test(classifier1, data, cycle, false, 0, 0);
				double accuracy2 = test(classifier2, data, cycle, false, 0, 0);
				double accuracy3 = test(classifier3, data, cycle, false, 0, 0);

		        System.out.println(cycle + "\t" + accuracy1 + "\t" + accuracy2 + "\t" + accuracy3);
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
