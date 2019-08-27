package com.hujianguo.constanst;

/**
 * 常量
 *
 * @author MrBird
 */
public class Constant {

    // 排序规则：降序
    public static final String ORDER_DESC = "desc";
    // 排序规则：升序
    public static final String ORDER_ASC = "asc";

    // 前端页面路径前缀
    public static final String VIEW_PREFIX = "febs/views/";

    // 验证码 Session Key
    public static final String CODE_PREFIX = "febs_captcha_";

    // 允许下载的文件类型，根据需求自己添加（小写）
    public static final String[] VALID_FILE_TYPE = {"xlsx", "zip"};

    /**
     *
     * getDataTable 中 HashMap 默认的初始化容量
     */
    public static final int DATA_MAP_INITIAL_CAPACITY = 4;

    public static final String MOBILE_REG = "[1]\\d{10}";
}