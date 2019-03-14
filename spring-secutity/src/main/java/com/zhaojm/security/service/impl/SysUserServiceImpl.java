package com.zhaojm.security.service.impl;

import com.zhaojm.security.dto.SysRoleDTO;
import com.zhaojm.security.dto.SysUserDTO;
import com.zhaojm.security.mapper.ISysRoleMapper;
import com.zhaojm.security.mapper.ISysUserMapper;
import com.zhaojm.security.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    ISysUserMapper sysUserMapper;

    @Autowired
    ISysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDTO sysUserDTO = sysUserMapper.selectByName(username).get(0);
        if(null == sysUserDTO) throw new UsernameNotFoundException("用户不存在！");
        List<SysRoleDTO> roleList = sysRoleMapper.selectUserRoleList(sysUserDTO.getId());
        List<GrantedAuthority> authorities = new ArrayList();
        roleList.forEach(role ->
                authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(role.getName()))
        );
        return new User(sysUserDTO.getUsername(), sysUserDTO.getPassword(), authorities);
    }

}
