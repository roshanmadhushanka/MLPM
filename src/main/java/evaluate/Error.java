package evaluate;

import log.Logger;
import log.MESSAGE_TYPE;

import java.util.ArrayList;

/**
 * Created by wso2123 on 8/31/16.
 */
public class Error {
    public static double meanAbsolutePercentError(ArrayList<Double> actual, ArrayList<Double> predicted){
        double value = 0.0;
        if(actual.size() != predicted.size()){
            Logger.log(MESSAGE_TYPE.WARNING, "Actual and Predicted data sizes are not equal",
                    "Error", "meanAbsolutePercentError");
            return value;
        }

        double absolutePrecentageDiff = 0.0;
        double actualValue;
        double predictedValue;

        for(int i=0; i<actual.size(); i++){
            actualValue = actual.get(i);
            predictedValue = predicted.get(i);
            absolutePrecentageDiff += Math.abs(actualValue - predictedValue) / Math.abs(actualValue);
        }

        value = 100 * absolutePrecentageDiff / actual.size();

        return value;
    }

    public static double rootMeanSquaredError(ArrayList<Double> actual, ArrayList<Double> predicted){
        double value = 0.0;
        if(actual.size() != predicted.size()){
            Logger.log(MESSAGE_TYPE.WARNING, "Actual and Predicted data sizes are not equal",
                    "Error", "rootMeanSquaredError");
            return value;
        }

        double totalDifference = 0.0;
        double actualValue;
        double predictedValue;

        for(int i=0; i<actual.size(); i++){
            actualValue = actual.get(i);
            predictedValue = predicted.get(i);
            totalDifference += Math.pow((actualValue - predictedValue), 2);
        }

        value = Math.sqrt(totalDifference/actual.size());

        return value;
    }

    public static double rootMeanSquarePercentageError(ArrayList<Double> actual, ArrayList<Double> predicted){
        double value = 0.0;
        if(actual.size() != predicted.size()){
            Logger.log(MESSAGE_TYPE.WARNING, "Actual and Predicted data sizes are not equal",
                    "Error", "rootMeanSquarePercentageError");
            return value;
        }

        double totalDifference = 0.0;
        double actualValue;
        double predictedValue;

        for(int i=0; i<actual.size(); i++){
            actualValue = actual.get(i);
            predictedValue = predicted.get(i);
            totalDifference += Math.pow(((actualValue - predictedValue)/actualValue), 2);
        }

        value = 100 * Math.sqrt(totalDifference/actual.size());

        return value;
    }
}
