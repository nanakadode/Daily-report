package com.example.demo.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Report;

@Repository
public class ReportDaoImpl implements ReportDao {

	private final JdbcTemplate jdbcTemplate;

	public ReportDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Report> findAll() {

		String sql = "SELECT report.id, user_id, title, content, date FROM report ";
//		String sql = "SELECT report.id, user_id, title, content, CONVERT(DATE, getDate()) FROM report ";

//				+ "INNER JOIN report_type ON report.type_id = report_type.id";

		//タスク一覧をMapのListで取得
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

		//return用の空のListを用意
		List<Report> list = new ArrayList<Report>();

		//二つのテーブルのデータをReportにまとめる
		for(Map<String, Object> result : resultList) {

			Report report = new Report();
			report.setId((int)result.get("id"));
			report.setUserId((int)result.get("user_id"));
			report.setTitle((String)result.get("title"));
			report.setContent((String)result.get("content"));
			report.setDate(((Timestamp) result.get("date")).toLocalDateTime());

//			ReportType type = new ReportType();
//			type.setId((int)result.get("type_id"));
//			type.setType((String)result.get("type"));
//			type.setComment((String)result.get("comment"));

			//ReportにReportTypeをセット

			list.add(report);
		}
		return list;
	}

	@Override
	public Optional<Report> findById(int id) {
		String sql = "SELECT report.id, user_id, title, content, date FROM report WHERE report.id = ?";
//		String sql = "SELECT report.id, user_id, title, content, CONVERT(DATE, getDate()) FROM report WHERE report.id = ?";

		//タスクを一件取得
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

		Report report = new Report();
		report.setId((int)result.get("id"));
		report.setUserId((int)result.get("user_id"));
		report.setTitle((String)result.get("title"));
		report.setContent((String)result.get("content"));
		report.setDate(((Timestamp) result.get("date")).toLocalDateTime());

//		ReportType type = new ReportType();
//		type.setId((int)result.get("type_id"));
//		type.setType((String)result.get("type"));
//		type.setComment((String)result.get("comment"));
//		report.setReportType(type);


		//ReportをOptionalでラップする
		Optional<Report> reportOpt = Optional.ofNullable(report);
		
		return reportOpt;
	}

	@Override
	public void insert(Report report) {
		jdbcTemplate.update("INSERT INTO report(user_id, title, content, date) VALUES(?, ?, ?, ?)",
//		jdbcTemplate.update("INSERT INTO report(user_id, title, content, CONVERT(DATE, getDate())) VALUES(?, ?, ?, ?)",
				 report.getUserId(), report.getTitle(), report.getContent(), report.getDate() );

	}

	@Override
	public int update(Report report) {
		return jdbcTemplate.update("UPDATE report SET  title = ?, content = ?, date = ? WHERE id = ?",
				report.getTitle(), report.getContent(), report.getDate(), report.getId());
	}
 
	@Override
	public int deleteById(int id) {
		return jdbcTemplate.update("DELETE FROM report WHERE id = ?", id);
	}

//	@Override
//	public List<Report> findByType(int typeId) {
//		//2-1 指定したtype_idと一致するタスクのリストを取得するためのSQLを記述する
//		String sql = null;
//
//		//2-2 SQLとtypeIdを渡し、タスク一覧をMapのListで取得する
//		List<Map<String, Object>> resultList = null;
//
//		//return用の空のListを用意
//		List<Report> list = new ArrayList<>();
//
//		//二つのテーブルのデータをReportにまとめる
//		for(Map<String, Object> result : resultList) {
//
//			Report report = new Report();
//			report.setId((int)result.get("id"));
//			report.setUserId((int)result.get("user_id"));
//			report.setTitle((String)result.get("title"));
//			report.setContent((String)result.get("content"));
//			report.setDate(((Timestamp) result.get("date")).toLocalDateTime());
//
////			ReportType type = new ReportType();
////			type.setId((int)result.get("type_id"));
////			type.setType((String)result.get("type"));
////			type.setComment((String)result.get("comment"));
////			report.setReportType(type);
//
//			list.add(report);
//		}
//		return list;
//	}

}
