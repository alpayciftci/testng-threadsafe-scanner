package io.github.alpayciftci.plugin;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

public class WebDriverTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        if (className.contains("ChromeDriver") || className.contains("FirefoxDriver") ||
                className.contains("EdgeDriver") || className.contains("SafariDriver") || className.contains("OperaDriver")) {
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass ctClass = cp.get(className.replace("/", "."));
                CtConstructor[] constructors = ctClass.getDeclaredConstructors();

                for (CtConstructor constructor : constructors) {
                    constructor.setBody("{ return io.github.alpayciftci.plugin.ThreadSafeDriver.getDriver(\"" + className + "\"); }");
                }

                return ctClass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}