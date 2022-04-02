package bdtc.lab1;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class HW1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable();
    private Text word = new Text();

    private HashMap<String, String> idsMap = new HashMap<String, String>() {{
        put("1", "Node1Cpu");
        put("2", "Node2Ram");
        put("3", "Node3Lol");
    }};

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {


        String line = value.toString();
        String[] tokens = line.split(", ");

        one.set(Integer.parseInt(tokens[2]));

        long time = Long.parseLong(tokens[1]);
        Date date = new java.util.Date(time);
        Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

        word.set(idsMap.get(tokens[0])+", "+calendar.getTimeInMillis());
        System.out.println(word);

        context.write(word, one);
    }
}
