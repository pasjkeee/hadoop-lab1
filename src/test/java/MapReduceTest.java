import eu.bitwalker.useragentutils.UserAgent;
import bdtc.lab1.HW1Mapper;
import bdtc.lab1.HW1Reducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapReduceTest {

    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    private ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
    private MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

    private final String testIP = "ip1 - - [24/Apr/2011:04:06:01 -0400] \"GET /~strabal/grease/photo9/927-3.jpg HTTP/1.1\" 200 40028 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\"\n";


    private final String test1 = "1, 1510670901703, 112";
    private final String test2 = "1, 1510670901903, 12";
    private final String test3 = "2, 1510670902703, 110";
    private final String test4 = "1, 1510670903703, 122";
    private final String test5 = "1, 1510671378542, 15";

    private UserAgent userAgent;
    @Before
    public void setUp() {
        HW1Mapper mapper = new HW1Mapper();
        HW1Reducer reducer = new HW1Reducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
        userAgent = UserAgent.parseUserAgentString(testIP);
    }

    @Test
    public void testMapReduce() throws IOException {
        mapReduceDriver
                .withInput(new LongWritable(112), new Text("1, 1510670901703, 112"))
                .withInput(new LongWritable(12), new Text("1, 1510670901903, 12"))
                .withInput(new LongWritable(110), new Text("2, 1510670902703, 110"))
                .withInput(new LongWritable(122), new Text("1, 1510670903703, 122"))
                .withInput(new LongWritable(15), new Text("1, 1510661378542, 15"))
                .withOutput(new Text("Node1Cpu, 1510661340000"), new IntWritable(15))
                .withOutput(new Text("Node1Cpu, 1510670880000"), new IntWritable(246/3))
                .withOutput(new Text("Node2Ram, 1510670880000"), new IntWritable(110))
                .runTest();
    }
}
