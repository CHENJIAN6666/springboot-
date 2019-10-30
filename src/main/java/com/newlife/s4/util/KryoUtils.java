package com.newlife.s4.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.*;

/**
 * 描述: kryo 序列化
 *       kryo 对session 的序列化 需要自定义 序列化构造器
 *       暂时使用原生java序列化对象
 *
 * @author withqianqian@163.com
 * @create 2018-05-30 11:16
 */
public class KryoUtils {

    private static KryoFactory factory = new KryoFactory() {
        @Override
        public Kryo create () {
            Kryo kryo = new Kryo();
            return kryo;
        }
    };

    private static KryoPool pool = new KryoPool.Builder(factory).build();

    public static byte[] encode(Object msg) {
        //        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Kryo kryo = pool.borrow();
//        kryo.setReferences(false);
//        kryo.register(msg.getClass(), new JavaSerializer());
//        //将msg序列化
//        Output output = new Output(outputStream,4096);
//        kryo.writeClassAndObject(output, msg);
//        output.flush();
//        output.close();
//
//        //将字节码写到byteBuf里
//        byte[] src = outputStream.toByteArray();
//        try {
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            pool.release(kryo);
//        }
//        return src;

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(msg);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;

    }

    public static Object decode(byte[] body) {
//
//        Kryo kryo = pool.borrow();
//        kryo.setReferences(false);
//        kryo.register(Session.class, new JavaSerializer());
//        //反序列化
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
//        Input input = new Input(body);
//        Object object = kryo.readClassAndObject(input);
//        input.close();
//        try {
//            byteArrayInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            pool.release(kryo);
//        }
        ByteArrayInputStream bi = new ByteArrayInputStream(body);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
    }
}
