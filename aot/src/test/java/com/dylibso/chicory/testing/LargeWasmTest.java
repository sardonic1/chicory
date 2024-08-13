package com.dylibso.chicory.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dylibso.chicory.aot.AotMachine;
import com.dylibso.chicory.log.SystemLogger;
import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.Module;
import com.dylibso.chicory.wabt.Wat2Wasm;
import com.dylibso.chicory.wasm.types.Value;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

public class LargeWasmTest {

    @Test
    public void testFunc50k() {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        System.out.println(new File(".").getAbsolutePath());
        var instance =
                Module.builder(buildHugeWasm(50_000))
                        .withLogger(new SystemLogger())
                        .withMachineFactory(AotMachine::new)
                        .withStart(false)
                        .build()
                        .instantiate();

        ExportFunction func1 = instance.export("func_1");
        assertEquals(42, func1.apply(Value.i32(50_045))[0].asInt());
    }

    private byte[] buildHugeWasm(int funcCount) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("(module");
        pw.println();
        String function1 =
                "(func $func_1 (param i32) (result i32)\n"
                        + "    local.get 0\n"
                        + "    i32.const 1\n"
                        + "    i32.sub\n"
                        + "    call $func_"
                        + funcCount
                        + "\n"
                        + ")";
        pw.println(function1);
        pw.println();
        String function =
                "(func $func_%d (param i32) (result i32)\n"
                        + "    local.get 0\n"
                        + "    i32.const %d\n"
                        + "    i32.sub\n"
                        + ")";
        for (int i = 2; i < funcCount; i++) {
            pw.println(String.format(function, i, i));
            pw.println();
        }
        String functionEnd =
                "(func $func_"
                        + funcCount
                        + " (param i32) (result i32)\n"
                        + "    local.get 0\n"
                        + "    i32.const "
                        + funcCount
                        + "\n"
                        + "    i32.sub\n"
                        + "    call $func_2\n"
                        + ")";
        pw.println(functionEnd);
        pw.println();
        for (int i = 1; i <= funcCount; i++) {
            pw.println(String.format("(export \"func_%d\" (func $func_%d))", i, i));
        }
        pw.println(")");

        return Wat2Wasm.parse(sw.toString());
    }
}
