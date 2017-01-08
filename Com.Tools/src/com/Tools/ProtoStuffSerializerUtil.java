//package com.Tools;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//import com.dyuproject.protostuff.LinkedBuffer;
//import com.dyuproject.protostuff.ProtostuffIOUtil;
//import com.dyuproject.protostuff.Schema;
//import com.dyuproject.protostuff.runtime.RuntimeSchema;
//
///**
// * 
// * <pre>
// * ���кŹ���
// * </pre>
// *
// * @author F.Fang
// */
//public class ProtoStuffSerializerUtil {
//
//    public static <T> byte[] serialize(T obj) {
//        if (obj == null) {
//            throw new RuntimeException("���л�����(" + obj + ")!");
//        }
//        @SuppressWarnings("unchecked")
//        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
//        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
//        byte[] protostuff = null;
//        try {
//            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } catch (Exception e) {
//            throw new RuntimeException("���л�(" + obj.getClass() + ")����(" + obj + ")�����쳣!", e);
//        } finally {
//            buffer.clear();
//        }
//        return protostuff;
//    }
//
//    public static <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
//        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
//            throw new RuntimeException("�����л��������쳣,byte����Ϊ��!");
//        }
//        T instance = null;
//        try {
//            instance = targetClass.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            throw new RuntimeException("�����л��������������ʹ�������ʧ��!", e);
//        }
//        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
//        ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
//        return instance;
//    }
//    
//    public static <T> byte[] serializeList(List<T> objList) {
//        if (objList == null || objList.isEmpty()) {
//            throw new RuntimeException("���л������б�(" + objList + ")�����쳣!");
//        }
//        @SuppressWarnings("unchecked")
//        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
//        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
//        byte[] protostuff = null;
//        ByteArrayOutputStream bos = null;
//        try {
//            bos = new ByteArrayOutputStream();
//            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
//            protostuff = bos.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("���л������б�(" + objList + ")�����쳣!", e);
//        } finally {
//            buffer.clear();
//            try {
//                if(bos!=null){
//                    bos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        
//        return protostuff;
//    }
//    
//    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
//        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
//            throw new RuntimeException("�����л��������쳣,byte����Ϊ��!");
//        }
//        
//        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
//        List<T> result = null;
//        try {
//            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
//        } catch (IOException e) {
//            throw new RuntimeException("�����л������б������쳣!",e);
//        }
//        return result;
//    }
//
//}