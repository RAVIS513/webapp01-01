package jp.ne.ravi.service;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.ravi.constant.Const;
import jp.ne.ravi.dao.UsersDao;
import jp.ne.ravi.dto.UsersDto;
import jp.ne.ravi.exception.WebException;
import jp.ne.ravi.form.LoginForm;
import jp.ne.ravi.utils.DbSessionUtil;
import jp.ne.ravi.utils.StringUtil;

@Scope("prototype")
@Service
public class NoteManagerLoginService extends ServiceAbstract {

	@Autowired
	private StringUtil stringUtil;

	@Autowired
	private DbSessionUtil dbSessionUtil;

	/**
	 * 管理 ログイン画面表示
	 * @param model
	 */
	public void login(Model model, HttpSession httpSession) {
		SqlSession session = null;
		try {
			//DB接続
			session = dbSessionUtil.open();

			common(model, Const.PAGE_MANAGE_LOGIN, session, httpSession, null);

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
	 * 管理 ログイン処理
	 * @param loginForm
	 * @param attributes
	 * @return
	 */
	public boolean checkLogin(LoginForm loginForm, RedirectAttributes attributes) {
		SqlSession session = null;
		boolean result = false;
		try {
			// ログインフォームチェック
			String id = loginForm.getId();
			String pass = loginForm.getPass();
			if (id == null || id.isEmpty()) {
				attributes.addFlashAttribute("message", "IDを入力して下さい");
				return false;
			}
			if (pass == null || pass.isEmpty()) {
				attributes.addFlashAttribute("message", "PASSを入力して下さい");
				return false;
			}

			// DB接続
			session = dbSessionUtil.open();

			// ユーザー検索
			UsersDao dao = new UsersDao(session);
			UsersDto dto = new UsersDto();
			dto.setUserId(loginForm.getId());
			dto.setNewPass(stringUtil.encryption(loginForm.getPass(), Const.ALGO));
			if (dao.count(dto) == 1) {
				result = true;
			} else {
				attributes.addFlashAttribute("message", "IDまたはPASSが間違っています");
				result = false;
			}

			if (result) {
				dao.updateTheLastLoginTime(loginForm.getId());
			}

			// DB切断
			dbSessionUtil.close(session);

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				dbSessionUtil.rollback(session);
			}
			throw new WebException("ログイン処理エラー", e);
		}
	}
}
