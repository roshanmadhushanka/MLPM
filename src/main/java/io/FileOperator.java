package io;

import data.DataSet;
import log.Logger;
import log.MESSAGE_TYPE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wso2123 on 8/22/16.
 */
public class FileOperator {
    private ArrayList<String> openFile(String fileName){
        /*
            Open file for reading for a given file name with extension
         */
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            ArrayList<String> lines = new ArrayList<String>();

            while((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
            bufferedReader.close();
            return lines;
        } catch (FileNotFoundException e) {
            Logger.log(MESSAGE_TYPE.ERROR, "File not found", this.getClass().getName(), "openFile");
        } catch (IOException e) {
            Logger.log(MESSAGE_TYPE.ERROR, "IO Error", this.getClass().getName(), "openFile");
        }
        return null;
    }

    public DataSet openCSVFile(String fileName){
        /*
            Open CSV file and return a DataSet
         */
        ArrayList<String> lines = openFile(fileName);

        if(lines == null){
            Logger.log(MESSAGE_TYPE.WARNING, "File is empty", this.getClass().getName(),
                    "openCSVFile");
            return null;
        }

        if(lines.size() > 1){
            //Features
            String headerLine = lines.get(0);
            String[] featureNames = headerLine.split(",");

            //Data dimensions
            int rows = lines.size() - 1;
            int cols = featureNames.length;

            //Data container
            String[][] data = new String[cols][rows];

            String[] columnData;
            for(int row=1; row<=rows; row++){
                columnData = lines.get(row).split(",");
                if(columnData.length != cols){
                    //Check number of features with the data set column size
                    Logger.log(MESSAGE_TYPE.ERROR, "Number of features are not matched with the " +
                            "data set", this.getClass().getName(), "openCSVFile");
                    return null;
                }

                for(int col=0; col<cols; col++){
                    data[col][row-1] = columnData[col];
                }
            }
            return new DataSet(featureNames, data);
        }else{
            Logger.log(MESSAGE_TYPE.ERROR, "Invalid data set", this.getClass().getName(),
                    "openCSVFile");
        }

        return null;
    }

    public void saveCSVFile(String fileName, DataSet dataSet){
        /*
            Write DataSet in to a CSV file
         */

        int rows = dataSet.getRowCount();
        int cols = dataSet.getColumnCount();

        //Print header
        String header = "";
        if (cols == 1) {
            header = dataSet.getFeatures().get(0);
        } else if (cols > 1) {
            header = dataSet.getFeatures().get(0);
            for (int i = 1; i<dataSet.getFeatures().size(); i++) {
                header += "," + dataSet.getFeatures().get(i);
            }
        } else {
            Logger.log(MESSAGE_TYPE.INFO, "Nothing to print", this.getClass().getName(),
                    "saveCSVFile");
            return;
        }

        FileWriter fileWriter;
        BufferedWriter bufferedWriter;

        //Initialize writing
        //Write headers
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(header);
            bufferedWriter.newLine();
        } catch (IOException ex) {
            Logger.log(MESSAGE_TYPE.ERROR, "IO Error", this.getClass().getName(), "saveCSVFile");
            return;
        }

        //Write data
        if (cols == 1) {
            for (String rowData : dataSet.getData().get(0)) {
                try {
                    bufferedWriter.write(rowData);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    Logger.log(MESSAGE_TYPE.ERROR, fileName + " cannot be written in to the disk",
                            this.getClass().getName(), "saveCSVFile");
                }

            }
        } else if (cols > 1) {
            for (int j = 0; j < rows; j++) {
                String dataRow = dataSet.getData().get(0).get(j);
                for (int i = 1; i < cols; i++) {
                    dataRow += "," + dataSet.getData().get(i).get(j);
                }
                try {
                    bufferedWriter.write(dataRow);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    Logger.log(MESSAGE_TYPE.ERROR, fileName + " cannot be written in to the disk",
                            this.getClass().getName(), "saveCSVFile");

                }
            }
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                Logger.log(MESSAGE_TYPE.ERROR, "IO Error",
                        this.getClass().getName(), "saveCSVFile");
            }
            Logger.log(MESSAGE_TYPE.SUCCESS, fileName + " write complete",
                    this.getClass().getName(), "saveCSVFile");

        }

    }

}
