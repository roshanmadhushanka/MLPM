package data;

import log.Logger;
import log.MESSAGE_TYPE;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wso2123 on 8/22/16.
 */
public class DataSet {
    private ArrayList<String> features; //Store feature details
    private ArrayList<ArrayList<String>> data; //Store data in two dimensional format
    private int rows; //Number of rows in data set
    private int cols; //Number of columns in data set

    //Begin : Constructor
    public DataSet(ArrayList<String> features, ArrayList<ArrayList<String>> data){
        //Data
        this.features = features;
        this.data = data;

        //Dimension
        this.cols = data.size();
        if(cols != 0){
            this.rows = data.get(0).size();
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Data set created!", this.getClass().getName(),
                "Constructor");
    }

    public DataSet(String[] features, String[][] data){
        this.features = new ArrayList<String>();
        this.data = new ArrayList<ArrayList<String>>();

        this.rows = data[0].length;
        this.cols = data.length;

        this.features = new ArrayList<String>(Arrays.asList(features));

        for(int col=0; col<cols; col++){
            ArrayList<String> dataColumn = new ArrayList<String>();
            for(int row=0; row<rows; row++){
                dataColumn.add(data[col][row]);
            }
            this.data.add(dataColumn);
        }

        Logger.log(MESSAGE_TYPE.SUCCESS, "Data set created!", this.getClass().getName(),
                "Constructor");
    }
    //End : Constructor

    //Begin : Setters
    public void addFeature(String featureName){
        this.features.add(featureName);
        this.cols = this.features.size();
    }

    public void addDataColumn(int columnIndex, ArrayList<String> dataColumn){
        if(dataColumn.size() == this.rows){
            if(columnIndex >=0 && columnIndex < cols){
                data.add(columnIndex, dataColumn);

                //Update column count
                cols = features.size();
            }else{
                Logger.log(MESSAGE_TYPE.ERROR, "Column index out of range",
                        this.getClass().getName(), "addDataColumn");
            }
        }else{
            Logger.log(MESSAGE_TYPE.ERROR, "Number of rows are not matched with original set",
                    this.getClass().getName(), "addDataColumn");
        }
    }

    public void addDataColumn(String featureName, ArrayList<String> dataColumn){
        int columnIndex = this.features.indexOf(featureName);
        addDataColumn(columnIndex, dataColumn);
    }

    public void replaceDataColumn(int columnIndex, ArrayList<String> dataColumn){
        if(dataColumn.size() == this.rows){
            if(columnIndex >=0 && columnIndex < cols){
                data.set(columnIndex, dataColumn);
            }else{
                Logger.log(MESSAGE_TYPE.ERROR, "Column index out of range",
                        this.getClass().getName(), "addDataColumn");
            }
        }else{
            Logger.log(MESSAGE_TYPE.ERROR, "Number of rows are not matched with original set",
                    this.getClass().getName(), "addDataColumn");
        }
    }

    public void replaceDataColumn(String featureName, ArrayList<String> dataColumn){
        int columnIndex = this.features.indexOf(featureName);
        replaceDataColumn(columnIndex, dataColumn);
    }

    public void removeDataColumn(int columnIndex){
        if(columnIndex >=0 && columnIndex < cols){
            data.remove(columnIndex);
            features.remove(columnIndex);

            //Update column count
            cols = features.size();
        }else{
            Logger.log(MESSAGE_TYPE.ERROR, "Column index out of range",
                    this.getClass().getName(), "removeDataColumn");
        }
    }

    public void removeDataColumn(String featureName){
        int columnIndex = this.features.indexOf(featureName);
        removeDataColumn(columnIndex);
    }
    //End : Setters

    //Begin : Getters
    public int getRowCount(){
        this.rows = this.data.get(0).size();
        return this.rows;
    }

    public int getColumnCount(){
        this.cols = this.features.size();
        return this.cols;
    }

    public ArrayList<String> getFeatures(){
        return this.features;
    }

    public ArrayList<ArrayList<String>> getData(){
        return this.data;
    }

    public ArrayList<String> getDataColumn(String featureName){
        int index = features.indexOf(featureName);
        if(index == -1){
            Logger.log(MESSAGE_TYPE.ERROR, featureName + " feature not found", this.getClass().getName(),
                    "getDataColumn");
            return null;
        }
        return data.get(index);
    }

    public ArrayList<Integer> getBreakingPoints(String featureName){
        ArrayList<String> dataColumn = getDataColumn(featureName);
        ArrayList<Integer> breakingPoints = new ArrayList<Integer>();

        //Initial point
        int loc = 0;
        String value = dataColumn.get(loc);
        breakingPoints.add(loc);

        while(loc < rows){
            if(!value.equals(dataColumn.get(loc))){
                breakingPoints.add(loc);
                value = dataColumn.get(loc);
            }
            loc++;
        }
        breakingPoints.add(dataColumn.size());
        return breakingPoints;
    }

    public DataSet removeFromAbove(String categoryFeature, int window){
        /*
            Remove window size amount of data from each category
         */
        ArrayList<ArrayList<String>> subsetData = new ArrayList<ArrayList<String>>();
        ArrayList<Integer> breakingPoints = getBreakingPoints(categoryFeature);

        int start;
        int end;

        for(String feature: features){
            ArrayList<String> columnData = getDataColumn(feature);
            ArrayList<String> subsetColumnData = new ArrayList<String>();

            for(int i=0; i<breakingPoints.size()-1; i++){
                start = breakingPoints.get(i);
                end = breakingPoints.get(i+1);

                int pos = start + window;
                while(pos < end){
                    subsetColumnData.add(columnData.get(pos));
                    pos++;
                }
            }
            subsetData.add(subsetColumnData);
        }
        //Update row count
        rows = data.get(0).size();

        return new DataSet(features, subsetData);
    }

    public String[] getDataRow(int rowId){
        if(rowId >= 0 && rowId < rows){
            String[] row = new String[cols];
            for(int i=0; i<cols; i++){
                row[i] = data.get(i).get(rowId);
            }
            return row;
        }else{
            Logger.log(MESSAGE_TYPE.ERROR, "Row index out of range", this.getClass().getName(),
                    "getDataRow");
            return null;
        }
    }

    public ArrayList<String[]> getDataRows(){
        ArrayList<String[]> dataRows = new ArrayList<String[]>();
        for(int i=0; i<rows; i++){
            dataRows.add(getDataRow(i));
        }
        return dataRows;
    }
    //End : Getters

    public boolean isEqual(DataSet dataSet){
        //Compare vertical dimensions
        if(this.getColumnCount() != dataSet.getColumnCount()){
            Logger.log(MESSAGE_TYPE.INFO, "Number of columns are not equal" +
                    "\nthis : " + this.getColumnCount() +
                    "\ncomp : " + dataSet.getColumnCount(), this.getClass().getName(), "isEqual");
            return false;
        }

        //Compare horizonal dimensions
        if(this.getRowCount() != dataSet.getRowCount()){
            Logger.log(MESSAGE_TYPE.INFO, "Number of rows are not equal" +
                    "\nthis : " + this.getRowCount() +
                    "\ncomp : " + dataSet.getRowCount(), this.getClass().getName(), "isEqual");
            return false;
        }

        //Compare features are same
        for(String feature: features){
                if(!dataSet.getFeatures().contains(feature)){
                    Logger.log(MESSAGE_TYPE.INFO, feature + " is not in the compared dataset"
                            , this.getClass().getName(), "isEqual");
                    return false;
                }
        }

        //Compare data
        for(String feature: features) {
            ArrayList<String> compareData = dataSet.getDataColumn(feature);
            ArrayList<String> originalData = getDataColumn(feature);

            for(int i=0; i<rows; i++){
                if(!originalData.get(i).equals(compareData.get(i))){
                    Logger.log(MESSAGE_TYPE.INFO,"Data is not matched with the original data"
                            , this.getClass().getName(), "isEqual");
                    return false;
                }
            }
        }



        return true;
    }
}
