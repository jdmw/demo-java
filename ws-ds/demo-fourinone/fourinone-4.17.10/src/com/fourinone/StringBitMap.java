package com.fourinone;

import com.fourinone.kv.CoolBitSet;

import java.io.Serializable;

public class StringBitMap implements Serializable
{
	int[] seeds = {3,5,7,11,13,17,19,23,31,37,41,43,47,53,59,61,67};
	CoolBitSet bs = new CoolBitSet(0x7FFFFFFF);
	
	int[] hashCode(String str)
	{
		int[] hs = new int[seeds.length];
		for(int j=0;j<seeds.length;j++){
			int h = 0;
			for(int i=0;i<str.length();i++)
        		h=h*seeds[j]+str.charAt(i);
	        hs[j]=h&0x7FFFFFFF;
		}
		return hs;
	}
	
	public boolean get(String str){
		int[] hs = hashCode(str);
		boolean flag = true;
		for(int h:hs)
			flag = flag&&bs.get(h);
		return flag;
	}
	
	public void set(String str){
		int[] hs = hashCode(str);
		for(int h:hs)
			bs.set(h);
		//total++;
	}
	
	public byte[] getBytes(){
		return bs.bitArr;
	}
	
	public String randomIMEI(){
		return (int)((Math.random()+1)*10000000)+""+(int)((Math.random()+1)*1000000);
	}
	
	public int collisionTest(int num){
		int count=0;
		for(int i=0;i<num;i++){
			String idstr = randomIMEI()+i;
			if(get(idstr))
				count++;
			else
				set(idstr);
		}
		return count;
	}
}