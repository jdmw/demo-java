package jd.demo.se.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class DemoSeriable {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Set<String> set = new TreeSet<>(new TimeComp());
		set.addAll(Arrays.asList("10:00","9:00"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		/*PipedInputStream pis = new PipedInputStream();
		PipedOutputStream pos = new PipedOutputStream();*/
		oos.writeObject(set);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		System.out.println(obj);
	}

	private static class TimeComp implements Serializable,Comparator<String>{
		private static final long serialVersionUID = 5754443716466403881L;

		@Override
		public int compare(String o1, String o2) {
			Integer t1 = Integer.valueOf(o1.replace(":", ""));
			Integer t2 = Integer.valueOf(o2.replace(":", ""));
			return t1.compareTo(t2);
		}
	};
}
