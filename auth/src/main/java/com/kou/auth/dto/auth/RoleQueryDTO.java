package com.kou.auth.dto.auth;

import java.io.Serializable;
import java.util.List;
//import com.kou.auth.database.mybatis.auth.DataScopeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类
 * 角色
 * </p>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "RoleSaveDTO", description = "角色")
public class RoleQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String name;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String code;
    /**
     * 功能描述
     */
    @ApiModelProperty(value = "功能描述")
    private String describe;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Boolean status;
    /**
     * 是否内置角色
     */
    @ApiModelProperty(value = "是否内置角色")
    private Boolean readonly;
    /**
     * 数据权限类型
     * #DataScopeType{ALL:1,全部;THIS_LEVEL:2,本级;THIS_LEVEL_CHILDREN:3,本级以及子级;CUSTOMIZE:4,自定义;SELF:5,个人;}
     */
    /*@ApiModelProperty(value = "数据权限类型")
    private DataScopeType dsType;*/
    /**
     * 关联的组织id
     */
    @ApiModelProperty(value = "关联的组织id")
    private List<Long> orgList;
}
