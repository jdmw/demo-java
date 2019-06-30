package jd.demo.se.fundamentals.languagebasic.controlflow;

public class DemoSwitch {

	public static void main(String[] args){
		System.out.println(testswith(1));
		System.out.println(testswith(5));
		System.out.println(testswith(100));
		int i = 1 ;
		while(i++<6) {
			System.out.format("test(%d) = %d\n",i,testswith2(i));
		}
		
		
		System.out.println(testNull( null));
	}
	
	public static int testif(int a){
		int b;
		if(a>0){
			b =2 ;
		}else if(a==0){
			b = 3 ;
		}else{
			b = 4 ;
		}
		return b ;
	}
	
	
	
	public static int testswith(int a){
		int b = 0 ;
		switch(a){
			case 1 : 
			case 2 :
			case 3 : b = 3 ;break;
			case 4 : b = 4 ;break;
			case 5 : b = 5 ;
			default : b = -1 ;
		}
		return b ;
	}
	
	public static int testswith1(int a){
		int b = 0 ;
		switch(a){
			case 1 : b = 1 ;break;
			case 2 : b = 2 ;break;
			case 3 : b = 3 ;break;
			case 4 : b = 4 ;break;
			case 5 : b = 5 ;break;
			default : b = -1 ;
		}
		return b ;
	}
	
	
	public static int testswith2(int a){
		int b = 0 ;
		switch(a){
			case 1 : 
			case 2 :
			case 3 : b = 3 ;break;
			case 4 : b = 4 ;break;
			default : b = -1 ;
			case 5 : b = 5 ;
			
		}
		return b ;
	}
	
	public static int testNull(String a) {
		switch(a) {
			case "a" : 
				return 1 ;
			default : return 0 ;
		}
	}
}
