package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/23/16.
 */
public class StandardDeviation {
    public static void add(DataSet dataSet, String categoryFeature, String features[], int window){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "MovingAverage",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;

        for(String feature: features){
            resultHeader = "sd_" + String.valueOf(window) + "_" +feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> standardDeviation = new ArrayList<String>();

            for(int i=0; i<breakingPoints.size()-1; i++){
                start = breakingPoints.get(i);
                end = breakingPoints.get(i+1);

                int pos = start;
                while(pos < start+window-1){
                    standardDeviation.add("0.0");
                    pos++;
                }

                pos = start + window - 1;
                while(pos < end){
                    Double tot = 0.0;
                    Double avg = 0.0;
                    Double var = 0.0;

                    for(int j = pos-window+1; j <= pos; j++){
                        tot += Double.parseDouble(columnData.get(j));
                    }

                    avg = tot / window;

                    for(int j = pos-window+1; j <= pos; j++){
                        var += Math.pow((Double.parseDouble(columnData.get(j))-avg), 2);
                    }
                    standardDeviation.add(String.valueOf(Math.sqrt(var/window)));
                    pos++;
                }
            }
            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, standardDeviation);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Moving standard deviation calculate complete" +
                "\nWindow : " + window, "StandardDeviation", "add");

    }
}
