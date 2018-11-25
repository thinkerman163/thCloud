package com.glodon.water.common.common.enumpo;

public enum LoginTypeEnum {
    
	glodon("glodon", 1), own("own", 0);
    
     // 成员变量
    private String name;
    private int index;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private LoginTypeEnum(String name, int index) {
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
    public static String getName(Byte index) {
        for (LoginTypeEnum c : LoginTypeEnum.values()) {
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

    public void setIndex(Byte index) {
        this.index = index;
    }


}
