package com.zhaojm.security.mapper;

import com.zhaojm.security.dto.SysRoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ISysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleDTO record);

    int insertSelective(SysRoleDTO record);

    SysRoleDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleDTO record);

    int updateByPrimaryKey(SysRoleDTO record);

    List<SysRoleDTO> selectUserRoleList(@Param("userId") Integer userId);
}