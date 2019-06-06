include "./demo.thrift"


namespace java jd.demo.rpc.thrift.interview

service Interview {
	
	bool interview(1: jd.demo.rpc.thrift.bean.User user);
}