package com.glodon.water.common.common.enumpo;

public enum SummaryTypeEnum {
    BY_HOUR("按小时", "hour"), BY_DAY("按天", "day"), BY_WEEK("按周", "week"), BY_MONTH("按月", "month"),
    BY_SEASON("按季度", "season"), BY_YEAR("按年", "year"),BY_PROJECT("按项目", "project"),BY_REGION("按区域", "region"),BY_ALL("整体", "all");
    private String name;
    private String index;

    SummaryTypeEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public static SummaryTypeEnum getSummaryTypeEnum(String index) {
        switch (index) {
            case "hour":
                return BY_HOUR;
            case "day":
                return BY_DAY;
            case "week":
                return BY_WEEK;
            case "month":
                return BY_MONTH;
            case "season":
                return BY_SEASON;
            case "year":
                return BY_YEAR;
            case "project":
                return BY_PROJECT;
            case "region":
                return BY_REGION;
            case "all":
                return BY_ALL;
            default:
                return BY_DAY;
        }
    }
}
