package jd.demo.se.collections.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jd.demo.example.common.SPerson;
import jd.util.lang.Console;
import jd.util.lang.RunUt;

/**
 * reference: https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html
 * @author jdmw
 *
 */
public class DemoListJdk8ConceptAggregateParallelismDemo {

	
	private static final List<SPerson> roster = SPerson.demoList();
	
	public static void sorting() {
		int length = 1000 ;//Integer.MAX_VALUE ;
		List<Integer> ascList = new ArrayList<>(length);
		List<Integer> descList = new ArrayList<>(length);
		for(int i=0;i<length;i++) {
			ascList.add(i);
			descList.add(0,i);
		}
		Comparator<Integer> comp = Integer::compare ;
		
		int times = 10 ;
		RunUt.printTimeNs("\nlistOfIntegers sorted in normal order:\n", ()->{
			ascList
				.stream()
				.forEach(e-> Console.print(e + ", "));
		},times);
		
		RunUt.printTimeNs("\nlistOfIntegers sorted in reverse order:\n", ()->{
			descList
				.stream()
				.forEach(e-> Console.print(e + ", "));
		},times);
		
		RunUt.printTimeNs("\nParallel stream:\n", ()->{
			ascList
				.parallelStream()
				.forEach(e -> Console.print(e + ", "));
		},times);
		
		//  lose the benefits of parallelism if you use operations like forEachOrdered with parallel streams.
		RunUt.printTimeNs("\nwith forEachOrdered:\n", ()->{
			descList
				.parallelStream()
				.forEachOrdered(e -> Console.print(e + ", "));
		},times);
	}
	
	public static void main(String[] args) {
/*		RunUt.printTimeMs("reduction", ()->{
			roster
				.stream()
				.filter(e->e.getGender() == Sex.FEMALE)
				.collect(Collectors.groupingBy(Person::getCity));
		},10);

		RunUt.printTimeMs("concurrent reduction", ()->{
			roster
				.parallelStream()
				.filter(e->e.getGender() == Sex.FEMALE)
				.collect(Collectors.groupingByConcurrent(Person::getCity));
		});*/
		
		sorting();
	}

}
