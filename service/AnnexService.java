package com.yonyou.iuap.annex.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.annex.dao.AnnexMapper;
import com.yonyou.iuap.annex.entity.ASBeginUploadResult;
import com.yonyou.iuap.annex.entity.Annex;
import com.yonyou.iuap.annex.entity.AnnexExample;
import com.yonyou.iuap.annex.entity.Main;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.staff.entity.Staff;
import com.yonyou.iuap.staff.service.StaffService;
import com.yonyou.iuap.tranorder.entity.Tranorder;
import com.yonyou.iuap.tranorder.service.TranorderService;

@Service
public class AnnexService {
	
	@Autowired
	private AnnexMapper annexMapper;
	
	@Autowired
	private  StaffService  staffService;
	
	@Autowired
	private TranorderService tranorderService;
		
	//private static final String path = "D:\\tomcat8.5.32\\webapps\\wuliu\\WEB-INF\\upload\\";//76本地临时存储路径
	//private static final String path = "D:\\iuap-server\\workdir\\wuliu\\WEB-INF\\upload\\";//74本地临时存储路径
	private static final String path = System.getProperty("user.home");
	
	/**
	 * 上传爱数文件柜方法
	 * @param filename
	 * @throws Exception 
	 */
	public String uploadFile(String type,String filename,String asFileName) throws Exception {
	    String ip = "anyshare.chem-family.com";
	    String account = "huangw";
	    String password = "st13806290";

	    // 1.调用GetNew协议进行登录
	    Main main = new Main();
	    main.SetServerIP(ip);
	    
	    ASBeginUploadResult result = null;
	    
		main.LogIn(account, password);
		/*		String userDocGnsPath = "";
		// 2.获取入口文档
		ArrayList<ASEntryDoc> entryDocs = main.GetEntryDoc();
		
		// 3.在入口文档中找到个人文档, docType == "userdoc"
		//String userDocGnsPath = "gns://2BDEB1BB80844A6DA2F542750868D165/5F96C1D89D62414BB2862FBDB17E8FD9";
		for (int i = 0; i < entryDocs.size(); i++) {
			ASEntryDoc tmpObj = entryDocs.get(i);
		System.out.println(tmpObj.docName+"++++"+tmpObj.docType);
			if(tmpObj.docType.equals("customdoc")) {
				userDocGnsPath = tmpObj.docId;
			}
		}
		
		if(userDocGnsPath.equals("")) {
			return "没有找到个人文档";
		}*/
		
		// 4.0 读取要上传的文件信息
		String uploadPath = path +File.separator+ filename;
		File file = new File(uploadPath);
		long length = file.length();
		
		// 多级目录的gns
		String gns = null;
		// /物流管理中心/提单平台文档/提货依据
		String tranorderUploadgns = "gns://A37A64C0746C4848A2467AE2691FCAED/6F32DE750CAE47639625AEBB2C130278/AEFE4877DF094850B6F0A322E95306B4";
		// /物流管理中心/提单平台文档/变更单
		String changebillUploadgns = "gns://A37A64C0746C4848A2467AE2691FCAED/6F32DE750CAE47639625AEBB2C130278/975F8D31A5A845998155D2110933B446";
		
		if(("tranorder").equals(type)) {
			gns = tranorderUploadgns;
		}
		if((("changeladingbill").equals(type))||(("changesubladingbill").equals(type))) {
			gns = changebillUploadgns;
		}
		// 4.1 调用file osbeginupload进行上传文件
		result = main.OSBeginupload(gns, length, asFileName, 1);   // 这里如果有重名文件，抛出异常
		
		// 4.2 根据服务器返回的对象存储请求，向对象存储上传数据
		main.UploadPut(result.url, result.headers, uploadPath);
		
		// 4.3 调用file endupload结束上传
		main.OSEndUpload(result.docId, result.rev);
		
		
		return result.docId;
	}
	
	/**
	 * 从爱数文件柜下载文件
	 * @throws Exception 
	 */
	public String downloadFile(Annex annex) throws Exception {
		
		String url = null;
		
		String ip = "anyshare.chem-family.com";
	    String account = "huangw";
	    String password = "st13806290";

	    // 1.调用GetNew协议进行登录
	    Main main = new Main();
	    main.SetServerIP(ip);
	    
		main.LogIn(account, password);
		// 下载文档， 1. 根据文件路径获取对应gns
		String link = main.getLink(annex.getN1());
		if(link!=null&&!link.equals("")) {
			url = "http://anyshare.chem-family.com:9000/#/link/"+link;
			annex.setDownloadurl(url);
			annexMapper.updateByPrimaryKeySelective(annex);
		}
		
		return url;
	}
	
	
	/**
	 * 保存单条
	 * 
	 */
	@Transactional
	public void saveFull(String idBill,String fileName,String asFileName,String docId) {

		String userId = InvocationInfoProxy.getUserid();
		Staff staff = staffService.getStaffbyUserId(userId);
		String userName = staff.getChinese();
		
		String id = UUID.randomUUID().toString();
		Date commitTime = new Date();
		
		Annex annex = new Annex();
		annex.setId(id);
		annex.setIdBill(idBill);
		annex.setFilename(fileName);
		annex.setCreator(userName);
		annex.setCommittime(commitTime);
		annex.setN1(docId);
		annex.setN2(asFileName);
		
		annexMapper.insertSelective(annex);
	}
	
	
	/**
	 * 查询该单据下所有的附件
	 * @param idBill
	 * @return
	 */
	public List<Annex> selectAll(String idBill){
		AnnexExample example = new AnnexExample();
		example.createCriteria().andIdBillEqualTo(idBill);
		example.setOrderByClause("committime");
		List<Annex> list = annexMapper.selectByExample(example);
		return list;
	}
	
	
	/**
	 * 根据行id查询单条附件
	 * 
	 */
	public Annex selectById(String id){
		Annex annex = annexMapper.selectByPrimaryKey(id);
		return annex;
	}
	
	
	/**
	 * 根据行id查询单条附件
	 * 
	 */
	public int deleteById(String id){
		return annexMapper.deleteByPrimaryKey(id);
	}
	
	
	/**
	 * 根据行id更新附件的描述
	 * 
	 */
	public int updateDesById(String id,String des){
		Annex annex = this.selectById(id);
		annex.setDescription(des);
		return annexMapper.updateByPrimaryKeySelective(annex);
	}
	
	
	/**
	 * 上传完成以后调用删除本地
	 * 
	 */
	public boolean delFile(String path,String filename){
        File file = new File(path+"/"+filename);
        if(file.exists()&&file.isFile()) {
            return file.delete();
        }else {
        	return false;
        }
    }
	
	
	/**
	 * 从爱数文件柜删除文件
	 * @throws GeneralSecurityException 
	 * @throws IOException 
	 */
	public void delASFile(List<Annex>list) throws IOException, GeneralSecurityException {
		String ip = "anyshare.chem-family.com";
	    String account = "huangw";
	    String password = "st13806290";

	    // 1.调用GetNew协议进行登录
	    Main main = new Main();
	    main.SetServerIP(ip);
	    
		main.LogIn(account, password);
		
		for(Annex annex:list) {
			main.DeleteFile(annex.getN1());
		}
	}
	
	
	/**
	 * 查询  满足调教的单据下附件的数量
	 * @param idBill
	 * @return
	 */
	public Tranorder selectCountAnnex(String idBill){
		
		Tranorder tranorder = tranorderService.selectById(idBill).getTranorder();
		if(tranorder.getTypeLading()!=null&&((tranorder.getTypeLading().equals("ZTYS"))||(tranorder.getTypeLading().equals("ZTWY")))
    			&&(tranorder.getTypeLadingbill()!=null&&tranorder.getTypeLadingbill().equals("SC"))) {
			List<Annex> list = selectAll(idBill);
			if(list==null||list.size()==0) {
				tranorder.setN5(new BigDecimal(0));
			}else {
				tranorder.setN5(new BigDecimal(list.size()));
			}
		}
		return tranorder;
	}
	
	
}
