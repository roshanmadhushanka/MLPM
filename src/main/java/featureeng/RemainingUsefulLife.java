package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/30/16.
 */
public class RemainingUsefulLife {
    //This a a specific method for the data set
    public static void add(DataSet dataSet, String categoryFeature, String feature){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "RemainingUsefulLife",
                    "add");
            return;
        }

        int start;
        int end;

        ArrayList<String> columnData = dataSet.getDataColumn(feature);
        ArrayList<String> remainingTime = new ArrayList<String>();
        String resultHeader = "RUL";

        for(int i=0; i<breakingPoints.size()-1; i++) {
            start = breakingPoints.get(i);
            end = breakingPoints.get(i + 1);

            for(int j=end-1; j>=start; j--){
                Integer cycle = Integer.parseInt(columnData.get(j));
                remainingTime.add(String.valueOf(cycle-1));
            }
        }

        //Add column header
        dataSet.addFeature(resultHeader);
        //Add column data
        dataSet.addDataColumn(resultHeader, remainingTime);
    }
}
