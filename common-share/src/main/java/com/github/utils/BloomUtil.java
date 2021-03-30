package utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/12/3 下午2:57
 * @description: BloomUtil
 */
@Slf4j
public class BloomUtil {
    private static final int TOTAL = 100000;
    /**
     * 创建布隆过滤器
     */
    private static BloomFilter bf = BloomFilter.create(Funnels.integerFunnel(), TOTAL);

    public static <T> boolean isExists(T key) {
        return bf.mightContain(key);
    }

    public static <T> void put(List<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            throw new RuntimeException("objects is not null!");
        }
        objects.forEach(s -> bf.put(s));
    }

    public static void main(String[] args) {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < TOTAL; i++) {
            bf.put(i);
        }
        int j = 10000 - 1;
        // 判断是否存在
        boolean flag = bf.mightContain(j);
        log.info("{} 判断结果为：{}", j, flag);
        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < TOTAL; i++) {
            if (!bf.mightContain(i)) {
                log.info("有坏人逃脱了~~~");
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (int i = TOTAL; i < TOTAL + 10000; i++) {
            if (bf.mightContain(i)) {
                count++;
            }
        }
        log.info("误伤的数量：" + count);
    }
}
