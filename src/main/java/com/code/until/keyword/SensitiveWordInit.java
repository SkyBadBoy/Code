package com.code.until.keyword;

import com.code.service.read.ReadKeywordService;
import com.code.until.SpringUtil;
import java.util.*;

/**
 * Created by majian
 */
public class SensitiveWordInit {
    private String ENCODING = "utf8"; // 字符编码
    @SuppressWarnings("rawtypes")
    public HashMap sensitiveWordMap;

    public SensitiveWordInit() {
        super();
    }

    ReadKeywordService readkeywordService= SpringUtil.getBean(ReadKeywordService.class);

    @SuppressWarnings("rawtypes")
    public Map initKeyWord() {
        try {
            // 敏读取感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            // 将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = { isEnd = 0 国 = {<br>
     * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd =
     * 1 } } } } 五 = { isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1
     * } } } }
     *
     * @param keyWordSet 敏感词库
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size()); // 初始化敏感词容器，减少扩容操作
        String key;
        Map nowMap;
        Map<String, String> newWorMap;
        // 迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next(); // 关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i); // 转换成char型
                if ((int) keyChar == 65279) {
                    continue;
                }
                Object wordMap = nowMap.get(keyChar); // 获取

                if (wordMap != null) { // 如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else { // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0"); // 不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1"); // 最后一个
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     *
     * @return
     * @version 1.0
     */
    private Set<String> readSensitiveWordFile() {
        Set<String> set=readkeywordService.getKeywordSet();
        return set;
    }
}
