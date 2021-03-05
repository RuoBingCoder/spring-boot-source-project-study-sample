package com.github.sharding.sample;

import lombok.Data;

/**
 * @author jianlei.shi
 * @date 2021/3/3 11:07 上午
 * @description Apple
 */
@Data
public class Apple {
    private String color;
    private String name;


    public static void main(String[] args) {
       /* Map<String, Apple> apply=new HashMap<>();
        Apple a1=new Apple();
        a1.setColor("red");
        a1.setName(null);
        Apple a2=new Apple();
        a2.setColor("green");
        a2.setName("富士");
        Apple a3=new Apple();
        a3.setColor("yellow");
        a3.setName("呵呵哒");
        apply.put("k1",a1);
        apply.put("k2",a2);
        apply.put("k3",a3);
        List<Apple> apaAppleList=new ArrayList<>();
        apaAppleList.add(a1);
        apaAppleList.add(a2);
        apaAppleList.add(a3);
        Map<String, String> toBeTranslated =apaAppleList.stream().filter(n->n.getName()!=null).map(Apple::getName).collect(Collectors.toMap(k -> k, v -> v, (t1, t2) -> t1, LinkedHashMap::new));
        System.out.println(JSONObject.toJSONString(toBeTranslated));
        */
        String str1=" --";
        System.out.println(true);



    }
}
