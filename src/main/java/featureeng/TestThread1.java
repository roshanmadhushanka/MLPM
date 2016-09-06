package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 9/6/16.
 */
public class TestThread1 extends Thread{
    DataSet dataSet;
    String categoryFeature;
    String[] features;
    int numBins;

    public TestThread1(DataSet dataSet, String categoryFeature, String[] features, int numBins){
        this.dataSet = dataSet;
        this.categoryFeature = categoryFeature;
        this.features = features;
        this.numBins = numBins;
    }

    public void add(){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "ProbabilityDistribution",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;
        for(String feature: features) {
            resultHeader = "prob_" + String.valueOf(numBins) + "_" + feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> probabilityDistribution = new ArrayList<String>();

            for (int i = 0; i < breakingPoints.size() - 1; i++) {
                start = breakingPoints.get(i);
                end = breakingPoints.get(i + 1);
                int dataSetSize = end - start;

                //Store double values correspond to string values
                ArrayList<Double> parsedValues = new ArrayList<Double>();

                double minValue = Double.MAX_VALUE;
                double maxValue = Double.MIN_VALUE;

                double currentValue;

                //Finding minimum and maximum value of the given data set
                for(int j=start; j<end; j++){
                    currentValue = Double.parseDouble(columnData.get(j));
                    if(currentValue > maxValue)
                        maxValue = currentValue;
                    if(currentValue < minValue)
                        minValue = currentValue;
                    parsedValues.add(currentValue);
                }

                int[] histogram = new int[numBins];

                //Calculating bin size
                double binSize = (maxValue - minValue) / numBins;

                if(binSize == 0.0){
                    //For not changing values consider probability as 1
                    for(int j=start; j<end; j++){
                        probabilityDistribution.add(String.valueOf(1.0));
                    }
                }else{
                    //Generating histogram
                    for(int j=0; j<parsedValues.size(); j++){
                        int bin = (int) ((parsedValues.get(j) - minValue) / binSize);
                        if(bin >=0 && bin < numBins)
                            histogram[bin] += 1;
                        else if(bin == numBins){
                            histogram[bin-1] += 1;
                        }
                    }

                    //Calculate and store probabilities
                    double[] probabilityDist = new double[numBins];
                    for(int j=0; j<numBins; j++){
                        probabilityDist[j] = 1.0 * histogram[j] / dataSetSize;
                    }

                    //Applying probabilities
                    for(int j=0; j<parsedValues.size(); j++){
                        int bin = (int) ((parsedValues.get(j) - minValue) / binSize);
                        if(bin >=0 && bin < numBins)
                            probabilityDistribution.add(String.valueOf(probabilityDist[bin]));
                        else if(bin == numBins){
                            probabilityDistribution.add(String.valueOf(probabilityDist[bin-1]));
                        }
                    }
                }
            }

            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, probabilityDistribution);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "ProbabilityDistribution" +
                "\nNumber of bins : " + numBins, "", "add");
    }

    public void run(){
        add();
    }
}
