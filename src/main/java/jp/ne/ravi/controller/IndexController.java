package jp.ne.ravi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ne.ravi.service.IndexService;
import jp.ne.ravi.utils.DebugUtil;

@Scope("prototype")
@Controller
public class IndexController {

	@Autowired
	private DebugUtil debugUtil;

	@Autowired
	private IndexService indexService;

	/**
	 * デフォルト画面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init(Model model, HttpSession httpSession) {
		debugUtil.outConsole("index");
		indexService.init(model, httpSession);
		return "index.jsp";
	}

	/**
	 * リスト表示（大分類）
	 *
	 * @param model
	 * @param sel
	 * @return
	 */
	@RequestMapping(value = "/list/{largeClass}", method = RequestMethod.GET)
	public String listOfLargeClass(Model model, @PathVariable String largeClass,
			@RequestParam(required = false) String search, HttpSession httpSession) {
		debugUtil.outConsole("list/" + largeClass);
		indexService.list(model, httpSession, largeClass, null, search);
		return "index.jsp";
	}

	/**
	 * リスト表示（中分類）
	 *
	 * @param model
	 * @param largeClass
	 * @param middleClass
	 * @return
	 */
	@RequestMapping(value = "/list/{largeClass}/{middleClass}", method = RequestMethod.GET)
	public String listOfMiddleClass(Model model, @PathVariable String largeClass, @PathVariable String middleClass,
			@RequestParam(required = false) String search, HttpSession httpSession) {
		debugUtil.outConsole("list/" + largeClass + "/" + middleClass);
		indexService.list(model, httpSession, largeClass, middleClass, search);
		return "index.jsp";
	}

	/**
	 * 記事表示
	 *
	 * @param model
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/view/{seqNo}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable String seqNo, HttpSession httpSession) {
		debugUtil.outConsole("view/" + seqNo);
		indexService.view(model, httpSession, seqNo);
		return "index.jsp";
	}
}
