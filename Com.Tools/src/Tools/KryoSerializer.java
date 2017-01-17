package Tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;



import com.esotericsoftware.kryo.Kryo;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoSerializer {
	  Kryo kryo = new Kryo();
	/**
	 *  序列化
	 * @param obj
	 * @return
	 */
public <T> byte[] Serializer(T obj)
{
        

	   kryo.register(obj.getClass());
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		Output output = new Output(buffer);
	   kryo.writeObject(output, obj);
		output.flush();
		output.close();
	  return	buffer.toByteArray();

}
/**
 *  反序列化
 * @param data
 * @return
 */
@SuppressWarnings({ "unchecked", "null" })
public <T> T DeSerializer(byte[]data)
{
          
            T obj = null;
	        kryo.register(obj.getClass());
	        ByteArrayInputStream buffer = new ByteArrayInputStream(data);
		    Input intput = new Input(buffer);
		    obj=   (T) kryo.readClassAndObject(intput);
		    intput.close();
			return obj;
	 

}
}
