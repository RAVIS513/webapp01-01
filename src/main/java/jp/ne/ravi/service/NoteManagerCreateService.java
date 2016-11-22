package jp.ne.ravi.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.ravi.constant.Const;
import jp.ne.ravi.dao.ContentsDao;
import jp.ne.ravi.dto.ContentsDto;
import jp.ne.ravi.exception.WebException;
import jp.ne.ravi.form.ContentsForm;
import jp.ne.ravi.session.SessionManager;
import jp.ne.ravi.utils.DbSessionUtil;
import jp.ne.ravi.utils.FileUtil;
import jp.ne.ravi.utils.StringUtil;

@Scope("prototype")
@Service
public class NoteManagerCreateService extends ServiceAbstract {

	@Autowired
	private DbSessionUtil dbSessionUtil;

	@Autowired
	private StringUtil stringUtil;

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private ServletContext context;

	/**
	 * 初期表示
	 *
	 * @param model
	 * @param sessionManager
	 * @param contentsForm
	 */
	public void init(Model model, ContentsForm contentsForm, HttpSession httpSession) {
		SqlSession session = null;
		try {
			// DB接続
			session = dbSessionUtil.open();

			common(model, Const.PAGE_MANAGE_CREATE, session, httpSession, null);

			SessionManager sessionManager = (SessionManager) httpSession.getAttribute("sessionManager");
			contentsForm = sessionManager.getContentsForm();

			String seqNo = sessionManager.getContentsForm().getSeqNo();
			if (seqNo != null && StringUtils.isNotBlank(seqNo)) {
				// 編集時、既存データ読み込み
				ContentsDao dao = new ContentsDao(session);
				ContentsDto dto = dao.selectBySeqNo(Integer.parseInt(seqNo));

				if (contentsForm.getTitle() == null || StringUtils.isBlank(contentsForm.getTitle())) {
					contentsForm.setTitle(dto.getTitle());
				}
				if (contentsForm.getOverView() == null || StringUtils.isBlank(contentsForm.getOverView())) {
					contentsForm.setOverView(dto.getOverView());
				}
				String folder = dto.getStatus().equals(Const.CONTENTS_RELEASE) ? Const.RELEASE_FOLDER : Const.PROVISIONAL_FOLDER;
				if (contentsForm.getJsp() == null || StringUtils.isBlank(contentsForm.getJsp())) {
					contentsForm.setJsp(fileUtil.readJSP(dto.getJspFileName(), folder));
				}
				if (contentsForm.getCss() == null || StringUtils.isBlank(contentsForm.getCss())) {
					contentsForm.setCss(fileUtil.readCSS(dto.getCssFileName(), folder));
				}
				if (contentsForm.getJspFileName() == null || StringUtils.isBlank(contentsForm.getJspFileName())) {
					contentsForm.setJspFileName(dto.getJspFileName());
				}
				if (contentsForm.getCssFileName() == null || StringUtils.isBlank(contentsForm.getCssFileName())) {
					contentsForm.setCssFileName(dto.getCssFileName());
				}
			} else {
				// 新規
				if (contentsForm.getJsp() == null || StringUtils.isBlank(contentsForm.getJsp())) {
					contentsForm.setJsp(fileUtil.readJSP("base.jsp", ""));
				}
				if (contentsForm.getCss() == null || StringUtils.isBlank(contentsForm.getCss())) {
					contentsForm.setCss(fileUtil.readCSS("base.css", ""));
				}
			}

			model.addAttribute(contentsForm);
			Path path = Paths.get(context.getRealPath(""), "img", "upload");
			model.addAttribute("imageFiles", fileUtil.getFileList(path.toString()));

			// DB切断
			dbSessionUtil.close(session);

		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				dbSessionUtil.rollback(session);
			}
			throw new WebException("管理画面表示エラー", e);
		}
	}

	/**
	 * プレビュー表示処理
	 *
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 */
	public void preView(Model model, ContentsForm contentsForm) {
		String jspData = contentsForm.getJsp();
		String cssData = contentsForm.getCss();

		// JSPチェック
		if (jspData == null || StringUtils.isBlank(jspData)) {
			model.addAttribute("message", "JSP内容が空です");
			model.addAttribute(contentsForm);
			return;
		}
		if (!stringUtil.checkJSPHeader(jspData)) {
			model.addAttribute("message", "JSPヘッダーが設定されていません<br>" + fileUtil.readJSP("header.jsp", ""));
			model.addAttribute(contentsForm);
			return;
		}

		// CSSチェック
		if (cssData != null && StringUtils.isNotBlank(cssData)) {
			if (!stringUtil.checkCSSHeader(cssData)) {
				model.addAttribute("message", "CSSヘッダーが設定されていません<br>" + fileUtil.readCSS("header.css", ""));
				model.addAttribute(contentsForm);
				return;
			}
		}

		// プレビュー表示
		model.addAttribute("status", Const.CONTENTS_TEMP);
		model.addAttribute("message", "PreView");
		model.addAttribute("title", contentsForm.getTitle());
		model.addAttribute("uploadDate", contentsForm.getUploadDate());
		model.addAttribute("largeClass", contentsForm.getLargeClass().trim());
		model.addAttribute("middleClass", contentsForm.getMiddleClass().trim());
		if (contentsForm.getSmallClass() != null) {
			model.addAttribute("smallClass", contentsForm.getSmallClass().trim());
		}
		String jspFileName = fileUtil.createJSP(contentsForm.getJspFileName(), contentsForm.getJsp(), Const.TEMP_FOLDER,
				false);
		model.addAttribute("jspFile", jspFileName);

		if (cssData != null && StringUtils.isNotBlank(cssData)) {
			String cssFileName = fileUtil.createCSS(contentsForm.getCssFileName(), contentsForm.getCss(),
					Const.TEMP_FOLDER, false);
			model.addAttribute("cssFile", cssFileName);
		}
	}

	/**
	 * 保存処理
	 *
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 */
	public void save(Model model, ContentsForm contentsForm, RedirectAttributes attributes,
			HttpSession httpSession) {

		boolean err = false;

		SessionManager sessionManager = (SessionManager) httpSession.getAttribute("sessionManager");
		sessionManager.setContentsForm(contentsForm);

		// 入力チェック
		if (StringUtils.isBlank(contentsForm.getTitle())) {
			attributes.addFlashAttribute("message", "タイトルを入力して下さい");
			return;
		}

		// JSP & CSSヘッダーチェック
		String jspData = contentsForm.getJsp();
		String cssData = contentsForm.getCss();

		if (jspData != null && StringUtils.isNotBlank(jspData)) {
			if (!stringUtil.checkJSPHeader(jspData)) {
				attributes.addFlashAttribute("message", "JSPヘッダーが設定されていません<br>" + fileUtil.readJSP("header.jsp", ""));
				err = true;
			}
		}
		if (cssData != null && StringUtils.isNotBlank(cssData)) {
			if (!stringUtil.checkCSSHeader(cssData)) {
				attributes.addFlashAttribute("message", "CSSヘッダーが設定されていません<br>" + fileUtil.readCSS("header.css", ""));
				err = true;
			}
		}

		if (err) {
			return;
		}

		// 保存処理
		SqlSession session = null;
		try {
			String jspFileName = "";
			String cssFileName = "";

			if (jspData != null && StringUtils.isNotBlank(jspData)) {
				jspFileName = fileUtil.createJSP(contentsForm.getJspFileName(), jspData, Const.PROVISIONAL_FOLDER,
						false);
			}
			if (cssData != null && StringUtils.isNotBlank(cssData)) {
				cssFileName = fileUtil.createCSS(contentsForm.getCssFileName(), cssData, Const.PROVISIONAL_FOLDER,
						false);
			}

			// DB接続
			session = dbSessionUtil.open();

			ContentsDto dto = new ContentsDto();
			dto.setUploadDate(new Date(System.currentTimeMillis()));
			dto.setJspFileName(jspFileName);
			dto.setCssFileName(cssFileName);
			dto.setTitle(contentsForm.getTitle());
			dto.setLargeClass(contentsForm.getLargeClass());
			dto.setMiddleClass(contentsForm.getMiddleClass());
			dto.setSmallClass(contentsForm.getSmallClass());
			dto.setOverView(contentsForm.getOverView());
			dto.setStatus(Const.CONTENTS_PROVISIONAL);
			dto.setCreateUserId(sessionManager.getLoginForm().getId());
			dto.setUpdateUserId(sessionManager.getLoginForm().getId());
			dto.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));

			ContentsDao dao = new ContentsDao(session);
			if (StringUtils.isNotBlank(contentsForm.getSeqNo())) {
				dto.setSeqNo(Integer.parseInt(contentsForm.getSeqNo()));
				dao.update(dto);
				attributes.addFlashAttribute("message", "上書き保存しました");
			} else {
				int seqNo = dao.insert(dto);
				contentsForm.setSeqNo(String.valueOf(seqNo));
				contentsForm.setJspFileName(jspFileName);
				contentsForm.setCssFileName(cssFileName);
				attributes.addFlashAttribute("message", "新規保存しました");
			}

			sessionManager.setContentsForm(contentsForm);

			// DB切断
			dbSessionUtil.close(session);

		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				dbSessionUtil.rollback(session);
			}
			throw new WebException("保存エラー", e);
		}
	}

	/**
	 * 記事投稿
	 *
	 * @param contentsForm
	 * @param attributes
	 * @param sessionManager
	 * @return
	 */
	public boolean create(ContentsForm contentsForm, RedirectAttributes attributes, HttpSession httpSession) {

		SessionManager sessionManager = (SessionManager) httpSession.getAttribute("sessionManager");
		sessionManager.setContentsForm(contentsForm);

		// 入力チェック
		if (contentsForm.getLargeClass() == null || StringUtils.isBlank(contentsForm.getLargeClass())) {
			attributes.addFlashAttribute("message", "大分類を選択して下さい");
			return false;
		}
		if (contentsForm.getTitle() == null || StringUtils.isBlank(contentsForm.getTitle())) {
			attributes.addFlashAttribute("message", "タイトルを入力して下さい");
			return false;
		}
		if (contentsForm.getOverView() == null || StringUtils.isBlank(contentsForm.getOverView())) {
			attributes.addFlashAttribute("message", "概要文を入力して下さい");
			return false;
		}
		if (contentsForm.getJsp() == null || StringUtils.isBlank(contentsForm.getJsp())) {
			attributes.addFlashAttribute("message", "JSPを入力して下さい");
			return false;
		}

		// JSP & CSSヘッダーチェック
		String jspData = contentsForm.getJsp();
		String cssData = contentsForm.getCss();

		if (jspData != null && StringUtils.isNotBlank(jspData)) {
			if (!stringUtil.checkJSPHeader(jspData)) {
				attributes.addFlashAttribute("message", "JSPヘッダーが設定されていません<br>" + fileUtil.readJSP("header.jsp", ""));
				return false;
			}
		}
		if (cssData != null && StringUtils.isNotBlank(cssData)) {
			if (!stringUtil.checkCSSHeader(cssData)) {
				attributes.addFlashAttribute("message", "CSSヘッダーが設定されていません<br>" + fileUtil.readCSS("header.css", ""));
				return false;
			}
		}

		// 投稿
		SqlSession dbSession = null;
		try {
			String jspFileName = "";
			String cssFileName = "";

			if (jspData != null && StringUtils.isNotBlank(jspData)) {
				jspFileName = fileUtil.createJSP(contentsForm.getJspFileName(), jspData, Const.RELEASE_FOLDER, false);
			}
			if (cssData != null && StringUtils.isNotBlank(cssData)) {
				cssFileName = fileUtil.createCSS(contentsForm.getCssFileName(), cssData, Const.RELEASE_FOLDER, false);
			}

			// DB接続
			dbSession = dbSessionUtil.open();

			ContentsDao dao = new ContentsDao(dbSession);

			ContentsDto dto = new ContentsDto();
			dto.setStatus(Const.CONTENTS_RELEASE);
			dto.setUploadDate(new Date(System.currentTimeMillis()));
			dto.setLargeClass(contentsForm.getLargeClass());
			dto.setMiddleClass(contentsForm.getMiddleClass());
			dto.setSmallClass(contentsForm.getSmallClass());
			dto.setTitle(contentsForm.getTitle());
			dto.setOverView(contentsForm.getOverView());
			dto.setJspFileName(jspFileName);
			dto.setCssFileName(cssFileName);

			if (StringUtils.isBlank(contentsForm.getSeqNo())) {
				// INSERT
				dto.setCreateUserId(sessionManager.getLoginForm().getId());
				dto.setCreateTime(new Timestamp(System.currentTimeMillis()));
				dao.insert(dto);
			} else {
				// UPDATE
				ContentsDto pdto = dao.selectBySeqNo(Integer.parseInt(contentsForm.getSeqNo()));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date date = sdf.parse(contentsForm.getUploadDate());
				Date sqlDate = new Date(date.getTime());

				dto.setSeqNo(Integer.parseInt(contentsForm.getSeqNo()));
				dto.setUploadDate(sqlDate);
				dto.setCreateUserId(pdto.getCreateUserId());
				dto.setUpdateUserId(sessionManager.getLoginForm().getId());
				dto.setCreateTime(pdto.getCreateTime());
				dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				dao.update(dto);

				// 未公開ファイルの削除
				fileUtil.deleteJSP(contentsForm.getJspFileName(), Const.PROVISIONAL_FOLDER, false);
				fileUtil.deleteCSS(contentsForm.getCssFileName(), Const.PROVISIONAL_FOLDER, false);
			}

			attributes.addFlashAttribute("message", "記事投稿が完了しました");

			// DB切断
			dbSessionUtil.close(dbSession);

		} catch (Exception e) {
			e.printStackTrace();
			if (dbSession != null) {
				dbSessionUtil.rollback(dbSession);
			}
			throw new WebException("投稿エラー", e);
		}

		sessionManager.setContentsForm(null);

		return true;
	}

}
