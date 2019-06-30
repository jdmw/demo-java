package jd.designpattern.dict.topdesign.structure.pac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jd.designpattern.dict.topdesign.structure.pac.presentation.AbstractPresentation;
import jd.designpattern.dict.topdesign.structure.pac.presentation.SettingPane;
import jd.designpattern.dict.topdesign.structure.pac.presentation.WorkPane;
import jd.util.ui.swing.ComponentUtil;
import jd.util.ui.swing.InteractiveUtil;

public class DMainPac extends JFrame {

	private static final long serialVersionUID = -9092613001430720636L;
	private WorkPane workPane = new WorkPane();
	private SettingPane settingPane = new SettingPane();
	JMenuItem setMi = new JMenuItem("Setting");
	JMenuItem workMi = new JMenuItem("Work");
	
	public DMainPac(String title){
		super(title);
		init();
	}
	
	private void init(){
		// menu
		
		JMenuItem exitMi = new JMenuItem("Exit");
		JMenu viewMu = new JMenu("View");
		JMenuBar menubar = new JMenuBar();
		menubar.add(viewMu).add(workMi);
		viewMu.add(exitMi);
		menubar.add(new JMenu("Setting")).add(setMi);
		this.setJMenuBar(menubar);
		
		// panels
		this.getContentPane().add(workPane);
		
		ActionListener menuAction = new ActionListener(){
			Map<JMenuItem,AbstractPresentation> mpMap = new HashMap<>();
			{
				mpMap.put(workMi,workPane);
				mpMap.put(setMi,settingPane);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractPresentation changePane = mpMap.get(e.getSource());
				if(changePane != null){
					/*for(Component pane : mpMap.values()){//DemoPacMain.this.getComponents()){
						DemoPacMain.this.remove(pane);
					}*/
					changePane.setBounds(DMainPac.this.getContentPane().bounds());
					DMainPac.this.setContentPane(changePane);
					changePane.notifyActive();
				}
				
			};
		};
		workMi.addActionListener(menuAction);
		setMi.addActionListener(menuAction);
		exitMi.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(InteractiveUtil.confirm("Save file?")){
					workPane.save();
				}
				System.exit(0);
			}
			
		});
/*		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				int code = e.getKeyCode();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int code = e.getKeyCode();
				
				System.out.println("pressed"+code + " -> " 
						+ (e.isControlDown()?" Ctrl+":"")
						+ (e.isShiftDown()?" Shift+":"")
						+ (e.isAltDown()?" Alt+":"")
						+ e.getKeyText(code)   );
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
			}
			
		});*/
		ComponentUtil.show(this,600,500);
	}
	
	
	
	public static void main(String[] args) {
		new DMainPac("Demo PAC");

	}

}
