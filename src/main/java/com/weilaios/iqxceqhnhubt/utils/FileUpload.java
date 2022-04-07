package com.weilaios.iqxceqhnhubt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class FileUpload {

	public  String openAccountFileDir;

	protected static final Logger log = LoggerFactory
			.getLogger(FileUpload.class);

	public   String getOpenAccountFileDir() {
		return openAccountFileDir;
	}

	public   void setOpenAccountFileDir(String openAccountFileDir) {
		this.openAccountFileDir = openAccountFileDir;
	}

	public static String fileUpload(String destDir, MultipartFile file)
			throws IllegalStateException, IOException {
		log.info("------------" + file.getOriginalFilename() + "-------------");
		String filePath = destDir + file.getOriginalFilename();
		File destFile = new File(filePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		log.info("-------------ready transfer file---------");
		file.transferTo(new File(filePath));
		log.info("-------------transfer file success---------");
		return filePath;
	}

	/**
	 * @Description 文件上传之后文件名前带时间戳以防被覆盖
	 * @author 
	 * @Date
	 * @param destDir
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String fileUploadWithTimestamp(String destDir,
			MultipartFile file,String fileName) throws IllegalStateException, IOException {
		log.info("------------" + file.getOriginalFilename() + "-------------");
		String originalFilename = file.getOriginalFilename();
		String[] s = originalFilename.split("\\.");
		String filePath = fileName + "-" + DateUtils.getDateString(DateUtils.currentTime(),"yyyyMMddHHmmss")+"."+s[s.length-1];
		
		return filePath;
	}

	public static void deleteFile(String destDir, String imageid,
			String middlePath) {
		log.info("删除文件-------------------" + imageid);
		String filePath = destDir + File.separator + imageid;
		File destFile = new File(filePath);
		destFile.delete();
	}
	
	/**
	 * 
	 * 删除某文件夹下所有文件
	 * @author
	 * @param destDir
	 */
	public static void deleteFile(File file){
    	     if(file.isDirectory()){
                    File[] files = file.listFiles();
                    for(int i=0; i<files.length; i++){
                         deleteFile(files[i]);
                    }
    	     }
    	      file.delete();
    	      log.info("删除文件夹-------------------" + file.getAbsolutePath()+"下所有文件");
	   }

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] imageUpload <br>
	 * [描述] TODO(这里用一句话描述这个方法的作用) <br>
	 * [参数] String destDir, MultipartFile file,String middlePath, String type
	 * 值：product/enterprise<br>
	 * [返回] String <br>
	 * [作者] zj <br>
	 * [时间] 2015-1-6 下午1:46:32 <br>
	 ********************************************************* .<br>
	 */
	public static String imageUpload(String destDir, MultipartFile file,
			String middlePath, String type, String file_ture_name)
			throws IllegalStateException, IOException {
		log.info("------------" + file.getOriginalFilename() + "-------------");
		String pic_type = file.getContentType();
		if (pic_type.equals("image/jpeg")) {
			file_ture_name = file_ture_name.concat(".jpg");
		} else if (pic_type.equals("image/pjpeg")) {
            file_ture_name = file_ture_name.concat(".jpg");
        } else  if (pic_type.equals("image/x-png")) {
            file_ture_name = file_ture_name.concat(".png");
        } else  if (pic_type.equals("image/png")) {
			file_ture_name = file_ture_name.concat(".png");
		} else if (pic_type.equals("image/bmp")) {
			file_ture_name = file_ture_name.concat(".bmp");
		} else if (pic_type.equals("image/gif")) {
			file_ture_name = file_ture_name.concat(".gif");
		} else {
			file_ture_name = file_ture_name.concat(".jpg");
		}
		if (null != type && !type.equals("")) {
			file_ture_name = type.concat("-" + file_ture_name);
		}

		if (null == middlePath) {
			middlePath = "";
		}
		String filePath = destDir + middlePath + File.separator
				+ file_ture_name;
		File destFile = new File(filePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		log.info("-------------ready transfer file---------");
		file.transferTo(new File(filePath));
		log.info("-------------transfer file success---------");
		// // 图片添加水印
		// WaterImage.pressImage(destDir, null, file_ture_name);
		return file_ture_name;
	}

	/**
	 * 
	 * TODO 图片文件重命名
	 * @author 
	 * @param file
	 * @param file_ture_name
	 * @return
	 */
	public static String fileReName(MultipartFile file,String file_ture_name){
	    String pic_type = file.getContentType();
		log.info("-------------pic_type---------"+pic_type);
        if (pic_type.equals("image/jpeg")) {
            file_ture_name = file_ture_name.concat(".jpg");
        } else if (pic_type.equals("image/pjpeg")) {
            file_ture_name = file_ture_name.concat(".jpg");
        } else  if (pic_type.equals("image/x-png")) {
            file_ture_name = file_ture_name.concat(".png");
        } else  if (pic_type.equals("image/png")) {
            file_ture_name = file_ture_name.concat(".png");
        } else if (pic_type.equals("image/bmp")) {
            file_ture_name = file_ture_name.concat(".bmp");
        } else if (pic_type.equals("image/gif")) {
            file_ture_name = file_ture_name.concat(".gif");
        } else if (pic_type.equals("application/pdf")) {
            file_ture_name = file_ture_name.concat(".pdf");
        } else if (pic_type.equals("application/msword")) {
            file_ture_name = file_ture_name.concat(".doc");//cws 20171109
        } else if (pic_type.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            file_ture_name = file_ture_name.concat(".docx");//cws 20171109
        } else if (pic_type.equals("application/vnd.ms-excel")) {
			file_ture_name = file_ture_name.concat(".xls");
		} else if (pic_type.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			file_ture_name = file_ture_name.concat(".xlsx");
		} else {
			file_ture_name = file_ture_name.concat(".jpg");
		}
        return file_ture_name;
	}
	
	/**
	 * 文件重命名，只传contentType
	 * @author 
	 * @param pic_type
	 * @param file_ture_name
	 * @return
	 */
	public static String fileReNameByContentType(String pic_type,String file_ture_name){
        if (pic_type.equals("image/jpeg")) {
            file_ture_name = file_ture_name.concat(".jpg");
        } else if (pic_type.equals("image/pjpeg")) {
            file_ture_name = file_ture_name.concat(".jpg");
        } else  if (pic_type.equals("image/x-png")) {
            file_ture_name = file_ture_name.concat(".png");
        } else  if (pic_type.equals("image/png")) {
            file_ture_name = file_ture_name.concat(".png");
        } else if (pic_type.equals("image/bmp")) {
            file_ture_name = file_ture_name.concat(".bmp");
        } else if (pic_type.equals("image/gif")) {
            file_ture_name = file_ture_name.concat(".gif");
        } else if (pic_type.equals("application/pdf")) {
            file_ture_name = file_ture_name.concat(".pdf");
        } else if (pic_type.equals("application/msword")) {
            file_ture_name = file_ture_name.concat(".doc");//cws 20171109
        } else if (pic_type.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            file_ture_name = file_ture_name.concat(".docx");//cws 20171109
        } else if (pic_type.equals("application/vnd.ms-excel")) {
			file_ture_name = file_ture_name.concat(".xls");
		} else if (pic_type.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			file_ture_name = file_ture_name.concat(".xlsx");
		} else if (pic_type.equals("text/xml")) {
			file_ture_name = file_ture_name.concat(".xlsx");
		} else {
			file_ture_name = file_ture_name.concat(".jpg");
		}
        return file_ture_name;
    }
	
	/**
	 * 判断上传文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static Boolean checkFileTypeExcel(String fileName) {
		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
			return false;
		}
		return true;
	}
}
