//3. Use Reflection to encode and decode the data.
package avro;

import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AvroReflect {
    final static ReflectData reflectData = ReflectData.get();
    final static Schema schema = reflectData.getSchema(Employee.class);

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(os, null);

        DatumWriter<Employee> writer = new ReflectDatumWriter<Employee>(schema);
        Employee employee = new Employee();
        employee.setName("Kamal");
        employee.setSsn("000-00-0000");
        employee.setAge(29);

        writer.write(employee, e);
        e.flush();
       
        System.out.println(os.toString());
       
        ReflectData reflectData = ReflectData.get();
        Schema schm = reflectData.getSchema(AnotherEmployee.class);
        ReflectDatumReader<AnotherEmployee> reader = new ReflectDatumReader<AnotherEmployee>(schm);
        Decoder decoder = DecoderFactory.get().binaryDecoder(os.toByteArray(), null);
        AnotherEmployee decodedEmployee = reader.read(null, decoder);
       
        System.out.println("Name: "+decodedEmployee.getName());
        System.out.println("Age: "+decodedEmployee.getAge());
        System.out.println("SSN: "+decodedEmployee.getSsn());
    }
}