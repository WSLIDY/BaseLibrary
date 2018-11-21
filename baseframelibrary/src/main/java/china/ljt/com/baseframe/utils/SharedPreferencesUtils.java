package china.ljt.com.baseframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * sharePreferences 工具类
 * 
 * 缓存对象 字符串 boolean
 * 
 * @author Leader 
 * 
 */
public class SharedPreferencesUtils {

	public static String SP_NAME = "config";
	private static SharedPreferences sp;
	private static String serStr;
	private static Object deSerialization;

	/**
	 * 序列化保存对象
	 * 
	 * @param context
	 * @param key
	 * @param obj
	 *            要保存的对象，只能保存实现了serializable的对象 modified:
	 */
	public static void saveObject(Context context, String key, Object obj) {
		String serialize = "";
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		try {
			serialize = serialize(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sp.edit().putString(key, serialize).commit();
	}

	/**
	 * 获得序列化对象
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Object getObject(Context context, String key) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		//
		String string = getString(context, key, "");

		try {
			deSerialization = deSerialization(string);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deSerialization;
	}

	/**
	 * 保存 boolead 类型的数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 获取 boolead 类型的保存数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
									 boolean defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 保存 string 类型的数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key, String value) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 获取 保存的 string 数据
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}

	/**
	 * 清除所有缓存数据
	 * 
	 * @param context
	 */
	public static void clearAll(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().clear().commit();
	}

	/**
	 * 序列化对象
	 * 
	 * @param obj
	 */
	private static String serialize(Object obj) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(obj);
		serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		return serStr;
	}

	/**
	 * 反序列化对象
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Object deSerialization(String str) throws IOException,
			ClassNotFoundException {
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		Object person = (Object) objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
		return person;
	}
}
