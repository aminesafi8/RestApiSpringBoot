package com.mysql.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Integer> {
	

}
