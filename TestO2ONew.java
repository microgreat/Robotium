package com.xiaomi.o2o.test;

import java.io.DataOutputStream;
import java.io.OutputStream;

import xuxu.autotest.AdbDevice;
import xuxu.autotest.element.Position;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import com.robotium.solo.By;
import com.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public class TestO2ONew extends ActivityInstrumentationTestCase2 {

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
	public TestO2ONew() throws ClassNotFoundException {

		super(launcherActivityClass);
	}

	private Solo solo;
	private AdbDevice adbDevice;
	private Position position;
	@Override
	protected void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());
		adbDevice=new AdbDevice();
		position=new Position();
		
		solo.sendKey(KeyEvent.KEYCODE_POWER);
		//屏幕解锁
		solo.unlockScreen();
	}
	
	// 小米生活首次进入点击CTA弹框的同意按钮
	public void test000CTA() throws Exception {
		solo.sleep(3000);
		boolean bool=solo.searchText("同意并继续",0,false);
		if(bool){
			solo.clickOnText("同意并继续");
			solo.sleep(5000);
			boolean expected = true;
			boolean actual=solo.searchText("精选",0,false)&& solo.searchText("我的",0,false);
			assertEquals("精选、我的··· are not found", expected, actual);
		}else{
			System.out.println("非首次进入，没有CTA弹框！");
		}
	}
	// 如果小米生活首页有广告悬浮窗就点击X按钮关闭掉它
		public void test000LayerClose() throws Exception {
			
			solo.sleep(3000);	
			boolean bool=solo.waitForWebElement(By.className("campaign-layer-close"));
			if(bool){
				solo.clickOnWebElement(By.className("campaign-layer-close"), 0);
				solo.sleep(5000);
			}else{
				System.out.println("campaign-layer-close are not found");
			}
	}
	// 测试小米生活首页是否有“精选、爆品专区···”
	public void test001Tab() throws Exception {
		solo.sleep(3000);	
		boolean expected = true;
		boolean actual = solo.searchText("爆品专区")&& solo.searchText("购物折扣街")&& solo.searchText("便捷生活")&& solo.searchText("精选内容")&& solo.searchText("查看全部团购");
		assertEquals("爆品专区、购物折扣街···  are not found", expected, actual);
		
	}
	
	//测试【点评团购】页面是否正常加载
	public void test002dianping() throws Exception {
		solo.sleep(3000);
		solo.clickOnText("团购",0);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("全部分类")&&solo.searchText("已售");
		assertEquals("全部分类or已售  are not found", expected, actual);
		
	}
	
	//测试【百度电影】页面是否正常加载
	public void test003bdmovie() throws Exception {
		solo.sleep(3000);
		solo.clickOnText("电影",0);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("影片")&&solo.searchText("客服电话");
		assertEquals("影片or分钟  are not found", expected, actual);
		
	}
	
	//测试【外卖】页面是否正常加载
	public void test004waimai() throws Exception {
		solo.sleep(3000);
		solo.clickOnText("外卖",0);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("全部菜系")&&solo.searchText("送达");
		assertEquals("全部菜系or送达  are not found", expected, actual);
		
	}
	
	//测试【彩票】页面是否正常加载
	public void test005caipiao() throws Exception {
		solo.sleep(3000);
		solo.clickOnText("彩票",0);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("双色球")&&solo.searchText("我的彩票");
		assertEquals("双色球or我的彩票  are not found", expected, actual);
		
	}
	
	//测试【爆品专区】页面是否正常加载
	public void test006baopin() throws Exception {
		solo.sleep(3000);
		solo.clickOnWebElement(By.className("mod-7-item-inner"), 0);
		solo.sleep(3000);
		
		solo.clickOnText("10点场");
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("10点场")&&solo.searchText("¥");
		assertEquals("10点场 or ¥ are not found", expected, actual);

		solo.clickOnText("14点场");
		solo.sleep(3000);
		boolean expected1 = true;
		boolean actual1 = solo.searchText("14点场")&&solo.searchText("¥");
		assertEquals("14点场 or ¥ are not found", expected1, actual1);

		solo.clickOnText("下期预告");
		solo.sleep(3000);
		boolean expected2 = true;
		boolean actual2 = solo.searchText("下期预告")&&solo.searchText("¥");
		assertEquals("下期预告 or ¥ are not found", expected2, actual2);

	}

	//测试【9.9包邮】页面是否正常加载
	public void test007zhe800() throws Exception {
		solo.sleep(3000);
		//solo.clickOnText("9.9包邮",0);
		//mod-4-part1-l第1个是zhe800页面
		solo.clickOnWebElement(By.className("mod-4-part1-l-inner border-tblr highlight action-item"),1);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("9块9包邮")&&solo.searchText("¥");
		assertEquals("9块9包邮 or ¥ are not found", expected, actual);
		
	}
	
	//测试【送礼攻略】页面是否正常加载
	public void test008shengri() throws Exception {
		solo.sleep(3000);
		//solo.clickOnText("送礼攻略",0);
		//mod-4-part1-r第1个是生日管家页面
		solo.clickOnWebElement(By.className("mod-4-part1-r-item border-tblr highlight action-item"),1);		
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("配送至") &&solo.searchText("查看更多");
		assertEquals("配送至 or 查看更多  are not found", expected, actual);
		
	}	
	
	//测试【门票玩乐】页面是否正常加载
	public void test009ctripticket() throws Exception {
		//景点门票Tab
		solo.sleep(3000);
		//solo.clickOnText("门票玩乐",0);
		//mod-4-part1-r第2个是携程门票玩乐页面
		solo.clickOnWebElement(By.className("mod-4-part1-r-item border-tblr highlight action-item"),2);		
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("景点门票") &&solo.searchText("¥");
		assertEquals("景点门票 or ¥ are not found", expected, actual);
		//当地玩乐Tab
		solo.clickOnText("当地玩乐",0);
		solo.sleep(3000);
		boolean expected1 = true;
		boolean actual1 = solo.searchText("一日游") &&solo.searchText("¥");
		assertEquals("一日游 or ¥ are not found", expected1, actual1);		
		
	}
	
	//测试【1元叫上门】页面是否正常加载
	
	public void test010daojia() throws Exception {
		solo.sleep(3000);
		//mod-4-part2-inner第1个是到家页面
		solo.clickOnWebElement(By.className("mod-4-part2-inner border-tblr highlight action-item"),1);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("人好评") &&solo.searchText("￥");
		assertEquals("到家：(人好评  or ¥) are not found", expected, actual);
		
	}
	
	
	//测试【1元叫上门】页面是否正常加载
	/*
	public void test010weipinhui() throws Exception {
		solo.sleep(3000);
		solo.clickOnText("品牌特卖",0);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("精选") &&solo.searchText("小时");
		assertEquals("唯品会：(精选  or 小时) are not found", expected, actual);
		
	}
	*/
	//测试【贝贝母婴】页面是否正常加载
	public void test011beibei() throws Exception {
		solo.sleep(3000);
		//mod-4-part2-inner第2个是贝贝母婴页面
		solo.clickOnWebElement(By.className("mod-4-part2-inner border-tblr highlight action-item"),2);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("上新") &&solo.searchText("更多分类");
		assertEquals("上新 or 更多分类  are not found", expected, actual);
		
	}
	
	//测试【蘑菇街】页面是否正常加载
	public void test012mogujie() throws Exception {
		solo.sleep(3000);
		//mod-4-part2-inner第3个是蘑菇街页面
		solo.clickOnWebElement(By.className("mod-4-part2-inner border-tblr highlight action-item"),3);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual = solo.searchText("精选") &&solo.searchText("¥");
		assertEquals("mogujie:精选 or ¥  are not found", expected, actual);
		
	}
	
	//测试【必要商城】页面是否正常加载
	public void test013biyao() throws Exception {
		solo.sleep(3000);
		//mod-4-part2-inner第4个是必要商城页面
		solo.clickOnWebElement(By.className("mod-4-part2-inner border-tblr highlight action-item"),4);
		solo.sleep(3000);
		boolean expected = true;
		boolean actual =solo.waitForWebElement(By.className("m_slider"));
		assertEquals("必要商城:页面元素 are not found", expected, actual);
		
	}
	
	
	@Override
	public void tearDown() throws Exception {
		// solo.finishOpenedActivities();
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// getActivity().finish();
		solo.finishOpenedActivities();
		super.tearDown();
	}
	/**
	 * 执行shell命令
	 *  
	 * @param cmd
	 */  
	private void execShellCmd(String cmd) {  
	  
	    try {  
	        // 申请获取root权限，这一步很重要，不然会没有作用  
	        Process process = Runtime.getRuntime().exec("su");  
	        // 获取输出流  
	        OutputStream outputStream = process.getOutputStream();  
	        DataOutputStream dataOutputStream = new DataOutputStream(  
	                outputStream);  
	        dataOutputStream.writeBytes(cmd);  
	        dataOutputStream.flush();  
	        dataOutputStream.close();  
	        outputStream.close();  
	    } catch (Throwable t) {  
	        t.printStackTrace();  
	    }  
	}
	@Override
	protected void runTest() throws Throwable {
		System.out.println(">>>>>>>>>>>>>>>>>runTest()");
		int retryTimes = 3;

		while (retryTimes > 0) {
			try {
				super.runTest();
				System.out.println(">>>>>>>>>>>>>>>Robotium:super.runTest()");
				break;
			} catch (Throwable e) {
				if (retryTimes > 1) {
					System.out.println(">>>>>>>>>>>>>>>Robotium：重跑>>>第"
							+ retryTimes+"次");
					retryTimes--;
					tearDown();
					setUp();
					continue;
				} else
					throw e; // 记得抛出异常，否则case永远不会失败
			}
		}
	}
}

