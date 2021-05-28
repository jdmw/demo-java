package com.fourinone.kv;

public interface CoolHash{
	public Object get(String key) throws CoolHashException;
	public <T> T get(String key, Class<T> valueType) throws CoolHashException;
	public <T> T get(String key, Class<T> valueType, boolean point, String... pointSubKey) throws CoolHashException;
	public <T> Object put(String key, T value) throws CoolHashException;
	public Object remove(String key) throws CoolHashException;
	public String putPoint(String keyPoint, String key) throws CoolHashException;
	public Object getPoint(String keyPoint, String... pointSubKey) throws CoolHashException;
	public <T> T getPoint(String keyPoint, Class<T> valueType, String... pointSubKey) throws CoolHashException;
	public CoolHashMap get(CoolHashMap.CoolKeySet<String> keys);
	public CoolHashMap get(CoolHashMap.CoolKeySet<String> keys, Filter filter);
	public CoolHashMap get(CoolHashMap.CoolKeySet<String> keys, Filter filter, boolean point, String... pointSubKey);
	public int put(CoolHashMap keyvalue);
	public int remove(CoolHashMap.CoolKeySet<String> keys);
	public CoolKeyResult findKey(String keywild);
	public CoolKeyResult findKey(String keywild, Filter filter);
	public CoolKeyResult findKey(String keywild, Filter filter, boolean point, String... pointSubKey);
	public CoolHashResult find(String keywild);
	public CoolHashResult find(String keywild, Filter filter);
	public CoolHashResult find(String keywild, Filter filter, boolean point, String... pointSubKey);
	//public int remove(String keywild, Filter filter);
	//2017.10
	public <T> Object putPlus(String key, T plusValue) throws CoolHashException;
	public <T> Object putNx(String key, T value) throws CoolHashException;
	public int putBitSet(String key, int index) throws CoolHashException;
	public boolean getBitSet(String key, int index) throws CoolHashException;
	public int putBitSet(String key, CoolBitSet cbs) throws CoolHashException;
	public Object putBitSet(String key, CoolBitSet cbs, String logical) throws CoolHashException;
}