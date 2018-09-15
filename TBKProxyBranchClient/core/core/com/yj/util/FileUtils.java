package com.yj.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将文件或是文件夹打包压缩成zip格式
 * 
 * @author ysc
 */
public class FileUtils {
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	private FileUtils() {
	};
	
	
	/**
	 * 把一个文件里的内容，修改一下 
	 * @param sourceFile 源文件路径（包含后缀）
	 * @param targetFile 要放的目标文件路径
	 * @param context 要替换的文本
	 * @param modifyLine 要更改的行数
	 * @param zwf  占位符
	 */
	 public static void xig(String sourceFile,String targetFile,String context,int modifyLine,String zwf){
	        BufferedReader in_ = null;
	        PrintWriter out = null ;
			try {
			  in_ = new BufferedReader(new FileReader(sourceFile));
		      out = new PrintWriter(new BufferedWriter(new FileWriter(targetFile)));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String line;
	        int count=1;
	        try {
				while((line=in_.readLine())!=null){
				    if(count==modifyLine){
				        out.println(line.replaceAll(zwf,context));  
				       //处理就是替换w成Z
				    }else {
				        out.println(line);
				    }
				      count ++ ;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				in_.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        out.flush();
	        out.close();
	    }
	
	
	
    
// 复制文件   
  public static void copyFile(File sourceFile,File targetFile)   
  throws IOException{  
          // 新建文件输入流并对它进行缓冲   
          FileInputStream input = new FileInputStream(sourceFile);  
          BufferedInputStream inBuff=new BufferedInputStream(input);  
    
          // 新建文件输出流并对它进行缓冲   
          FileOutputStream output = new FileOutputStream(targetFile);  
          BufferedOutputStream outBuff=new BufferedOutputStream(output);  
            
          // 缓冲数组   
          byte[] b = new byte[1024 * 5];  
          int len;  
          while ((len =inBuff.read(b)) != -1) {  
              outBuff.write(b, 0, len);  
          }  
          // 刷新此缓冲的输出流   
          outBuff.flush();  
          //关闭流   
          inBuff.close();  
          outBuff.close();  
          output.close();  
          input.close();  
      }  
  
  
	  public static void copyDirectiory(String sourceDir, String targetDir)  
	            throws IOException {  
	        // 新建目标目录   
	        (new File(targetDir)).mkdirs();  
	        // 获取源文件夹当前下的文件或目录   
	        File[] file = (new File(sourceDir)).listFiles();  
	        for (int i = 0; i < file.length; i++) {  
	            if (file[i].isFile()) {  
	                // 源文件   
	                File sourceFile=file[i];  
	                // 目标文件   
	               File targetFile=new   
	            		   	File(new File(targetDir).getAbsolutePath()  
	            		   				+File.separator+file[i].getName());  
	                copyFile(sourceFile,targetFile);  
	            }  
	            if (file[i].isDirectory()) {  
	                // 准备复制的源文件夹   
	                String dir1=sourceDir + "/" + file[i].getName();  
	                // 准备复制的目标文件夹   
	                String dir2=targetDir + "/"+ file[i].getName();  
	                copyDirectiory(dir1, dir2);  
	            }  
	        }  
	    }

	/**
	 * 创建ZIP文件
	 * 
	 * @param sourcePath
	 *            文件或文件夹路径
	 * @param zipPath
	 *            生成的zip文件存在路径（包括文件名）
	 */
	public static void createZip(String sourcePath, String zipPath) {
		try {
			ZipCompressorByAnt zca = new ZipCompressorByAnt(zipPath);
			zca.compressExe(sourcePath);
		} catch (Exception e) {
		}

	}

	private static void writeZip(File file, String parentPath,
			ZipOutputStream zos) {
		if (file.exists()) {
			if (file.isDirectory()) {// 处理文件夹
				parentPath += file.getName() + File.separator;
				File[] files = file.listFiles();
				for (File f : files) {
					writeZip(f, parentPath, zos);
				}
			} else {
				FileInputStream fis = null;
				DataInputStream dis = null;
				try {
					fis = new FileInputStream(file);
					dis = new DataInputStream(new BufferedInputStream(fis));
					ZipEntry ze = new ZipEntry(parentPath + file.getName());
					zos.putNextEntry(ze);
					byte[] content = new byte[1024];
					int len;
					while ((len = fis.read(content)) != -1) {
						zos.write(content, 0, len);
						zos.flush();
					}

				} catch (FileNotFoundException e) {
					log.error("创建ZIP文件失败", e);
				} catch (IOException e) {
					log.error("创建ZIP文件失败", e);
				} finally {
					try {
						if (dis != null) {
							dis.close();
						}
					} catch (IOException e) {
						log.error("创建ZIP文件失败", e);
					}
				}
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param urlString
	 * @param filename
	 * @param savePath
	 * @throws Exception
	 */
	public static void download(String urlString, String filename,
			String savePath) {
		try {
			if(null==urlString||urlString.isEmpty()){
				return;
			}
			if (!urlString.contains("http")) {
				urlString = "http:" + urlString;
			}
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			File sf = new File(savePath);
			if (!sf.exists()) {
				sf.mkdirs();
			}
			OutputStream os = new FileOutputStream(sf.getPath() + "/"
					+ filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			//log.info(urlString);
			//log.error(e.getMessage());
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param fileName
	 * @param savePath
	 * @param content
	 */
	public static void saveFile(String fileName, String savePath, String content) {
		PrintWriter pfp = null;
		try {
			File fp = new File(savePath + "/" + fileName);
			pfp = new PrintWriter(fp);
			pfp.print(content);
			pfp.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pfp.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	

	/**
	 * 保存文件
	 * @param fileName
	 * @param savePath
	 * @param content
	 */
	public static void saveFileUtf8(String fileName, String savePath, String content) {
		PrintWriter pfp = null;
		try {
			File fp = new File(savePath + "/" + fileName);
			pfp = new PrintWriter(fp, "utf-8");
			pfp.print(content);
			pfp.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pfp.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param fileName
	 * @param savePath
	 * @param content
	 */
	public static void saveFile2(String fileName, String savePath, List content) {
		try {
			File root = new File(savePath);
			if (!root.exists()) {
				root.mkdir();
			}
			File fp = new File(savePath + "/" + fileName);
			PrintWriter pfp = new PrintWriter(fp);
			for (Object o : content) {
				pfp.println(o);
				pfp.println("\n\t");
			}
			pfp.close();
		} catch (Exception e) {
		}
	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static void main(String[] args) {
//		String filepath = "D:/bots/wxBot/start1.sh";
//		String targetpath="D:/bots/wxBot/start.sh";
//		String context = "3123123";
//		
//			String targetFile = "D:/bots/wxBot";
//			String targetFilepath = targetFile + "/start11.sh";
//			String targetFilepath2 = targetFile + "/start.sh";
//			
//			FileUtils.xig(filepath, targetFilepath, "更改内容1",1,"%");//修改start.sh 中的 cd 
//			FileUtils.xig(targetFilepath, targetFilepath2, "更改内容2",2,"%");//修改start.sh 中的 python后面的 
//				String pythonFile = targetFile +"/proxy.py";
//				String pythonTargetFile = targetFile +"/proxy1.py";
//				FileUtils.xig(pythonFile, pythonTargetFile, "",1,"%");//修改start.sh 中的 python后面的 
			File file = new File("D://bots");
			file.delete();
		// ZipUtils.createZip("M:\\新建文件夹", "M:\\新建文件夹.zip");
		// ZipUtils.createZip("D:\\workspaces\\netbeans\\APDPlat\\APDPlat_Web\\target\\APDPlat_Web-2.2\\platform\\index.jsp",
		// "D:\\workspaces\\netbeans\\APDPlat\\APDPlat_Web\\target\\APDPlat_Web-2.2\\platform\\index.zip");
	}
}