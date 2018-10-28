import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

	public static class Map extends Mapper<LongWritable, Text,Text,IntWritable>{
		public void map(LongWritable key,Text value, Context context)throws IOException,InterruptedException{
			//key to mapper:byte offset (byte offset is the number of character that exists counting from the beginning of a line.)
			//value:this is bad world(line from input file)
			//byte off set for above line is 17 .It is in hexadecimal form hence LongWritable data type
			String line=value.toString();
			//We are converting value to string type in storing it in "line" variable
			StringTokenizer tokenizer=new StringTokenizer(line);
			//We are creating an object tokenizer of class StringTokenizer n passing line as argument to it
			while(tokenizer.hasMoreTokens()){//till the tokenizer object has got tokens
				value.set(tokenizer.nextToken());//assigning value to variable "value" i.e. now value will contain word
				//this
				//is
				//very
				//very
				//bad
				//world
				context.write(value,new IntWritable(1));//assigning 1 against each word
				//this,(1)
				//is,(1)
				//very(1,1)   //this will be input to reduce function
				//bad,(1)
				//world,(1)
				//context.write() writes the output of map on local disk before sending it to reduce function

			}
		}

	}
	public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable>{
		public void reduce(Text key,Iterable<IntWritable> values, Context context)throws IOException,InterruptedException{
			//this,(1)
			//key,value
			//is,(1)
			//very(1,1)   //eg,"this" will be key and (1) will be value
			//bad,(1)	//reduce function will execute as many times as the count of key
			//world,(1)    // in this case ,it will execute for 5 times
			int sum=0;
			for (IntWritable x:values)
			{
				sum+=x.get();

			}
			context.write(key, new IntWritable(sum));
		}
	}
	public static void main(String[] args)throws Exception{
		Configuration conf=new Configuration();
		//used for configuring any map reduce example
		Job job=Job.getInstance(conf,"WordCount");
		//name of map reduce program
		job.setJarByClass(WordCount.class);//setting main class,in this main class we have two classes mapper n reducer

		job.setMapperClass(Map.class);//setting mapper class
		job.setReducerClass(Reduce.class);//setting reducer class
		//By doing this , we are telling hadoop framework to use this classes while execution of the job


		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		Path outputPath=new Path(args[1]);

		FileInputFormat.addInputPath(job, new Path(args[0]));


		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		//deleting the output path automatically from hdfs so that we dont have to delete it explicitly
		outputPath.getFileSystem(conf).delete(outputPath, true);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
