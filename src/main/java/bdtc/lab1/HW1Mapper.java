package bdtc.lab1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class HW1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    /** Value for <K,V> output mapper */
    private final static IntWritable one = new IntWritable();

    /** Key for <K,V> output mapper */
    private Text word = new Text();

    /** Constant Map from id to humanReadableName */
    private HashMap<String, String> idsMap = new HashMap<String, String>() {{
        put("1", "Node1Cpu");
        put("2", "Node2Ram");
        put("3", "Node3Lol");
    }};

    /**
     * map
     * @param key
     * @param value input string
     * @param context
     * @return void; Write <K,V> pairs into context
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        /** parse value to string */
        String line = value.toString();

        /** split value into tokens [id, timestamp, value] */
        String[] tokens = line.split(", ");

        /** set value into IntWritable container */
        one.set(Integer.parseInt(tokens[2]));

        /** parse string timestamp value into long timestamp value */
        long time = Long.parseLong(tokens[1]);

        /** parse long timestamp value into date */
        Date date = new java.util.Date(time);

        /** parse date into calendar without seconds and milliseconds */
        Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

        /** make key value from id humanReadableName and timestamp without seconds */
        word.set(idsMap.get(tokens[0])+", "+calendar.getTimeInMillis());

        context.write(word, one);
    }
}
