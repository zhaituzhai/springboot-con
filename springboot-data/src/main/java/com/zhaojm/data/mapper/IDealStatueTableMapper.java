package com.zhaojm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zhaojm.data.bean.DealStatueTableDTO;

@Mapper
public interface IDealStatueTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DealStatueTableDTO record);

    int insertSelective(DealStatueTableDTO record);

    DealStatueTableDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DealStatueTableDTO record);

    int updateByPrimaryKey(DealStatueTableDTO record);
    
    List<DealStatueTableDTO> selectAll();
    
    List<DealStatueTableDTO> selectOpreat();
}