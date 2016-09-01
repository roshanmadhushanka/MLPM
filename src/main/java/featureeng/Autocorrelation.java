package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wso2123 on 8/25/16.
 */
public class Autocorrelation {
    public static void add(DataSet dataSet, String categoryFeature, String features[], int window, int lag){
        if(lag > window){
            Logger.log(MESSAGE_TYPE.WARNING, "Lag cannot be greater than window size", "Autocorrelation",
                    "add");
            return;
        }

        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "Autocorrelation",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;

        for(String feature: features) {
            resultHeader = "ac_" + String.valueOf(window) + "_" + String.valueOf(lag) + "_" + feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> autocorrelation = new ArrayList<String>();

            for (int i = 0; i < breakingPoints.size() - 1; i++) {
                start = breakingPoints.get(i);
                end = breakingPoints.get(i + 1);

                int pos = start;
                while (pos < start + window - 1) {
                    autocorrelation.add("0.0");
                    pos++;
                }

                pos = start + window - 1;
                while (pos < end) {
                    Double tot = 0.0; //Total
                    Double avg = 0.0; //Average
                    Double var = 0.0; //Variance

                    ArrayList<Double> values = new ArrayList<Double>();

                    for (int j = pos - window + 1; j <= pos; j++) {
                        Double val = Double.parseDouble(columnData.get(j));
                        tot += val;
                        values.add(val);
                    }

                    avg = tot / window;

                    for (int j = 0; j < values.size(); j++) {
                        var += Math.pow((values.get(j) - avg), 2);
                    }

                    //Shifted values of the same data set
                    ArrayList<Double> shiftedValues = new ArrayList<Double>(values.subList(lag, values.size()));

                    //Calculate autocorrelation
                    Double result;
                    if(var == 0.0){
                        result = 1.0;
                    }else{
                        Double val = 0.0;
                        for(int j=0; j<shiftedValues.size(); j++){
                            val += (values.get(j) - avg)*(shiftedValues.get(j) - avg);
                        }
                        result = val / var;
                    }
                    autocorrelation.add(String.valueOf(result));
                    pos++;
                }
            }

            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, autocorrelation);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Autocorrelation calculate complete" +
                "\nWindow : " + window +
                "\nLag    : " + lag, "Autocorrelation", "add");
    }

    public static ArrayList<String> get(DataSet dataSet, String categoryFeature, String feature, int window, int lag){
        if(lag > window){
            Logger.log(MESSAGE_TYPE.WARNING, "Lag cannot be greater than window size", "Autocorrelation",
                    "add");
            return null;
        }

        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "Autocorrelation",
                    "add");
            return null;
        }

        int start;
        int end;

        ArrayList<String> columnData = dataSet.getDataColumn(feature);
        ArrayList<String> autocorrelation = new ArrayList<String>();

        for (int i = 0; i < breakingPoints.size() - 1; i++) {
            start = breakingPoints.get(i);
            end = breakingPoints.get(i + 1);

            int pos = start;
            while (pos < start + window - 1) {
                autocorrelation.add("0.0");
                pos++;
            }

            pos = start + window - 1;
            while (pos < end) {
                Double tot = 0.0; //Total
                Double avg = 0.0; //Average
                Double var = 0.0; //Variance

                ArrayList<Double> values = new ArrayList<Double>();

                for (int j = pos - window + 1; j <= pos; j++) {
                    Double val = Double.parseDouble(columnData.get(j));
                    tot += val;
                    values.add(val);
                }

                avg = tot / window;

                for (int j = 0; j < values.size(); j++) {
                    var += Math.pow((values.get(j) - avg), 2);
                }

                //Shifted values of the same data set
                ArrayList<Double> shiftedValues = new ArrayList<Double>(values.subList(lag, values.size()));

                //Calculate autocorrelation
                Double result;
                if (var == 0.0) {
                    result = 1.0;
                } else {
                    Double val = 0.0;
                    for (int j = 0; j < shiftedValues.size(); j++) {
                        val += (values.get(j) - avg) * (shiftedValues.get(j) - avg);
                    }
                    result = val / var;
                }
                autocorrelation.add(String.valueOf(result));
                pos++;
            }
        }
        return autocorrelation;
    }

    public static double measure(ArrayList<String> values){
        double tot = 0.0;

        return tot;
    }

}
