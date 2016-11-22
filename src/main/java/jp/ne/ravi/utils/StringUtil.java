package jp.ne.ravi.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

import javax.servlet.ServletContext;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.ne.ravi.constant.Const;
import jp.ne.ravi.exception.WebException;

@Scope("prototype")
@Component
public class StringUtil {

	@Autowired
	private DebugUtil debugUtil;

	@Autowired
	private ServletContext context;

	/**
	 * ハッシュ化.
	 * @param target
	 * @param algorithm
	 * @return
	 */
	public String encryption(String target, String algorithm) {
		MessageDigest messageDigest = null;
		try {
			messageDigest =  MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			debugUtil.outConsole("Non-Support Algorithm");
			throw new WebException("Non-Support Algorithm", e);
		}
		// 初期化
		messageDigest.reset();

		// SALTの追加
		messageDigest.update(Const.SALT.getBytes());

		// 暗号化
		byte[] hash = messageDigest.digest(target.getBytes());

		// 16進数化
		StringBuffer sb = new StringBuffer();
		for (byte b : hash) {
			String str = String.format("%02x", b);
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * URL Encoder.
	 * @param target
	 * @return
	 */
	public String encodeUrl(String target) {
		try {
			URLCodec codec = new URLCodec("UTF-8");
			return codec.encode(target, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("URLEncode Error", e);
		}
	}

	/**
	 * URL Decoder.
	 * @param target
	 * @return
	 */
	public String decodeUrl(String target) {
		try {
			URLCodec codec = new URLCodec("UTF-8");
			return codec.decode(target, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("URLDecode Error", e);
		}
	}

	/**
	 * ファイル拡張子取得.
	 * @param fileName
	 * @return
	 */
	public String getExtension(String fileName) {
		String ext = "";
		if (fileName != null && StringUtils.isNotBlank(fileName)) {
			int point = fileName.lastIndexOf(".");
			if (point != -1) {
				ext = fileName.substring(point + 1);
			}
		}
		return ext;
	}

	/**
	 * JSPヘッダーチェック
	 * @param target 検索対象
	 * @param str 検索文字列
	 * @return
	 */
	public Boolean checkJSPHeader(String data) {
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			// JSPヘッダー取得
			Path inPath = Paths.get(context.getRealPath(""), "WEB-INF", "views", "header.jsp");
			FileInputStream fis = new FileInputStream(inPath.toString());
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);

			String str;
			while ((str = br.readLine()) != null) {
				if (!data.contains(str)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("JSPヘッダーチェックエラー", e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new WebException("JSPヘッダーチェックエラー", e2);
			}
		}
		return true;
	}

	/**
	 * CSSヘッダーチェック
	 * @param data
	 * @return
	 */
	public Boolean checkCSSHeader(String data) {
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			// JSPヘッダー取得
			Path inPath = Paths.get(context.getRealPath(""), "css", "header.css");
			FileInputStream fis = new FileInputStream(inPath.toString());
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);

			String str;
			while ((str = br.readLine()) != null) {
				if (!data.contains(str)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("CSSヘッダーチェックエラー", e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new WebException("CSSヘッダーチェックエラー", e2);
			}
		}
		return true;
	}
}
