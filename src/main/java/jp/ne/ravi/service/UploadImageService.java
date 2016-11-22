package jp.ne.ravi.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.ravi.exception.WebException;
import jp.ne.ravi.utils.DebugUtil;
import jp.ne.ravi.utils.StringUtil;
import jp.ne.ravi.utils.TimeUtil;

@Scope("prototype")
@Service
public class UploadImageService {

	@Autowired
	private DebugUtil debugUtil;

	@Autowired
	private ServletContext context;

	@Autowired
	private TimeUtil timeUtil;

	@Autowired
	private StringUtil stringUtil;

	public void uploadImage(MultipartFile file, Model model, RedirectAttributes attributes) {
		try {
			if (file == null || file.isEmpty()) {
				attributes.addFlashAttribute("message", "ファイルが選択されていません");
				return;
			}
			Path path = Paths.get(context.getRealPath(""), "img", "upload",
					timeUtil.getNowAtyyyyMMddHHmmss() + "." + stringUtil.getExtension(file.getOriginalFilename()));
			debugUtil.outConsole(path.toString());
			file.transferTo(new File(path.toString()));

			attributes.addFlashAttribute("message", "画像アップロード完了");

		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("Upload Image Error", e);
		}
	}

	public void deleteImage(String src, RedirectAttributes attributes) {
		try {
			if (src == null && StringUtils.isBlank(src)) {
				attributes.addFlashAttribute("message", "ファイルが選択されていません");
				return;
			}
			String[] split = src.split("/");
			Path path = Paths.get(context.getRealPath(""), "img", "upload", split[split.length - 1]);
			debugUtil.outConsole(path.toString());
			File file = new File(path.toString());
			if (!file.exists()) {
				attributes.addFlashAttribute("message", "画像が見つかりません");
				return;
			}

			file.delete();
			attributes.addFlashAttribute("message", "画像を削除しました");

		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("Delete Image Error", e);
		}
	}

}
