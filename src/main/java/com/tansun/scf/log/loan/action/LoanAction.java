package com.tansun.scf.log.loan.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/user/loan")
@Controller
public class LoanAction extends BaseAppAction  {
	

	/**
	 * 
	 * @方法名称 upLoadLoanDetailExcel
	 * @功能描述 <pre>根据文件地址下载供应链文�?</pre>
	 * @作�??    yangfeng
	 * @创建时间 2017�?4�?27�? 下午2:04:20
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upLoadLoanDetailExcel")
	public ResponseEntity<byte[]> upLoadLoanDetailExcel() throws IOException {
	/*	Map<String, String> paramMap = this.getParameterMap(this.getRequest());//文件地址
		String filePath = paramMap.get("filePath");//文件地址
		if (filePath == null || "".equals("")){
			 throw new RuntimeException("文件地址为空!");
        }*/
		File file = new File("C:\\Users\\hqhangyu\\Desktop\\test.txt");
		 //处理显示中文文件名的问题
        String fileName=new String(file.getName().getBytes("utf-8"),"ISO-8859-1");
        //设置请求头内�?,告诉浏览器代�?下载窗口
        HttpHeaders headers = new HttpHeaders();  
        headers.setContentDispositionFormData("attachment",fileName );
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       /* headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"");  */
		/*headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);*/
/*		headers.setCacheControl("must-revalidate");
		headers.setCacheControl("post-check=0");
		headers.setCacheControl("pre-check=0");
		*/
	/*	headers.set("Content-Transfer-Encoding", "binary");
		headers.set("Pragma", "binary");*/
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);
	}
	
	
}
