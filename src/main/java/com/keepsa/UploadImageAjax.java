package com.keepsa;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

/**
 * 上传图片。
 */
@SuppressWarnings("restriction")
public class UploadImageAjax extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)

			throws ServletException, IOException {

		String image = req.getParameter("image");

		// 只允许jpg

		String header = "data:image/jpeg;base64,";

		if (image.indexOf(header) != 0) {

			resp.getWriter().print(wrapJSON(false));

			return;

		}

		// 去掉头部

		image = image.substring(header.length());

		// 写入磁盘

		boolean success = false;

		BASE64Decoder decoder = new BASE64Decoder();

		try {

			byte[] decodedBytes = decoder.decodeBuffer(image);

			String imgFilePath = "D://uploadimage.jpg";

			FileOutputStream out = new FileOutputStream(imgFilePath);

			out.write(decodedBytes);

			out.close();

			success = true;

		} catch (Exception e) {

			success = false;

			e.printStackTrace();

		}

		resp.getWriter().print(wrapJSON(success));

	}

	private String wrapJSON(boolean success) {

		return "{\"success\":" + success + "}";

	}
}