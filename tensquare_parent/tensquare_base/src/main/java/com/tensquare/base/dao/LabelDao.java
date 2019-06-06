package com.tensquare.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {
}
