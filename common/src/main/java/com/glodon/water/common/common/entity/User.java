package com.glodon.water.common.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    /** 变量意义 */
    private static final long serialVersionUID = 4314093547094037341L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 公司id
     */
    @Column(name = "corp_id")
    private Integer corpId;

    /**
     * 广联云id
     */
    @Column(name = "global_id")
    private String globalId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像地址
     */
    @Column(name = "avatar_path")
    private String avatarPath;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 修改时间
     */
    @Column(name = "update_at")
    private Date updateAt;

    /**
     * 用户组织id
     */
    @Column(name = "organization_id")
    private Integer organizationId;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 广联达id
     */
    @Column(name = "glodon_id")
    private String glodonId;
    
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 类别
     */
    private String type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 排班人员标识
     */
    @Transient
    private Integer rotaMember = 0;

    public Integer getRotaMember() {
        return rotaMember;
    }

    public void setRotaMember(Integer rotaMember) {
        this.rotaMember = rotaMember;
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
     * @param id
     *            id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username
     *            用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     * 获取用户名
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户名
     *
     * @param password
     *            密码
     */
    public void setPassworde(String password) {
        this.password = password;
    }

    /**
     * 获取公司id
     *
     * @return corp_id - 公司id
     */
    public Integer getCorpId() {
        return corpId;
    }

    /**
     * 设置公司id
     *
     * @param corpId
     *            公司id
     */
    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    /**
     * 获取广联云id
     *
     * @return global_id - 广联云id
     */
    public String getGlobalId() {
        return globalId;
    }

    /**
     * 设置广联云id
     *
     * @param globalId
     *            广联云id
     */
    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname
     *            昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile
     *            手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email
     *            邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt
     *            创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取修改时间
     *
     * @return update_at - 修改时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置修改时间
     *
     * @param updateAt
     *            修改时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * 获取用户组织id
     *
     * @return organization_id - 用户组织id
     */
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * 设置用户组织id
     *
     * @param organizationId
     *            用户组织id
     */
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除
     *
     * @param isDelete
     *            是否删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取广联达id
     *
     * @return glodon_id - 广联达id
     */
    public String getGlodonId() {
        return glodonId;
    }

    /**
     * 设置广联达id
     *
     * @param glodonId
     *            广联达id
     */
    public void setGlodonId(String glodonId) {
        this.glodonId = glodonId;
    }

    /**
     * 获取类别
     *
     * @return type - 类别
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类别
     *
     * @param type
     *            类别
     */
    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}