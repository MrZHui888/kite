package com.hession;

import com.Domain;
import com.caucho.hessian.io.*;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author : Guzh
 * @since : 2018/11/23
 */
public class HessionDemo {

    /**
     * Hessian实现序列化
     *
     * @param employee
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Domain employee, SerializerFactory serializerFactory) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        HessianOutput hessianOutput = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            // Hessian的序列化输出
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.setSerializerFactory(serializerFactory);

            hessianOutput.writeObject(employee);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hessianOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Hessian实现反序列化
     *
     * @param employeeArray
     * @return
     */
    public static Domain deserialize(byte[] employeeArray, SerializerFactory serializerFactory) {
        ByteArrayInputStream byteArrayInputStream = null;
        HessianInput hessianInput = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(employeeArray);
            // Hessian的反序列化读取对象
            hessianInput = new HessianInput(byteArrayInputStream);
            hessianInput.setSerializerFactory(serializerFactory);
            return (Domain) hessianInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hessianInput.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        Domain domain = new Domain();

        domain.setDate(new Date());
        domain.setHehe(LocalDateTime.now());
        domain.setFailRosterStatusList(Lists.newArrayList(Byte.valueOf("1"), Byte.valueOf("2"), Byte.valueOf("3")));
        domain.setRosterTypeList(Lists.newArrayList(1, 22, 33));

        ExtSerializerFactory extSerializerFactory = new ExtSerializerFactory();
        SerializerFactory serializerFactory = new SerializerFactory();
        extSerializerFactory.addSerializer(java.time.LocalDateTime.class, new LocalDateTimeSerializer());
        extSerializerFactory.addDeserializer(java.time.LocalDateTime.class, new LocalDateTimeDeserializer());
        serializerFactory.addFactory(extSerializerFactory);

        byte[] bytes = serialize(domain, serializerFactory);
        System.out.println(bytes);


        Domain domain1 = deserialize(bytes, serializerFactory);
        System.out.println(domain1);

    }

    static class LocalDateTimeDeserializer extends AbstractDeserializer {
        @Override
        public Class getType() {
            return LocalDateTime.class;
        }


        @Override
        public Object readObject(AbstractHessianInput in,
                                 Object[] fields)
                throws IOException {
            String[] fieldNames = (String[]) fields;

            int ref = in.addRef(null);

            long initValue = Long.MIN_VALUE;

            for (int i = 0; i < fieldNames.length; i++) {
                String key = fieldNames[i];

                if (key.equals("value")) {
                    initValue = in.readUTCDate();
                } else {
                    in.readObject();
                }
            }
            Object value = create(initValue);
            in.setRef(ref, value);
            return value;
        }

        private Object create(long initValue)
                throws IOException {
            if (initValue == Long.MIN_VALUE) {
                throw new IOException(LocalDateTime.class + " expects name.");
            }
            try {
                return LocalDateTime.ofEpochSecond(new Long(initValue) / 1000, Integer.valueOf(String.valueOf(initValue % 1000)) * 1000, ZoneOffset.of("+8"));
            } catch (Exception e) {
                throw new IOExceptionWrapper(e);
            }
        }
    }


    static class LocalDateTimeSerializer extends AbstractSerializer {
        @Override
        public void writeObject(Object obj, AbstractHessianOutput out)
                throws IOException {
            if (obj == null) {
                out.writeNull();
            } else {
                Class cl = obj.getClass();

                if (out.addRef(obj)) {
                    return;
                }
                // ref 返回-2 便是开始写Map
                int ref = out.writeObjectBegin(cl.getName());

                if (ref < -1) {
                    out.writeString("value");
                    Long milliSecond = ((LocalDateTime) obj).toInstant(ZoneOffset.of("+8")).toEpochMilli();
                    out.writeUTCDate(milliSecond);
                    out.writeMapEnd();
                } else {
                    if (ref == -1) {
                        out.writeInt(1);
                        out.writeString("value");
                        out.writeObjectBegin(cl.getName());
                    }

                    Long milliSecond = ((LocalDateTime) obj).toInstant(ZoneOffset.of("+8")).toEpochMilli();
                    out.writeUTCDate(milliSecond);
                }
            }
        }
    }

}
