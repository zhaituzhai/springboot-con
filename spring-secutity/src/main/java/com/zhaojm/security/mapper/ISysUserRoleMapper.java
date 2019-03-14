package com.zhaojm.security.mapper;

import com.zhaojm.security.dto.SysUserRoleDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRoleDTO record);

    int insertSelective(SysUserRoleDTO record);

    SysUserRoleDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRoleDTO record);

    int updateByPrimaryKey(SysUserRoleDTO record);
}