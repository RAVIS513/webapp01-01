package jp.ne.ravi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.ravi.service.UploadImageService;
import jp.ne.ravi.utils.DebugUtil;

@Scope("prototype")
@Controller
public class UploadImageController {

	@Autowired
	private DebugUtil debugUtil;

	@Autowired
	private UploadImageService uploadImageService;

	@RequestMapping(value = "/uploadImage")
	public String uploadImage(@RequestParam MultipartFile file, Model model, RedirectAttributes attributes) {
		debugUtil.outConsole("uploadImage");
		uploadImageService.uploadImage(file, model, attributes);
		return "redirect:/manager/create";
	}

	@RequestMapping(value = "/deleteImage")
	public String deleteImage(@RequestParam String src, Model model, RedirectAttributes attributes) {
		debugUtil.outConsole("deleteImage");
		uploadImageService.deleteImage(src, attributes);
		return "redirect:/manager/create";
	}
}
