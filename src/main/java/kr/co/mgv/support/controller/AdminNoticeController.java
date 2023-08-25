package kr.co.mgv.support.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.mgv.support.dto.NoticeList;
import kr.co.mgv.support.form.AddNoticeForm;
import kr.co.mgv.support.form.ModifyNoticeForm;
import kr.co.mgv.support.service.NoticeService;
import kr.co.mgv.support.vo.Notice;
import kr.co.mgv.user.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/support/notice")
@RequiredArgsConstructor
public class AdminNoticeController {

	private final NoticeService noticeService;
	
    @RequestMapping
    public String notice(@RequestParam(name = "catNo", required = false, defaultValue = "21") int catNo,
    		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
    		@RequestParam(name = "locationNo", required = false, defaultValue = "0") int locationNo,
			@RequestParam(name = "theaterNo", required = false, defaultValue = "0") int theaterNo,
			@RequestParam(name ="keyword", required = false) String keyword, 
			Model model) {
    	
    	Map<String, Object> param = new HashMap<>();
    	param.put("catNo", catNo);
    	param.put("page", page);
    	
    	if (locationNo != 0) {
			param.put("locationNo", locationNo);
		}
		
		if (theaterNo != 0) {
			param.put("theaterNo", theaterNo);
		}
		
    	if (StringUtils.hasText(keyword)) {
    		param.put("keyword", keyword);
    	}
    	NoticeList noticeList = noticeService.search(param);
    	model.addAttribute("result", noticeList);
    	
        return "/view/admin/support/notice/list";
    }
    
    @GetMapping("/list")
    @ResponseBody
    public NoticeList getNotice(@RequestParam(name = "catNo", required = false, defaultValue = "21") int catNo,
    		@RequestParam(name = "page", required = false, defaultValue = "1") int page,
    		@RequestParam(name = "locationNo", required = false, defaultValue = "0") int locationNo,
			@RequestParam(name = "theaterNo", required = false, defaultValue = "0") int theaterNo,
			@RequestParam(name ="keyword", required = false) String keyword) {
    	
    	Map<String, Object> param = new HashMap<>();
    	param.put("catNo", catNo);
    	param.put("page", page);
    	
    	if (locationNo != 0) {
			param.put("locationNo", locationNo);
		}
		
		if (theaterNo != 0) {
			param.put("theaterNo", theaterNo);
		}
		
    	if (StringUtils.hasText(keyword)) {
    		param.put("keyword", keyword);
    	}
    	
    	NoticeList noticeList = noticeService.search(param);
    	
    	return noticeList;
    }

    @RequestMapping("/detail")
    public String getNoticeByNo(@RequestParam("no") int noticeNo, Model model) {
    	Notice notice = noticeService.getNoticeByNo(noticeNo);
    	
    	Notice preNotice = noticeService.getPrevNotice(noticeNo);
    	Notice nextNotice = noticeService.getNextNotice(noticeNo);
    	
    	model.addAttribute("notice", notice);
    	
    	model.addAttribute("preNotice", preNotice);
    	model.addAttribute("nextNotice", nextNotice);
    	
        return "/view/admin/support/notice/detail";
    }
    
    @GetMapping("/form")
    public String noticeForm() {
    	return "/view/admin/support/notice/form";
    }
    
    @PostMapping("/add")
    public String insertNotice(@AuthenticationPrincipal User user, AddNoticeForm form) {
    	noticeService.insertNotice(form, user);
    	return "redirect:/admin/support/notice";
    }
    
    @GetMapping("/modifyform")
    public String modifyForm(@RequestParam("no") int noticeNo, Model model) {
    	Notice notice = noticeService.getNoticeByNo(noticeNo);
    	model.addAttribute("notice", notice);
    	model.addAttribute("locations", noticeService.getLocations());
    	model.addAttribute("theaters", noticeService.getTheatesrByLocationNo(notice.getLocation().getNo()));
    	
    	return "/view/admin/support/notice/modifyform";
    }
    
    @PostMapping("/modify")
    public String modifyNotice(@RequestParam("no") int noticeNo, ModifyNoticeForm form) {
    	noticeService.modifyNotice(form, noticeNo);
    	return "redirect:/admin/support/notice/detail?no=" + noticeNo;
    }
    
    @GetMapping("/delete")
    public String deleteNotice(@RequestParam("no") int noticeNo, Model model) {
    	noticeService.deleteNotice(noticeNo);
    	return "redirect:/admin/support/notice";
    }
    
}











