package jd.designpattern.dict.topdesign.structure;

public interface Observer<S> { // S:Subject
	public void register();
	//public void notify();
}

interface Subject<O> {
	public void register();
	//public void notify();
}

class AbstactObserver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
