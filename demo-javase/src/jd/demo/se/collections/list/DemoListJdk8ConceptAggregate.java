package jd.demo.se.collections.list;

import jd.demo.example.common.SPerson;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemoListJdk8ConceptAggregate {

	private static List<SPerson> roster = SPerson.demoList();
	
	private static void demoAggregate() {
		/** aggregate */
		out.println("men in list:");
		roster.stream().filter( person ->person.getGender() == SPerson.Sex.MALE)
			.forEach(e -> out.format("\t%s %s(%d)\n",e.getGender()== SPerson.Sex.MALE?"M":"W",e.getName(),e.getAge()));
		
		
		out.println("\n" + roster.stream().findFirst().get().getName() + " is a man");
		
		// IntStream
		out.format("average age : %f\tsum:%d\tcount:%d\n",roster.stream().mapToInt(SPerson::getAge).average().getAsDouble(),
				roster.stream().mapToInt(SPerson::getAge).sum(),roster.stream().mapToInt(SPerson::getAge).count());
	}
	
	private static void demoReduce() {
		/** reduction */
		int sum = roster.stream().mapToInt(SPerson::getAge).reduce(0,(a,b)->a+b);
		int max = roster.stream().mapToInt(SPerson::getAge).reduce(0,(a,b)->a>b?a:b);
		int min = roster.stream().mapToInt(SPerson::getAge).reduce(0,(a,b)->a<b?a:b);
		out.format("sum : %d\tmax:%d\tmin:%d\n",sum,max,min);
		
		// accumulate names
		List<String> nameList = roster.stream().map(SPerson::getName).collect(Collectors.toList());
		out.println("names : " + Arrays.toString(nameList.toArray()));
		
		// growping by city 
		Map<String,List<SPerson>> peopleByGroup = roster.stream().collect(Collectors.groupingBy(SPerson::getCity));
		
		Map<SPerson.Sex,List<SPerson>> peopleByGender = roster.stream().collect(Collectors.groupingBy(SPerson::getGender));
		
		
		Map<SPerson.Sex,Double> averageAageByGender = roster.stream()
				.collect(Collectors.groupingBy(SPerson::getGender, Collectors.averagingDouble(SPerson::getAge)));
		
		//out.println("totalAgeByGender" + totalAgeByGender);
		out.println("averageAageByGender" + averageAageByGender);
	}

	
	public static void main(String[] args) {
		
		demoAggregate();
		demoReduce();
		
	}

}
