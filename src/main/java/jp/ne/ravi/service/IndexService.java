package jp.ne.ravi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.ne.ravi.constant.Const;
import jp.ne.ravi.dao.ContentsDao;
import jp.ne.ravi.dto.ContentsDto;
import jp.ne.ravi.exception.WebException;
import jp.ne.ravi.session.SessionNote;
import jp.ne.ravi.utils.DbSessionUtil;
import jp.ne.ravi.web.dto.WebContentsDto;

@Scope("prototype")
@Service
public class IndexService extends ServiceAbstract {

	@Autowired
	private DbSessionUtil dbSessionUtil;

	/**
	 * ホーム表示
	 *
	 * @param model
	 * @param httpSession
	 */
	public void init(Model model, HttpSession httpSession) {
		SessionNote sessionNote = (SessionNote) httpSession.getAttribute("sessionNote");
		if (sessionNote == null) {
			// セッション情報が無い場合、DBより取得
			SqlSession session = null;
			try {
				// DB接続
				session = dbSessionUtil.open();

				common(model, Const.PAGE_MAIN_HOME, session, httpSession, null);

				// DB切断
				dbSessionUtil.close(session);
			} catch (Exception e) {
				e.printStackTrace();
				if (session != null) {
					dbSessionUtil.rollback(session);
				}
			}
		} else {
			// セッション情報が有る場合、セッションより取得
			common(model, Const.PAGE_MAIN_HOME, null, httpSession, sessionNote);
		}
	}

	/**
	 * リスト表示
	 *
	 * @param model
	 * @param httpSession
	 * @param largeClass
	 * @param middleClass
	 */
	public void list(Model model, HttpSession httpSession, String largeClass, String middleClass, String search) {
		SqlSession session = null;
		try {
			// DB接続
			session = dbSessionUtil.open();

			// 共通項目設定
			SessionNote sessionNote = (SessionNote) httpSession.getAttribute("sessionNote");
			if (sessionNote == null) {
				// セッション情報が無い場合、DBより取得
				common(model, Const.PAGE_MAIN_LIST, session, httpSession, null);
			} else {
				// セッション情報が有る場合、セッションより取得
				common(model, Const.PAGE_MAIN_LIST, null, httpSession, sessionNote);
			}

			// リスト取得
			List<WebContentsDto> list = new ArrayList<WebContentsDto>();
			List<ContentsDto> results = new ArrayList<ContentsDto>();
			ContentsDao dao = new ContentsDao(session);

			if (middleClass == null || StringUtils.isBlank(middleClass)) {
				if (search == null || StringUtils.isBlank(search)) {System.out.println(largeClass);
					results = dao.selectByClassLAndStatus(largeClass, Const.CONTENTS_RELEASE);
				} else {
					results = dao.selectByClassLAndStatusAndSearch(largeClass, Const.CONTENTS_RELEASE, search);
				}
			} else {
				if (search == null || StringUtils.isBlank(search)) {
					results = dao.selectByClassLMAndStatus(largeClass, middleClass, Const.CONTENTS_RELEASE);
				} else {
					results = dao.selectByClassLMAndStatusAndSearch(largeClass, middleClass, Const.CONTENTS_RELEASE,
							search);
				}
			}

			if (results != null) {
				for (ContentsDto d : results) {
					WebContentsDto wdto = new WebContentsDto();
					wdto.setSeqNo(String.valueOf(d.getSeqNo()));
					wdto.setUploadDate(new String(new SimpleDateFormat("yyyy/MM/dd").format(d.getUploadDate())));
					wdto.setTitle(d.getTitle());
					wdto.setLargeClass(d.getLargeClass().trim());
					wdto.setMiddleClass(d.getMiddleClass().trim());
					wdto.setOverView(d.getOverView());
					wdto.setStatus(d.getStatus());
					list.add(wdto);
				}
			}

			// 画面表示用
			model.addAttribute("list", list);
			model.addAttribute("listLarge", largeClass);
			model.addAttribute("listMiddle", middleClass);

			// DB切断
			dbSessionUtil.close(session);

		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				dbSessionUtil.rollback(session);
			}
		}
	}

	/**
	 * 詳細表示
	 *
	 * @param model
	 * @param httpSession
	 */
	public void view(Model model, HttpSession httpSession, String seqNo) {
		// セッション情報が無い場合、DBより取得
		SqlSession session = null;
		try {
			// DB接続
			session = dbSessionUtil.open();

			// 共通項目設定
			SessionNote sessionNote = (SessionNote) httpSession.getAttribute("sessionNote");
			if (sessionNote == null) {
				// セッション情報が無い場合、DBより取得
				common(model, Const.PAGE_MAIN_VIEW, session, httpSession, null);
			} else {
				// セッション情報が有る場合、セッションより取得
				common(model, Const.PAGE_MAIN_VIEW, null, httpSession, sessionNote);
			}

			// 記事取得
			ContentsDao dao = new ContentsDao(session);
			ContentsDto result = dao.selectBySeqNo(Integer.parseInt(seqNo));

			if (result != null) {
				model.addAttribute("title", result.getTitle());
				model.addAttribute("uploadDate",
						new String(new SimpleDateFormat("yyyy/MM/dd").format(result.getUploadDate())));
				model.addAttribute("largeClass", result.getLargeClass().trim());
				model.addAttribute("middleClass", result.getMiddleClass().trim());
				if (result.getSmallClass() != null) {
					model.addAttribute("smallClass", result.getSmallClass().trim());
				}
				model.addAttribute("releaseJspFile",
						StringUtils.isNotBlank(result.getJspFileName()) ? result.getJspFileName() : "");
				model.addAttribute("releaseCssFile",
						StringUtils.isNotBlank(result.getCssFileName()) ? result.getCssFileName() : "");
			}

			// DB切断
			dbSessionUtil.close(session);
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				dbSessionUtil.rollback(session);
			}
			throw new WebException("記事取得エラー", e);
		}
	}

}
