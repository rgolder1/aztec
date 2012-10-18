package com.aztec.util;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class MarshallingUtil {
	
	/**
	 * Marshall the object to a Json String
	 * 	Default is to NOT wrap the root value
	 * 
	 * @param obj
	 * @return a json string
	 */
	public static String marshalJson(Object obj) {
		return marshalJson(obj, false);
	}
    
    /**
     * Marshal an object into a JSON string
     * 
     * @param obj
     * @param wrapRootValue
     * 
     * @return a JSON string
     */
	public static String marshalJson(Object obj, boolean wrapRootValue) {
		try {
			final ObjectMapper mapper = createJsonObjectMapper();
			mapper.getSerializationConfig().set(SerializationConfig.Feature.WRAP_ROOT_VALUE, wrapRootValue);
            String json = mapper.writeValueAsString(obj);
			return json;
		}
		catch (Exception e) {
			throw new RuntimeException("Error marshalling object to string.  obj: " + obj, e);
		}
	}
	
	/**
	 * Unmarshal a JSON string 
	 * 
	 * @param src
	 * @param clazz
	 * @return the unmarshalled object
	 */
	public static <T> T unmarshalJson(String src, Class<T> clazz) {
		T obj = null;
		try {
			final ObjectMapper mapper = createJsonObjectMapper();
			obj = mapper.readValue(src, clazz);

			return obj;
		} catch (Exception e) {
			throw new RuntimeException("Error unmarshalling object to string.", e);
		}
	}	
	

	/**
	 * Create an {@link ObjectMapper} instance
	 */
	private static ObjectMapper createJsonObjectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		final AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		
		// make deserializer use JAXB annotations (only)
		mapper.getDeserializationConfig().setAnnotationIntrospector(introspector);
		mapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// make serializer use JAXB annotations (only)
		mapper.getSerializationConfig().setAnnotationIntrospector(introspector);
		
		mapper.getSerializationConfig().set(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		return mapper;
	}
}
