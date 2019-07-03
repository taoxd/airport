package config;

/**
 * @Description: 菜单固定选项
 * @Author: taoxudong
 * @CreateDate: 2019/6/27 8:39
 * @Version: 1.0
 */
public enum Menu {
    DONG_HANG_KE_CANG("东航客舱", "500"),
    HOME_PAGE("首页", "200"),
    TEMPLATE_LIST("模版列表", "600"),
    RESOURCE_ADD("资源添加", "400"),
    CONSTANT_BROAD("常量广播词", "100"),
    VARIABLE_BROAD("变量广播词", "300"),
    PREVIEW_RESOURCE_XML("资源XML预览", "700"),
    PREVIEW_TEMPLATE_XML("模版XML预览", "800"),
    EXPORT("导出", "800");

    private String name;
    private String code;

    Menu(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }}
