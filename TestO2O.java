package com.xiaomi.o2o.test;

import xuxu.autotest.AdbDevice;

import xuxu.autotest.element.Element;

import xuxu.autotest.element.Position;

import android.app.Activity;

import android.test.ActivityInstrumentationTestCase2;

import android.view.KeyEvent;

import android.view.View;

import com.robotium.solo.By;

import com.robotium.solo.Solo;

@SuppressWarnings("rawtypes")

public class TestO2O extends ActivityInstrumentationTestCase2 {

	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.xiaomi.o2o.activity.O2OTabActivity";

	private static Class<?> launcherActivityClass;

	static {

		try {

			launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
			
		} catch (ClassNotFoundException e) {

			throw new RuntimeException(e);

		}

	}

	@SuppressWarnings("unchecked")

	public TestO2O() throws ClassNotFoundException {
		
		super(launcherActivityClass);

	}

	private Solo solo;

	private AdbDevice adbDevice;

	private Position position;

	@Override

	protected void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());

		adbDevice = new AdbDevice();

		position = new Position();

	}

	// 测试小米生活首页是否有“小米生活”title

	public void test001Title() throws Exception {

		boolean expected = true;

		boolean actual = solo.searchText("小米生活",0,false);

		assertEquals("小米生活  are not found", expected, actual);

	}

	// 测试小米生活首页是否定位到城市“北京”

	public void test002Location() {

		boolean expected = true;

		boolean actual = solo.searchText("北京");

		assertEquals("定位城市：北京  are not found", expected, actual);

	}

	// 测试首页是否有各个分类

	public void test003Category() throws Exception {

		boolean expected = true;

		boolean actual = solo.searchText("美食") && solo.searchText("电影")&& solo.searchText("KTV") && solo.searchText("酒店")&& solo.searchText("更多分类");

		assertEquals("美食 or 电影 or KTV or 酒店 or 更多分类  are not found", expected,	actual);

	}



	// 测试小米生活首页是否有“元、人购买”,即检查首页列表页是否加载出数据



	public void test004List() throws Exception {

		boolean actual = solo.searchText("元") && solo.searchText("人购买");

		boolean expected = true;

		assertEquals("首页列表：元 or人购买  are not found", expected, actual);

	}



	// 美食团购是否可以正常调起支付



	public void test008Cate() throws Exception {



		// adbDevice.tap(540,1000);//点击首页中间搜索框



		solo.sleep(3000);



		solo.clickOnText("(?i).*?美食.*", 0);



		solo.sleep(1000);



		// solo.clickOnText("(?i).*?望湘园",0);//点击某一个商品名称



		int index = (int) (Math.random() * 10 + 1);



		solo.clickOnText("(?i).*?人购买", index);



		solo.sleep(1000);



		solo.clickOnText("(?i).*?立即购买");



		solo.sleep(1000);

		

		//如果没有订单支付页面没有填写手机号码，就进入编辑框填写手机号码

		boolean bool=solo.searchText("请填写您的手机号码");

		if(bool){			

			solo.clickOnWebElement(By.id("phoneNum"));

			solo.enterTextInWebElement(By.id("phoneInput"), "15724736887");

			solo.clickOnText("确定");

		}

		

		TransferPayment();

		

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);

	}

	/*

	// 酒店团购是否可以正常调起支付

	public void test005Hotel() throws Exception {



			solo.sleep(1000);

			solo.clickOnText("(?i).*?酒店.*", 0);



			solo.sleep(1000);



			solo.clickOnText("(?i).*?如家快捷酒店.*", 0);



			solo.sleep(1000);



			solo.clickOnText("(?i).*?人购买", 0);



			solo.sleep(1000);



			solo.clickOnText("(?i).*?立即购买", 0);



			solo.sleep(1000);



			TransferPayment();

			

			adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

			solo.sleep(1000);

			adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

			solo.sleep(1000);

			adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

			solo.sleep(1000);

			adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

			solo.sleep(1000);

	}

	*/

	// 格瓦拉电影选座是否可以正常调起支付



	public void test006MovieGewara() throws Exception {



		solo.sleep(3000);

		solo.clickOnText("(?i).*?电影.*", 0);

		solo.sleep(1000);

		//solo.clickOnText("(?i).*?海淀剧院.*", 0);



		solo.clickOnText("(?i).*?CGV星聚汇影城.*", 0);

		

		MovieCommon();

	}



	// 时光网电影选座是否可以正常调起支付



	public void test007MovieMtime() throws Exception {



		solo.sleep(3000);

		solo.clickOnText("(?i).*?电影.*", 0);



		solo.sleep(1000);



		// solo.clickOnText("(?i).*?选座.*", 0);



		solo.clickOnText("(?i).*?五道口工人俱乐部电影院.*", 0);



		MovieCommon();

	}



	private void MovieCommon() throws Exception {



		solo.sleep(1000);

		/*

		solo.clickOnText("(?i).*?今天.+?月.+?日.*", 1);

		solo.clickOnText("(?i).*?明天.+?月.+?日.*", 1);

		solo.clickOnText("(?i).*?后天.+?月.+?日.*", 1);

		*/

		boolean today=false;

		boolean tomorrow=false;

		boolean postnatal=false;

		boolean bool=false;

		

		for(int i=0;i<3;i++){

			//第一次循环选"后天"

			switch(i){

			case 0:

				postnatal = solo.searchText("后天", 0, false);

				if(postnatal)

				{				

				solo.clickOnText("后天", 0, false);

				bool = solo.waitForText("(?i).*?散场.*", 0, 1000);

				solo.sleep(1000);

					if(bool){

						solo.clickOnText("选座", 2);				

						solo.sleep(1000);

						boolean select=solo.searchText("(?i).*?1人.*");

						if(select==true){

							break;

						}else{

							clickById("action_bar_image");

							solo.sleep(1000);

							solo.scrollToTop();

							continue;

						}

					}else{

						continue;

					}

				}

				continue;

			case 1:

				//第二次循环选"明天"

				tomorrow = solo.searchText("明天", 0, false);

				if(tomorrow)

				{

					solo.clickOnText("明天", 0, false);

					bool = solo.waitForText("(?i).*?散场.*", 0, 1000);

					solo.sleep(1000);

					if(bool){

						solo.clickOnText("选座", 2);				

						solo.sleep(1000);

						boolean select=solo.searchText("(?i).*?1人.*");

						if(select==true){

							break;

						}else{

							clickById("action_bar_image");

							solo.sleep(1000);

							solo.scrollToTop();

							continue;

						}

					}else{

						continue;

					}

				}

				continue;

			case 2:

				//第三次循环选"今天"

				today = solo.searchText("今天", 0, false);

				if(today)

				{

					solo.clickOnText("今天", 0, false);

					bool = solo.waitForText("(?i).*?散场.*", 0, 1000);

					solo.sleep(1000);

					if(bool){

						solo.clickOnText("选座", 2);				

						solo.sleep(1000);

						boolean select=solo.searchText("(?i).*?1人.*");

						if(select==true){

							break;

						}else{

							clickById("action_bar_image");

							solo.sleep(1000);

							solo.scrollToTop();

							solo.sleep(1000);

							//循环三次即“后天”、“明天”、“今天”都没有选到座位，将循环i置为-1

							i=-1;

							//选择下一个影片

							boolean actual=solo.waitForWebElement(By.tagName("li"), 2,1000, false);

							boolean expected = true;

							assertEquals("没有下一部影片了", expected, actual);

							solo.clickOnWebElement(By.tagName("li"), 2);

							continue;						

						}

					}else{

						//循环三次即“后天”、“明天”、“今天”都没有选到座位，将循环i置为-1继续开始新一轮循环

						i=-1;

						solo.scrollToTop();

						//选择下一个影片

						solo.clickOnWebElement(By.tagName("li"), 2);

						solo.sleep(1000);

					}

				}

				continue;

			default:

				break;

			}		

		}



		solo.clickOnText("(?i).*?1人.*");



		solo.sleep(1000);



		solo.clickOnText("(?i).*?提交订单.*");



		solo.sleep(5000);

		

		TransferPayment();

		

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);



	}

	

	

	//个人中心——未支付订单是否正常调起支付

	public void test009UserCenterPendingOrder() throws Exception {

		solo.sleep(3000);

		//点击个人中心图标

		clickById("action_bar_personal_settings");

		solo.sleep(5000);

		solo.clickOnText("(?i).*?未支付的订单.*");

		solo.sleep(5000);

		

		boolean bool=solo.waitForText("总计");

		if(bool){

			solo.clickOnText("总计",0);

			solo.sleep(1000);		

			//未支付订单再次支付

			solo.clickOnText("付款",0);

			solo.sleep(1000);

			TransferPayment();

		}else{

		solo.goBack();

		solo.goBack();

		}

	}

	

	//个人中心——未支付订单循环删除待支付订单直到为空，验证删除未支付订单是否正常

	public void test010UserCenterDeleteOrder() throws Exception {



	solo.sleep(3000);



	//点击个人中心图标



	clickById("action_bar_personal_settings");



	solo.sleep(5000);



	solo.clickOnText("(?i).*?未支付的订单.*");



	solo.sleep(5000);





	boolean bool=solo.waitForText("总计");







	while(bool){



	solo.clickOnText("总计",0);



	solo.sleep(1000);





	//返回到未支付订单页面



	//solo.clickOnText("删除");



	boolean bool_del=solo.searchText("删除");



	if(bool_del){



	solo.clickOnWebElement(By.id("deleteOrder"));



	solo.sleep(1000);



	//clickByIdAndroid("button1");



	solo.clickOnButton("确定");



	solo.sleep(1000);



	//clickByIdAndroid("button3");



	solo.clickOnButton("确定");



	solo.sleep(5000);



	solo.goBack();



	solo.clickOnText("(?i).*?未支付的订单.*");



	bool=solo.waitForText("总计");



	solo.sleep(1000);



	}else{



	break;



	}



	}



	solo.goBack();



	solo.goBack();



	}

		//个人中心——全部订单&抽奖历史是否加载出列表页

		public void test011UserCenterAllOrder() throws Exception {

					solo.sleep(3000);

					//点击个人中心图标

					clickById("action_bar_personal_settings");

					solo.sleep(1000);

					solo.clickOnText("(?i).*?全部订单.*");

					solo.sleep(1000);

					boolean expected = true;

					boolean actual = solo.searchText("总计");

					assertEquals("总计  are not found", expected, actual);

					if(actual){

					solo.clickOnText("总计",0);

					boolean result = solo.searchText("本次团购内容")||solo.searchText("有效期")||solo.searchText("密码")||solo.searchText("订单")||solo.searchText("客服电话");

					assertEquals("订单详情页 are not found", true, result);

					solo.sleep(1000);

					solo.goBack();

					}

					solo.goBack();

					solo.goBack();

				}

		//个人中心——全部订单&抽奖历史是否加载出列表页

		public void test012UserCenterLotteryHistory() throws Exception {

					solo.sleep(3000);

					//点击个人中心图标

					clickById("action_bar_personal_settings");

					solo.sleep(1000);

					solo.clickOnText("(?i).*?抽奖历史.*");

					solo.sleep(1000);

					boolean expected = true;

					boolean actual = solo.searchText("抽奖号");

					assertEquals("抽奖号  are not found", expected, actual);

					if(actual){

						solo.clickOnText("抽奖号",0);

						boolean result = solo.searchText("中奖信息公布")||solo.searchText("抽奖号码");

						assertEquals("开奖页面 are not found", true, result);

						solo.sleep(1000);

						solo.goBack();

						}

					solo.goBack();

					solo.goBack();

				}

		

		//个人中心——我的抵用券(添加过抵用券)是否正常加载出来

		public void test013UserCenterMyTicket() throws Exception {

					solo.sleep(3000);

					//点击个人中心图标

					clickById("action_bar_personal_settings");

					solo.searchText("(?i).*?我的抵用券.*");

					solo.sleep(1000);

					solo.clickOnText("(?i).*?我的抵用券.*");

					solo.sleep(1000);

					boolean expected = true;

					boolean actual = solo.searchText("已获得的抵用券") && solo.searchText("有效期");

					assertEquals("已获得的抵用券&&有效期  are not found", expected, actual);

					solo.goBack();

				}

		//城市切换到“日照”，并查看窝窝美食团购是否正常调起支付

		public void test014UserCenterCurrentCity() throws Exception {

					solo.sleep(3000);

					//点击个人中心图标

					clickById("action_bar_personal_settings");

					solo.sleep(3000);

					solo.searchText("(?i).*?更多.*");

					solo.sleep(3000);

					solo.clickOnText("(?i).*?当前城市.*");

					solo.sleep(1000);					

					solo.clickOnText("(?i).*日照.*");

					solo.sleep(3000);

					adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

					solo.sleep(3000);

					test008Cate();

					adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

					solo.sleep(1000);

					adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

					solo.sleep(1000);

					adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

					solo.sleep(1000);

				}

		

		//城市切换到“北京”

		public void test015UserCenterReCurrentCity() throws Exception {

			solo.sleep(3000);

			boolean bool=solo.searchText("(?i).*?切换至北京.*",0,false);

			System.out.println("bool:-------------"+bool);

		

			if(bool){

				solo.sleep(1000);

				solo.clickOnButton("切换至北京");

				solo.sleep(1000);

			}else{

			//点击个人中心图标

			solo.sleep(3000);

			clickById("action_bar_personal_settings");

			solo.searchText("(?i).*?更多.*");

			solo.sleep(1000);

			solo.clickOnText("(?i).*?当前城市.*");

			solo.sleep(1000);

			

			solo.clickOnText("(?i).*北京.*");

			solo.sleep(1000);

			solo.goBack();

			solo.sleep(1000);

			}

}		

	// 通过布局里的resource-id获取对应的View并点击该View(resource-id位于SDK系统自带的android包名下)

	//(注意：这里的包名并不是act.getPackageName()=com.xiaomi.o2o)

		/*

		private boolean clickByIdAndroid(String id) throws Exception {



			if (id == "") {

				return false;

			}

			try {

				Activity act = solo.getCurrentActivity();// 获取当前的activity

				int view_id = act.getResources().getIdentifier(id, "id","android");// 获取id		

				View view = solo.getView(view_id);// 得到view

				solo.clickOnView(view);// 点击View

			} catch (Exception e) {

				e.printStackTrace();

			}

			return true;

		}

		*/

	// 通过布局里的resource-id获取对应的View并点击该View(resource-id位于当前Activity所在的包名下)

	private boolean clickById(String id) throws Exception {



		if (id == "") {

			return false;

		}

		try {

			Activity act = solo.getCurrentActivity();// 获取当前的activity

			int view_id = act.getResources().getIdentifier(id, "id",act.getPackageName());// 获取id

			View view = solo.getView(view_id);// 得到view

			//View view = act.findViewById(view_id);//这种方式弹框中的控件获取不到

			solo.clickOnView(view);// 点击View

		} catch (Exception e) {

			e.printStackTrace();

		}

		return true;

	}



	private void TransferPayment() throws Exception {

		// 使用“支付宝快捷支付”

		solo.sleep(1000);

		solo.clickOnText("(?i).*?支付宝快捷支付.*");

		solo.sleep(1000);

		solo.clickOnText("(?i).*?立即支付.*");



		solo.sleep(10000);

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(2000);

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(5000);

		/*

		// 点击支付宝弹出框中的"是"按钮

		try {

			Element element_yes= position.findElementByText("是");

			System.out.println("------------element_yes"+element_yes);

			adbDevice.tap(element_yes);

			solo.sleep(1000);

		} catch (Exception e) {

			

				Element retry=position.findElementByText("重试");

				if(retry!=null){

					adbDevice.tap(retry);

					solo.sleep(5000);

					solo.goBack();

					solo.sleep(1000);

					solo.goBack();

					solo.sleep(1000);

				Element element_yes= position.findElementByText("是");

					adbDevice.tap(element_yes);

					solo.sleep(1000);

			}

		}

		*/

		//Element element_yes= position.findElementByText("是");

		Element element_id= position.findElementById("android:id/button2");

		//adbDevice.tap(element_yes);

	

		adbDevice.tap(element_id);

		solo.sleep(2000);			

		// 使用“小米支付”



		//solo.clickOnText("(?i).*?小米支付");

		

		solo.clickOnWebElement(By.id("mipay"));

		solo.sleep(1000);

		solo.clickOnText("(?i).*?立即支付.*");



		solo.sleep(10000);



		// 如果在小米支付弹出框中找到"金额:"信息则可以通过，找不到该信息则失败



		//Element element_card = position.findElementByText("金额:");

		Element element_order_title = position.findElementById("com.mipay.wallet:id/order_title");

				System.out.println("element_order_title-------------"+element_order_title.toString());

				boolean expected_card = true;



				boolean actual_card = false;



				if (element_order_title != null) {

					actual_card = true;

				}

		assertEquals("小米支付id/order_title are not found", expected_card, actual_card);

		solo.sleep(1000);

				/*

				 *

				 * // 点击小米支付弹出框中的"确定"按钮

				 *

				 * Element element = position.findElementByText("确定");

				 *

				 * adbDevice.tap(element);

				 *

				 * solo.sleep(5000);

				 */



				// 用adbDevice点击手机“返回”按钮



		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);

		adbDevice.sendKeyEvent(KeyEvent.KEYCODE_BACK);

		solo.sleep(1000);

	}



	/*

	 *

	 * 使用adbDevice控制屏幕操作需要root权限。否则这一步会报错

	 *

	 * adbDevice.tap(550,660);//点击“请输入支付密码”编辑框位置坐标

	 *

	 * 填写6位支付密码,这里默认是123456,使用时把数字修改成自己的支付密码即可

	 *

	 * adbDevice.sendKeyEvent(KeyEvent.KEYCODE_1);

	 *

	 * adbDevice.sendKeyEvent(KeyEvent.KEYCODE_2);

	 *

	 * adbDevice.sendKeyEvent(KeyEvent.KEYCODE_3);

	 *

	 * adbDevice.sendKeyEvent(KeyEvent.KEYCODE_4);

	 *

	 * adbDevice.sendKeyEvent(KeyEvent.KEYCODE_5);

	 *

	 * adbDevice.sendKeyEvent(KeyEvent.KEYCODE_6);

	 *

	 * 用adbDevice点击屏幕坐标可以实现(点击“确定支付”-点击支付宝“返回”-点击“回到首页”) solo.sleep(1000);

	 *

	 * adbDevice.tap(550, 850); solo.sleep(5000); adbDevice.tap(550, 550);

	 *

	 * solo.sleep(5000); adbDevice.tap(300, 1800);

	 */



	@Override

	public void tearDown() throws Exception {

		//solo.finishOpenedActivities();

		try {

			solo.finalize();

		} catch (Throwable e) {

			e.printStackTrace();

		}

		 //getActivity().finish();

		 solo.finishOpenedActivities();

		 super.tearDown();

	}



	@Override

	protected void runTest() throws Throwable {

		System.out.println(">>>>>>>>>>>>>>>>>runTest()");

		int retryTimes = 3;



	    while(retryTimes > 0)

	    {

	        try{	        	

	        	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Robotium:super.runTest()_start");

	            //Log.d(">>>>>>>>>>>>>>>>>>>>>>>>>>Robotium", "super");

	            super.runTest();

	            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Robotium:super.runTest()_finish");

	            break;

	        } catch (Throwable e)

	        {

	            if(retryTimes > 1) {

	            	System.out.println(">>>>>>>>>>>>>>>>>>>>>>Robotium：重跑:"+retryTimes);

	                //Log.d(">>>>>>>>>>>>>>>>>>>>>>Robotium", "fail，重跑--"+retryTimes);

	                retryTimes--;

	                tearDown();	                	  

		            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Robotium:tearDown()");

	                setUp();

	                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Robotium:setUp()");

	                continue;

	            }

	            else

	                throw e;  //记得抛出异常，否则case永远不会失败	            

	        }

	    }

	}

}


