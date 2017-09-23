<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@page import="java.io.*"%>
<%-- <%@page import = "java.io.BufferedInputStream" %>
<%@page import = "java.io.BufferedOutputStream" %>
<%@page import = "java.io.FileInputStream" %>
<%@page import = "java.io.File" %> 

<%
	String fileName = request.getParameter("file"); 
	
	String realPath = this.getServletContext().getRealPath("/boardUpload");
	System.out.println("realPath"+realPath);
	
	File file = new File(realPath+"\\"+ fileName);
	
	System.out.println(file);
	
	response.setContentType("application/octet-stream");	
	response.setHeader("Content-Disposition", "attachment;filename= \"" + fileName + "\";");	
	FileInputStream fileInputStream = new FileInputStream(file);
	
	ServletOutputStream servletOutputStream = response.getOutputStream();
	
	byte b[] = new byte[1024];
	int data = 0;
	
	while((data = (fileInputStream.read(b, 0, b.length))) != -1)
	{
		servletOutputStream.write(b, 0, data);
	}
	
	servletOutputStream.flush();
	servletOutputStream.close();
	fileInputStream.close();
	
%> --%>
    
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
		String path = request.getParameter("path");
		String originalFileName = request.getParameter("originalFileName");
		
		
		String saveFolder = "/apprUpload/" + path;

		fileUrl = request.getSession().getServletContext().getRealPath(saveFolder);
		System.out.println(fileUrl);
		
		
		originalFileName = java.net.URLEncoder.encode(originalFileName,"UTF-8");
		originalFileName = originalFileName.replaceAll("\\+", "%20" );
		
		path = java.net.URLEncoder.encode(path,"UTF-8");
		path = path.replaceAll("\\+", "%20" );

		response.setContentType("application/octet-stream;");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control", "no-store, no-cache,must-revalidate");
		response.setHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Content-Disposition", "attachment; filename =\"" + originalFileName + "\"");
		//request.getParameter("path")
		
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