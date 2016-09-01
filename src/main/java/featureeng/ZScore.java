package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wso2123 on 8/24/16.
 */
public class ZScore {
    public static void add(DataSet dataSet, String categoryFeature, String features[], int window, boolean replaceOriginal){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "ZScore",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;

        for(String feature: features){
            resultHeader = "z_" + String.valueOf(window) + "_" +feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> zScore = new ArrayList<String>();

            for(int i=0; i<breakingPoints.size()-1; i++){
                start = breakingPoints.get(i);
                end = breakingPoints.get(i+1);

                int pos = start;
                while(pos < start+window-1){
                    zScore.add("0.0");
                    pos++;
                }

                pos = start + window - 1;
                while(pos < end){
                    Double tot = 0.0; //Total
                    Double avg = 0.0; //Average
                    Double var = 0.0; //Variance
                    Double std = 0.0; //Standard Deviation
                    ArrayList<Double> parsedValues = new ArrayList<Double>();

                    for(int j = pos-window+1; j <= pos; j++){
                        Double val = Double.parseDouble(columnData.get(j));
                        tot += val;
                        parsedValues.add(val);
                    }

                    avg = tot / window;

                    for(int j = 0; j < parsedValues.size();  j++){
                        var += Math.pow((parsedValues.get(j)-avg), 2);
                    }

                    std = Math.sqrt(var/window);
                    Double result;
                    if(std == 0.0){
                        result = 0.0;
                    }else{
                        result = (parsedValues.get(parsedValues.size()-1) - avg) / std;
                    }
                    zScore.add(String.valueOf(result));
                    pos++;
                }
            }

            if(!replaceOriginal){
                //Add as a new feature
                resultHeader = "za_" + "_" +feature;
                //Add column header
                dataSet.addFeature(resultHeader);
                //Add column data
                dataSet.addDataColumn(resultHeader, zScore);

            }else{
                //Replace existing feature
                dataSet.replaceDataColumn(feature, zScore);
            }
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Moving Z score calculate complete" +
                "\nWindow : " + window, "ZScore", "add");
    }

    public static void add(DataSet dataSet, String categoryFeature, String features[], boolean replaceOriginal){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "ZScore",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;

        for(String feature: features){


            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> zScore = new ArrayList<String>();

            for(int i=0; i<breakingPoints.size()-1; i++){
                ArrayList<Double> parsedDouble = new ArrayList<Double>();
                start = breakingPoints.get(i);
                end = breakingPoints.get(i+1);

                double tot = 0.0; //Total
                double avg = 0.0; //Average
                double var = 0.0; //Variance
                double std = 0.0; //Standard deviation

                //Find total
                for(int j=start; j<end; j++){
                    Double val = Double.parseDouble(columnData.get(j));
                    parsedDouble.add(val);
                    tot += val;
                }

                int size = end - start;

                //Calculate average
                avg = tot / size;

                //Calculate variance of the given block
                for(int j=0; j<parsedDouble.size(); j++){
                    var += Math.pow((parsedDouble.get(j) - avg), 2);
                }

                //Calculate standard deviation
                std = Math.sqrt(var/size);

                Double result;
                if(std == 0.0){
                    //Since standard deviation is 0 add the maximum value to the column
                    result = 0.0;
                    for(int j=0; j<parsedDouble.size(); j++){
                        zScore.add(String.valueOf(result));
                    }
                }else{
                    //Calculate z score
                    for(int j=0; j<parsedDouble.size(); j++){
                        result = (parsedDouble.get(j) - avg) / std;
                        zScore.add(String.valueOf(result));
                    }
                }

            }

            if(!replaceOriginal){
                //Add as a new feature
                resultHeader = "za_" + "_" +feature;
                //Add column header
                dataSet.addFeature(resultHeader);
                //Add column data
                dataSet.addDataColumn(resultHeader, zScore);

            }else{
                //Replace existing feature
                dataSet.replaceDataColumn(feature, zScore);
            }

        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Moving Z score calculate complete"
                , "ZScore", "add");
    }
}
