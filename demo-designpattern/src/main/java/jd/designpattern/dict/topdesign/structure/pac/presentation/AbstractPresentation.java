package jd.designpattern.dict.topdesign.structure.pac.presentation;

import javax.swing.JPanel;

interface Presentation {
	public void notifyActive();
}


public abstract class AbstractPresentation extends JPanel implements Presentation {

	@Override
	public void notifyActive() {}

}
