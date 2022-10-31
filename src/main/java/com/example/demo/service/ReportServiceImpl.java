package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReportDao;
import com.example.demo.entity.Report;

@Service
public class ReportServiceImpl implements ReportService {

	private final ReportDao dao;

	public ReportServiceImpl(ReportDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Report> findAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Report> getReport(int id) {

		//Optional<Report>一件を取得 idが無ければ例外発生　
		try {
			return dao.findById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ReportNotFoundException("The report is not found. ");
		}
		
	}

	@Override
	public void insert(Report report) {
		dao.insert(report);
	}

	@Override
	public void update(Report report) {

		//reportを更新　idが無ければ例外発生
		if(dao.update(report) == 0) {
			throw new ReportNotFoundException("The report to update is not found. ");
		}

	}

	@Override
	public void deleteById(int id) {

		//reportを更新 idがなければ例外発生
		if(dao.deleteById(id) == 0) {
			throw new ReportNotFoundException("The report to delete is not found. ");
		}
	}

//	@Override
//	public List<Report> findByType(int typeId) {
//		//2-3 typeIdを引数に指定してdaoのfindByType実行し、結果をreturnする
//		return null;
//	}
}
