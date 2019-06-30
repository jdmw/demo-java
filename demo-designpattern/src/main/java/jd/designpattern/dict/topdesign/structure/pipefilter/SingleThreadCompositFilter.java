package jd.designpattern.dict.topdesign.structure.pipefilter;

import java.util.ArrayList;
import java.util.List;

import jd.designpattern.common.dto.DataDTO;

public class SingleThreadCompositFilter<D> implements CompositFilter<D> {

	public enum Logic{AND,OR} ;
	public Logic logic = Logic.AND;
	public boolean shortCur = true ;
	
	private final List<Filter<DataDTO<D,Boolean>>> filters = new ArrayList<>();
	
	@Override
	public SingleThreadCompositFilter<D> addFilter(Filter<DataDTO<D,Boolean>> filter){
		filters.add(filter);
		return this;
	}
	
	@Override
	public void removeFilter(Filter<DataDTO<D,Boolean>> filter){
		filters.remove(filter);
	}
	
	@Override
	public boolean match(D data) {
		boolean ib = false;
		if(!filters.isEmpty()){
			DataDTO<D,Boolean> dto = new DataDTO<>(data,null);
			if(logic.equals(Logic.AND)){
				ib = true ;
				for(Filter<DataDTO<D,Boolean>> f : filters){
					if(shortCur && !ib){
						break;
					}
					ib &= f.match(dto);
					dto.setResult(ib);
				}
			}else if(logic.equals(Logic.OR)){
				ib = false ;
				for(Filter<DataDTO<D,Boolean>> f : filters){
					if(shortCur && ib){
						break;
					}
					ib |= f.match(dto);
					dto.setResult(ib);
				}
			}
		}
		return ib;
	}
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/
}
