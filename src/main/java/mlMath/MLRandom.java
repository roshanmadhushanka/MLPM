package mlMath;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wso2123 on 8/31/16.
 */
public class MLRandom {
    public ArrayList<Integer> nonRepeatingRandom(int min, int max, int size){
        ArrayList<Integer> duplicate = new ArrayList<Integer>();

        ArrayList<Integer> values = new ArrayList<Integer>();
        int count = 0;
        Random r = new Random();
        while(count < size){
            int result = r.nextInt(max-min) + min;
            if(duplicate.contains(result)){
                continue;
            }
            values.add(result);
            duplicate.add(result);
            count++;
        }
        return values;
    }

    public ArrayList<ArrayList<Integer>> nonRepeatingRandomList(int min, int max, int size, int rows){
        ArrayList<Integer> duplicate = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();
        while(values.size() < rows){
            ArrayList<Integer> tmp = new ArrayList<Integer>(size);
            int count = 0;
            Random r = new Random();
            while(count < size){
                int result = r.nextInt(max-min) + min;
                if(duplicate.contains(result)){
                    continue;
                }
                tmp.add(result);
                duplicate.add(result);
                count++;
            }
            values.add(tmp);
        }
        return values;
    }

    public ArrayList<Integer> repeatingRandom(int min, int max, int size){
        ArrayList<Integer> values = new ArrayList<Integer>();
        int count = 0;
        Random r = new Random();
        while(count < size){
            int result = r.nextInt(max-min) + min;
            values.add(result);
            count++;
        }
        return values;
    }

    public ArrayList<ArrayList<Integer>> repeatingRandomList(int min, int max, int size, int rows){
        ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();
        while(values.size() < rows){
            ArrayList<Integer> tmp = new ArrayList<Integer>(size);
            int count = 0;
            Random r = new Random();
            while(count < size){
                int result = r.nextInt(max-min) + min;
                tmp.add(result);
                count++;
            }
            values.add(tmp);
        }
        return values;
    }
}
