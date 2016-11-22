package jp.ne.ravi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.ravi.form.ContentsForm;
import jp.ne.ravi.service.NoteManagerCreateService;
import jp.ne.ravi.utils.DebugUtil;

@Scope("prototype")
@Controller
public class NoteManagerCreateController {

	@Autowired
	private DebugUtil debugUtil;

	@Autowired
	private NoteManagerCreateService noteManagerCreateService;

	@ModelAttribute
	public ContentsForm setupModelAttribute() {
		ContentsForm contentsForm = new ContentsForm();
		return contentsForm;
	}

	/**
	 * 記事投稿 初期表示
	 * @param model
	 * @param contentsForm
	 * @return
	 */
	@RequestMapping(value = "/manager/create")
	public String init(Model model, ContentsForm contentsForm, HttpSession session) {
		debugUtil.outConsole("create");
		noteManagerCreateService.init(model, contentsForm, session);
		return "index.jsp";
	}

	/**
	 * プレビュー表示
	 * @param model
	 * @param contentsForm
	 * @return
	 */
	@RequestMapping(value = "/manager/create/preview", method = RequestMethod.POST)
	public String preview(Model model, ContentsForm contentsForm) {
		debugUtil.outConsole("create/preview");
		noteManagerCreateService.preView(model, contentsForm);
		return "management/preview.jsp";
	}

	/**
	 * 保存
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/manager/create/save", method = RequestMethod.POST)
	public String save(Model model, ContentsForm contentsForm, RedirectAttributes attributes, HttpSession session) {
		debugUtil.outConsole("create/save");
		noteManagerCreateService.save(model, contentsForm, attributes, session);
		return "redirect:/manager/create";
	}

	/**
	 * 投稿（送信）
	 * @param model
	 * @param contentsForm
	 * @return
	 */
	@RequestMapping(value = "/manager/create/contents", method = RequestMethod.POST)
	public String create(Model model, ContentsForm contentsForm, RedirectAttributes attributes, HttpSession session) {
		debugUtil.outConsole("create/contents");
		if (noteManagerCreateService.create(contentsForm, attributes, session)) {
			return "redirect:/manager";
		} else {
			return "redirect:/manager/create";
		}
	}
}
