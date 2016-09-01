package featureeng;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/23/16.
 */
public class BinaryClassification {
    public static void add(DataSet dataSet, String categoryFeature, int window, String positiveChar, String negativeChar){
        ArrayList<Integer> breakingPoints = dataSet.getBreakingPoints(categoryFeature);

        if(breakingPoints == null){
            Logger.log(MESSAGE_TYPE.WARNING, "Unable to add separations", "BinaryClassification",
                    "add");
            return;
        }

        int start;
        int end;
        ArrayList<String> binaryClassificationData = new ArrayList<String>();

        for(int i=0; i<breakingPoints.size()-1; i++){
            start = breakingPoints.get(i);
            end = breakingPoints.get(i+1);

            int pos = start;
            //Add negative behaviour
            while(pos < end-window){
                binaryClassificationData.add(negativeChar);
                pos++;
            }

            //Add positive behaviour
            while(pos < end){
                binaryClassificationData.add(positiveChar);
                pos++;
            }
        }

        dataSet.addFeature("Binary-Classification");

        dataSet.addDataColumn("Binary-Classification", binaryClassificationData);

        Logger.log(MESSAGE_TYPE.SUCCESS, "Binary classification complete" +
                "\nWindow : " + window +
                "\nRows   : " + dataSet.getRowCount() + " affected","BinaryClassification", "add");

    }
}
