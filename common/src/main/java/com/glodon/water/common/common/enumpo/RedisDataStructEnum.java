package com.glodon.water.common.common.enumpo;

public enum RedisDataStructEnum {
	 //数据结构  0kv、2hs、3ls、4zs
	KEYVALUE("kv", 0), HASH("hs", 1), LIST("ls", 2),ZSETS("zs", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private RedisDataStructEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * 
     * @Description 根据index获取name
     * @author zhouzy-a
     * @date 2016年12月11日 上午9:59:50
     * @return  String
     */
    public static String getName(int index) {
        for (RedisDataStructEnum c : RedisDataStructEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
