package com.zhaojm.security.mapper;

import com.zhaojm.security.dto.SysResourcesDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISysResourcesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysResourcesDTO record);

    int insertSelective(SysResourcesDTO record);

    SysResourcesDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysResourcesDTO record);

    int updateByPrimaryKey(SysResourcesDTO record);
}