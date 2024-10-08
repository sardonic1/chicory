package com.dylibso.chicory.imports;

import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.HostImports;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasm.types.Value;
import com.dylibso.chicory.wasm.types.ValueType;
import java.util.List;

public final class SpecV1RefFuncHostFuncs {

    private SpecV1RefFuncHostFuncs() {}

    public static HostImports fallback() {
        return HostImports.builder()
                .addFunction(
                        new HostFunction(
                                (Instance instance, Value... args) -> args,
                                "M",
                                "f",
                                List.of(ValueType.I32),
                                List.of(ValueType.I32)))
                .addFunction(
                        new HostFunction(
                                (Instance instance, Value... args) -> args,
                                "M",
                                "g",
                                List.of(ValueType.I32),
                                List.of(ValueType.I32)))
                .build();
    }
}
