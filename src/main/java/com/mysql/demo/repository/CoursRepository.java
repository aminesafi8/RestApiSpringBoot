package com.mysql.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysql.demo.entities.Cours;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Integer> {
	

}
