package jp.ne.ravi.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import jp.ne.ravi.constant.Const;
import jp.ne.ravi.dao.ClassDao;
import jp.ne.ravi.dao.ContentsDao;
import jp.ne.ravi.dto.ClassDto;
import jp.ne.ravi.session.SessionNote;
import jp.ne.ravi.utils.StringUtil;
import jp.ne.ravi.web.dto.WebMenuDto;
import jp.ne.ravi.web.dto.WebMenuSubDto;

public abstract class ServiceAbstract {

	@Value("${kind.home}")
	private String KIND_HOME;

	@Value("${kind.list}")
	private String KIND_LIST;

	@Value("${kind.view}")
	private String KIND_VIEW;

	@Value("${kind.login}")
	private String KIND_LOGIN;

	@Value("${kind.manage.home}")
	private String KIND_MANAGE_HOME;

	@Value("${kind.manage.create}")
	private String KIND_MANAGE_CREATE;

	@Autowired
	private StringUtil stringUtil;

	@Autowired
	private ServletContext context;

	/**
	 * 画面表示処理
	 *
	 * @param model
	 * @param select
	 * @param session
	 */
	protected void common(Model model, char select, SqlSession dbSession, HttpSession httpSession,
			SessionNote sessionNote) {

		if (sessionNote == null) {
			String uploadDate = getUploadDate(dbSession);
			List<WebMenuDto> wdto = getMenu(dbSession);

			sessionNote = new SessionNote();
			sessionNote.setUploadDate(uploadDate);
			sessionNote.setSideMenuList(wdto);
			httpSession.setAttribute("sessionNote", sessionNote);

			model.addAttribute("update", uploadDate);
			model.addAttribute("menu", wdto);
			model.addAttribute("select", selectMainContent(select));
		} else {
			model.addAttribute("update", sessionNote.getUploadDate());
			model.addAttribute("menu", sessionNote.getSideMenuList());
			model.addAttribute("select", selectMainContent(select));
		}

	}

	/**
	 * 最終更新日付取得
	 *
	 * @param session
	 * @return
	 */
	private String getUploadDate(SqlSession session) {
		String date = "";
		if (session != null) {
			ContentsDao dao = new ContentsDao(session);
			Date result = dao.selectMaxUploadDate();
			if (result != null) {
				date = new SimpleDateFormat("yyyy/MM/dd").format(result);
			}
		}
		return date;
	}

	/**
	 * サイドメニューリスト生成
	 *
	 * @param session
	 * @return
	 */
	private List<WebMenuDto> getMenu(SqlSession session) {
		List<WebMenuDto> list = new ArrayList<WebMenuDto>();
		if (session != null) {
			ClassDao dao = new ClassDao(session);
			List<ClassDto> result = dao.selectAll();
			if (result != null && !result.isEmpty()) {
				// 大分類リスト作成
				String largeClass = "";
				List<String> largeClassList = new ArrayList<String>();
				for (ClassDto d : result) {
					if (!largeClass.equals(d.getLargeClass())) {
						largeClass = d.getLargeClass();
						largeClassList.add(largeClass);
					}
				}
				// メニューリスト作成
				for (String s : largeClassList) {
					WebMenuDto mdto = new WebMenuDto();
					WebMenuSubDto sdto = new WebMenuSubDto();
					List<WebMenuSubDto> subList = new ArrayList<WebMenuSubDto>();
					String middleClass = "";
					// 大分類
					sdto.setName(s);
					sdto.setUrl(context.getContextPath() + "/" + KIND_LIST + "/" + stringUtil.encodeUrl(s));
					mdto.setMain(sdto);
					// 中分類
					for (ClassDto d : result) {
						if (s.equals(d.getLargeClass())) {
							if (!middleClass.equals(d.getMiddleClass().trim())) {
								middleClass = d.getMiddleClass().trim();
								WebMenuSubDto sd = new WebMenuSubDto();
								sd.setName(middleClass);
								sd.setUrl(sdto.getUrl() + "/" + stringUtil.encodeUrl(middleClass));
								subList.add(sd);
							}
						}
					}
					mdto.setSub(subList);
					list.add(mdto);
				}
			}
		}
		return list;
	}

	/**
	 * コンテンツ選択
	 *
	 * @param select
	 * @return
	 */
	private String selectMainContent(char select) {
		String str = null;
		switch (select) {
		case Const.PAGE_MAIN_LIST:
			str = KIND_LIST;
			break;
		case Const.PAGE_MAIN_VIEW:
			str = KIND_VIEW;
			break;
		case Const.PAGE_MANAGE_LOGIN:
			str = KIND_LOGIN;
			break;
		case Const.PAGE_MANAGE_HOME:
			str = KIND_MANAGE_HOME;
			break;
		case Const.PAGE_MANAGE_CREATE:
			str = KIND_MANAGE_CREATE;
			break;
		default:
			str = KIND_HOME;
			break;
		}
		return str;
	}

}
