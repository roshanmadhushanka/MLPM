package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/29/16.
 */
public class Kurtosis {
    public static void add(DataSet dataSet, String categoryFeature, String features[], int window){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "Kurtosis",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;

        for(String feature: features) {
            resultHeader = "kurtosis_" + String.valueOf(window) + "_" + feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> zScore = new ArrayList<String>();

            for (int i = 0; i < breakingPoints.size() - 1; i++) {
                start = breakingPoints.get(i);
                end = breakingPoints.get(i + 1);

                int pos = start;
                while (pos < start + window - 1) {
                    zScore.add("0.0");
                    pos++;
                }

                pos = start + window - 1;
                while (pos < end) {
                    Double tot = 0.0; //Total
                    Double avg = 0.0; //Average
                    Double var = 0.0; //Variance
                    Double std = 0.0; //Standard Deviation
                    ArrayList<Double> parsedValues = new ArrayList<Double>();

                    for (int j = pos - window + 1; j <= pos; j++) {
                        Double val = Double.parseDouble(columnData.get(j));
                        tot += val;
                        parsedValues.add(val);
                    }

                    avg = tot / window;

                    for (int j = 0; j < parsedValues.size(); j++) {
                        var += Math.pow((parsedValues.get(j) - avg), 2);
                    }

                    std = Math.sqrt(var / window);
                    Double result;
                    if (std == 0.0) {
                        result = 0.0;
                    } else {
                        result = Math.pow((parsedValues.get(parsedValues.size() - 1) - avg), 4) / (Math.pow(std, 4) * window);
                    }
                    zScore.add(String.valueOf(result));
                    pos++;
                }
            }


            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, zScore);
        }


        Logger.log(MESSAGE_TYPE.SUCCESS, "Kurtosis calculate complete" +
                "\nWindow : " + window, "Kurtosis", "add");
    }
}
