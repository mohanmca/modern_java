package com.apps;

import com.nikias.avro.User;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class SimpleAvro {
    public static void main(String[] args) throws IOException {
        var is = SimpleAvro.class.getResourceAsStream("/schema/user.avsc");
        Schema schema = new Schema.Parser().parse(is);
        // Construct via builder
        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(1)
                .build();
       User user = User.newBuilder(user3).setFavoriteColor("green").build();
// Serialize user1 and user2 to disk
        File file = new File("users.avro");
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(user);
        dataFileWriter.append(user3);
        dataFileWriter.close();
        System.out.println(file.getAbsolutePath() + " output is written!");
    }
}
