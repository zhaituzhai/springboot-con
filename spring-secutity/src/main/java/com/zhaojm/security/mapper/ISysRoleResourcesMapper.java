package com.zhaojm.security.mapper;

import com.zhaojm.security.dto.SysRoleResourcesDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISysRoleResourcesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleResourcesDTO record);

    int insertSelective(SysRoleResourcesDTO record);

    SysRoleResourcesDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleResourcesDTO record);

    int updateByPrimaryKey(SysRoleResourcesDTO record);
}