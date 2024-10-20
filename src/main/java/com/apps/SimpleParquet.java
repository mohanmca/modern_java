package com.apps;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.conf.PlainParquetConfiguration;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.io.LocalOutputFile;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SimpleParquet {

    public static void main(String[] args) throws IOException {
        // Define Avro schema
        var is = SimpleAvro.class.getResourceAsStream("/schema/user.avsc");
        Schema schema = new Schema.Parser().parse(is);

        // Create Avro records
        GenericRecord user1 = new GenericData.Record(schema);
        user1.put("name", "John");
        user1.put("favorite_number", 30);
        user1.put("favorite_color", "green");
        user1.put("temperature", ByteBuffer.wrap(bigDecimalToDecimalBytes(new BigDecimal("123.456"), 19)));

        GenericRecord user2 = new GenericData.Record(schema);
        user2.put("name", "Jane");
        user2.put("favorite_number", 28);
        user2.put("favorite_color", "blue");
        user2.put("temperature", ByteBuffer.wrap(bigDecimalToDecimalBytes(new BigDecimal("000.01"), 19)));

        GenericRecord user3 = new GenericData.Record(schema);
        user3.put("name", "Jane");
        user3.put("favorite_number", 28);
        user3.put("favorite_color", "blue");
        user3.put("temperature", ByteBuffer.wrap(bigDecimalToDecimalBytes(new BigDecimal("12345678901234567890123456789012345678901234567890.12345678901234567890"), 19)));



        org.apache.parquet.conf.ParquetConfiguration conf = new PlainParquetConfiguration();
        // Write Parquet file
        LocalOutputFile parquetFile = new LocalOutputFile(Path.of("user.parquet"));
        try (ParquetWriter<GenericRecord> parquetWriter = AvroParquetWriter.<GenericRecord>builder(parquetFile)
                .withSchema(schema)
                .withConf(conf)
                .withWriteMode(ParquetFileWriter.Mode.OVERWRITE)
                .build()) {

            parquetWriter.write(user1);
            parquetWriter.write(user2);
            parquetWriter.write(user3);
        }

        System.out.println(parquetFile.getPath()  + " Parquet file is written!");
    }

    // Helper method to convert BigDecimal to byte[] considering its scale
    private static byte[] bigDecimalToDecimalBytes(BigDecimal bigDecimal, int scale) {
        var bigDecimal2 = bigDecimal.setScale(scale, RoundingMode.FLOOR); // Set scale
        BigInteger unscaledValue = bigDecimal2.unscaledValue(); // Unscaled value (BigInteger)
        return unscaledValue.toByteArray(); // Convert to byte array
    }
}
