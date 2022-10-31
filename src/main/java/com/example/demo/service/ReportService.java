package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Report;

public interface ReportService {

	List<Report> findAll();

	Optional<Report> getReport(int id);

	void insert(Report report);

	void update(Report report);

	void deleteById(int id);

//	List<Report> findByType(int typeId);

}
