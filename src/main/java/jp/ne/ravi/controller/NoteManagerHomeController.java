package jp.ne.ravi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.ravi.form.ClassForm;
import jp.ne.ravi.form.ContentsForm;
import jp.ne.ravi.service.NoteManagerHomeService;
import jp.ne.ravi.utils.DebugUtil;

@Scope("prototype")
@Controller
public class NoteManagerHomeController {

	@Autowired
	private DebugUtil debugUtil;

	@Autowired
	private NoteManagerHomeService noteManagerHomeService;

	@ModelAttribute
	public ClassForm setupModelAttributeForClassForm() {
		ClassForm classForm = new ClassForm();
		return classForm;
	}

	@ModelAttribute
	public ContentsForm setupModelAttributeForContentsForm() {
		ContentsForm contentsForm = new ContentsForm();
		return contentsForm;
	}

	/**
	 * デフォルト画面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String home(Model model, HttpSession session, @RequestParam(required = false) Integer page) {
		noteManagerHomeService.home(model, session, page);
		return "index.jsp";
	}

	/**
	 * 分類追加
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manager/addclass", method = RequestMethod.POST)
	public String addClass(Model model, ClassForm classForm, RedirectAttributes attributes) {
		debugUtil.outConsole("addClass");
		noteManagerHomeService.addClass(classForm, attributes);
		return "redirect:/manager";
	}

	/**
	 * 分類削除
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manager/delclass", method = RequestMethod.POST)
	public String delClass(Model model, ClassForm classForm, RedirectAttributes attributes) {
		debugUtil.outConsole("delClass");
		noteManagerHomeService.delClass(classForm, attributes);
		return "redirect:/manager";
	}

	/**
	 * 記事投稿
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/manager/contents", method = RequestMethod.POST)
	public String createContents(Model model, ContentsForm contentsForm, RedirectAttributes attributes, HttpSession session) {
		debugUtil.outConsole("createContents");
		if (noteManagerHomeService.createContents(model, contentsForm, attributes, session)) {
			// OK
			return "redirect:/manager/create";
		} else {
			// NG
			return "redirect:/manager";
		}
	}

	/**
	 * 検索
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manager/searchContents", method = RequestMethod.POST)
	public String search(Model model, ClassForm classForm, RedirectAttributes attributes, HttpSession session) {
		debugUtil.outConsole("search");
		noteManagerHomeService.searchContents(model, classForm, attributes, session);
		return "redirect:/manager";
	}

	/**
	 * 記事編集
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/manager/editContents", method = RequestMethod.POST)
	public String editContents(Model model, ContentsForm contentsForm, HttpSession session) {
		debugUtil.outConsole("editContents");
		noteManagerHomeService.editContents(model, contentsForm, session);
		return "redirect:/manager/create";
	}

	/**
	 * 記事削除
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/manager/deleteContents", method = RequestMethod.POST)
	public String deleteContents(Model model, ContentsForm contentsForm, RedirectAttributes attributes, HttpSession session) {
		debugUtil.outConsole("deleteContents");
		noteManagerHomeService.deleteContents(model, contentsForm, attributes, session);
		return "redirect:/manager";
	}

	/**
	 * 記事参照
	 * @param model
	 * @param contentsForm
	 * @return
	 */
	@RequestMapping(value = "/manager/viewContents", method = RequestMethod.POST)
	public String view(Model model, ContentsForm contentsForm) {
		debugUtil.outConsole("view");
		noteManagerHomeService.viewContents(model, contentsForm);
		return "/management/preview.jsp";
	}

	/**
	 * 分類取得（Json）
	 * @return
	 */
	@RequestMapping(value = "/manager/classjson")
	@ResponseBody
	public String getClassJson() {
		debugUtil.outConsole("getClassJson");
		return noteManagerHomeService.getClassJson();
	}
}
