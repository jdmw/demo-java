package jd.demo.se9.nashorn;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DemoGetScriptEngine {
    public static void main(String[] args) throws ScriptException {
        ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn");
        nashorn.eval("print(1+2)");
    }
}
