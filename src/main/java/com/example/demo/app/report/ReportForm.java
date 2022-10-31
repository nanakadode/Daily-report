package com.example.demo.app.report;

import java.time.LocalDateTime;

//import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class ReportForm {

    @NotNull (message = "タイトルを入力してください。")
    @Size(min = 1, max = 20, message = "20文字以内で入力してください。")
    private String title;

    @NotNull (message = "内容を入力してください。")
    private String content;

    @NotNull (message = "日付を設定してください。")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @Future (message = "期限が過去に設定されています。")
    private LocalDateTime date;

    private boolean newReport;
    
    public ReportForm() {}

	public ReportForm(
			String title,
			String content, 
			LocalDateTime date,
			boolean newReport) {
//		this.typeId = typeId;
		this.title = title;
		this.content = content;
		this.date = date;
		this.newReport = newReport;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public boolean isNewReport() {
		return newReport;
	}

	public void setNewReport(boolean newReport) {
		this.newReport = newReport;
	}
}