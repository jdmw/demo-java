package jd.designpattern.dict.topdesign.structure.pipefilter;

import jd.designpattern.common.dto.DataDTO;

public interface CompositFilter<D> extends Filter<D> {

	public SingleThreadCompositFilter<D> addFilter(Filter<DataDTO<D,Boolean>> filter);
	
	public void removeFilter(Filter<DataDTO<D,Boolean>> filter);
	
	public boolean match(D data);
}
