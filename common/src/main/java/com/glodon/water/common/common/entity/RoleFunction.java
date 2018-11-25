package com.glodon.water.common.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.glodon.water.common.common.enumpo.AuthorizationTypeEnum;

@Table(name = "role_function")
public class RoleFunction implements Serializable {
    /**  变量意义 */
    private static final long serialVersionUID = -4657989360218904031L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 功能id
     */
    @Column(name = "function_id")
    private Integer functionId;

    /**
     * 权限字符串
     */
    @Column(name = "authorization")
    private String authorization ;

    @Transient
    private String functionCode;

    @Transient
    private String functionType;

    @Transient
    private String functionName;
    
    @Transient
    private String functionPath;
    
    @Transient
    private Integer moduleId;

    @Transient
    private String functionImgUrl;

    /**
     * 读写类型，1表示只有读，2表示只有写，3以后详见AuthorizationTypeEnum定义
     */
    @Column(name = "type")
    private Byte type = (byte)AuthorizationTypeEnum.read.getIndex();

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取功能id
     *
     * @return function_id - 功能id
     */
    public Integer getFunctionId() {
        return functionId;
    }

    /**
     * 设置功能id
     *
     * @param functionId 功能id
     */
    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }
    
    
    /**
     * 获取模块id
     *
     * @return moduleId - 模块id
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * 设置模块id
     *
     * @param moduleId 模块id
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    
    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath;
    }
    

    public String getFunctionImgUrl() {
        return functionImgUrl;
    }

    public void setFunctionImgUrl(String functionImgUrl) {
        this.functionImgUrl = functionImgUrl;
    }
}