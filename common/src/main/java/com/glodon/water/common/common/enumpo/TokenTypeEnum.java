package com.glodon.water.common.common.enumpo;

public enum TokenTypeEnum {
	     //数据结构  内部请求、外部restful请求、外部服务请求，内部调用
	     REST("rest", 0),FEIGN("feign", 1),INNER("inner", 2),OTHER("other", 2);
	    // 成员变量
	    private String name;
	    private int index;

	    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
	    private TokenTypeEnum(String name, int index) {
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
	        for (TokenTypeEnum c : TokenTypeEnum.values()) {
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
	    
	    public static TokenTypeEnum getSummaryTypeEnum(String index) {
	        switch (index) {
	            case "rest":
	                return REST;
	            case "feign":
	                return FEIGN;
	            case "INNER":
	                return INNER;
	             default:
	                return OTHER;
	        }
	    }
}
