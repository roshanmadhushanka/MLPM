package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/23/16.
 */
public class MovingAverage {
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
            resultHeader = "ma_" + String.valueOf(window) + "_" +feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> movingAverage = new ArrayList<String>();

            for(int i=0; i<breakingPoints.size()-1; i++){
                start = breakingPoints.get(i);
                end = breakingPoints.get(i+1);

                int pos = start;
                int count = 1;
                while(pos < start+window-1){
                    movingAverage.add("0.0");
                    pos++;
                }

                pos = start + window - 1;
                while(pos < end){
                    Double tot = 0.0;
                    for(int j = pos-window+1; j <= pos; j++){
                        tot += Double.parseDouble(columnData.get(j));
                    }
                    movingAverage.add(String.valueOf(tot/window));
                    pos++;
                }
            }
            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, movingAverage);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Moving average calculate complete" +
                "\nWindow : " + window, "MovingAverage", "add");

    }
}
