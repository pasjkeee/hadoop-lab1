package bdtc.lab1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class HW1Partitioner extends Partitioner<Text, IntWritable>  {

    @Override
    public int getPartition(Text key, IntWritable value, int numPartitions) {

        String id = key.toString();

        /** find id on Node[id][Name] */
        if (id.charAt(4) == '1') {
            return 0;
        }
        if (id.charAt(4) == '2') {
            return 1;
        } else {
            return 2;
        }
    }
}
