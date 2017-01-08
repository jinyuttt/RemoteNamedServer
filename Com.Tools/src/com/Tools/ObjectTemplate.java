package com.Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.template.Templates;
import org.msgpack.type.ArrayValue;

import org.msgpack.type.MapValue;

import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;
import org.msgpack.unpacker.Unpacker;

public class ObjectTemplate extends AbstractTemplate<Object>{

	//boolean required=false;
	static final ObjectTemplate instance=new ObjectTemplate();
	public static ObjectTemplate getInstance()
	{
		return instance;
	}
	@Override
	public Object read(Unpacker u, Object to, boolean required) throws IOException {
	
		if(!required&&u.trySkipNil())
		{
		  return null;
		}
		return toObject(u.readValue());
	}

	@Override
	public void write(Packer pk, Object v, boolean required) throws IOException {
	
		if(v==null)
		{
			if(required)
			{
				throw new MessageTypeException("");
			}
			pk.writeNil();
			return;
		}
		pk.write(v);
	}
  @SuppressWarnings("resource")
private static Object toObject(Value value) 
  {
	  Converter conv=new Converter(value);
	  if(value.isNilValue())
	  {
		  try {
			conv.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		  return null;
		  
	  }
	  else if(value.isRawValue())
	  {
		  //RawValue v=value.asRawValue();
		  try {
			return conv.read(Templates.TString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  else if(value.isBooleanValue())
	  {
		  try {
			return conv.read(Templates.TBoolean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  else if(value.isIntegerValue())
	  {
		//  IntegerValue v=value.asIntegerValue();
		  try {
			return conv.read(Templates.TInteger);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  else if(value.isFloatValue())
	  {
		//  FloatValue v=value.asFloatValue();
		  try {
			return conv.read(Templates.TFloat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  else if(value.isArrayValue())
	  {
		  ArrayValue v=value.asArrayValue();
		  List<Object> ret=new ArrayList<Object>();
		  for(Value elementValue: v)
		  {
			  ret.add(toObject(elementValue));
		  }
		  return ret;
	  }
	  else if(value.isMapValue())
	  {
		  MapValue v=value.asMapValue();
		  Map<Object, Object> map=new HashMap<>(v.size());
		 
		  for(Map.Entry<Value, Value> entry: v.entrySet())
		  {
			  Value key=entry.getKey();
			  Value val=entry.getValue();
			  map.put(toObject(key), toObject(val));
		  }
		  return map;
	  }
	  else
	  {
		  throw new RuntimeException("fatal error");
	  }
	return null;
  }
}
