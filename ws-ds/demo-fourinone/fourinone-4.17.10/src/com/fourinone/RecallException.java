package com.fourinone;

import com.fourinone.pattern.warehouse.WareHouse;
import com.fourinone.util.LogUtil;

public class RecallException extends ServiceException {
	private boolean recall;
	
	public RecallException(){
		super("The call has not been returned yet, you cant repeat call!");
	}
	
	public void setRecall(boolean recall){
		this.recall = recall;
	}
	
	public boolean checkRecall() throws RecallException
	{
		if(recall)
			throw this;
		return recall;
	}
	
	public int tryRecall(WareHouse inhouse)
	{
		try{
			if(!checkRecall())
				setRecall(true);
		}catch(RecallException re){
			LogUtil.info("[RecallException]", "[tryRecall]"+inhouse, re);
			return -1;
		}
		return 0;
	}
}