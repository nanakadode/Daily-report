package com.example.demo.service;


import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.demo.entity.Report;

@SpringJUnitConfig //Junit5上でSpring TestContext Frameworkを利用することを示す
@SpringBootTest //毎回サーバ起動
@ActiveProfiles("unit")//application-unit.ymlのunitを対応（DBの設定を読み込む）
@DisplayName("ReportServiceImplの結合テスト")
class ReportServiceImplTest {

    @Autowired
    private ReportService reportService;

    @Test
    @DisplayName("タスクが取得できない場合のテスト")
    void testGetReportFormReturnNull() {

        try {
        	Optional<Report> report = reportService.getReport(0);
        } catch (ReportNotFoundException e) {
        	Assertions.assertEquals(e.getMessage(), "指定されたタスクが存在しません");
        }
    }

    @Test//order byがある場合は順序の確認をすることがある
    @DisplayName("全件検索のテスト")
    void testFindAllCheckCount() {
    	//全件取得

        //Reportテーブルに入っている2件が取得できているか確認

    }

    @Test
    @DisplayName("1件のタスクが取得できた場合のテスト")
    void testGetReportFormReturnOne() {
    	//idが1のReportを取得

        //取得できたことを確認
    }

}
