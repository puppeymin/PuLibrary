package com.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.framework.annotation.Id;
import com.framework.annotation.Table;


/**
 * 数据库内部工具类
 * @author lee
 *
 */
public class ClassUtils {
	
	
	/**
	 * 根据实体类 获得 实体类对应的表名
	 * @param entity
	 * @return
	 */
	public static String getTableName(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if(table == null || table.name().trim().length() == 0 ){
			return clazz.getName().replace('.', '_');
		}
		return table.name();
	}
	
	public static Object getPrimaryKeyValue(Object entity) {
		return FieldUtils.getFieldValue(entity,ClassUtils.getPrimaryKeyField(entity.getClass()));
	}
	
	/**
	 * 根据实体类 获得 实体类对应的表名
	 * @param entity
	 * @return
	 */
	public static String getPrimaryKeyColumn(Class<?> clazz) {
		String primaryKey = null ;
		Field[] fields = clazz.getDeclaredFields();
		if(fields != null){
			Id idAnnotation = null ;
			Field idField = null ;
			
			for(Field field : fields){ //获取ID注解
				idAnnotation = field.getAnnotation(Id.class);
				if(idAnnotation != null){
					idField = field;
					break;
				}
			}
			
			if(idAnnotation != null){ //有ID注解
				primaryKey = idAnnotation.column();
				if(primaryKey == null || primaryKey.trim().length() == 0)
					primaryKey = idField.getName();
			}else{ //没有ID注解,默认去找 _id 和 id 为主键，优先寻找 _id
				for(Field field : fields){
					if("_id".equals(field.getName()))
						return "_id";
				}
				
				for(Field field : fields){
					if("id".equals(field.getName()))
						return "id";
				}
			}
		}else{
			throw new RuntimeException("this model["+clazz+"] has no field");
		}
		return primaryKey;
	}
	
	
	/**
	 * 根据实体类 获得 实体类对应的表名
	 * @param entity
	 * @return
	 */
	public static Field getPrimaryKeyField(Class<?> clazz) {
		Field primaryKeyField = null ;
		Field[] fields = clazz.getDeclaredFields();
		if(fields != null){
			
			for(Field field : fields){ //获取ID注解
				if(field.getAnnotation(Id.class) != null){
					primaryKeyField = field;
					break;
				}
			}
			
			if(primaryKeyField == null){ //没有ID注解
				for(Field field : fields){
					if("_id".equals(field.getName())){
						primaryKeyField = field;
						break;
					}
				}
			}
			
			if(primaryKeyField == null){ // 如果没有_id的字段
				for(Field field : fields){
					if("id".equals(field.getName())){
						primaryKeyField = field;
						break;
					}
				}
			}
			
		}else{
			throw new RuntimeException("this model["+clazz+"] has no field");
		}
		return primaryKeyField;
	}
	
	
	/**
	 * 根据实体类 获得 实体类对应的表名
	 * @param entity
	 * @return
	 */
	public static String getPrimaryKeyFieldName(Class<?> clazz) {
		Field f = getPrimaryKeyField(clazz);
		return f==null ? null:f.getName();
	}
	
	/**
	 * 返回一个对象的泛型的数据类型
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static <T> Class<T> getSuperClassGenricType(Class clazz,int index) {  
        
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。  
        Type genType = clazz.getGenericSuperclass();  
  
        if (!(genType instanceof ParameterizedType)) {  
           return (Class<T>) Object.class;  
        }  
        //返回表示此类型实际类型参数的 Type 对象的数组。  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
                     return (Class<T>) Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
              return (Class<T>) Object.class;  
        }  
  
        return (Class<T>) params[index];  
    }
	
}
