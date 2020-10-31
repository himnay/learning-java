package com.org.java8.nashorn;

import com.org.java8.lambda.Person;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Calling javascript functions from java with nashorn.
 *
 * @author Himansu Nayak
 */
public class Nashorn1 {

    public static void main(String... args) throws Exception {
        final String ABS_PATH = "C:\\Mercury\\github\\java8-tutorial\\src\\main\\resources\\";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(ABS_PATH + "nashorn1.js"));

        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        System.out.println(result);
        System.out.println(result.getClass());

        invocable.invokeFunction("fun2", new Date());
        Object fun2 = invocable.invokeFunction("fun2", LocalDateTime.now());
        invocable.invokeFunction("fun2", new Person());
    }

}