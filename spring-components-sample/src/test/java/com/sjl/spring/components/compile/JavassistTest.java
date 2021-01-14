package com.sjl.spring.components.compile;

import com.sjl.spring.components.compile.support.JavassistCompiler;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author jianlei.shi
 * @date 2021/1/13 3:46 下午
 * @description JavassistTest
 */

public class JavassistTest {
    public final static String SUBFIX = "$";
    private static final Pattern METHODS_PATTERN = Pattern.compile("\n(private|public|protected)\\s+");
    private static final String PACKAGE_PREFIX = "package";
    private static final String CODE_PACKAGE = "package %s;\n";
    private static final String CODE_IMPORTS = "import %s;\n";
    private static final String CODE_CLASS = "public class  %s\n";
    private static final String CODE_IMPL = " implements %s\n";
    private static final String CODE_METHOD = " public %s %s \n";
    private static final String CODE_PARAM = " %s  %s \n";

    public static String getSimpleCode(Class<?> typeClass) {
        StringBuilder code = new StringBuilder();
        generatePackageInfo(code, typeClass);
        generateClassInfo(code, typeClass);
        Method[] methods = typeClass.getMethods();
        for (Method method : methods) {
            generateMethod(code, method);

        }

       /* code.append("public class HelloServiceImpl" + SUBFIX + " implements HelloService {");
        code.append("   public String sayHello(){ ");
        code.append("       return \"Hello world!\"; ");
        code.append("   }");
        code.append("   public String queryName(String name){");
        code.append("      String newName=name+\"张三\"; ");
        code.append("      return newName;  ");
        code.append("     }");
        code.append("}");*/

        code.append("}");
        return code.toString();
    }

    private static void generateMethod(StringBuilder code, Method method) {
        code.append(String.format(CODE_METHOD, method.getReturnType().getSimpleName(), method.getName())).append("(");
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            for (Class<?> parameterType : method.getParameterTypes()) {
                code.append(String.format(CODE_PARAM, parameterType.getSimpleName(), "name"));
                code.append("){");
                code.append("String newName=name+\"张三\"; ");
                code.append("return newName;");
                code.append(" }");
            }
            return;
        }
        code.append("){");
        code.append("return \"Hello word!\"; ");
        code.append(" }");
    }

    private static void generateClassInfo(StringBuilder code, Class<?> typeClass) {
        code.append(String.format(CODE_CLASS, typeClass.getSimpleName() + SUBFIX)).append(String.format(CODE_IMPL, typeClass.getSimpleName()));
        code.append("{");
    }

    private static void generatePackageInfo(StringBuilder code, Class<?> typeClass) {
        String name = typeClass.getName();
        int i = name.lastIndexOf(typeClass.getSimpleName());
        String substring = name.substring(0, i - 1);
        code.append(String.format(CODE_PACKAGE, substring));
    }

//    public class HelloService$ implements HelloService {
//        public String sayHello() {
//            return "Hello word!";
//        }
//
//        public String queryName(String name) {
//            String newName = name + "张三";
//            return newName;
//        }
//    }

    public static void main(String[] args) throws Exception {
        JavassistCompiler javassistCompiler = new JavassistCompiler();
        Class<?> compile = javassistCompiler.compile(getSimpleCode(HelloService.class), javassistCompiler.getClass().getClassLoader());
        Object instance = compile.newInstance();
        Method[] methods = instance.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("queryName")){
                System.out.println(method.invoke(instance,"haha"));
            }else if (method.getName().equals("sayHello")){
                System.out.println("-->>hello "+method.invoke(instance));

            }
        }


    }
}
