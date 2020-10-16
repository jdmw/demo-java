package ${packageName};

import java.io.*;
import java.net.*;
import java.util.*;
import jd.demo.se.bypkg.tools.compiler.example.Function1;
${imports}

class ${classname} implements Function1 {
	public Map doWithMap(Map map) throws Exception{
		${expression} ;
		return map ;
	}
}
