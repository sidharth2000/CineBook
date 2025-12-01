package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.GlobalConfig;

public interface GlobalConfigRepository extends JpaRepository<GlobalConfig,Long> {

	GlobalConfig findByConfigKey(String configKey);

}
