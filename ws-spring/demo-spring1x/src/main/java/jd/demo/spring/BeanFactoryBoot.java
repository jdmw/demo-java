package jd.demo.spring;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jd.util.ui.swing.ComponentUtil;

public class BeanFactoryBoot extends JFrame {

	private JTree tree ;
	private File baseFile ;
	
	/**
	 * spring demo
	 * @param pkg 
	 * 		package path 
	 */
	public BeanFactoryBoot(String pkg) {
		super("Spring DEMO");
		try {
			baseFile = new File(this.getClass().getClassLoader().getResource(pkg).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		tree = new JTree();
		scan();
		//tree.setCellRenderer(new TreeCellRenderer());
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(e->{
			//if(null != e.getSource() && e.getSource() instanceof DefaultMutableTreeNode) {
				Object[] path = e.getNewLeadSelectionPath().getPath();
				if(path[path.length-1].toString().endsWith(".xml")) {
					File file = baseFile ;
					for(int i=1;i<path.length;i++) {
						file = new File(file,path[i].toString());
					}
					//File file =(File) ((DefaultMutableTreeNode)e.getSource()).getUserObject();
					if(file != null && file.exists()) {
						start(file);
					}
				}
				
			//}
		});
		
		JScrollPane treeView = new JScrollPane(tree);
		this.add(treeView);
		ComponentUtil.show(this,500,400);
	}
	
	public void scan()  {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("spring configuration");
		DefaultTreeModel model = new DefaultTreeModel(root);
		scan(model,baseFile,root);
		tree.setModel(model);
		tree.expandPath( new TreePath(root));
	}
	
	public void scan(DefaultTreeModel model,File dirFile,DefaultMutableTreeNode parentNode) {
		if(dirFile.isDirectory() ) {
			File[] xmlFiles = dirFile.listFiles(filter->filter.getName().endsWith(".xml"));
			File[] dirFiles = dirFile.listFiles(filter->filter.isDirectory());
			if(xmlFiles.length > 0 || dirFiles.length > 0) {
				if(dirFiles.length > 0){
					for(File file : dirFiles) {
						DefaultMutableTreeNode subDirNode = new DefaultMutableTreeNode(file.getName());
						scan(model,file,subDirNode);
						model.insertNodeInto(subDirNode, parentNode, parentNode.getChildCount());
					}
				}
				if(xmlFiles.length > 0){
					for(File file : xmlFiles) {
						DefaultMutableTreeNode node =  new DefaultMutableTreeNode(file.getName());
						//node.setUserObject(file);
						//Console.fmt("%s add %s \n",parentNode.toString(),parentNode.toString());
						model.insertNodeInto(node, parentNode, parentNode.getChildCount());
					}
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		new BeanFactoryBoot(BeanFactoryBoot.class.getPackage().getName().replaceAll("\\.","/"));
		
	}
	
	
	public static ApplicationContext start(String ... xmlPaths) {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(xmlPaths);
		return appContext ;
	}
	
	public static BeanFactory start(File xmlPath) {
		XmlBeanFactory factory = null;
		try {
			factory = new XmlBeanFactory(new FileInputStream(xmlPath));
			System.out.println("start bean factory with configuration:"+xmlPath);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return factory ;
	}

}
