package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.code.domain.Return;
import com.github.pagehelper.PageInfo;
import com.code.domain.Box;
import com.code.domain.Record;
import com.code.domain.User;
import com.code.service.write.BoxService;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 红包盒子相关类
 */
@Controller
@RequestMapping("/box")
public class BoxController extends BaseController{


	/**
	 *
	 * @param data
	 * @return
	 *
	 * 创建房间
	 * 金额 盒子数 谁  测试用,正式必须要关闭
     */
	@GetMapping("/CreateBox")
	@ResponseBody
	public Map<String,Object> CreateBox(String data){
		Map<String,Object> returnMap=new HashMap<String, Object>();
		CreateBox(data,boxService);

		return returnMap;
	}


	/**
	 * 轮询盒子
	 * @param userid
	 * @return
     */
	@GetMapping("/CheckBoxRun/{userid}")
	@ResponseBody
	public Return CheckBoxRun(@PathVariable("userid")String userid){
		Return returnMap=new Return();
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userid",userid);
		queryMap.put("type",CommonStatus.Status.Run.getid());
		List<Box> box=boxService.query(queryMap);
		if (box.size()>0){
			box.get(0).setPlace("-1");//位置不给看到
			returnMap=CommonUntil.ReturnMap(0,"当前你有存在的盒子",box.get(0));
		}else{
			returnMap=CommonUntil.ReturnMap(-1,"您还未创建盒子",null);
		}
		return returnMap;
	}

	/**
	 * 开盒子
	 * @param userid
	 * @param response
	 * @param Request
     * @return
     */
	@GetMapping("/openBoxRun/{userid}")
	@ResponseBody
	public Return openBoxRun(@PathVariable("userid")String userid, HttpServletResponse response, HttpServletRequest Request){
		Return returnMap=new Return();
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userid",userid);
		queryMap.put("type",CommonStatus.Status.Run.getid());
		List<Box> box=boxService.query(queryMap);
		if (box.size()>0){
			box.get(0).setType(CommonStatus.Status.Over.getid());
			Box b=boxService.update(box.get(0));
			if(b!=null){
				queryMap=new HashMap<String, Object>();
				queryMap.put("boxid",b.getId());
				queryMap.put("status",CommonStatus.Status.Run.getid());
				int count=recordService.queryCount(queryMap);
				if(count>0){
					int num=Integer.parseInt(b.getPlace());
					queryMap=new HashMap<String, Object>();
					queryMap.put("boxid",b.getId());
					queryMap.put("status",CommonStatus.Status.Run.getid());
					queryMap.put("num",num);
					List<Record> records=recordService.query(queryMap);
					int draw=records.size();
					if(records.size()>0){
						List<String> money=getMoney(b.getMoney(),draw);
						for (int i=0;i<records.size();i++){
							records.get(i).setMoney(money.get(i));
							records.get(i).setStatus(CommonStatus.Status.Win.getid());
							recordService.update(records.get(i));
							//打钱
							if(!money.get(i).equals("0.00")&&!money.get(i).equals("0")&&!money.get(i).equals("0")&&Float.parseFloat(money.get(0))!=0){
								User user=userService.findById(records.get(i).getUserid());
								if(user!=null){
									CommonUntil.getWithdraw(user,Float.parseFloat(money.get(i)),response,Request,weChatInfoService);
								}
							}

						}
					}
					queryMap=new HashMap<String, Object>();
					queryMap.put("boxid",b.getId());
					queryMap.put("status",CommonStatus.Status.Run.getid());
					queryMap.put("nonum",num);
					records=recordService.query(queryMap);
					for (int i=0;i<records.size();i++){
						records.get(i).setMoney("0");
						records.get(i).setStatus(CommonStatus.Status.NotWin.getid());
						recordService.update(records.get(i));
					}
					String message="";
					if(draw==0){
						if(!box.get(0).getMoney().equals("0.00")&&!box.get(0).getMoney().equals("0")&&!box.get(0).getMoney().equals("0")&&Float.parseFloat(box.get(0).getMoney())!=0){
							User user=userService.findById(box.get(0).getUserid());
							if(user!=null){
								CommonUntil.getWithdraw(user,Float.parseFloat(box.get(0).getMoney()),response,Request,weChatInfoService);
							}
						}
						message="当前没有人中奖哦";
					}else{
						message="当前"+draw+"人中奖";
					}
					returnMap=CommonUntil.ReturnMap(0,"开奖成功,"+message,null);
				}else{
					if(!box.get(0).getMoney().equals("0.00")&&!box.get(0).getMoney().equals("0")&&!box.get(0).getMoney().equals("0")&&Float.parseFloat(box.get(0).getMoney())!=0){
						User user=userService.findById(box.get(0).getUserid());
						if(user!=null){
							CommonUntil.getWithdraw(user,Float.parseFloat(box.get(0).getMoney()),response,Request,weChatInfoService);
						}
					}
					returnMap=CommonUntil.ReturnMap(0,"没有用户抽盒子,金额将退回",null);
				}

			}else {
				returnMap = CommonUntil.ReturnMap(-1, "发生异常,请稍后重试", null);
			}
		}else{
			returnMap=CommonUntil.ReturnMap(-1,"您还未创建盒子",null);
		}
		return returnMap;
	}

	public  static Box CreateBox (String box,BoxService boxService){
		Box b= JSON.parseObject(box,Box.class);
		b=new Box();
		b.setId(CommonUntil.CreateNewID());
		b.setStatus(CommonStatus.Status.Ectivity.getid());
		b.setType(CommonStatus.Status.Run.getid());
		//int place = new Random().nextInt(Integer.parseInt(b.getNum())) + 1;
		int place =1;
		b.setPlace(place+"");
		b.setUserid("123123");
		Box bo=boxService.insert(b);
		return bo!=null?bo:null;
	}


	/**
	 *
	 * 抽奖盒子
	 * @param data
	 * @return boxid
     */
	@PostMapping("/lotteryBox")
	@ResponseBody
	public Return lotteryBox(String data){
		Return returnMap=new Return();
		Record record= JSON.parseObject(data,Record.class);
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("id",record.getBoxid());
		queryMap.put("type",CommonStatus.Status.Run.getid());
		List<Box> box=boxService.query(queryMap);

		//限制参与人数
		if (box.size()>0){

			queryMap=new HashMap<String, Object>();
			queryMap.put("boxid",box.get(0).getId());
			queryMap.put("status",CommonStatus.Status.Run.getid());
			queryMap.put("num",box.get(0).getNum());
			int recordsCount=recordService.queryCount(queryMap);//当前中奖的人数
			Double m=Double.valueOf(box.get(0).getMoney());//整形的金钱
			if(m>=recordsCount){
				if(box.get(0).getUserid().equals(record.getUserid())){
					returnMap = CommonUntil.ReturnMap(-1,"自己不能开盒子哦",null);
				}else{
					queryMap=new HashMap<String, Object>();
					queryMap.put("boxid",record.getBoxid());
					queryMap.put("userid",record.getUserid());
					queryMap.put("nostatus",CommonStatus.Status.Delete.getid());
					List<Record> records=recordService.query(queryMap);
					if (records.size()>0){
						returnMap = CommonUntil.ReturnMap(-1,"你已经开过,不能太贪心哦",null);
					}else {
						record.setId(CommonUntil.CreateNewID());
						record.setStatus(CommonStatus.Status.Run.getid());
						Record recor=recordService.insert(record);
						if (recor!=null){
							returnMap = CommonUntil.ReturnMap(0,"开盒子成功,等待稍后开奖",null);
						}else{
							returnMap = CommonUntil.ReturnMap(-1,"开盒子失败,请稍后重试",null);
						}
					}
				}
			}else{
				returnMap = CommonUntil.ReturnMap(-1,"该红包参与人数过多哦，可选择其他红包~",null);
			}
		}else{
			returnMap = CommonUntil.ReturnMap(-1,"该盒子不存在,或者已开奖",null);
		}
		return returnMap;
	}

	/**
	 *  找房间
	 * @param id
	 * @param userid
     * @return
     */
	@GetMapping("/findBox/{id}/{userid}")
	@ResponseBody
	public Return findBox(@PathVariable("id") String id,@PathVariable("userid")String userid){
		Return returnMap=new Return();
		Box box=boxService.findById(id);
		if (box!=null){
			box.setPlace("-1");//设置位置 ,不给查看
			Map<String,Object> queryMap=new HashMap<String, Object>();
			queryMap.put("boxid",id);
			if(CommonUntil.CheckIsNull(userid)&&!userid.equals("0")){
				queryMap.put("userid",userid);
			}
			List<Record> records=recordService.query(queryMap);
			box.setRecord(records);
			returnMap = CommonUntil.ReturnMap(0,"获取成功",box);
		}else{
			returnMap = CommonUntil.ReturnMap(-1,"该盒子不存在",null);
		}
		return returnMap;
	}

	public static void main(String[] args) throws Exception {
		List<String> getMoney=getMoney("1",1);
	}
	/**
	 *
	 * @param money
	 * @param num
     * @return
	 *
	 * 金额数  人数 近似平均分
     */
	public static List<String> getMoney(String money,int num){
		List<String> moneys=new ArrayList<String>();
		BigDecimal AllMoney = new BigDecimal(money);//总钱
		BigDecimal people = new BigDecimal(String.valueOf(num));//人数
		BigDecimal remainder = AllMoney.remainder(people);//获取余数的钱 就是余数的钱不能被整除了
		BigDecimal allocationMoney = AllMoney.subtract(remainder);//将被分配的钱
		BigDecimal peopleMoney = allocationMoney.divide(people);//将被分配的钱
		float smallMoney=Float.parseFloat(remainder.toPlainString())/(float)num;
		String smallMoneyStr=String.valueOf(smallMoney).substring(0,(String.valueOf(smallMoney).indexOf(".")+3)>String.valueOf(smallMoney).length()?String.valueOf(smallMoney).length():(String.valueOf(smallMoney).indexOf(".")+3));//平均钱 获取小数后面2位
		BigDecimal cha=remainder.subtract(new BigDecimal(smallMoneyStr).multiply(new BigDecimal(String.valueOf(num))));
		for(int i=0;i<num;i++){
			if(i==0){//第一个要加差值
				moneys.add(String.valueOf(peopleMoney.add(new BigDecimal(smallMoneyStr)).add(cha)));
			}else{
				moneys.add(String.valueOf(peopleMoney.add(new BigDecimal(smallMoneyStr))));
			}
		}
		return moneys;
	}

	/**
	 *  查询盒子的列表
	 * @param status
	 * @param search
	 * @param pageNumber
	 * @param pageSize
     * @return
     */
	@RequestMapping("/queryPageBox")
	@ResponseBody
	public Map<String,Object> queryPageBox( int status,String search,int pageNumber,int pageSize){
		Map<String,Object> returnMap=new HashMap<String, Object>();
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("search",search);
		if(status!=0){
			queryMap.put("status",status);
		}
		PageInfo<Box> page = this.ReadBoxService.queryPage(queryMap, pageNumber, pageSize);
		returnMap.put("rows",page.getList());
		returnMap.put("total",page.getTotal());
		return returnMap;
	}

	/**
	 *  查询盒子抽奖记录的列表
	 * @param boxid
	 * @param status
	 * @param search
	 * @param pageNumber
	 * @param pageSize
     * @return
     */
	@RequestMapping("/queryPageRecord")
	@ResponseBody
	public Map<String,Object> queryPageRecord( String boxid,int status,String search,int pageNumber,int pageSize){
		Map<String,Object> returnMap=new HashMap<String, Object>();
		Map<String,Object> queryMap=new HashMap<String, Object>();
		queryMap.put("search",search);
		if(status!=0){
			queryMap.put("status",status);
		}
		queryMap.put("boxid",boxid);
		PageInfo<Record> page = this.recordService.queryPage(queryMap, pageNumber, pageSize);
		returnMap.put("rows",page.getList());
		returnMap.put("total",page.getTotal());
		return returnMap;
	}


	/**
	 * 设置状态
	 * @param data
	 * @return
     */
	@RequestMapping("/setStatus")
	@ResponseBody
	public Map<String,Object> setStatus(String data){
		Map<String,Object> returnMap=new HashMap<String, Object>();
		Box user=JSON.parseObject(data,Box.class);
		String[] ids=user.getId().split(",");
		for (String id : ids){
			if(Integer.parseInt(user.getStatus())==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
				boxService.recoverById(id);
			}else{
				boxService.deleteById(id);
			}
		}
		returnMap.put("code",0);
		returnMap.put("message","操作成功");
		return returnMap;
	}

	/**
	 * 设置记录的状态
	 * @param data
	 * @return
     */
	@RequestMapping("/setRecordStatus")
	@ResponseBody
	public Map<String,Object> setRecordStatus(String data){
		Map<String,Object> returnMap=new HashMap<String, Object>();
		Box user=JSON.parseObject(data,Box.class);
		String[] ids=user.getId().split(",");
		for (String id : ids){
			if(Integer.parseInt(user.getStatus())==Integer.parseInt(CommonStatus.Status.Delete.getid())){
				recordService.deleteById(id);
			}else{
				//TODO 自行扩展
			}
		}
		returnMap.put("code",0);
		returnMap.put("message","操作成功");
		return returnMap;
	}


	@GetMapping("/findBoxx/{id}")
	@ResponseBody
	public Return findBoxx(@PathVariable("id") String id){
		Return returnMap=new Return();
		Box box=boxService.findById(id);
		if (box!=null){
			returnMap = CommonUntil.ReturnMap(0,"获取成功",box);
		}else{
			returnMap = CommonUntil.ReturnMap(-1,"该盒子不存在",null);
		}
		return returnMap;
	}

}
