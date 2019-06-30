package jd.demo.se.fundamentals;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

class V1{
	static String NOT_USE = ""; 
	String v1code ;  
	List<V2> v2List = new ArrayList<>();
	List<V3> v3List = new ArrayList<>();
	List<V4> v4List = new ArrayList<>();
	public V1(String code) {
		this.v1code = code;
	}
	public boolean addV2(V2 e) {
		return v2List.add(e);
	}
	public boolean addV3(V3 e) {
		return v3List.add(e);
	}
	public boolean addV4(V4 e) {
		return v4List.add(e);
	}
}

class V2{
	String v2code ;
	List<V5> v5List = new ArrayList<>();
	List<V6> v6List = new ArrayList<>();
	public V2(String code) {
		this.v2code = code;
	}
	public boolean addV5(V5 e) {
		return v5List.add(e);
	}
	public boolean addV6(V6 e) {
		return v6List.add(e);
	}
}

class V3{
	String v3code ;
	List<V7> v7List = new ArrayList<>();
	
	public V3(String code) {
		this.v3code = code;
	}
	public boolean addV7(V7 e) {
		return v7List.add(e);
	}
}

class V4{
	String v4code ;
	String v4name ;
	public V4(String code,String name) {
		this.v4code = code;
		this.v4name = name;
	}
}
class V5{
	String v5code ;
	String v5name ;
	public V5(String code,String name) {
		this.v5code = code;
		this.v5name = name;
	}
}

class V6{
	String v5code ;
	String v5name ;
	public V6(String code,String name) {
		this.v5code = code;
		this.v5name = name;
	}
}

class V7{
	String v7code ;
	String v7name ;
	public V7(String code,String name) {
		this.v7code = code;
		this.v7name = name;
	}
}

class V8{
	String v8code ;
	String v8name ;
	public V8(String code,String name) {
		this.v8code = code;
		this.v8name = name;
	}
}

public class Imix {

	/**
	 * examble
	 * 												v1-1
	 * 					_____________________________|___________________________________
	 * 					|															    |
	 * 				   v2-1													           v2-2
	 * 	________________|______________________							________________|____________
	 *       |   						|   											| 
	 *       v3-1					   v3-3												3-2
	 *       | 					________|_______																		
	 *       |				      |          |
	 *      v4-1 			     v4-2        v4-3
	 *       
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		V1 v11 = new V1("V1-1");
		V2 v21 = new V2("V2-1");
		V2 v22 = new V2("V2-2");
		V3 v31 = new V3("V3-1");
		V3 v32 = new V3("V3-2");
		//V3 v33 = new V3("V3-3");
		V4 v41 = new V4("V4-1","#4-1");
		//V4 v42 = new V4("V4-2","#4-2");
		//V4 v43 = new V4("V4-3","#4-3");
		V5 v51 = new V5("V5-1","#5-1");
		V5 v52 = new V5("V5-2","#5-2");
		V5 v53 = new V5("V5-3","#5-3");
		//V6 v61 = new V6("V6-1","#6-1");
		V7 v71 = new V7("V7-1","#7-1");
		v11.addV2(v21);
		v11.addV2(v22);
		v11.addV3(v31);
		v11.addV3(v32);
		v11.addV4(v41);
		v21.addV5(v51);
		v21.addV5(v52);
		v21.addV5(v53);
		v31.addV7(v71);
		v32.addV7(v71);
		List<Map<String,Object>> mapList = new ArrayList<>();
		
		new Imix().addInEachMap(mapList,v11);
		completeMaps(mapList);
		
		for(Map<String,Object> map : mapList){
			System.out.println(map);
		}
	}

	/**
	 * 判断是有效域的条件：
	 * 	1:不为enum
	 *  2:非static属性
	 *  。。。其他条件待修改
	 * @param f
	 * @return true/false
	 */
	private boolean isRequiredField(Field f){
		boolean isStaticField = ( Modifier.STATIC & f.getModifiers() ) > 0;
		return !isStaticField && !f.isEnumConstant() ;
	}
	/**
	 * 判断是需要的VO的条件：
	 *  1、非空
	 * 	1：是cn.com.cfets包中的类的对象（至少能排除String、Ingeger等javaApI中的类）
	 * 。。。其他条件待修改
	 * @param obj VoList中的一个元素
	 * @return true/false
	 */
	private boolean isVo(Object obj) {
		return obj != null && 
				obj.getClass().getPackage().getName().startsWith("cn.com.cfets");
	}
	
	@SuppressWarnings("rawtypes")
	public void addInEachMap(List<Map<String,Object>> mapList,Object obj) throws IllegalArgumentException, IllegalAccessException{
		if(!isVo(obj)){
			return ;
		}
		Map<String,Object> props = getPropties(obj);
		List<List> listList = getListList(obj);
		// 将VO的各属性加入MapList中的每个map
		if(mapList.size() ==0){
			mapList.add(props);
		}else{
			for(Map<String,Object> map : mapList){
				map.putAll(props);
			}
		}
		// 运用递给逐个解析当前VO中包括的vo列表，次数list1_size * list2_size * ... listn_size
		if(listList.size() > 0){
			cur(mapList,listList,0);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void cur(List<Map<String,Object>> mapList,List<List> listList,int listPos) throws IllegalArgumentException, IllegalAccessException{
		if(listPos == listList.size()){
			return ;
		}
		
		List list = listList.get(listPos);
		if(list.size() > 0){
			if(list.size() == 1){
				addInEachMap(mapList,list.get(0));
			}else{
				List<Map<String,Object>> mList = duplicateMap(mapList);// 模板
				
				addInEachMap(mapList,list.get(0));
				for(int i=2;i<list.size() ;i++){
					List<Map<String,Object>> newMl = duplicateMap(mList) ;
					addInEachMap(newMl,list.get(i));
					mapList.addAll(newMl);
				}
				
				addInEachMap(mList,list.get(1));
				mapList.addAll(mList);
			}
		}
		cur(mapList,listList,++listPos);
		
	}
	
	/**
	 * 复制mapList中的所有map，然后将复制出的所有map加入mapList
	 * @param mapList 包含要复制的map的列表
	 * @return Map列表，与mapList是同一对象
	 */
	private List<Map<String,Object>> duplicateMap(List<Map<String,Object>> mapList){
		List<Map<String,Object>> newMapList = new ArrayList<>(mapList.size());
		for(Map<String,Object> map:mapList){
			newMapList.add(cloneMap(map));
		}
		return newMapList ;
	}
	
	/**
	 * 复制一份map
	 * @param old 原map
	 * @return 新map
	 */
	private Map<String,Object> cloneMap(Map<String,Object> old){
		Iterator<String> iter = old.keySet().iterator();
		Map<String,Object> newMap = new HashMap<>();
		while(iter.hasNext()){
			String key = iter.next();
			newMap.put(key, old.get(key));
		}
		return newMap;
	}
	
	/**
	 * 解析一个VO对象，将其包含的有用域-值，全部添加到map中
	 * @param obj 视图对象
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	
	private Map<String,Object> getPropties(Object obj) throws IllegalArgumentException, IllegalAccessException{
		Map<String,Object> props = new HashMap<>();
		if(obj != null ){
			Class<?> voClass = obj.getClass();
			for(Field f : voClass.getDeclaredFields()){
				f.setAccessible(true);
				if(isRequiredField(f) && !f.getType().equals(List.class)){
					props.put(f.getName(), f.get(obj));
				}
			}
		}
		return props ;
	}
	
	/**
	 * 解析一个VO对象，依次遍历其中的属性，找出为需要的VO列表的属性，组成集合返回
	 * @param obj 视图对象 
	 * @return VO列表的集合
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private List<List> getListList(Object obj) throws IllegalArgumentException, IllegalAccessException{
		List<List> listList = new ArrayList<>();
		if(obj != null ){
			Class<?> voClass = obj.getClass();
			for(Field f : voClass.getDeclaredFields()){
				f.setAccessible(true);
				if(isRequiredField(f) && f.getType().equals(List.class)){
					if(f.get(obj) != null ){
						List l = (List)f.get(obj);
						if(l.size() > 0 && isVo(l.get(0))){
							listList.add((List)f.get(obj));
						}
					}
				}
			}
		}
		return listList ;
	}
	
	/**
	 * 对于一个笛卡尔积必要的null域，例如  集合[A=1] * [B=3,B=4] + [A=2] * [] 
	 * 得到 	A=1,B=3
	 * 		A=1,B=4
	 * 		A=2
	 * 补全B=null后为
	 * 		A=1,B=3
	 * 		A=1,B=4
	 * 		A=2,B=null
	 * @param mapList
	 */
	private static void completeMaps(List<Map<String,Object>> mapList){
		Iterator<String> iter = null;
		Set<String> keySet = new TreeSet<>();
		
		// 获得最多的Key名集合
		for(Map<String,Object> map : mapList){
			keySet.addAll(map.keySet());
		}
		
		// 补全空键值对
		for(Map<String,Object> map : mapList){
			iter = keySet.iterator();
			while(iter.hasNext()){
				String key = iter.next();
				if(!map.containsKey(key)){
					map.put(key, null);
				}
			}
		}
	}
}
