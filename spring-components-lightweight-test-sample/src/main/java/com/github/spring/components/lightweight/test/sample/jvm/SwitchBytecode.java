package com.github.spring.components.lightweight.test.sample.jvm;

import com.github.spring.components.lightweight.test.sample.jvm.enums.NumTypeEnum;

/**
 * @author jianlei.shi
 * @date 2021/4/1 10:12 上午
 * @description SwitchBytecode
 */

public class SwitchBytecode {

    /**
     * 选择
     *
     * @param type 类型
     * @return {@link Integer }
     * @author jianlei.shi
     * @date 2021-04-01 10:17:59
     * <code>
     * getstatic #2 <com/github/spring/components/lightweight/test/sample/jvm/SwitchBytecode$1.$SwitchMap$com$github$spring$components$lightweight$test$sample$jvm$enums$NumTypeEnum>
     * 3 aload_0
     * 4 invokevirtual #3 <com/github/spring/components/lightweight/test/sample/jvm/enums/NumTypeEnum.ordinal>
     * 7 iaload
     * 8 tableswitch 1 to 4	1:  40 (+32)  枚举使用的tableswitch  其他紧凑的选择tableswitch 时间复杂度o(1)     lookupswitch二分查找时间复杂度o(log n)
     * 2:  45 (+37)
     * 3:  50 (+42)
     * 4:  55 (+47)
     * default:  60 (+52)
     * 40 iconst_1
     * 41 invokestatic #4 <java/lang/Integer.valueOf>
     * 44 areturn
     * 45 iconst_2
     * 46 invokestatic #4 <java/lang/Integer.valueOf>
     * 49 areturn
     * 50 iconst_3
     * 51 invokestatic #4 <java/lang/Integer.valueOf>
     * 54 areturn
     * 55 iconst_4
     * 56 invokestatic #4 <java/lang/Integer.valueOf>
     * 59 areturn
     * 60 iconst_5
     * 61 invokestatic #4 <java/lang/Integer.valueOf>
     * 64 areturn
     * </code>
     */
    public static Integer select(NumTypeEnum type) {
        switch (type) {
            case INT:
                return 1;
            case LONG:
                return 2;
            case BYTE:
                return 3;
            case SHORT:
                return 4;
            default:
                return 5;
        }

    }

    /**
     * <pre>
     *     前面我们看到过几个关于方法调用的指令了。比如上篇文章有讲到的对象实例初始化<init>函数由 invokespecial 调用。这篇文章我们将介绍关于方法调用的五个指令：
     *
     * invokestatic：用于调用静态方法
     * invokespecial：用于调用私有实例方法、构造器，以及使用 super 关键字调用父类的实例方法或构造器，和所实现接口的默认方法
     * invokevirtual：用于调用非私有实例方法
     * invokeinterface：用于调用接口方法
     * invokedynamic：用于调用动态方法
     * </pre>
     */
    public static void main(String[] args) {

    }
}
