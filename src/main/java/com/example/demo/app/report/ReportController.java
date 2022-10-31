package com.example.demo.app.report;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Report;
import com.example.demo.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    /**
     * タスクの一覧を表示します
     * @param reportForm
     * @param model
     * @return resources/templates下のHTMLファイル名
     */
    @GetMapping
    public String report(ReportForm reportForm, Model model) {

    	//新規登録か更新かを判断する仕掛け
    	reportForm.setNewReport(true);
        //reportのリストを取得する
    	List<Report> list = reportService.findAll();
    	
        model.addAttribute("list", list);
        model.addAttribute("title", "新規日報作成");

        return "report/index";
    }

    /**
     * reportデータを一件挿入
     * @param reportForm
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/insert")
    public String insert(
    	@Valid @ModelAttribute ReportForm reportForm,
        BindingResult result,
        Model model
        ) {

        if (!result.hasErrors()) {

        	//reportFormのデータをreportに格納
//        	Report report = new Report();
//        	report.setUserId(1);
//        	report.setTitle(reportForm.getTitle());
//        	report.setContent(reportForm.getContent());
//        	report.setDate(reportForm.getDate());
        	Report report = makeReport(reportForm, 0);
        	
        	//一件挿入後リダイレクト
        	reportService.insert(report);
            return "redirect:/report";
        } else {
            reportForm.setNewReport(true);
            model.addAttribute("ReportForm", reportForm);
            List<Report> list = reportService.findAll();
            model.addAttribute("list", list);
            model.addAttribute("title", "Report List");
            return "report/index";
        }
    }

    /**
     * 一件タスクデータを取得し、フォーム内に表示
     * @param reportForm
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String showUpdate(
    	ReportForm reportForm,
        @PathVariable int id,
        Model model) {

    	//reportを取得(Optionalでラップ)
    	Optional<Report> reportOpt = reportService.getReport(id);
        //reportFormへの詰め直し
    	Optional<ReportForm> reportFormOpt = reportOpt.map(t -> makeReportForm(t)); 
        //reportFormがnullでなければ中身を取り出し
    	if(reportFormOpt.isPresent()) {
    		reportForm = reportFormOpt.get();
    	}
    	
        model.addAttribute("reportForm", reportForm);
        List<Report> list = reportService.findAll();
        model.addAttribute("list", list);
        model.addAttribute("reportId", id);
        model.addAttribute("title", "修正画面");

        return "report/index";
    }

    /**
     * タスクidを取得し、一件のデータ更新
     * @param reportForm
     * @param result
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/update")
    public String update(
    	@Valid @ModelAttribute ReportForm reportForm,
    	BindingResult result,
    	@RequestParam("reportId") int reportId,
    	Model model,
    	RedirectAttributes redirectAttributes) {

        //reportFormのデータをreportに格納
    	Report report = makeReport(reportForm, reportId);
    	
    	if (!result.hasErrors()) {
        	//更新処理、フラッシュスコープの使用、リダイレクト（個々の編集ページ）
        	reportService.update(report);
        	redirectAttributes.addFlashAttribute("complete", "修正完了");
            return "redirect:/report/" + reportId ;
            
        } else {
            model.addAttribute("reportForm", reportForm);
            model.addAttribute("title", "日報一覧");
            return "report/index";
        }
    }

    /**
     * タスクidを取得し、一件のデータ削除
     * @param id
     * @param model
     * @return
     */
    @PostMapping("/delete")
    public String delete(
    	@RequestParam("reportId") int id,
    	Model model) {

    	//タスクを一件削除しリダイレクト
    	reportService.deleteById(id);
        return "redirect:/report";
    }

    /**
     * 複製用に一件タスクデータを取得し、フォーム内に表示
     * @param reportForm
     * @param id
     * @param model
     * @return
     */
    //1-1　"/duplicate"に対してマッピングを行うアノテーションを記述する
    public String duplicate(
    	ReportForm reportForm,
    	//1-2　Requestパラメータから"reportId"の名前でint idを取得するようにする
    	int id,
        Model model) {

    	//1-3　reportService.getreportを用いてreportを取得する
        Optional<Report> reportOpt = null;

        //reportFormへの詰め直し
        Optional<ReportForm> reportFormOpt = reportOpt.map(t -> makeReportForm(t));

        //ReportFormがnullでなければ中身を取り出し
        if(reportFormOpt.isPresent()) {
        	reportForm = reportFormOpt.get();
        }

        //新規登録のためNewreportにtrueをセット
        reportForm.setNewReport(true);

        model.addAttribute("reportForm", reportForm);
        List<Report> list = reportService.findAll();
        model.addAttribute("list", list);
        model.addAttribute("title", "タスク一覧");

        return "report/index";
    }

    /**
     * 選択したタスクタイプのタスク一覧を表示
     * @param reportForm
     * @param id
     * @param model
     * @return
     */
    //2-4 "/selectType"に対してマッピングを行うアノテーションを記述する
    public String selectType(
    	ReportForm reportForm,
    	//2-5 Requestパラメータから"typeId"の名前でint idを取得するようにする
    	int id,
        Model model) {

    	//新規登録か更新かを判断する仕掛け
        reportForm.setNewReport(true);

        //2-6 reportService.findByTypeを用いてReportのリストを取得する
        List<Report> list = null;

        model.addAttribute("list", list);
        model.addAttribute("title", "日報一覧");

        return "report/index";
    }


    /**
     * reportFormのデータをreportに入れて返す
     * @param reportForm
     * @param reportId 新規登録の場合は0を指定
     * @return
     */
    private Report makeReport(ReportForm reportForm, int reportId) {
        Report report = new Report();
        if(reportId != 0) {
        	report.setId(reportId);
        }
        report.setUserId(1);
    	report.setTitle(reportForm.getTitle());
    	report.setContent(reportForm.getContent());
    	report.setDate(reportForm.getDate());
        return report;
    }

    /**
     * reportのデータをreportFormに入れて返す
     * @param report
     * @return
     */
    private ReportForm makeReportForm(Report report) {

        ReportForm reportForm = new ReportForm();

        reportForm.setTitle(report.getTitle());
        reportForm.setContent(report.getContent());
        reportForm.setDate(report.getDate());
        reportForm.setNewReport(false);

        return reportForm;
    }
}
