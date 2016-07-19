import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.M5P;
import weka.core.Instance;
import weka.core.Instances;


public class BuildRegression {
	
	int numFolds = 10;

	public static void main(String[] args) {
		if (args.length == 0) {
			new BuildRegression("traces/scmPvT_Protoss_Mid.arff");
			return;
		}
		
		new BuildRegression(args[0]);
	}
	
	public BuildRegression(String file) {
		
		Classifier classifier1 = new ZeroR();
		Classifier classifier2 = new LinearRegression();
		AdditiveRegression classifier3 = new AdditiveRegression();
		M5P classifier4 = new M5P();

        System.out.println("UnitType" + "\t" + "Classifier 1" + "\t" + "Classifier 2" + "\t" + "Classifier 3" + "\t" + "Classifier 4");
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(file)));

			for (int attIndex=0; attIndex<data.numAttributes(); attIndex++) {
				data.setClassIndex(attIndex);
				String attribute = data.attribute(attIndex).name();
				
				double[] error1 = test(classifier1, data, attIndex);
				double accuracy1 = mean(error1);
		        		        
				double[] error2 = test(classifier2, data, attIndex);
				double accuracy2 = mean(error2);

				double[] error3 = test(classifier3, data, attIndex);
				double accuracy3 = mean(error3);

				double[] error4 = test(classifier4, data, attIndex);
				double accuracy4 = mean(error4);

		        System.out.println(attribute.substring(7) + "\t" + accuracy1 + "\t" + accuracy2 + "\t" + accuracy3 + "\t" + accuracy4);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public double[] test(Classifier classifier, Instances data, int attIndex) {

		// filter out examples that do not have a time for the given attribute
		Instances newData = data.stringFreeStructure();
		newData.delete();
				
		for (int index=0; index<data.numInstances(); index++) {
			
			// make a copy of the attribute
			Instance instance = (Instance)data.instance(index).copy();
			
			if (instance.value(attIndex) > 0) {
				newData.add(instance);
				
				double time = instance.value(attIndex);
				for (int attribute=0; attribute<data.numAttributes(); attribute++) {
					if (attribute != attIndex) {
						if (instance.value(attribute) > time) {
							instance.setValue(attribute, 0);
						}
					}
				}
			}
		}

		data = newData;
		if (data.numInstances() < numFolds) {
			return new double[] {0};
		}
		
		double[] error = new double[data.numInstances()];		

		int endex = 0;
		try {		
		for (int i=0; i<numFolds; i++) {
			Instances train = data.trainCV(numFolds, i);
			Instances test = data.testCV(numFolds, i);

			double[] result = testClassifier(classifier, train, test);			
			for (int index=0; index<result.length; index++) {
				error[endex] = result[index];
				endex++;
			}
		}		
		}
		catch (Exception e) {e.printStackTrace(); }

		return error;
	}
	
	public double[] testClassifier(Classifier classifier, Instances train, Instances test) {		
		double[] error = new double[test.numInstances()];
		
		try {
			classifier.buildClassifier(train);
			
			for (int i = 0; i < test.numInstances(); i++) {
				double pred = classifier.classifyInstance(test.instance(i));
				error[i] = Math.abs(pred - test.instance(i).classValue());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return error;
	}
	
	public double mean(double[] values) {
		if (values.length == 0) {
			return 0;
		}
		else {
			double total = 0;
			for (double val : values) {
				total += val;
			}
			
			return total/(double)values.length;
		}
	}	
}
