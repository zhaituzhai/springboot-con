package com.zhaojm.redis.mapper;

import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.vo.UserQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserAccountMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserAccountDTO record);

    int insertSelective(UserAccountDTO record);

    UserAccountDTO selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserAccountDTO record);

    int updateByPrimaryKey(UserAccountDTO record);

    List<UserAccountDTO> getAllUser();

    List<UserAccountDTO> getUserByName(@Param("username") String username);

    List<UserAccountDTO> getPageUser(UserQueryVO userVO);
}