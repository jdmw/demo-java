package com.fourinone.kv;

import com.fourinone.util.LogUtil;

import java.io.Serializable;

public class CoolBitSet implements Serializable
{
	static final int defaultMax = 0x5f5e100;
	static final byte[] btable = {
		0,1,1,2,1,2,2,3,1,2,2,3,2,3,3,4,1,2,2,3,
		2,3,3,4,2,3,3,4,3,4,4,5,1,2,2,3,2,3,3,4,
		2,3,3,4,3,4,4,5,2,3,3,4,3,4,4,5,3,4,4,5,
		4,5,5,6,1,2,2,3,2,3,3,4,2,3,3,4,3,4,4,5,
		2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,2,3,3,4,
		3,4,4,5,3,4,4,5,4,5,5,6,3,4,4,5,4,5,5,6,
		4,5,5,6,5,6,6,7,1,2,2,3,2,3,3,4,2,3,3,4,
		3,4,4,5,2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,
		2,3,3,4,3,4,4,5,3,4,4,5,4,5,5,6,3,4,4,5,
		4,5,5,6,4,5,5,6,5,6,6,7,2,3,3,4,3,4,4,5,
		3,4,4,5,4,5,5,6,3,4,4,5,4,5,5,6,4,5,5,6,
		5,6,6,7,3,4,4,5,4,5,5,6,4,5,5,6,5,6,6,7,
		4,5,5,6,5,6,6,7,5,6,6,7,6,7,7,8
	};
	
	public byte[] bitArr = null;//byte[0x1312D0]
	
	public CoolBitSet(){
		this(0x989680, true);//0x7FFFFFFF
	}
	
	public CoolBitSet(int maxSize){
		this(maxSize, false);
	}
	
	CoolBitSet(byte[] bitArr){
		this.bitArr = bitArr;
	}
	
	CoolBitSet(int maxSize, boolean free){
		/*if(!free){
			if(maxSize<=0||maxSize>defaultMax)
			maxSize=defaultMax;
		}*/
		int r = (maxSize&0x7)>0?1:0;
		this.bitArr = new byte[(maxSize>>0x3)+r];
	}
	
	public boolean get(int n){
		int i=0;
		try{
			i = bitArr[n>>0x3]&(byte)0x1<<(n&0x7);//(byte):-128
		}catch(Exception ex){
			LogUtil.info("[CoolBitSet]", "[get]", ex.toString());
		}
		return i!=0?true:false;
	}
	
	public void set(int n){
		try{
			bitArr[n>>0x3]|=(byte)0x1<<(n&0x7);
		}catch(Exception ex){
			LogUtil.info("[CoolBitSet]", "[set]", ex.toString());
		}
	}
	
	public int put(int n){
		if(get(n))
			return 1;
		set(n);
		return 0;
	}
	
	public int set(byte[] bitArr){
		int n = 0;
		int m = Math.min(this.bitArr.length, bitArr.length);
		for(int i=0;i<m;i++){//this.bitArr?
			if(this.bitArr[i]!=bitArr[i]){
				n+=btable[((this.bitArr[i]|bitArr[i])^this.bitArr[i])&0xff];
				this.bitArr[i]|=bitArr[i];
			}
		}
		return n;	
	}
	
	public int set(CoolBitSet cbs){
		return set(cbs.bitArr);
	}
	
	public CoolBitSet setNew(CoolBitSet cbs){
		return binaryOperation("new", cbs);
	}
	
	public CoolBitSet and(CoolBitSet cbs){
		return binaryOperation("and", cbs);
	}
	
	public CoolBitSet or(CoolBitSet cbs){
		return binaryOperation("or", cbs);
	}
	
	public CoolBitSet xor(CoolBitSet cbs){
		return binaryOperation("xor", cbs);
	}
	
	public CoolBitSet andnot(){
		return binaryOperation("andnot", null);
	}
	
	CoolBitSet binaryOperation(String opera, CoolBitSet cbs){
		int m = cbs!=null?Math.min(this.bitArr.length,cbs.bitArr.length):this.bitArr.length;
		for(int i=0;i<m;i++){
			if(opera.equals("and"))
				this.bitArr[i]&=cbs.bitArr[i];
			if(opera.equals("or"))
				this.bitArr[i]|=cbs.bitArr[i];
			if(opera.equals("xor"))
				this.bitArr[i]^=cbs.bitArr[i];
			if(opera.equals("andnot"))
				this.bitArr[i]=(byte)~this.bitArr[i];
			if(opera.equals("new"))
				this.bitArr[i]=(byte)((this.bitArr[i]|cbs.bitArr[i])^this.bitArr[i]);
		}
		return this;
	}	
	
	public byte[] getBytes(){
		return this.bitArr;
	}
	
	public int getTotal(){
		int total=0; 
		for(byte b:bitArr)
			total+=btable[b&0xff];
		return total;
	} 
	
	/*
	public int getTotal(){
		int total=0; 
		for(byte b:bitArr){
			int i=b&0xff;//
			while(i>0){
				total++;
				i&=i-1;//1000->0111
			}
		}
		return total;
	}*/
	
	public int getSize(){
		return  this.bitArr.length<<0x3;
	}
	
	public String toString(int num){
		StringBuffer bitStr = new StringBuffer();
		int m = Math.min(bitArr.length, num);
		for(int i=m-1;i>-1;i--)
			bitStr.append(Integer.toBinaryString((bitArr[i]&0xFF)+0x100).substring(1)).append(",");
		return bitStr.toString();
	}
}