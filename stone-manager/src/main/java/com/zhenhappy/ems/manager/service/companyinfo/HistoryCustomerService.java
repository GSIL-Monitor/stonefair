package com.zhenhappy.ems.manager.service.companyinfo;

import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.ems.entity.managerrole.TUserRole;
import com.zhenhappy.ems.manager.dao.companyinfo.HistoryCustomerInfoDao;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.dto.companyinfo.*;
import com.zhenhappy.ems.manager.entity.companyinfo.THistoryCustomer;
import com.zhenhappy.ems.manager.service.TagManagerService;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.service.CountryProvinceService;
import com.zhenhappy.ems.service.ExhibitorService;
import com.zhenhappy.util.Page;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by wangxd on 2016-05-23.
 */
@Service
public class HistoryCustomerService extends ExhibitorService {

	private static Logger log = Logger.getLogger(HistoryCustomerService.class);

	@Autowired
	private HistoryCustomerInfoDao historyCustomerInfoDao;
	@Autowired
	private CountryProvinceService countryProvinceService;

	List<THistoryCustomer> isExistCustomerList = new ArrayList<THistoryCustomer>();
	List<THistoryCustomer> willImportCustomerList = new ArrayList<THistoryCustomer>();

	/**
	 * 导出国内历史客商数据
	 * @param customers
	 * @return
	 */
	public List<ExportHistoryCustomerInfo> exportHistoryInlandCustomer(List<THistoryCustomer> customers) {
		List<ExportHistoryCustomerInfo> exportCustomerInfos = new ArrayList<ExportHistoryCustomerInfo>();
		if(customers.size() > 0){
			for(THistoryCustomer customer:customers){
				ExportHistoryCustomerInfo exportCustomerInfo = new ExportHistoryCustomerInfo();
				exportCustomerInfo.setCateory(customer.getCateory());
				exportCustomerInfo.setCompany(customer.getCompany());
				exportCustomerInfo.setAddress(customer.getAddress());
				exportCustomerInfo.setContact(customer.getContact());
				exportCustomerInfo.setPosition(customer.getPosition());
				exportCustomerInfo.setTelphone(customer.getTelphone());
				exportCustomerInfo.setEmail(customer.getEmail());
				exportCustomerInfo.setFixtelphone(customer.getFixtelphone());
				exportCustomerInfo.setFax(customer.getFax());
				exportCustomerInfo.setWebsite(customer.getWebsite());
				exportCustomerInfo.setRemark(customer.getRemark());
				BeanUtils.copyProperties(customer, exportCustomerInfo);
				exportCustomerInfos.add(exportCustomerInfo);
			}
		}
		return exportCustomerInfos;
	}

	/**
	 * 导出国外历史客商数据
	 * @param customers
	 * @return
	 */
	public List<ExportHistoryCustomerInfo> exportHistoryForeignCustomer(List<THistoryCustomer> customers) {
		List<ExportHistoryCustomerInfo> exportCustomerInfos = new ArrayList<ExportHistoryCustomerInfo>();
		if(customers.size() > 0){
			for(THistoryCustomer customer:customers){
				ExportHistoryCustomerInfo exportCustomerInfo = new ExportHistoryCustomerInfo();
				WCountry country = countryProvinceService.loadCountryById(customer.getCountry());
				exportCustomerInfo.setCountryEx(country.getChineseName() + "\n" + country.getCountryValue());
				exportCustomerInfo.setCompany(customer.getCompany());
				String nameValue = customer.getContact();
				String[] nameArray = null;
				if(StringUtils.isNotEmpty(nameValue)){
					nameArray = nameValue.split("&");
				}
				String positionValue = customer.getPosition();
				String[] positionArray = null;
				if(StringUtils.isNotEmpty(positionValue)){
					positionArray = positionValue.split("&");
				}
				StringBuffer contactResult = new StringBuffer();
				if(nameArray != null){
					contactResult.append("ATTN:");
					for(int i=0;i<nameArray.length;i++){
						contactResult.append(nameArray[i].trim());
						if(positionArray != null && !positionArray[i].trim().equals("")){
							contactResult.append(", " + positionArray[i].trim());
						}
						contactResult.append(" & ");
					}
				}else {
					if(positionArray != null) {
						contactResult.append("ATTN:");
						for(int k=0;k<positionArray.length;k++){
							contactResult.append(positionArray[k].trim() + " & ");
						}
					}
				}
				String contactValue = "";
				if(StringUtils.isNotEmpty(contactResult.toString())){
					int index = contactResult.toString().lastIndexOf("&");
					contactValue = contactResult.toString().substring(0,index);
				}
				exportCustomerInfo.setContactEx(contactValue);
				exportCustomerInfo.setAddress(customer.getAddress());
				if(StringUtil.isNotEmpty(customer.getEmail())) {
					exportCustomerInfo.setEmail(customer.getEmail().replaceAll(",","\n"));
				}else {
					exportCustomerInfo.setEmail("");
				}
				exportCustomerInfo.setWebsite(customer.getWebsite());
				exportCustomerInfo.setBackupaddress(customer.getBackupaddress());
				exportCustomerInfo.setRemark(customer.getRemark());
				exportCustomerInfo.setTelphone(customer.getTelphone());
				//BeanUtils.copyProperties(customer, exportCustomerInfo);
				exportCustomerInfos.add(exportCustomerInfo);
			}
		}
		return exportCustomerInfos;
	}

	private String getCellValue(org.apache.poi.ss.usermodel.Cell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			DecimalFormat df = new DecimalFormat("#");
			//return String.valueOf(hssfCell.getNumericCellValue());
			return String.valueOf(df.format(hssfCell.getNumericCellValue()));
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * 导入客商信息
	 * @param importFile
	 * @param flag 1表示xlsx格式，即国内；2表示xls即国外
	 * @param isForceIncludeFlag 1表示强制导；0表示要去重判断
	 * @return
	 * @throws IOException
	 */
	public ImportHistoryCustomerResponse importHistoryCustomer(File importFile, TUserInfo userInfo, Integer flag, Integer isForceIncludeFlag) {
		if(flag == 1){
			return importInlandHistoryCustomer(importFile, userInfo, isForceIncludeFlag);
		}else{
			return importForeignHistoryCustomer(importFile, userInfo, isForceIncludeFlag);
		}
	}

	/**
	 * 导入门襟系统数据
	 * @param importFile
	 * @return
	 * @throws IOException
	 */
	public ImportHistoryCustomerResponse importPlacketSystemData(File importFile, TUserInfo userInfo) {
		//这里根据门襟系统的表结构进行处理，只导入石材展期间的数据
		return importInlandHistoryCustomer(importFile, userInfo, 0);
	}

	public ImportHistoryCustomerResponse importInlandHistoryCustomer(File importFile, TUserInfo userInfo, Integer isForceIncludeFlag) {
		Integer count = 0;
		Integer willImportId = 1;
		ImportHistoryCustomerResponse importHistoryCustomerResponse = new ImportHistoryCustomerResponse();
		isExistCustomerList = new ArrayList<THistoryCustomer>();
		willImportCustomerList = new ArrayList<THistoryCustomer>();
		List resultArray = new ArrayList();
		boolean isExistCompanyNameOrAddress = false;
		boolean isExistCompanyNameOrAddressOrEmail = false;
		try {
			InputStream is = new FileInputStream(importFile);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// 循环工作表Sheet
			/*for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			}*/
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			List<THistoryCustomer> tHistoryCustomerListTemp = new ArrayList<THistoryCustomer>();
			if (xssfSheet != null) {
				//总行数
				int value = xssfSheet.getLastRowNum();
				for (int beginIndex = 1;beginIndex <= value; beginIndex++) {
					isExistCompanyNameOrAddress = false;
					isExistCompanyNameOrAddressOrEmail = false;

					Row xssfRow = xssfSheet.getRow(beginIndex);
					if (xssfRow != null && !(xssfRow.equals(""))) {
						//公司
						org.apache.poi.ss.usermodel.Cell companyCell = xssfRow.getCell(1);
						//地址  只有一个
						org.apache.poi.ss.usermodel.Cell addressShell = xssfRow.getCell(2);
						//联系点人
						org.apache.poi.ss.usermodel.Cell contactShell = xssfRow.getCell(3);
						String companyStr = "";
						String addressStr = "";
						if(companyCell != null){
							companyStr = companyCell.getStringCellValue().trim();
						}
						if(addressShell != null){
							addressStr = addressShell.getStringCellValue().trim();
						}

						org.apache.poi.ss.usermodel.Cell emailShell = xssfRow.getCell(6);
						//isForceIncludeFlag=1表示需要到数据库里判断是否有重复
						if(isForceIncludeFlag != 1){
							//邮箱
							if(emailShell != null && emailShell.getStringCellValue() != null){
								String emailValue = emailShell.getStringCellValue().trim();
								if(StringUtil.isNotEmpty(emailValue)){
									String emailContent = emailValue.replaceAll(" ", "");
									if(StringUtil.isNotEmpty(emailContent)){
										String[] emailList = emailContent.split("\n");
										//如果isExistCompanyName为false,则说明公司名是没有重复，那么变判断地址是否有重复
										int length = emailList.length;
										if(!isExistCompanyNameOrAddress && length>0) {
											for(int emailIndex=0;emailIndex<length;emailIndex++){
												String emailTemp = emailList[emailIndex];
											/*tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddressAndEmail(companyStr, addressStr, emailTemp);
											if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
												isExistCompanyNameOrAddressOrEmail = true;
												break;
											}*/
											}
										}
									}else {
										tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddress(companyStr, addressStr);
										if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
											isExistCompanyNameOrAddress = true;
										}
									}
								}else {
									tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddress(companyStr, addressStr);
									if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
										isExistCompanyNameOrAddress = true;
									}
								}
							}else{
								tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddress(companyStr, addressStr);
								if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
									isExistCompanyNameOrAddress = true;
								}
							}
						}

						THistoryCustomer tHistoryCustomer = new THistoryCustomer();
						tHistoryCustomer.setId(willImportId++);
						tHistoryCustomer.setCountry(44);
						if(userInfo != null){
							if(userInfo.getOwnerId() != null){
								tHistoryCustomer.setOwner(userInfo.getOwnerId());
							} else {
								tHistoryCustomer.setOwner(0);
							}
						}else{
							tHistoryCustomer.setOwner(0);
						}
						tHistoryCustomer.setCreatetime(new Date());
						tHistoryCustomer.setUpdatetime(new Date());
						tHistoryCustomer.setIsDelete(0);
						if(userInfo != null){
							tHistoryCustomer.setUpdateowner(userInfo.getName());
						}

						//获取列数
						int size = xssfRow.getLastCellNum();
						tHistoryCustomer.setAddress("");
						tHistoryCustomer.setCompany("");
						for (int i = 0; i < size; i++) {
							org.apache.poi.ss.usermodel.Cell cell = xssfRow.getCell(i);
							if(cell != null){
								String cellValue = getCellValue(cell).trim();
								if(StringUtil.isNotEmpty(cellValue)){
									cellValue = cellValue.replaceAll(" ", "");
								}else{
									cellValue = "";
								}
								switch (i) {
									case 0:	//类别
										tHistoryCustomer.setCateory(cellValue);
										break;
									case 1:	//公司名
										tHistoryCustomer.setCompany(cellValue);
										break;
									case 2:	//地址
										tHistoryCustomer.setAddress(cellValue);
										break;
									case 3:	//联系人
										tHistoryCustomer.setContact(cellValue);
										break;
									case 4:	//职位
										tHistoryCustomer.setPosition(cellValue);
										break;
									case 5:	//手机
										tHistoryCustomer.setTelphone(cellValue);
										break;
									case 6:	//邮箱
										//String[] email = cell.getContents().trim().replaceAll(" ", "").split("\n");
										tHistoryCustomer.setEmail(cellValue);
										break;
									case 7:	//座机
										tHistoryCustomer.setFixtelphone(cellValue);
										break;
									case 8:	//传真
										tHistoryCustomer.setFax(cellValue);
										break;
									case 9:	//网址
										tHistoryCustomer.setWebsite(cellValue);
										break;
									case 10://备注
										tHistoryCustomer.setRemark(cellValue);
										break;
									default:
										break;
								}
							}
						}

						if(isExistCompanyNameOrAddress || isExistCompanyNameOrAddressOrEmail) {
							isExistCustomerList.addAll(tHistoryCustomerListTemp);
							willImportCustomerList.add(tHistoryCustomer);
						}else{
							historyCustomerInfoDao.create(tHistoryCustomer);
							count ++;
						}
					}
				}
			}

			if(willImportCustomerList != null && willImportCustomerList.size()>0){
				StringBuffer resultBuffer = new StringBuffer();
				resultBuffer.append("共导入：" + count + "条数据，其中有" + willImportCustomerList.size() + "条记录，可能存在重复！");
				resultArray.add(resultBuffer.toString());
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, Boolean.TRUE);
				JSONArray resultJson = JSONArray.fromObject(resultArray);
				importHistoryCustomerResponse.setResult(resultJson.toString());
				JSONArray isExistDataJsonArray = JSONArray.fromObject(isExistCustomerList);
				JSONArray willImportDataJsonArray = JSONArray.fromObject(willImportCustomerList);
				importHistoryCustomerResponse.setIsExistData(isExistDataJsonArray.toString());
				importHistoryCustomerResponse.setWillImportData(willImportDataJsonArray.toString());
				importHistoryCustomerResponse.setResultCode(1);
			}else{
				StringBuffer resultBuffer = new StringBuffer();
				resultBuffer.append("共导入：" + count + "条数据");
				resultArray.add(resultBuffer.toString());
				JSONArray resultJson = JSONArray.fromObject(resultArray);
				importHistoryCustomerResponse.setResult(resultJson.toString());
				importHistoryCustomerResponse.setIsExistData("");
				importHistoryCustomerResponse.setWillImportData("");
				importHistoryCustomerResponse.setResultCode(0);
			}
			xssfWorkbook.close();
		} catch (Exception e) {
			System.out.println(e);
			importHistoryCustomerResponse.setResultCode(-1);
			e.printStackTrace();
		}
		return importHistoryCustomerResponse;
	}

	public ImportHistoryCustomerResponse importForeignHistoryCustomer(File importFile, TUserInfo userInfo, Integer isForceIncludeFlag) {
		Integer count = 0;
		Integer willImportId = 1;
		ImportHistoryCustomerResponse report = new ImportHistoryCustomerResponse();
		isExistCustomerList = new ArrayList<THistoryCustomer>();
		willImportCustomerList = new ArrayList<THistoryCustomer>();
		List resultArray = new ArrayList();
		boolean isExistCompanyNameOrAddress = false;
		boolean isExistCompanyNameOrAddressOrEmail = false;
		try {
			Workbook book = Workbook.getWorkbook(importFile);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			List<THistoryCustomer> tHistoryCustomerListTemp = new ArrayList<THistoryCustomer>();

			// 得到单元格
			//判断公司名，地址和邮箱，若有重复的，则进行提示，让操作员自已判断
			for (int j = 1; j < sheet.getRows(); j++) {
				isExistCompanyNameOrAddress = false;
				isExistCompanyNameOrAddressOrEmail = false;

				StringBuffer contactBuffer = new StringBuffer();
				StringBuffer positionBuffer = new StringBuffer();
				//公司名 只有一个值
				//若公司名有重复，则提示，让导入人员自已手动判断是合并还是保留
				Cell companyShell = sheet.getCell(1,j);
				//地址  只有一个
				Cell addressShell = sheet.getCell(3,j);
				String companyStr = "";
				String addressStr = "";
				if(companyShell != null){
					companyStr = companyShell.getContents().trim();
				}
				if(addressShell != null){
					addressStr = addressShell.getContents().trim();
				}

				//联系人
				Cell contactShell = sheet.getCell(2,j);
				String contact = contactShell.getContents().trim();
				if(StringUtils.isNotEmpty(contact)){
					String[] contactStr = contact.split(":");
					if(contactStr.length>1){
						String contactValue = contactStr[1];
						//多个联系人和职务的情况  联系人1，职务1&联系人2，职务2
						String[] contactValueTemp = contactValue.split("&");
						if(contactValueTemp.length>0){
							for(int k=0;k<contactValueTemp.length;k++){
								String[] positionStr = contactValueTemp[k].split(",");
								if(positionStr.length>1){
									contactBuffer.append(positionStr[0] + "&");
									positionBuffer.append(positionStr[1] + "&");
								}else{
									contactBuffer.append(positionStr[0] + "&");
								}
							}
						} else {
							String[] positionValueTemp = contactValue.split(",");
							if(positionValueTemp.length>0) {
								contactBuffer.append(positionValueTemp[0] + "&");
								positionBuffer.append(positionValueTemp[1] + "&");
							}else{
								contactBuffer.append(positionValueTemp[0] + "&");
							}
						}
					}
				}

				//邮箱
				Cell emailShell = sheet.getCell(4,j);

				//isForceIncludeFlag=1表示需要到数据库里判断是否有重复
				if(isForceIncludeFlag != 1){
					if(emailShell != null){
						String emailValue = emailShell.getContents().trim();
						if(StringUtil.isNotEmpty(emailValue)){
							String emailContent = emailValue.replaceAll(" ", "");
							if(StringUtil.isNotEmpty(emailContent)){
								String[] emailList = emailContent.split("\n");
								//如果isExistCompanyName为false,则说明公司名是没有重复，那么变判断地址是否有重复
								int length = emailList.length;
								if(!isExistCompanyNameOrAddress && length>0) {
									for(int emailIndex=0;emailIndex<length;emailIndex++){
										String emailTemp = emailList[emailIndex];
										tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddressAndEmail(companyStr, addressStr, emailTemp);
										if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
											isExistCompanyNameOrAddressOrEmail = true;
											break;
										}
									}
								}
							}else {
								tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddress(companyStr, addressStr);
								if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
									isExistCompanyNameOrAddress = true;
								}
							}
						}else {
							tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddress(companyStr, addressStr);
							if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
								isExistCompanyNameOrAddress = true;
							}
						}
					}else{
						tHistoryCustomerListTemp = loadHistoryCustomerByCompanyNameAndAddress(companyStr, addressStr);
						if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
							isExistCompanyNameOrAddress = true;
						}
					}
				}

				THistoryCustomer tHistoryCustomer = new THistoryCustomer();
				tHistoryCustomer.setId(willImportId++);
				if(userInfo != null){
					tHistoryCustomer.setOwner(userInfo.getOwnerId());
				}
				tHistoryCustomer.setCreatetime(new Date());
				tHistoryCustomer.setUpdatetime(new Date());
				int lastContactIndex = contactBuffer.toString().lastIndexOf("&");
				String contactValue = contactBuffer.toString().substring(0,lastContactIndex);
				int lastPositionIndex = positionBuffer.toString().lastIndexOf("&");
				String positionValue = "";
				if(lastPositionIndex > 0){
					positionValue = positionBuffer.toString().substring(0,lastPositionIndex);
				}
				tHistoryCustomer.setContact(contactValue);
				tHistoryCustomer.setPosition(positionValue);
				tHistoryCustomer.setIsDelete(0);
				if(userInfo != null){
					tHistoryCustomer.setUpdateowner(userInfo.getName());
				}
				int size = sheet.getColumns();
				for (int i = 0; i < sheet.getColumns(); i++) {
					Cell cell = sheet.getCell(i, j);
					switch (i) {
						case 0:	//国家
							String country = cell.getContents().trim();
							String[] countryArray = country.split(" ");
							if(countryArray.length>1){
								WCountry countryInfo = countryProvinceService.loadCountryByCountryValue(countryArray[1]);
								if(countryInfo != null){
									tHistoryCustomer.setCountry(countryInfo.getId());
								}else {
									//记录有问题
								}
							}else{
								//记录有问题
							}
							break;
						case 1:	//公司名
							tHistoryCustomer.setCompany(cell.getContents().trim());
							break;
						case 3:	//地址
							tHistoryCustomer.setAddress(cell.getContents().trim().replaceAll(" ", ""));
							break;
						case 4:	//邮箱)
							//String[] email = cell.getContents().trim().replaceAll(" ", "").split("\n");
							tHistoryCustomer.setEmail(cell.getContents().trim().replaceAll(" ", ""));
							break;
						case 5:	//网址
							tHistoryCustomer.setWebsite(cell.getContents().trim().replaceAll(" ", ""));
							break;
						case 6:	//备用地址
							tHistoryCustomer.setBackupaddress(cell.getContents().trim().replaceAll(" ", ""));
							break;
						case 7:	//备注
							tHistoryCustomer.setRemark(cell.getContents().trim().replaceAll(" ", ""));
							break;
						default:
							break;
					}
				}

				if(isExistCompanyNameOrAddress || isExistCompanyNameOrAddressOrEmail) {
					isExistCustomerList.addAll(tHistoryCustomerListTemp);
					willImportCustomerList.add(tHistoryCustomer);
				}else{
					historyCustomerInfoDao.create(tHistoryCustomer);
					count ++;
				}
			}
			if(isExistCompanyNameOrAddress || isExistCompanyNameOrAddressOrEmail) {
				if(willImportCustomerList != null && willImportCustomerList.size()>0){
					StringBuffer resultBuffer = new StringBuffer();
					resultBuffer.append("共导入：" + count + "条数据，其中有" + willImportCustomerList.size() + "条记录，可能存在重复！");
					resultArray.add(resultBuffer.toString());
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, Boolean.TRUE);
					JSONArray resultJson = JSONArray.fromObject(resultArray);
					report.setResult(resultJson.toString());
					JSONArray isExistDataJsonArray = JSONArray.fromObject(isExistCustomerList);
					JSONArray willImportDataJsonArray = JSONArray.fromObject(willImportCustomerList);
					report.setIsExistData(isExistDataJsonArray.toString());
					report.setWillImportData(willImportDataJsonArray.toString());
					report.setResultCode(1);
				}
			}else{
				StringBuffer resultBuffer = new StringBuffer();
				resultBuffer.append("共导入：" + count + "条数据");
				resultArray.add(resultBuffer.toString());
				JSONArray resultJson = JSONArray.fromObject(resultArray);
				report.setResult(resultJson.toString());
				report.setIsExistData("");
				report.setWillImportData("");
				report.setResultCode(0);
			}
			book.close();
		} catch (Exception e) {
			report.setResultCode(-1);
			e.printStackTrace();
		}
		return report;
	}

	public QueryHistoryCustomerResponse getWillImportCustomerList(QueryHistoryCustomerRequest request){
		Page page = new Page();
		page.setPageSize(request.getRows());
		page.setPageIndex(request.getPage());
		QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
		response.setResultCode(0);
		response.setRows(willImportCustomerList);
		response.setTotal(page.getTotalCount());
		return response;
	}

	public boolean refreshIsExistCustomerList(Integer[] tids, TUserInfo userInfo){
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadSelectedHistoryCustomers(tids);
		List<THistoryCustomer> tHistoryCustomerListTemp = new ArrayList<THistoryCustomer>();
		if(tHistoryCustomerList != null){
			for(THistoryCustomer tHistoryCustomer:tHistoryCustomerList){
				if(isExistCustomerList != null && isExistCustomerList.size()>0){
					for(int i=0;i<isExistCustomerList.size();i++){
						if(tHistoryCustomer.equals(isExistCustomerList.get(i)) && tHistoryCustomer.getOwner().equals(userInfo.getOwnerId())){
							isExistCustomerList.remove(i);
						}else{
							tHistoryCustomerListTemp.add(tHistoryCustomer);
						}
					}
				}
			}
			if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}

	/**
	 * 修改历史重复客商信息
	 * @param request flag=1表示修改要导入我资料，flag=2表示已经存在缓存资料, flag=3表示新增资料信息
	 * @return
	 */
	public void refreshRepeatCustomerList(ModifyHistoryCustomer request, TUserInfo userInfo){
		if(request.getFlag() == 1){
			if(willImportCustomerList != null && willImportCustomerList.size()>0){
				for(int i=0;i<willImportCustomerList.size();i++){
					THistoryCustomer tHistoryCustomer = willImportCustomerList.get(i);
					if (request.getId() == tHistoryCustomer.getId()){
						willImportCustomerList.remove(tHistoryCustomer);
						tHistoryCustomer.setCountry(Integer.parseInt(request.getCountry()));
						tHistoryCustomer.setCompany(request.getCompany());
						tHistoryCustomer.setContact(request.getContact());
						tHistoryCustomer.setPosition(request.getPosition());
						tHistoryCustomer.setAddress(request.getAddress());
						tHistoryCustomer.setEmail(request.getEmail());
						tHistoryCustomer.setWebsite(request.getWebsite());
						tHistoryCustomer.setBackupaddress(request.getBackupaddress());
						tHistoryCustomer.setRemark(request.getRemark());
						willImportCustomerList.add(i,tHistoryCustomer);
					}
				}
			}
		} else if(request.getFlag() == 2){
			if(isExistCustomerList != null && isExistCustomerList.size()>0){
				for(int i=0;i<isExistCustomerList.size();i++){
					THistoryCustomer tHistoryCustomer = isExistCustomerList.get(i);
					if (request.getId().equals(tHistoryCustomer.getId())){
						isExistCustomerList.remove(tHistoryCustomer);
						tHistoryCustomer.setCountry(Integer.parseInt(request.getCountry()));
						tHistoryCustomer.setCompany(request.getCompany());
						tHistoryCustomer.setContact(request.getContact());
						tHistoryCustomer.setPosition(request.getPosition());
						tHistoryCustomer.setAddress(request.getAddress());
						tHistoryCustomer.setEmail(request.getEmail());
						tHistoryCustomer.setWebsite(request.getWebsite());
						tHistoryCustomer.setBackupaddress(request.getBackupaddress());
						tHistoryCustomer.setRemark(request.getRemark());
						isExistCustomerList.add(i,tHistoryCustomer);
					}
				}
			}
		} else {
			THistoryCustomer tHistoryCustomer = new THistoryCustomer();
			tHistoryCustomer.setIsDelete(0);
			if(null != request.getCountry()){
				tHistoryCustomer.setCountry(Integer.parseInt(request.getCountry()));
			}else {
				tHistoryCustomer.setCountry(44);
			}
			tHistoryCustomer.setTelphone(request.getTelphone());
			tHistoryCustomer.setWebsite(request.getWebsite());
			tHistoryCustomer.setAddress(request.getAddress());
			tHistoryCustomer.setCateory(request.getCateory());
			tHistoryCustomer.setCompany(request.getCompany());
			tHistoryCustomer.setBackupaddress(request.getBackupaddress());
			tHistoryCustomer.setEmail(request.getEmail());
			tHistoryCustomer.setFax(request.getFax());
			tHistoryCustomer.setFixtelphone(request.getFixtelphone());
			tHistoryCustomer.setContact(request.getContact());
			if(userInfo != null){
				tHistoryCustomer.setOwner(userInfo.getOwnerId());
				tHistoryCustomer.setUpdateowner(userInfo.getName());
			}
			tHistoryCustomer.setCreatetime(new Date());
			tHistoryCustomer.setUpdatetime(new Date());
			tHistoryCustomer.setPosition(request.getPosition());
			tHistoryCustomer.setRemark(request.getRemark());
			getHibernateTemplate().save(tHistoryCustomer);
		}
	}

	public QueryHistoryCustomerResponse getIsExistCustomerList(QueryHistoryCustomerRequest request, Integer[] willImportCheckedItems){
		Page page = new Page();
		page.setPageSize(request.getRows());
		page.setPageIndex(request.getPage());
		QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
		response.setResultCode(0);

		List<THistoryCustomer> isExistSelectCustomerList = new ArrayList<THistoryCustomer>();
		if(willImportCheckedItems == null || willImportCheckedItems[0] == null){
			response.setRows(isExistCustomerList);
		}else{
			for(int i=0;i<willImportCheckedItems.length;i++){
				if(willImportCheckedItems[i] != null){
					int selectIndex= willImportCheckedItems[i];
					if(willImportCustomerList != null && selectIndex>0){
						for(int kk=0;kk<willImportCustomerList.size();kk++){
							THistoryCustomer tHistoryCustomer = willImportCustomerList.get(kk);
							if(tHistoryCustomer.getId() == selectIndex){
								if(isExistCustomerList != null && isExistCustomerList.size()>0){
									for(int k=0;k<isExistCustomerList.size();k++){
										THistoryCustomer tHistoryCustomer1 = isExistCustomerList.get(k);
										if(tHistoryCustomer1.getCompany().equalsIgnoreCase(tHistoryCustomer.getCompany())
												|| tHistoryCustomer1.getAddress().equalsIgnoreCase(tHistoryCustomer.getAddress())
												|| isEmailExist(tHistoryCustomer1.getEmail(),tHistoryCustomer.getEmail())){
											isExistSelectCustomerList.add(tHistoryCustomer1);
										}
									}
								}
							}
						}
					}
				}
			}
			response.setRows(isExistSelectCustomerList);
		}
		response.setTotal(page.getTotalCount());
		return response;
	}

	/**
	 * 判断邮箱是否存在
	 * @param emailSource 表示被比较的对象
	 * @param emailTarget 表示要比较的对象
	 * @return boolean
	 */
	private boolean isEmailExist(String emailSource, String emailTarget){
		boolean isExistFlag = false;
		if(StringUtil.isNotEmpty(emailSource) && StringUtil.isNotEmpty(emailTarget)){
			String[] emailSourceList = emailSource.split("\n");
			String[] emailTargetList = emailTarget.split("\n");
			//如果isExistCompanyName为false,则说明公司名是没有重复，那么变判断地址是否有重复
			Set<String> set = new HashSet<String>(Arrays.asList(emailSourceList));
			if(emailTargetList != null && emailTargetList.length>0){
				for(int i=0;i<emailTargetList.length;i++){
					String emailTemp = emailTargetList[i];
					if(set.contains(emailTemp)){
						isExistFlag =  true;
					}
				}
			}else{
				isExistFlag =  false;
			}
		}else{
			isExistFlag =  false;
		}
		return isExistFlag;
	}

	/**
	 * 分页获取国内历史客商列表
	 * @param request
	 * @param userInfo
	 * @param userRole  根据用户所在角色加载对应的资料库
	 * @param inlandOrForeignFlag
	 * @return
	 */
	public QueryHistoryCustomerResponse queryHistoryCustomersByPage(QueryHistoryCustomerRequest request,
																	TUserInfo userInfo,
																	TUserRole userRole,
																	@ModelAttribute Integer inlandOrForeignFlag) throws UnsupportedEncodingException{
		List<String> conditions = new ArrayList<String>();
		QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
		if(userInfo.getRoleId() == 1){
			if (request.getOwner() != null) {
				conditions.add(" e.owner = " + request.getOwner().intValue() + " ");
			}
		}else if(userInfo.getRoleId() == 6 || userInfo.getRoleId() == 2) {    //表示国外资料库管理员
			//表示显示所有国外资料库管理员,所以不做处理
		}else {
			if(request.getOwner() == null){
				if(StringUtil.isNotEmpty(userInfo.getShareId())){
					conditions.add(" e.owner in (" + userInfo.getShareId() + ") ");
				} else {
					response.setResultCode(1);
					response.setDescription("没有对应的资料库");
					return response;
				}
			}else {
				conditions.add(" e.owner in (" + request.getOwner().intValue() + ") ");
			}
		}
		Page page = new Page();
		page.setPageSize(request.getRows());
		page.setPageIndex(request.getPage());
		if (StringUtils.isNotEmpty(request.getCateory())) {
			conditions.add(" (e.cateory like '%" + request.getCateory() + "%' OR e.cateory like '%" + new String(request.getCateory().getBytes("ISO-8859-1"),"GBK") + "%' OR e.cateory like '%" + new String(request.getCateory().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getCompany())) {
			conditions.add(" (e.company like '%" + request.getCompany() + "%' OR e.company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"GBK") + "%' OR e.company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getAddress())) {
			conditions.add(" (e.address like '%" + request.getAddress() + "%' OR e.address like '%" + new String(request.getAddress().getBytes("ISO-8859-1"),"GBK") + "%' OR e.address like '%" + new String(request.getAddress().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getContact())) {
			conditions.add(" (e.contact like '%" + request.getContact() + "%' OR e.contact like '%" + new String(request.getContact().getBytes("ISO-8859-1"),"GBK") + "%' OR e.contact like '%" + new String(request.getContact().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getPosition())) {
			conditions.add(" (e.position like '%" + request.getPosition() + "%' OR e.position like '%" + new String(request.getPosition().getBytes("ISO-8859-1"),"GBK") + "%' OR e.position like '%" + new String(request.getPosition().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getTelphone())) {
			conditions.add(" (e.telphone like '%" + request.getTelphone() + "%' OR e.telphone like '%" + new String(request.getTelphone().getBytes("ISO-8859-1"),"GBK") + "%' OR e.telphone like '%" + new String(request.getTelphone().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getEmail())) {
			conditions.add(" (e.email like '%" + request.getEmail() + "%' OR e.email like '%" + new String(request.getEmail().getBytes("ISO-8859-1"),"GBK") + "%' OR e.email like '%" + new String(request.getEmail().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getFixtelphone())) {
			conditions.add(" (e.fixtelphone like '%" + request.getFixtelphone() + "%' OR e.fixtelphone like '%" + new String(request.getFixtelphone().getBytes("ISO-8859-1"),"GBK") + "%' OR e.fixtelphone like '%" + new String(request.getFixtelphone().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getFax())) {
			conditions.add(" (e.fax like '%" + request.getFax() + "%' OR e.fax like '%" + new String(request.getFax().getBytes("ISO-8859-1"),"GBK") + "%' OR e.fax like '%" + new String(request.getFax().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getWebsite())) {
			conditions.add(" (e.website like '%" + request.getWebsite() + "%' OR e.website like '%" + new String(request.getWebsite().getBytes("ISO-8859-1"),"GBK") + "%' OR e.website like '%" + new String(request.getWebsite().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getBackupaddress())) {
			conditions.add(" (e.backupaddress like '%" + request.getBackupaddress() + "%' OR e.backupaddress like '%" + new String(request.getBackupaddress().getBytes("ISO-8859-1"),"GBK") + "%' OR e.backupaddress like '%" + new String(request.getBackupaddress().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getRemark())) {
			conditions.add(" (e.remark like '%" + request.getRemark() + "%' OR e.remark like '%" + new String(request.getRemark().getBytes("ISO-8859-1"),"GBK") + "%' OR e.remark like '%" + new String(request.getRemark().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getCreatetime())) {
			conditions.add(" (e.createtime like '%" + request.getCreatetime() + "%' OR e.createtime like '%" + new String(request.getCreatetime().getBytes("ISO-8859-1"),"GBK") + "%' OR e.createtime like '%" + new String(request.getCreatetime().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getUpdatetime())) {
			conditions.add(" (e.updatetime like '%" + request.getUpdatetime() + "%' OR e.updatetime like '%" + new String(request.getUpdatetime().getBytes("ISO-8859-1"),"GBK") + "%' OR e.updatetime like '%" + new String(request.getUpdatetime().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}

		if(inlandOrForeignFlag == 1) {
			conditions.add(" e.country = 44 ");
		} else {
			if (request.getCountry() != null) {
				conditions.add(" e.country = " + request.getCountry());
			}else{
				conditions.add(" e.country <> 44 ");
			}
		}
		conditions.add(" e.isDelete = 0 ");
		String conditionsSql = StringUtils.join(conditions, " and ");
		String conditionsSqlNoOrder = "";

		if(StringUtils.isNotEmpty(conditionsSql)){
			conditionsSqlNoOrder = " where " + conditionsSql;
		}

		List<QueryHistoryCustomer> queryHistoryCustomers = historyCustomerInfoDao.queryPageByHQL("select count(*) from THistoryCustomer e " + conditionsSqlNoOrder,
				"select new com.zhenhappy.ems.manager.dto.companyinfo.QueryHistoryCustomer(e.id, e.cateory, e.company, e.country, e.address, e.contact, " +
						"e.position, e.telphone, e.email, e.fixtelphone, e.fax, e.website, e.backupaddress, e.remark, e.owner, e.createtime, e.updatetime, e.updateowner,e.isDelete) "
						+ "from THistoryCustomer e " + conditionsSqlNoOrder + " order by e.updatetime", new Object[]{}, page);
		response.setResultCode(0);
		response.setRows(queryHistoryCustomers);
		response.setTotal(page.getTotalCount());
		return response;
	}

	/**
	 * 分页获取国外历史客商列表
	 * @param request
	 * @return
	 */
	public QueryHistoryCustomerResponse queryHistoryForeignCustomersByPage(QueryHistoryCustomerRequest request) throws UnsupportedEncodingException{
		Page page = new Page();
		page.setPageSize(request.getRows());
		page.setPageIndex(request.getPage());
		List<THistoryCustomer> customers = historyCustomerInfoDao.queryPageByHQL("select count(*) from THistoryCustomer where isDelete = 0 and country <> 44",
				"from THistoryCustomer where isDelete = 0 and country = 44 order by updatetime DESC", new Object[]{}, page);
		QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
		response.setResultCode(0);
		response.setRows(customers);
		response.setTotal(page.getTotalCount());
		return response;
	}

	/**
	 * 查询国内历史客商基本信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryInlandCustomer() {
		List<THistoryCustomer> customers = historyCustomerInfoDao.queryByHql("from THistoryCustomer where isDelete = 0 and country = 44 order by updatetime desc", new Object[]{});
		return customers.size() > 0 ? customers : null;
	}

	/**
	 * 查询国内历史客商基本信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryInlandCustomerByUserInfoAndUserRole(TUserInfo userInfo) {
		List<THistoryCustomer> customers = new ArrayList<THistoryCustomer>();
		if(userInfo != null && userInfo.getRoleId() == 2) {
			customers = historyCustomerInfoDao.queryByHql("from THistoryCustomer where isDelete = 0 and country = 44 order by updatetime desc", new Object[]{});
		}else if(userInfo != null){
			customers = historyCustomerInfoDao.queryByHql("from THistoryCustomer where isDelete = 0 and country = 44 and owner=? order by updatetime desc", new Object[]{userInfo.getRoleId()});
		}
		return customers.size() > 0 ? customers : null;
	}

	/**
	 * 查询国内历史客商基本信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryInlandCustomerByowner(String owner) {
		String[] value = owner.split(",");
		if(value.length>0){
			Integer[] ownerId = new Integer[value.length];
			for(int i=0;i<value.length;i++){
				ownerId[i] = Integer.parseInt(value[i]);
			}
			List<THistoryCustomer> customers = loadSelectedHistoryInlandCustomersByOwner(ownerId);
			return customers.size() > 0 ? customers : null;
		}else {
			return null;
		}
	}

	/**
	 * 根据ids查询国内历史客商信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadSelectedHistoryInlandCustomersByOwner(Integer[] ownerIds) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadSelectedHistoryInlandCustomersByOwner(ownerIds);
		return tHistoryCustomerList;
	}

	/**
	 * 根据ids查询国内历史客商信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadSelectedHistoryCustomers(Integer[] ids) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadSelectedHistoryCustomers(ids);
		return tHistoryCustomerList;
	}

	/**
	 * 根据ids查询国内历史客商信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadSelectedHistoryInlandCustomers(Integer[] ids) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadSelectedHistoryInlandCustomers(ids);
		return tHistoryCustomerList;
	}

	/**
	 * 查询国外历史客商基本信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryForeignCustomer() {
		List<THistoryCustomer> customers = historyCustomerInfoDao.queryByHql("from THistoryCustomer where isDelete = 0 and country <> 44 order by updatetime desc", new Object[]{});
		return customers.size() > 0 ? customers : null;
	}

	/**
	 * 查询国外历史客商基本信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryForeignCustomerByOwner(String ownerId) {
		String[] value = ownerId.split(",");
		if(value.length>0){
			Integer[] ownerIds = new Integer[value.length];
			for(int i=0;i<value.length;i++){
				ownerIds[i] = Integer.parseInt(value[i]);
			}
			List<THistoryCustomer> customers = loadAllHistoryForeignCustomersByOwner(ownerIds);
			return customers.size() > 0 ? customers : null;
		}else {
			return null;
		}
	}

	/**
	 * 根据ids查询国内历史客商信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByOwner(Integer[] ownerIds) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadAllHistoryForeignCustomersByOwner(ownerIds);
		return tHistoryCustomerList;
	}

	/**
	 * 查询国外历史客商基本信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryForeignCustomerByCountryId(Integer countryId) {
		List<THistoryCustomer> customers = historyCustomerInfoDao.queryByHql("from THistoryCustomer where isDelete = 0 and country =? order by updatetime desc", new Object[]{countryId});
		return customers.size() > 0 ? customers : null;
	}

	/**
	 * 查询国外历史客商基本信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryForeignCustomerByCountryIdAndOwner(Integer countryId, String ownerId) {
		String[] value = ownerId.split(",");
		if(value.length>0){
			Integer[] ownerIds = new Integer[value.length];
			for(int i=0;i<value.length;i++){
				ownerIds[i] = Integer.parseInt(value[i]);
			}
			List<THistoryCustomer> customers = loadAllHistoryForeignCustomersByCountryIdAndOwner(countryId, ownerIds);
			return customers.size() > 0 ? customers : null;
		}else {
			return null;
		}
	}

	/**
	 * 根据ids查询国内历史客商信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByCountryIdAndOwner(Integer countryId, Integer[] ownerIds) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadAllHistoryForeignCustomersByCountryIdAndOwner(countryId, ownerIds);
		return tHistoryCustomerList;
	}

	/**
	 * 根据ids查询国内历史客商信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadSelectedHistoryForeignCustomers(Integer[] ids) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadSelectedHistoryForeignCustomers(ids);
		return tHistoryCustomerList;
	}

	/**
	 * 根据id查询历史客商基本信息
	 * @param id
	 * @return
	 */
	@Transactional
	public ExportHistoryCustomerInfo loadHistoryCustomerInfoById(Integer id) {
		THistoryCustomer customerInfo = historyCustomerInfoDao.query(id);
		ExportHistoryCustomerInfo exportCustomerInfo = new ExportHistoryCustomerInfo();
		if(customerInfo != null){
			WCountry country = countryProvinceService.loadCountryById(customerInfo.getCountry());
			exportCustomerInfo.setCountryEx(country.getChineseName());
			exportCustomerInfo.setCateory(customerInfo.getCateory());
			exportCustomerInfo.setTelphone(customerInfo.getTelphone());
			exportCustomerInfo.setFixtelphone(customerInfo.getFixtelphone());
			exportCustomerInfo.setCompany(customerInfo.getCompany());
			exportCustomerInfo.setContactEx(customerInfo.getContact());
			exportCustomerInfo.setPosition(customerInfo.getPosition());
			exportCustomerInfo.setAddress(customerInfo.getAddress());
			exportCustomerInfo.setEmail(customerInfo.getEmail());
			exportCustomerInfo.setWebsite(customerInfo.getWebsite());
			exportCustomerInfo.setBackupaddress(customerInfo.getBackupaddress());
			exportCustomerInfo.setRemark(customerInfo.getRemark());
			exportCustomerInfo.setUpdatetime(customerInfo.getUpdatetime());
			exportCustomerInfo.setFax(customerInfo.getFax());
			exportCustomerInfo.setIsDelete(customerInfo.getIsDelete());
		}
		return exportCustomerInfo;
	}

	/**
	 * 根据邮箱查询历史客商
	 * @param email
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadHistoryCustomerByEmail(String email) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.queryByHql("from THistoryCustomer where email=? and isDelete=0", new Object[]{email});
		return tHistoryCustomerList.size() > 0 ? tHistoryCustomerList : null;
	}

	/**
	 * 根据公司名查询历史客商
	 * @param companyName
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadHistoryCustomerByCompanyName(String companyName) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.queryByHql("from THistoryCustomer where company=? and isDelete=0", new Object[]{companyName});
		return tHistoryCustomerList.size() > 0 ? tHistoryCustomerList : null;
	}

	/**
	 * 根据公司名和地址查询历史客商
	 * @param companyName
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadHistoryCustomerByCompanyNameAndAddress(String companyName, String Address) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.queryByHql("from THistoryCustomer where (company=? or address=?) and isDelete=0", new Object[]{companyName, Address});
		return tHistoryCustomerList.size() > 0 ? tHistoryCustomerList : null;
	}

	/**
	 * 根据公司名、地址和邮箱查询历史客商
	 * @param companyName
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadHistoryCustomerByCompanyNameAndAddressAndEmail(String companyName, String address, String email) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.queryByHql("from THistoryCustomer where (company=? or address=? or CHARINDEX(?,email)>0) and isDelete=0", new Object[]{companyName, address, email});
		return tHistoryCustomerList.size() > 0 ? tHistoryCustomerList : null;
	}

	/**
	 * 根据公司名查询历史客商
	 * @param companyAddress
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadHistoryCustomerByCompanyAddress(String companyAddress) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.queryByHql("from THistoryCustomer where address=? and isDelete=0", new Object[]{companyAddress});
		return tHistoryCustomerList.size() > 0 ? tHistoryCustomerList : null;
	}

	/**
	 * 根据ids查询国内历史客商信息
	 * @return
	 */
	@Transactional
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByEmail(String[] emails) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadAllHistoryForeignCustomersByEmail(emails);
		return tHistoryCustomerList;
	}

	/**
	 * 修改历史客商资料
	 * @param request
	 * @throws Exception
	 */
	@Transactional
	public void modifyHistoryCustomerAccount(ModifyHistoryCustomer request, TUserInfo userInfo) throws Exception {
		if(request.getInsertOrModify() == 1){
			THistoryCustomer tHistoryCustomer = new THistoryCustomer();
			tHistoryCustomer.setIsDelete(0);
			if(request.getCountry() != null){
				tHistoryCustomer.setCountry(Integer.parseInt(request.getCountry()));
			}else{
				if(request.getCountryValue() == 1){
					tHistoryCustomer.setCountry(44);
				}
			}
			tHistoryCustomer.setTelphone(request.getTelphone());
			tHistoryCustomer.setWebsite(request.getWebsite());
			tHistoryCustomer.setAddress(request.getAddress());
			tHistoryCustomer.setCateory(request.getCateory());
			tHistoryCustomer.setCompany(request.getCompany());
			tHistoryCustomer.setBackupaddress(request.getBackupaddress());
			tHistoryCustomer.setEmail(request.getEmail());
			tHistoryCustomer.setFax(request.getFax());
			tHistoryCustomer.setFixtelphone(request.getFixtelphone());
			tHistoryCustomer.setContact(request.getContact());
			if(userInfo != null){
				tHistoryCustomer.setOwner(userInfo.getOwnerId());
				tHistoryCustomer.setUpdateowner(userInfo.getName());
			}
			tHistoryCustomer.setCreatetime(new Date());
			tHistoryCustomer.setUpdatetime(new Date());
			tHistoryCustomer.setPosition(request.getPosition());
			tHistoryCustomer.setRemark(request.getRemark());
			getHibernateTemplate().save(tHistoryCustomer);
		}else{
			THistoryCustomer tHistoryCustomer = historyCustomerInfoDao.query(request.getId());
			if(tHistoryCustomer != null){
				if(StringUtils.isNotEmpty(request.getCountry())){
					WCountry country = countryProvinceService.loadCountryByName(request.getCountry());
					if(country != null){
						tHistoryCustomer.setCountry(country.getId());
					}
				}
				tHistoryCustomer.setCateory(request.getCateory());
				tHistoryCustomer.setTelphone(request.getTelphone());
				tHistoryCustomer.setFixtelphone(request.getFixtelphone());
				tHistoryCustomer.setCompany(request.getCompany());
				tHistoryCustomer.setContact(request.getContact());
				tHistoryCustomer.setPosition(request.getPosition());
				tHistoryCustomer.setAddress(request.getAddress());
				tHistoryCustomer.setEmail(request.getEmail());
				tHistoryCustomer.setWebsite(request.getWebsite());
				tHistoryCustomer.setBackupaddress(request.getBackupaddress());
				tHistoryCustomer.setRemark(request.getRemark());
				tHistoryCustomer.setUpdatetime(new Date());
				tHistoryCustomer.setFax(request.getFax());
				if(userInfo != null){
					tHistoryCustomer.setUpdateowner(userInfo.getName());
				}
				historyCustomerInfoDao.update(tHistoryCustomer);
			}
		}
	}

	/**
	 * 导入资料库
	 * @param tids
	 */
	@Transactional
	public void insertCustomerInfoByTids(Integer[] tids) {
		if(tids != null && tids.length>0){
			for(int k=0;k<tids.length;k++){
				int id = tids[k];
				if(willImportCustomerList != null && willImportCustomerList.size()>0){
					for(int i=0;i<willImportCustomerList.size();i++){
						THistoryCustomer tHistoryCustomer = willImportCustomerList.get(i);
						if(tHistoryCustomer.getId() == id){
							getHibernateTemplate().save(tHistoryCustomer);
							willImportCustomerList.remove(tHistoryCustomer);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * 忽略导入资料库
	 * @param tids
	 */
	@Transactional
	public void ignoreCustomerInfoByTids(Integer[] tids) {
		if(tids != null && tids.length>0){
			for(int k=0;k<tids.length;k++){
				int id = tids[k];
				if(willImportCustomerList != null && willImportCustomerList.size()>0){
					for(int i=0;i<willImportCustomerList.size();i++){
						THistoryCustomer tHistoryCustomer = willImportCustomerList.get(i);
						if(tHistoryCustomer.getId() == id){
							willImportCustomerList.remove(tHistoryCustomer);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * 删除资料库
	 * @param tids
	 */
	@Transactional
	public boolean removeCustomerInfoByTids(Integer[] tids, TUserInfo userInfo) {
		List<THistoryCustomer> tHistoryCustomerList = historyCustomerInfoDao.loadSelectedHistoryCustomers(tids);
		List<THistoryCustomer> tHistoryCustomerListTemp = new ArrayList<THistoryCustomer>();
		if(tHistoryCustomerList != null){
			for(THistoryCustomer tHistoryCustomer:tHistoryCustomerList){
				if(tHistoryCustomer.getOwner().equals(userInfo.getOwnerId())){
					tHistoryCustomer.setIsDelete(1);
					if(userInfo != null){
						tHistoryCustomer.setUpdateowner(userInfo.getName());
					}
					tHistoryCustomer.setUpdatetime(new Date());
					getHibernateTemplate().update(tHistoryCustomer);
				}else {
					tHistoryCustomerListTemp.add(tHistoryCustomer);
				}
			}
			if(tHistoryCustomerListTemp != null && tHistoryCustomerListTemp.size()>0){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}

	/**
	 * 忽略导入资料库
	 * @param customerList
	 */
	@Transactional
	public void ignoreCustomerInfoByTids(List<THistoryCustomer> customerList, TUserInfo userInfo) {
		if(customerList != null){
			for(THistoryCustomer tHistoryCustomer:customerList){
				tHistoryCustomer.setIsDelete(1);
				if(userInfo != null){
					tHistoryCustomer.setUpdateowner(userInfo.getName());
				}
				tHistoryCustomer.setUpdatetime(new Date());
				getHibernateTemplate().update(tHistoryCustomer);
			}
		}
	}
}