package com.demo.nace.repository;

import com.demo.nace.model.entity.NACEDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NACEDetailRepository extends CrudRepository<NACEDetail, String> {}
