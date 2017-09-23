<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
		request.setCharacterEncoding("utf-8");
		
		String fileUrl = "";
		//String path = request.getParameter("path");
		String path = (String) request.getAttribute("path");
		String originalFileName = (String) request.getAttribute("originalFileName");
		
		//System.out.println("originalFileName: "+originalFileName);
		//System.out.println("path: "+path);
		
		String saveFolder = "/boardUpload/" + path;

		fileUrl = request.getSession().getServletContext().getRealPath(saveFolder);
		System.out.println(fileUrl);
		
		originalFileName = java.net.URLEncoder.encode(originalFileName,"UTF-8"); // 인코딩 처리
		originalFileName = originalFileName.replaceAll("\\+", "%20");
		
		path = java.net.URLEncoder.encode(path,"UTF-8"); // 인코딩 처리
		path = path.replaceAll("\\+", "%20");
		
		
		
		
		
		response.setContentType("application/download;charset=UTF-8"); // 다운로드 할 파일의 인코딩 설정		
		response.setContentType("application/octet-stream;");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control", "no-store, no-cache,must-revalidate");
		response.setHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache"); // 캐시 여부
		response.setHeader("Content-Disposition", "attachment; filename =\"" + originalFileName + "\"");
		
		out.clear();
		out = pageContext.pushBody();
		
		java.io.BufferedOutputStream outs 
				= new java.io.BufferedOutputStream(response.getOutputStream());

		byte fileContens[] = new byte[8192];
		int read = 0;
		java.io.BufferedInputStream fin = null;
		try {
			fin = new java.io.BufferedInputStream(new java.io.FileInputStream(fileUrl));

			while ((read = fin.read(fileContens)) != -1) {
				outs.write(fileContens, 0, read);
				outs.flush();
			}
		} catch (Exception e) {
			;
		} finally {
			if (outs != null)
				outs.close();
			if (fin != null)
				fin.close();
		}
	%>
    
</body>
</html>