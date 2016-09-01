package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/29/16.
 */
public class Entropy {
    public static void add(DataSet dataSet, String categoryFeature, String[] features, int numBins){
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
            resultHeader = "entropy_" + String.valueOf(numBins) + "_" + feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> entropy = new ArrayList<String>();

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
                    //For not changing values consider probability as 1 then entropy is 0
                    for(int j=start; j<end; j++){
                        entropy.add(String.valueOf(0.0));
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

                    //Calculating entropy values
                    for(int j=0; j<parsedValues.size(); j++){
                        double entropyValue = 0.0;
                        int bin = (int) ((parsedValues.get(j) - minValue) / binSize);
                        if(bin >=0 && bin < numBins){
                            entropyValue = Math.abs(probabilityDist[bin] * Math.log10(probabilityDist[bin]));
                        }
                        else if(bin == numBins){
                            entropyValue = Math.abs(probabilityDist[bin-1] * Math.log10(probabilityDist[bin-1]));
                        }
                        entropy.add(String.valueOf(entropyValue));
                    }
                }
            }

            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, entropy);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Entropy" +
                "\nNumber of bins : " + numBins, "", "add");
    }
}
