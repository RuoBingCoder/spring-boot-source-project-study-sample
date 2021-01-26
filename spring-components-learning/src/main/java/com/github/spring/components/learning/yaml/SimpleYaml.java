package com.github.spring.components.learning.yaml;

import com.alibaba.fastjson.JSONObject;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/1/12 7:43 下午
 * @description SimpleYaml
 * 自定义yaml读取
 * <a href="https://www.baeldung.com/java-snake-yaml"/>
 */

public class SimpleYaml extends Yaml {
    public SimpleYaml() {
    }

    /**
     * 方式2 构造yaml type
     * @param constructor
     */
    public SimpleYaml(Constructor constructor) {
        super(constructor);
    }

    /**
     * 非类型测试
     */
    public static void nonTypeTest(){
        SimpleYaml simpleYaml=new SimpleYaml();
        InputStream inputStream = simpleYaml.getClass()
                .getClassLoader()
                .getResourceAsStream("customer.yaml");
        Map<String, Object> obj = simpleYaml.load(inputStream);
        System.out.println("==>"+ JSONObject.toJSONString(obj));
    }

    /**
     * 类型测试
     */
    public static void typeTest(){
        SimpleYaml simpleYaml=new SimpleYaml(new Constructor(Customer.class));
        InputStream inputStream = simpleYaml.getClass()
                .getClassLoader()
                .getResourceAsStream("customer_with_type.yaml");
        Customer  obj = simpleYaml.load(inputStream);
        System.out.println("==>"+ obj.toString());
    }

    /**
     * 我们将从一个简单的示例开始，该示例将Map <String，Object>的一个实例转储到YAML文档（String）中：我们将从一个简单的示例开始，该示例将Map <String，Object>的一个实例转储到YAML文档（String）中：
     */
    public static void whenDumpmapThengeneratecorrectyaml() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("name", "Silenthand Olleander");
        data.put("race", "Human");
        data.put("traits", new String[]{"ONE_HAND", "ONE_EYE"});
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(data, writer);
        String expectedYaml = "name: Silenthand Olleander\nrace: Human\ntraits: [ONE_HAND, ONE_EYE]\n";
        System.out.println("==>"+writer.toString());
    }

    public  static void whenDumpACustomType_thenGenerateCorrectYAML() {
        Customer customer = new Customer();
        customer.setAge(45);
        customer.setFirstName("Greg");
        customer.setLastName("McDowell");
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(customer, writer);
        String expectedYaml = "!!com.baeldung.snakeyaml.Customer {age: 45, contactDetails: null, firstName: Greg,\n  homeAddress: null, lastName: McDowell}\n";
        System.out.println("==>"+writer.toString());
    }
    public static void main(String[] args) {
//        typeTest();
        whenDumpmapThengeneratecorrectyaml();
        whenDumpACustomType_thenGenerateCorrectYAML();
    }

}
