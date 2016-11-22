package jp.ne.ravi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.ne.ravi.exception.WebException;

@Scope("prototype")
@Component
public class FileUtil {

	@Autowired
	private ServletContext context;

	@Autowired
	private TimeUtil timeUtil;

	/**
	 * ファイルリスト取得
	 * @param dirPath
	 * @return
	 */
	public List<String> getFileList(String dirPath) {
		List<String> list = new ArrayList<String>();
		File dir = new File(dirPath);
		if (dir.exists()) {
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (File f : files) {
					list.add(f.getName());
				}
			}
		}
		return list;
	}

	/**
	 * JSPファイル作成
	 *
	 * @param data
	 * @return
	 */
	public String createJSP(String fileName, String data, String folder, Boolean header) {
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			if (header) {
				// JSPヘッダ取得
				Path inPath = Paths.get(context.getRealPath(""), "WEB-INF", "views", "header.jsp");
				FileInputStream fis = new FileInputStream(inPath.toString());
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
			}

			// 出力ファイルパス取得
			if (fileName == null || StringUtils.isBlank(fileName)) {
				fileName = timeUtil.getNowAtyyyyMMddHHmmss() + ".jsp";
			}
			Path outPath = Paths.get(context.getRealPath(""), "WEB-INF", "views", folder, fileName);

			// 出力
			pw = new PrintWriter(outPath.toString(), "UTF-8");

			if (header) {
				String str;
				while ((str = br.readLine()) != null) {
					pw.println(str);
				}
			}

			pw.println(data);

			return fileName;

		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("JSP Create Error", e);
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
				throw new WebException("JSP Create Error", e2);
			}
		}
	}

	/**
	 * CSSファイル作成
	 *
	 * @param data
	 * @return
	 */
	public String createCSS(String fileName, String data, String folder, Boolean header) {
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			if (header) {
				// CSSヘッダ取得
				Path inPath = Paths.get(context.getRealPath(""), "css", "header.css");
				FileInputStream fis = new FileInputStream(inPath.toString());
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
			}

			// 出力ファイルパス取得
			if (fileName == null || StringUtils.isBlank(fileName)) {
				fileName = timeUtil.getNowAtyyyyMMddHHmmss() + ".css";
			}
			Path outPath = Paths.get(context.getRealPath(""), "css", folder, fileName);

			// 出力
			pw = new PrintWriter(outPath.toString(), "UTF-8");

			if (header) {
				String str;
				while ((str = br.readLine()) != null) {
					pw.println(str);
				}
			}

			pw.println(data);

			return fileName;

		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("CSS Create Error", e);
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
				throw new WebException("CSS Create Error", e2);
			}
		}
	}

	/**
	 * JSPファイル削除
	 * @param fileName
	 * @param folder
	 */
	public void deleteJSP(String fileName, String folder, boolean all) {
		try {
			if (all) {
				// 全削除
				Path path = Paths.get(context.getRealPath(""), "WEB-INF", "views", folder);
				File dir = new File(path.toString());
				if (dir.exists()) {
					File[] files = dir.listFiles();
					if (files != null && files.length > 0) {
						for (File f : files) {
							if (f.exists() && f.isFile()) {
								f.delete();
							}
						}
					}
				}
			} else {
				// 単体削除
				Path path = Paths.get(context.getRealPath(""), "WEB-INF", "views", folder, fileName);
				File file = new File(path.toString());
				if (file.exists() && file.isFile()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("JSPファイル削除エラー", e);
		}
	}

	/**
	 * CSSファイル削除
	 * @param fileName
	 * @param folder
	 */
	public void deleteCSS(String fileName, String folder, boolean all) {
		try {
			if (all) {
				// 全削除
				Path path = Paths.get(context.getRealPath(""), "css", folder);
				File dir = new File(path.toString());
				if (dir.exists()) {
					File[] files = dir.listFiles();
					if (files != null && files.length > 0) {
						for (File f : files) {
							if (f.exists() && f.isFile()) {
								f.delete();
							}
						}
					}
				}

			} else {
				// 単体削除
				Path path = Paths.get(context.getRealPath(""), "css", folder, fileName);
				File file = new File(path.toString());
				if (file.exists() && file.isFile()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("CSSファイル削除エラー", e);
		}
	}

	/**
	 * JSPファイル読み込み
	 * @param fileName
	 * @param folder
	 * @return
	 */
	public String readJSP(String fileName, String folder) {
		Path path = Paths.get(context.getRealPath(""), "WEB-INF", "views", folder, fileName);
		return readFile(path.toString());
	}

	/**
	 * CSSファイル読み込み
	 * @param fileName
	 * @param folder
	 * @return
	 */
	public String readCSS(String fileName, String folder) {
		Path path = Paths.get(context.getRealPath(""), "css", folder, fileName);
		return readFile(path.toString());
	}

	/**
	 * ファイル読み込み
	 * @param filePath
	 * @return
	 */
	public String readFile(String filePath) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

				String str = br.readLine();
				while (str != null) {
					sb.append(str + System.getProperty("line.separator"));
					str = br.readLine();
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("File Read Error", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new WebException("File Read Error", e2);
			}
		}
	}

}
