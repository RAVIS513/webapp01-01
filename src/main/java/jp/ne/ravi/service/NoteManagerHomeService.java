package jp.ne.ravi.service;

import java.sql.Timestamp;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ne.ravi.constant.Const;
import jp.ne.ravi.dao.ClassDao;
import jp.ne.ravi.dao.ContentsDao;
import jp.ne.ravi.dto.ClassDto;
import jp.ne.ravi.dto.ContentsDto;
import jp.ne.ravi.exception.WebException;
import jp.ne.ravi.form.ClassForm;
import jp.ne.ravi.form.ContentsForm;
import jp.ne.ravi.session.SessionManager;
import jp.ne.ravi.utils.DbSessionUtil;
import jp.ne.ravi.utils.FileUtil;
import jp.ne.ravi.web.dto.WebClassDto;
import jp.ne.ravi.web.dto.WebClassSubDto;
import jp.ne.ravi.web.dto.WebContentsDto;

@Scope("prototype")
@Service
public class NoteManagerHomeService extends ServiceAbstract {

	@Autowired
	private DbSessionUtil dbSessionUtil;

	@Autowired
	private FileUtil fileUtil;

	private static List<WebClassDto> wdtoList;

	private static Integer prevPage;
	private static Integer nextPage;
	private static Integer currentPageStart;
	private static Integer currentPageEnd;
	private static Integer contentsNum;

	/**
	 * 初期表示処理
	 *
	 * @param model
	 */
	public void home(Model model, HttpSession httpSession, Integer page) {
		SqlSession session = null;
		try {
			// 初期化
			fileUtil.deleteJSP("", Const.TEMP_FOLDER, true);
			fileUtil.deleteCSS("", Const.TEMP_FOLDER, true);

			prevPage = null;
			nextPage = null;
			currentPageStart = null;
			currentPageEnd = null;
			contentsNum = null;

			// DB接続
			session = dbSessionUtil.open();

			common(model, Const.PAGE_MANAGE_HOME, session, httpSession, null);

			SessionManager sessionManager = (SessionManager) httpSession.getAttribute("sessionManager");

			wdtoList = getClassAll(session);
			model.addAttribute("classes", wdtoList);
			model.addAttribute("contents", getContents(session, sessionManager, page));
			model.addAttribute("prevPage", prevPage);
			model.addAttribute("nextPage", nextPage);
			model.addAttribute("currentPageStart", currentPageStart);
			model.addAttribute("currentPageEnd", currentPageEnd);
			model.addAttribute("contentsNum", contentsNum);

			// セッション初期化
			sessionManager.setClassForm(null);

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
	 * 分類追加処理
	 *
	 * @param classForm
	 * @param attributes
	 */
	public void addClass(ClassForm classForm, RedirectAttributes attributes) {
		SqlSession session = null;
		try {
			String l_class = classForm.getLargeClass();
			String m_class = classForm.getMiddleClass();
			String s_class = classForm.getSmallClass();
			if (l_class == null || l_class.isEmpty()) {
				attributes.addFlashAttribute("message", "[分類追加] 大分類を入力して下さい");
				return;
			} else if (m_class == null || m_class.isEmpty()) {
				attributes.addFlashAttribute("message", "[分類追加] 中分類を入力して下さい");
				return;
			}

			// DB接続
			session = dbSessionUtil.open();

			// データ設定
			ClassDao dao = new ClassDao(session);
			ClassDto dto = new ClassDto();
			dto.setLargeClass(l_class);
			dto.setMiddleClass((m_class == null || m_class.isEmpty()) ? " " : m_class);
			dto.setSmallClass((s_class == null || s_class.isEmpty()) ? " " : s_class);
			dto.setCreateTime(new Timestamp(System.currentTimeMillis()));

			// 重複チェック
			if (dao.count(dto) > 0) {
				attributes.addFlashAttribute("message", "[分類追加] 既に登録されています");
				return;
			}

			// 登録
			dao.insert(dto);
			attributes.addFlashAttribute("message", "[分類追加] 分類が登録されました");

			// DB切断
			dbSessionUtil.close(session);

		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				dbSessionUtil.rollback(session);
			}
			throw new WebException("分類追加エラー", e);
		}
	}

	/**
	 * 分類削除処理
	 *
	 * @param classForm
	 * @param attributes
	 */
	public void delClass(ClassForm classForm, RedirectAttributes attributes) {
		SqlSession session = null;
		try {
			String l_class = classForm.getLargeClass();
			String m_class = classForm.getMiddleClass();
			String s_class = classForm.getSmallClass();

			if (l_class != null && m_class != null && s_class != null) {
				// DB接続
				session = dbSessionUtil.open();

				// データ設定
				ClassDao dao = new ClassDao(session);
				ClassDto dto = new ClassDto();
				dto.setLargeClass(l_class);
				dto.setMiddleClass(m_class.isEmpty() ? " " : m_class);
				dto.setSmallClass(s_class.isEmpty() ? " " : s_class);
				dto.setCreateTime(new Timestamp(System.currentTimeMillis()));

				// 存在チェック
				if (dao.count(dto) > 0) {
					// 削除処理
					dao.delete(dto);
					attributes.addFlashAttribute("message", "[分類削除] 分類が削除されました");
				} else {
					attributes.addFlashAttribute("message", "[分類削除] 削除対象が見つかりません");
				}

				// DB切断
				dbSessionUtil.close(session);

			} else {
				attributes.addFlashAttribute("message", "[分類削除] 削除対象が見つかりません");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				dbSessionUtil.rollback(session);
			}
			throw new WebException("分類削除エラー", e);
		}
	}

	/**
	 * クラス一覧取得
	 *
	 * @param session
	 * @return
	 */
	private List<WebClassDto> getClassAll(SqlSession session) {
		List<WebClassDto> list = new ArrayList<WebClassDto>();
		if (session != null) {
			ClassDao dao = new ClassDao(session);
			// クラス一覧取得
			List<ClassDto> result = dao.selectAll();
			if (result != null && !result.isEmpty()) {
				// 大分類リスト作成
				String lc = "";
				List<String> lcList = new ArrayList<String>();
				for (ClassDto d : result) {
					if (!lc.equals(d.getLargeClass())) {
						lc = d.getLargeClass();
						lcList.add(lc);
					}
				}

				// 一覧生成
				for (String ls : lcList) {
					// 中分類リスト作成
					String mc = "";
					List<String> mcList = new ArrayList<String>();
					for (ClassDto d : result) {
						if (ls.equals(d.getLargeClass()) && !mc.equals(d.getMiddleClass())) {
							mc = d.getMiddleClass();
							mcList.add(mc);
						}
					}

					// 中、小分類一覧
					List<WebClassSubDto> subList = new ArrayList<WebClassSubDto>();
					for (String ms : mcList) {
						WebClassSubDto sdto = new WebClassSubDto();
						List<String> scList = new ArrayList<String>();
						for (ClassDto d : result) {
							if (ls.equals(d.getLargeClass()) && ms.equals(d.getMiddleClass())) {
								scList.add(d.getSmallClass());
							}
						}
						// 中分類セット
						sdto.setMainClass(ms);
						// 小分類セット
						sdto.setSubClass(scList);
						// リスト化
						subList.add(sdto);
					}
					// 大分類セット
					WebClassDto wdto = new WebClassDto();
					wdto.setMainClass(ls);
					wdto.setSubClass(subList);
					list.add(wdto);
				}
			}
		}
		return list;
	}

	/**
	 * 記事一覧取得
	 *
	 * @return
	 */
	private List<WebContentsDto> getContents(SqlSession dbSession, SessionManager sessionManager, Integer page) {
		List<WebContentsDto> list = new ArrayList<WebContentsDto>();
		if (dbSession != null) {
			ContentsDao dao = new ContentsDao(dbSession);

			String largeClass = "";
			String middleClass = "";
			String smallClass = "";

			if (sessionManager.getClassForm() != null) {
				largeClass = sessionManager.getClassForm().getLargeClass();
				middleClass = sessionManager.getClassForm().getMiddleClass();
				smallClass = sessionManager.getClassForm().getSmallClass();
			}

			List<ContentsDto> result = new ArrayList<ContentsDto>();

			if ((largeClass != null && StringUtils.isNotBlank(largeClass))
					&& (middleClass == null || StringUtils.isBlank(middleClass))
					&& (smallClass == null || StringUtils.isBlank(smallClass))) {
				// 大分類検索
				result = dao.selectByClassLAndExceptStatus(largeClass, Const.CONTENTS_DELETE);
			} else if ((largeClass != null && StringUtils.isNotBlank(largeClass))
					&& (middleClass != null && StringUtils.isNotBlank(middleClass))
					&& (smallClass == null || StringUtils.isBlank(smallClass))) {
				// 大中分類検索
				result = dao.selectByClassLMAndExceptStatus(largeClass, middleClass, Const.CONTENTS_DELETE);
			} else if ((largeClass != null && StringUtils.isNotBlank(largeClass))
					&& (middleClass != null && StringUtils.isNotBlank(middleClass))
					&& (smallClass != null && StringUtils.isNotBlank(smallClass))) {
				// 大中小分類検索
				result = dao.selectByClassLMSAndExceptStatus(largeClass, middleClass, smallClass, Const.CONTENTS_DELETE);
			} else {
				// 全検索
				Integer count = dao.countByExceptStatus(Const.CONTENTS_DELETE);

				if (count > 0) {
					Integer start = 1;
					Integer end = 20 > count ? count : 20;
					if (page != null && !page.equals("") && page > 0) {
						start = page > count ? count : page;
						end = page + 19 > count ? count : page + 19;
					}
					result = dao.selectByExceptStatusWithPaging(Const.CONTENTS_DELETE, start, end);

					prevPage = (start - 20) > 0 ? (start - 20) : null;
					nextPage = end < count ? (end + 1) : null;
					currentPageStart = start;
					currentPageEnd = end;
					contentsNum = count > 0 ? count : null;
				}
			}

			// コンテンツ取得
			if (result != null && !result.isEmpty()) {
				for (ContentsDto d : result) {
					WebContentsDto wd = new WebContentsDto();
					wd.setSeqNo(String.valueOf(d.getSeqNo()));
					wd.setUploadDate(new String(new SimpleDateFormat("yyyy/MM/dd").format(d.getUploadDate())));
					wd.setTitle(d.getTitle());
					wd.setLargeClass(d.getLargeClass());
					wd.setMiddleClass(d.getMiddleClass());
					wd.setSmallClass(d.getSmallClass());
					wd.setStatus(d.getStatus());
					list.add(wd);
				}
			}
		}
		return list;
	}

	/**
	 * 分類一覧取得（Json）
	 *
	 * @return
	 */
	public String getClassJson() {
		String json = "";
		if (wdtoList != null && !wdtoList.isEmpty()) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(wdtoList);
			} catch (Exception e) {
				e.printStackTrace();
				throw new WebException("Get Class Json Error", e);
			}
		}
		return json;
	}

	/**
	 * 記事投稿チェック
	 *
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 * @return
	 */
	public boolean createContents(Model model, ContentsForm contentsForm, RedirectAttributes attributes,
			HttpSession session) {
		// NG
		if (StringUtils.isBlank(contentsForm.getLargeClass())) {
			attributes.addFlashAttribute("message", "[記事投稿] 大分類を選択して下さい");
			return false;
		} else if (StringUtils.isBlank(contentsForm.getMiddleClass())) {
			attributes.addFlashAttribute("message", "[記事投稿] 中分類を選択して下さい");
			return false;
		}
		// OK
		SessionManager sessionManager = (SessionManager) session.getAttribute("sessionManager");
		ContentsForm form = new ContentsForm();
		form = contentsForm;
		sessionManager.setContentsForm(form);
		return true;
	}

	/**
	 * 記事検索
	 *
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 * @param session
	 */
	public void searchContents(Model model, ClassForm classForm, RedirectAttributes attributes, HttpSession session) {
		if (classForm.getLargeClass() == null || StringUtils.isBlank(classForm.getLargeClass())) {
			attributes.addFlashAttribute("message", "[記事検索] 大分類を設定して下さい");
			return;
		}

		SessionManager sessionManager = (SessionManager) session.getAttribute("sessionManager");
		sessionManager.setClassForm(classForm);
	}

	/**
	 * 記事編集
	 *
	 * @param model
	 * @param contentsForm
	 * @param session
	 */
	public void editContents(Model model, ContentsForm contentsForm, HttpSession session) {
		SessionManager sessionManager = (SessionManager) session.getAttribute("sessionManager");
		sessionManager.setContentsForm(contentsForm);
	}

	/**
	 * 記事削除
	 *
	 * @param model
	 * @param contentsForm
	 * @param attributes
	 */
	public void deleteContents(Model model, ContentsForm contentsForm, RedirectAttributes attributes,
			HttpSession session) {
		SqlSession dbSession = null;
		try {
			// DB接続
			dbSession = dbSessionUtil.open();

			SessionManager sessionManager = (SessionManager) session.getAttribute("sessionManager");

			ContentsDao dao = new ContentsDao(dbSession);
			dao.updateTheStatus(Integer.parseInt(contentsForm.getSeqNo()), Const.CONTENTS_DELETE,
					sessionManager.getLoginForm().getId());

			attributes.addFlashAttribute("message", "[記事削除] 記事を削除しました[" + contentsForm.getSeqNo() + "番]");

			// DB切断
			dbSessionUtil.close(dbSession);

		} catch (Exception e) {
			e.printStackTrace();
			if (dbSession != null) {
				dbSessionUtil.rollback(dbSession);
			}
		}
	}

	/**
	 * 記事参照
	 *
	 * @param model
	 * @param contentsForm
	 */
	public void viewContents(Model model, ContentsForm contentsForm) {
		if (contentsForm != null) {
			SqlSession session = null;
			try {
				// DB接続
				session = dbSessionUtil.open();
				ContentsDao dao = new ContentsDao(session);
				ContentsDto result = dao.selectBySeqNo(Integer.parseInt(contentsForm.getSeqNo()));

				if (result != null) {
					model.addAttribute("status", result.getStatus());
					model.addAttribute("message", "View");
					model.addAttribute("title", result.getTitle());
					model.addAttribute("uploadDate", new String(new SimpleDateFormat("yyyy/MM/dd").format(result.getUploadDate())));
					model.addAttribute("largeClass", result.getLargeClass().trim());
					model.addAttribute("middleClass", result.getMiddleClass().trim());
					if (result.getSmallClass() != null) {
						model.addAttribute("smallClass", result.getSmallClass().trim());
					}
					model.addAttribute("jspFile",
							StringUtils.isNotBlank(result.getJspFileName()) ? result.getJspFileName() : "");
					model.addAttribute("cssFile",
							StringUtils.isNotBlank(result.getCssFileName()) ? result.getCssFileName() : "");
				} else {
					model.addAttribute("message", "[記事参照] DB取得エラー");
				}

				// DB切断
				dbSessionUtil.close(session);

			} catch (Exception e) {
				e.printStackTrace();
				if (session != null) {
					dbSessionUtil.rollback(session);
				}
				throw new WebException("コンテンツ参照エラー", e);
			}
		}
	}
}
