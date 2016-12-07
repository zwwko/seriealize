import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Main {
    private static Person person = new Person(100, "alan");

    public static void main(String[] args) {

        String str = FastXMLSerializer.convert(person);

        byte[] bytes = SerializeUtil.serialize(person);

        Kryo kryo = new Kryo();
//        Output output = new Output(8, 65536);
//        kryo.writeObject(output, person);


        Code fastXml = () -> {
//            FastXMLSerializer.convert(person);
            Person person1 = FastXMLSerializer.convert(str);
        };
        Code javaSerialize = () -> {
//            SerializeUtil.serialize(person);
            Person person2 = (Person) SerializeUtil.unserialize(bytes);
        };


            Output output = new Output(8, 65536);
            kryo.writeObject(output, person);
        Code kryoBufferSerialize = () -> {
            Input input = new Input(output.getBuffer());
            Person person3 = kryo.readObject(input, Person.class);
        };


        Schema schema = ReflectData.get().getSchema(Person.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<Person> writer = new ReflectDatumWriter<>(Person.class);
        Code avroSerialize = () -> {
            DataFileWriter<Person> out = null;
//        DatumWriter<Person> userDatumWriter = new SpecificDatumWriter<>(Person.class);
            try {
                out = new DataFileWriter<>(writer).create(schema, outputStream);
                out.append(person);
//            System.out.println(new String(outputStream.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }
//            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//            SeekableByteArrayInput sin = new SeekableByteArrayInput(outputStream.toByteArray());
//            DatumReader<Person> reader = new ReflectDatumReader<>(Person.class);
//            DataFileReader<Person> in = null;
//            try {
//                in = new DataFileReader<>(sin, reader);
//                for (Person p : in) {
//                }
//                if (in.hasNext()) {
//                    System.out.println("ss");
//                    Person person2 = (Person) in.iterator();
//                    System.out.println(person2.getId());
//                    System.out.println(person2.getName());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        };

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(os, null);
        DatumWriter<Person> ww = new ReflectDatumWriter<Person>(schema);
        try {
            ww.write(person, e);
            e.flush();
//                System.out.println(os.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ReflectData reflectData = ReflectData.get();
        Schema schm = reflectData.getSchema(Person.class);
        ReflectDatumReader<Person> reader = new ReflectDatumReader<Person>(schm);
        Code kryoEncodeSerialize = () -> {
            Decoder decoder = DecoderFactory.get().binaryDecoder(os.toByteArray(), null);
            try {
                Person person = reader.read(null, decoder);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };


        System.out.println(countTime(fastXml, 2000));
        System.out.println(countTime(javaSerialize, 2000));
        System.out.println(countTime(kryoBufferSerialize, 2000));
        System.out.println(countTime(kryoEncodeSerialize, 2000));

    }


    /**
     * 统计执行时间
     *
     * @param code
     * @param count
     * @return
     */
    public static long countTime(Code code, int count) {
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            code.exe();
        }
        long time2 = System.currentTimeMillis();
        return time2 - time1;
    }

/*　序列化时间
    845
    156
    55
    644
    反序列化时间
    105
    135
    35
 */

}

@FunctionalInterface
interface Code {
    void exe();
}


   /* Code kryoFileSerialize = () -> { Kryo kryo = new Kryo(); try { Output output = new Output(new FileOutputStream("person.bin")); kryo.writeObject(output, person); output.close(); Input input = new Input(new FileInputStream("person.bin"));
            Person person3 = kryo.readObject(input, Person.class);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    };
    Schema schema = new Schema.Parser().parse("{\n" +
        "  \"type\": \"record\", \n" +
        "  \"name\": \"Employee\", \n" +
        "  \"fields\": [\n" +
        "      {\"name\": \"name\", \"type\": \"string\"},\n" +
        "      {\"name\": \"age\", \"type\": \"int\"},\n" +
        "      {\"name\": \"emails\", \"type\": {\"type\": \"array\", \"items\": \"string\"}},\n" +
        "      {\"name\": \"boss\", \"type\": [\"Employee\",\"null\"]}\n" +
        "  ]\n" +
        "}");
    */