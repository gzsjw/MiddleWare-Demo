package org.mw.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mw.demo.param.AttachmentFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class AttachmentManageCtrl {
	protected static final Logger LOG = LoggerFactory.getLogger(AttachmentManageCtrl.class);

	@RequestMapping(path = "/attachment/{systemid}/{objectid}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> uploadAttachment(@RequestParam("file") MultipartFile uploadFile,
			@PathVariable("systemid") String systemid, @PathVariable("objectid") String objectid,
			MultipartHttpServletRequest request) {
		AttachmentFile result = new AttachmentFile();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		try {
			String attachmentPathStr = request.getServletContext().getRealPath("/attachments");
			attachmentPathStr = attachmentPathStr + File.separator + systemid + File.separator + objectid;
			File attachmentPath = new File(attachmentPathStr);
			if (!attachmentPath.exists()) {
				attachmentPath.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = sdf.format(new Date());
			String originalname = uploadFile.getOriginalFilename();
			String subfix = "";
			if (originalname.lastIndexOf(".") >= 0) {
				subfix = originalname.substring(originalname.lastIndexOf(".") + 1);
			}
			if (subfix.length() > 0) {
				fileName = fileName + "." + subfix;
			}
			File file = new File(attachmentPathStr + File.separator + fileName);
			OutputStream ops = new BufferedOutputStream(new FileOutputStream(file));
			InputStream ips = uploadFile.getInputStream();
			FileCopyUtils.copy(ips, ops);

			result.setName(fileName);
			result.setSize(uploadFile.getSize());
			result.setUrl("/attachment/" + systemid + "/" + objectid + "/" + fileName);
		} catch (Exception e) {
			LOG.error("上传文件失败:" + e.getMessage(), e);
			return new ResponseEntity<String>("上传文件失败:" + e.getMessage(), headers, HttpStatus.NOT_ACCEPTABLE);
		}
		Gson gson = new Gson();
		ResponseEntity<String> entity = new ResponseEntity<String>(gson.toJson(result), headers, HttpStatus.OK);
		return entity;
	}

	@RequestMapping(value = {
			"/attachment/{systemid}/{objectid}/{filename:.+}" }, method = RequestMethod.GET)
	public void getAttachment(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("systemid") String systemid, @PathVariable("objectid") String objectid,
			@PathVariable String filename) throws IOException {
		String attachmentPathStr = request.getServletContext().getRealPath("/attachments");
		attachmentPathStr = attachmentPathStr + File.separator + systemid + File.separator + objectid;
		File attachmentFile = new File(attachmentPathStr + File.separator + filename);
		if (!attachmentFile.exists()) {
			response.setContentType("application/json; charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.setStatus(HttpStatus.NOT_FOUND.value());
			response.getWriter().write("找不到指定的文件.");
			return;
		}

		/*
		 * Set Content-Type in response(HttpServletResponse) with MIME type can
		 * be of application/pdf, text/html,application/xml,image/png, ..others
		 */
		String mimeType = URLConnection.guessContentTypeFromName(filename);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);

		/*
		 * Set Content-Disposition HEADER in response.
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + filename + "\""));
		// response.setHeader("Content-Disposition", String.format("attachment;
		// filename=\"%s\"", attachmentFile.getName()));

		response.setContentLength((int) attachmentFile.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(attachmentFile));

		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

	@RequestMapping(value = {
			"/attachment/{systemid}/{objectid}/{filename:.+}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteAttachment(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("systemid") String systemid, @PathVariable("objectid") String objectid,
			@PathVariable String filename) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		try {
			String attachmentPathStr = request.getServletContext().getRealPath("/attachments");
			attachmentPathStr = attachmentPathStr + File.separator + systemid + File.separator + objectid;
			File attachmentFile = new File(attachmentPathStr + File.separator + filename);
			if (!attachmentFile.exists()) {
				return new ResponseEntity<String>("找不到指定的文件.", headers, HttpStatus.NOT_FOUND);
			}
			if (!attachmentFile.delete()) {
				return new ResponseEntity<String>("指定的文件无法删除.", headers, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			LOG.error("删除文件失败:" + e.getMessage(), e);
			return new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.SERVICE_UNAVAILABLE);
		}
		return ResponseEntity.ok(null);
	}

	@RequestMapping(value = {
			"/attachment/{systemid}/{objectid}" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> listAttachment(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("systemid") String systemid, @PathVariable("objectid") String objectid) {
		List<AttachmentFile> files = new ArrayList<AttachmentFile>();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		try {
			String attachmentPathStr = request.getServletContext().getRealPath("/attachments");
			attachmentPathStr = attachmentPathStr + File.separator + systemid + File.separator + objectid;
			File attachmentPath = new File(attachmentPathStr);
			if (!attachmentPath.exists()) {
				return ResponseEntity.ok(null);
			}
			if (!attachmentPath.isDirectory()) {
				LOG.error("获取附件列表失败,目录结构异常,systemid:" + systemid + ",objectid:" + objectid + ".");
				return new ResponseEntity<String>("获取附件列表失败,目录结构异常.", headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			File[] attachmentFiles = attachmentPath.listFiles();
			if ((attachmentFiles != null) && (attachmentFiles.length > 0)) {
				for (File attachmentFile : attachmentFiles) {
					if (attachmentFile.isFile()) {
						AttachmentFile afile = new AttachmentFile();
						afile.setName(attachmentFile.getName());
						afile.setSize(attachmentFile.length());
						afile.setUrl("/attachment/" + systemid + "/" + objectid + "/" + attachmentFile.getName());
						files.add(afile);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("获取附件列表失败:" + e.getMessage(), e);
			return new ResponseEntity<String>("获取附件列表失败:" + e.getMessage(), headers, HttpStatus.SERVICE_UNAVAILABLE);
		}

		Gson gson = new Gson();
		Type type = new TypeToken<List<AttachmentFile>>() {
		}.getType();
		String result = gson.toJson(files, type);
		ResponseEntity<String> entity = new ResponseEntity<String>(result, headers, HttpStatus.OK);
		return entity;
	}
}
