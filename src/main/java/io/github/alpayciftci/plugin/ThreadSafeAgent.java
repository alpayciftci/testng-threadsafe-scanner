package io.github.alpayciftci.plugin;

import java.lang.instrument.Instrumentation;

public class ThreadSafeAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("ThreadSafeAgent is active.");
        inst.addTransformer(new WebDriverTransformer());
    }
}