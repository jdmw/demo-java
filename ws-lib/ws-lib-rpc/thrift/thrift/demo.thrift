namespace java jd.demo.rpc.thrift

enum Sex {
	M,W ;
}

 
struct User {

	1: optional string name ;
	2: optional Sex sex ;
	3: optional i32 age ;
}



service Interview {
	
	bool interview(1: User user);
}