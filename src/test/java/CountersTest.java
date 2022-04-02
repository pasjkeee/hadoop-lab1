import eu.bitwalker.useragentutils.UserAgent;
import bdtc.lab1.CounterType;
import bdtc.lab1.HW1Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class CountersTest {

    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;

    private final String testMalformedIP = "mama mila ramu";
    private final String testIP = "ip1 - - [24/Apr/2011:04:06:01 -0400] \"GET /~strabal/grease/photo9/927-3.jpg HTTP/1.1\" 200 40028 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\"\n";

    private final String test1 = "1, 1510670901703, 112";
    private final String test2 = "1, 1510670901903, 12";
    private final String test3 = "2, 1510670902703, 110";
    private final String test4 = "1, 1510670903703, 122";
    private final String test5 = "1, 1510670904703, 15";

    @Before
    public void setUp() {
        HW1Mapper mapper = new HW1Mapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

//    @Test
//    public void testMapperCounterOne() throws IOException  {
//        mapDriver
//                .withInput(new LongWritable(), new Text(testMalformedIP))
//                .runTest();
//        assertEquals("Expected 1 counter increment", 1, mapDriver.getCounters()
//                .findCounter(CounterType.MALFORMED).getValue());
//    }
//
//    @Test
//    public void testMapperCounterZero() throws IOException {
//        UserAgent userAgent = UserAgent.parseUserAgentString(testIP);
//        mapDriver
//                .withInput(new LongWritable(), new Text(testIP))
//                .withOutput(new Text(userAgent.getBrowser().getName()), new IntWritable(1))
//                .runTest();
//        assertEquals("Expected 1 counter increment", 0, mapDriver.getCounters()
//                .findCounter(CounterType.MALFORMED).getValue());
//    }

    @Test
    public void testMapperCounters() throws IOException {
//        UserAgent userAgent = UserAgent.parseUserAgentString(testIP);
        String[] tokens = test1.split(", ");
        mapDriver
                .withInput(new LongWritable(), new Text(test1))
                .withInput(new LongWritable(), new Text(test2))
                .withInput(new LongWritable(), new Text(test3))
                .withInput(new LongWritable(), new Text(test4))
                .withInput(new LongWritable(), new Text(test5))
                .withOutput(new Text("Node1Cpu, 1510670880000"), new IntWritable(112))
                .withOutput(new Text("Node1Cpu, 1510670880000"), new IntWritable(12))
                .withOutput(new Text("Node2Ram, 1510670880000"), new IntWritable(110))
                .withOutput(new Text("Node1Cpu, 1510670880000"), new IntWritable(122))
                .withOutput(new Text("Node1Cpu, 1510670880000"), new IntWritable(15))
                .runTest();

//        assertEquals("Expected 2 counter increment", 2, mapDriver.getCounters()
//                .findCounter(CounterType.MALFORMED).getValue());
    }
}

