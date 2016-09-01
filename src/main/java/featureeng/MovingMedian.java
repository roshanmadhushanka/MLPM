package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by wso2123 on 8/24/16.
 */
public class MovingMedian {
    public static void add(DataSet dataSet, String categoryFeature, String features[], int window){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "MovingMedian",
                    "add");
            return;
        }

        int start;
        int end;
        String resultHeader;

        for(String feature: features) {
            resultHeader = "mm_" + String.valueOf(window) + "_" + feature;

            ArrayList<String> columnData = dataSet.getDataColumn(feature);
            ArrayList<String> movingMedian = new ArrayList<String>();

            for (int i = 0; i < breakingPoints.size() - 1; i++) {
                start = breakingPoints.get(i);
                end = breakingPoints.get(i + 1);

                int pos = start;
                while (pos < start + window - 1) {
                    movingMedian.add("0.0");
                    pos++;
                }

                pos = start + window - 1;
                while (pos < end) {
                    Double val = 0.0;
                    /*
                    tempArray stores values within the window in order to add the median value.
                     */
                    ArrayList<Double> tempArray = new ArrayList<Double>();
                    for (int j = pos - window + 1; j <= pos; j++) {
                        val = Double.parseDouble(columnData.get(j));
                        tempArray.add(val);
                    }
                    Collections.sort(tempArray);

                    //Find median for odd/even window sizes
                    if(window % 2 == 0){
                        double mean = (tempArray.get(window/2) + tempArray.get(window/2 - 1)) / 2.0;
                        movingMedian.add(String.valueOf(mean));
                    }else{
                        movingMedian.add(String.valueOf(tempArray.get(window/2)));
                    }
                    pos++;
                }
            }
            //Add column header
            dataSet.addFeature(resultHeader);
            //Add column data
            dataSet.addDataColumn(resultHeader, movingMedian);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Moving median calculate complete" +
                "\nWindow : " + window, "MovingMedian", "add");
    }
}
