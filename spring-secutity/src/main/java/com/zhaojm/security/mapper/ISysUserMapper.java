package com.zhaojm.security.mapper;

import com.zhaojm.security.dto.SysUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ISysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserDTO record);

    int insertSelective(SysUserDTO record);

    SysUserDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserDTO record);

    int updateByPrimaryKey(SysUserDTO record);

    List<SysUserDTO> selectByName(@Param("username") String username);
}