package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by wso2123 on 8/30/16.
 */
public class Percentile {
    public static void add(DataSet dataSet, String categoryFeature, String features[], int window, double percentile){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "Percentile",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;

        for(String feature: features) {
            resultHeader = "percent_" + String.valueOf(window) + "_" + percentile + "_" + feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> percentileValues = new ArrayList<String>();

            for (int i = 0; i < breakingPoints.size() - 1; i++) {
                start = breakingPoints.get(i);
                end = breakingPoints.get(i + 1);

                int pos = start;
                while (pos < start + window - 1) {
                    percentileValues.add("0.0");
                    pos++;
                }

                pos = start + window - 1;
                while (pos < end) {
                    Double val = 0.0;
                    ArrayList<Double> tempArray = new ArrayList<Double>();
                    for (int j = pos - window + 1; j <= pos; j++) {
                        val = Double.parseDouble(columnData.get(j));
                        tempArray.add(val);
                    }
                    Collections.sort(tempArray);

                    //Calculate index
                    double indexD = 1.0 * percentile * window;
                    if(indexD % 1 == 0){
                        //Whole Number
                        int indexI = (int) indexD;
                        try{
                            double average = 1.0*(tempArray.get(indexI) + tempArray.get(indexI-1)) / 2;
                            percentileValues.add(String.valueOf(average));
                        }catch (ArrayIndexOutOfBoundsException e){
                            Logger.log(MESSAGE_TYPE.WARNING, "Array index out of range, Please " +
                                    "adjust window or percentile", "Percentile", "add");
                            return;
                        }
                    }else{
                        //Not a whole number
                        int indexI = (int) indexD;
                        try{
                            percentileValues.add(String.valueOf(tempArray.get(indexI)));
                        }catch (ArrayIndexOutOfBoundsException e){
                            Logger.log(MESSAGE_TYPE.WARNING, "Array index out of range, Please " +
                                    "adjust window or percentile", "Percentile", "add");
                            return;
                        }
                    }
                    pos++;
                }
            }

            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, percentileValues);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Percentile calculate complete" +
                "\nWindow     : " + window +
                "\nPercentile : " + percentile, "Percentile", "add");
    }
}
