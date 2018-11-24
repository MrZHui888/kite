package com.hession;

import com.Domain;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public static byte[] serialize(Domain employee) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        HessianOutput hessianOutput = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            // Hessian的序列化输出
            hessianOutput = new HessianOutput(byteArrayOutputStream);
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
    public static Domain deserialize(byte[] employeeArray) {
        ByteArrayInputStream byteArrayInputStream = null;
        HessianInput hessianInput = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(employeeArray);
            // Hessian的反序列化读取对象
            hessianInput = new HessianInput(byteArrayInputStream);
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

        Domain domain=new Domain();

        domain.setDate(new Date());
        domain.setFailRosterStatusList(Lists.newArrayList(Byte.valueOf("1"),Byte.valueOf("2"),Byte.valueOf("3")));
        domain.setRosterTypeList(Lists.newArrayList(1,22,33));

        byte[] bytes=serialize(domain);

        System.out.println(bytes);

        Domain domain1=deserialize(bytes);

        System.out.println(domain1);

    }


}
