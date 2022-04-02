package bdtc.lab1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

public class HW1Partitioner extends Partitioner<Text, IntWritable>  {

    @Override
    public int getPartition(Text key, IntWritable value, int numPartitions) {

        String country = key.toString();

        if (country.charAt(0) == '1') {
            return 0;
        }
        if (country.charAt(0) == '2') {
            return 1;
        } else {
            return 2;
        }
    }
}
