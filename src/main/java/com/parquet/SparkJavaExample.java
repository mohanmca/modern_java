package com.parquet;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.RowFactory;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
public class SparkJavaExample {
    public static void main(String[] args) {
        // Create a SparkConf object
//        SparkConf conf = new SparkConf()
//                .setAppName("SparkConfExample")  // Set the application name
//                .setMaster("local[*]")           // Set the master to run locally with all available cores
//                .set("spark.executor.memory", "2g") // Set executor memory to 2GB
//                .set("spark.ui.enabled", "false") // Set executor memory to 2GB
//                .set("spark.some.config.option", "some-value"); // Set any additional Spark properties
//
//        JavaSparkContext spark = new JavaSparkContext(conf);

        // Initialize SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("SparkJavaExample")
                .config("spark.ui.enabled", false)
                .master("local[*]") // Run Spark locally with as many cores as available
                .getOrCreate();

        // Define schema
        StructType schema = new StructType(new StructField[]{
                DataTypes.createStructField("id", DataTypes.IntegerType, false),
                DataTypes.createStructField("name", DataTypes.StringType, false),
                DataTypes.createStructField("age", DataTypes.IntegerType, false)
        });

        // Create data as a list of Rows
        List<Row> data = Arrays.asList(
                RowFactory.create(1, "Alice", 34),
                RowFactory.create(2, "Bob", 45),
                RowFactory.create(3, "Carol", 29)
        );

        // Create a DataFrame from data and schema
        Dataset<Row> df = spark.createDataFrame(data, schema);

        // Write DataFrame to Parquet file
        df.write().parquet("output.parquet");

        // Stop SparkSession
        spark.stop();
    }
}
