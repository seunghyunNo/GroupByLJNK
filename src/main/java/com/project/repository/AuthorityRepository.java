package com.project.repository;

import com.project.domain.Authority;

import java.util.List;

public interface AuthorityRepository {

    public Authority findByName(String authorityName);

    public int addAuthority(Long user_id,Long Authority_id);

    List<Authority> findAuthorityById(Long id);
}
