

package com.example.meeters.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.util.Log;

public class JSONUtils
{
    private static String TAG = JSONUtils.class.getSimpleName();
    public static ObjectMapper objectMapper = new ObjectMapper();

    static
    {

        // objectMapper.setVisibility(JsonMethod.FIELD,
        // JsonAutoDetect.Visibility.ANY) // auto-detect all member fields
        // .setVisibility(JsonMethod.GETTER, JsonAutoDetect.Visibility.NONE) //
        // but only public getters
        // .setVisibility(JsonMethod.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        // // and none of "is-setters"
        // .setVisibility(PropertyAccessor.IS_GETTER,
        // JsonAutoDetect.Visibility.NONE);

    }

    @SuppressWarnings("unchecked")
    public static <typeClass> Object toObject(String jsonStr, Class<?> typeClass)
    {

        if (StringUtils.isBlank(jsonStr))
        {
            return null;
        }
        typeClass obj = null;
        try
        {
            obj = (typeClass) objectMapper.readValue(jsonStr, typeClass);
            System.out.println("JsonUtils to Obj:  " + toJson(obj));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return obj;

    }

    public static String toJson(Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        String str = null;
        try
        {
            str = objectMapper.writeValueAsString(obj);
        }
        catch (JsonGenerationException e)
        {
            // TODO
            e.printStackTrace();

        }
        catch (JsonMappingException e)
        {
            // TODO
            e.printStackTrace();

        }
        catch (IOException e)
        {
            // TODO
            e.printStackTrace();

        }

        return str;

    }

    public static byte[] toBytes(Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        byte[] bytes = null;
        try
        {
            bytes = objectMapper.writeValueAsBytes(obj);
        }
        catch (JsonGenerationException e)
        {
            // TODO
            e.printStackTrace();

        }
        catch (JsonMappingException e)
        {
            // TODO
            e.printStackTrace();

        }
        catch (IOException e)
        {
            // TODO
            e.printStackTrace();

        }

        return bytes;

    }

    public static Map<String, Object> strToMap(String json)
    {
        Log.i(TAG, "string to map input: ---->" + json);
        if (StringUtils.isBlank(json))
        {
            return null;
        }
        Map<String, Object> map = null;
        try
        {
            map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>()
            {
            });
        }
        catch (JsonParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
    }

    public static Map<String, Object> objToMap(Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        Map<String, Object> map = null;
        try
        {
            map = objectMapper.readValue((JsonParser) obj, new TypeReference<Map<String, Object>>()
            {
            });
        }
        catch (JsonParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
    }

    public static void main(String[] args)
    {
        // System.out.println("abcd");

        // String json = "{ \"username\":\"aaron\", \"age\":28, \"id\":258961}";
        //
        // User user = (User) convertJsonToObject(json, User.class);
        //
        // System.out.println(user.age);
        // System.out.println(user.username);
        // System.out.println(user.id);
        //
        // System.out.println(convertObjectToJson(user));

    }

}
