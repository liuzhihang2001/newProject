package com.yonyou.iuap.annex.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.iuap.annex.entity.Annex;
import com.yonyou.iuap.annex.service.AnnexService;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.ladingbill.entity.Ladingbill;
import com.yonyou.iuap.ladingbill.service.LadingbillService;
import com.yonyou.iuap.subladingbill.entity.Subladingbill;
import com.yonyou.iuap.subladingbill.service.SubladingbillService;
import com.yonyou.iuap.tranorder.entity.Tranorder;
import com.yonyou.iuap.tranorder.service.TranorderService;

@RestController
@RequestMapping(value = "/annexhandle")
public class AnnexHandleController extends BaseController{

	@Autowired
	private AnnexService annexService;
	
	@Autowired
	private TranorderService tranorderService;
	
	@Autowired
	private LadingbillService ladingbillService;
	
	@Autowired
	private SubladingbillService subladingbillService;
	
	//private static final String path = "D:\\tomcat8.5.32\\webapps\\wuliu\\WEB-INF\\upload";//76本地临时存储路径
	//private static final String path = "D:\\iuap-server\\workdir\\wuliu\\WEB-INF\\upload";//74本地临时存储路径
	private static final String path = System.getProperty("user.home");//用户个人目录 类似 C:\Users\Administrator
	
	
	/**   
     * 文件上传具体实现方法;   
     *    
     * @param file   
     * @return   
     */    
    @SuppressWarnings("resource")
	@RequestMapping("/upload")
    @ResponseBody    
    public Object handleFileUpload(@RequestParam("file1") MultipartFile file,String type,String idBill) {    
        if (!file.isEmpty()) {
            try {
                /*
                 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；   
                 * 3、文件格式; 4、文件大小的限制;   
                 */    
            	//为了避免文件重复覆盖，用时间戳重命名文件
            	String tmpName = file.getOriginalFilename() + System.currentTimeMillis();
            	
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(path + File.separator + tmpName)));
                
                if(file.getSize()>5242880) {
                	return buildGlobalError("文件上传大小最大支持5M！");
                }
                //本地指定路径写入文件
                out.write(file.getBytes());
                out.flush();
                out.close();
                
                String asFileName = null;
                if(type!=null&&type.equals("tranorder")) {
                	Tranorder tranorder = tranorderService.selectById(idBill).getTranorder();
                	asFileName = tranorder.getCode()+"_"+file.getOriginalFilename();
                }
                if(type!=null&&type.equals("changeladingbill")) {
                	Ladingbill ladingbill = ladingbillService.selectById(idBill).getLadingbill();
                	asFileName = ladingbill.getCode()+"_"+file.getOriginalFilename();
                }
                if(type!=null&&type.equals("changesubladingbill")) {
                	Subladingbill subladingbill = subladingbillService.selectById(idBill).getSubladingbill();
                	asFileName = subladingbill.getCode()+"_"+file.getOriginalFilename();
                }
                
                String result = annexService.uploadFile(type,tmpName,asFileName);//文件上传爱数
                if(result!=null&&result.equals("没有找到个人文档")) {
                	return buildGlobalError("上传失败！");
                }
                
                annexService.saveFull(idBill, file.getOriginalFilename(),asFileName,result);//相关信息保存数据库
                annexService.delFile(path, tmpName);//上传完成调用本地删除
                
            } catch (FileNotFoundException e) {    
                e.printStackTrace();
                return buildGlobalError("上传失败！");    
            } catch (IOException e) {    
                e.printStackTrace();    
                return buildGlobalError("上传失败！");
            } catch (Exception e) {
            	e.printStackTrace();
                return buildGlobalError("上传失败！");
            }
            
    
            return buildSuccess("上传成功！");
    
        } else {
            return buildGlobalError("上传失败！");
        }
    }
    
    
    /**
	 * 查询该单据下所有的附件
	 * @param idBill
	 * @return
	 */
    @RequestMapping(value = "/selectall", method = RequestMethod.GET)
	public Object selectAll(String idBill){
		
		if(idBill==null||idBill.equals("")) {
			return buildGlobalError("单据号不能为空！");
		}
		
		List<Annex> list = annexService.selectAll(idBill);
		
		return buildSuccess(list);
	}
    
    
    /**
	 * 查询子提单对应的运输委托单下所有的附件
	 * @param idBill
	 * @return
	 */
    @RequestMapping(value = "/selectannex4tranorder", method = RequestMethod.GET)
	public Object selectannex4tranorder(String idBill){
		
		if(idBill==null||idBill.equals("")) {
			return buildGlobalError("单据号不能为空！");
		}
		
		Subladingbill subladingbill = subladingbillService.selectById(idBill).getSubladingbill();
		
		List<Annex> list = annexService.selectAll(subladingbill.getIdTranorder());
		
		return buildSuccess(list);
	}
    
    
    /**   
     * 文件下载具体实现方法;   
     * @param file   
     * @return   
     */    
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public Object handleFileDownload(@RequestBody List<String> idList) {
    	if(idList==null||idList.size()==0) {
            return buildGlobalError("行id不能为空！");
        }
    	
    	List<String> downloadUrlList = new ArrayList<String>();
    	
    	for(String id : idList) {
    		Annex annex = annexService.selectById(id);
    		
    		if(annex.getDownloadurl()==null) {
    			try {
    				String downloadUrl = annexService.downloadFile(annex);
    				if(downloadUrl!=null) {
    					downloadUrlList.add(downloadUrl);
    				}else {
    					return buildGlobalError("获取下载链接异常！");
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    				return buildGlobalError("下载失败！");
    			}
    		}else {
    			downloadUrlList.add(annex.getDownloadurl());
    		}
    	}
    	return buildSuccess(downloadUrlList);
    }
    
    
    
    /**   
     * 文件删除具体实现方法;   
     * @param file   
     * @return   
     */
    @Transactional
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object handleFileDelete(@RequestBody List<String> idList) {
    	if(idList==null||idList.size()==0) {
            return buildGlobalError("行id不能为空！");
        }
    	ArrayList<Annex>list = new ArrayList<Annex>();
    	boolean flag = true;
    	for(String id : idList) {
    		Annex annex = annexService.selectById(id);
    		list.add(annex);
    		int result = annexService.deleteById(id);
    		if(result !=1 ) {
    			flag = false;
    			break;
    		}
    	}
    	
    	try {
			annexService.delASFile(list);
		} catch (IOException e) {
			e.printStackTrace();
			return buildGlobalError("删除失败！");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return buildGlobalError("删除失败！");
		}
    	
    	if(flag) {
    		return buildSuccess("删除成功！");
    	}
    	return buildGlobalError("删除失败！");
    }
    
    
    /**   
     * 附件保存具体实现方法;   
     * @param file
     * @return   
     */    
    @RequestMapping(value = "/batchsave", method = RequestMethod.POST)
    public Object handleFileSave(@RequestBody List<Annex> list) {
    	if(list==null||list.size()==0) {
            return buildGlobalError("记录不能为空！");
        }
    	
    	for(Annex annex:list) {
    		annexService.updateDesById(annex.getId(), annex.getDescription());
    	}
    	
		return buildSuccess("保存成功！");
    }
    
    
}
