package com.device;

/**
 * 
 * @ClassName: Device
 * @Description:指纹操作、RFID操作
 * @author: lsy
 * @date: 2015年11月22日 上午9:40:37
 * 
 */
public class Device {
	static {
		try {
			System.loadLibrary("Device");
		} catch (UnsatisfiedLinkError e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getImage
	 * @Description: 获取指纹图片，用于注册指纹
	 * @param: @param timeout 超时时间
	 * @param: @param finger 指纹图片数据， 长度[2000+152*200]
	 * @param: @param message 错误信息，[100]
	 * @param: @return
	 * @return: int 返回为0获取指纹图像成功；其他获取指纹图像失败
	 * @throws
	 */
	public native static int getImage(int timeout, byte[] finger, byte[] message);

	/**
	 * 
	 * @Title: getFinger
	 * @Description: 获取指纹特征，用于比对指纹
	 * @param: timeout 超时时间
	 * @param: finger 指纹特征 长度， [256]
	 * @param: message 错误信息，[100]
	 * @param: @return
	 * @return: int 返回为0获取指纹特征成功；其他获取指纹特征失败
	 * @throws
	 */
	public native static int getFinger(int timeout, byte[] finger,
			byte[] message);

	/**
	 * 
	 * @Title: ImageToFeature
	 * @Description: 将指纹图像转成特征，用于注册指纹时校验指纹
	 * @param: @param image 指纹图片 ，[2000+152*200]
	 * @param: @param feature 指纹特征， 长度[256]
	 * @param: @param message错误信息[100]
	 * @param: @return
	 * @return: int 返回为0成功；其他失败
	 * @throws
	 */
	public native static int ImageToFeature(byte[] image, byte[] feature,
			byte[] message);

	/**
	 * 
	 * @Title: FeatureToTemp
	 * @Description: 特征转成模板，用于注册指纹
	 * @param: @param tz1 第一个指纹特征 ，长度[256]
	 * @param: @param tz2 第二个指纹特征，长度[256]
	 * @param: @param tz3 第三个指纹特征，长度[256]
	 * @param: @param mb 指纹模板，长度[256]
	 * @param: @param message 错误信息，长度[100]
	 * @param: @return
	 * @return: int 返回为0成功；其他失败
	 * @throws
	 */
	public native static int FeatureToTemp(byte[] tz1, byte[] tz2, byte[] tz3,
			byte[] mb, byte[] message);

	/**
	 * 
	 * @Title: verifyFinger
	 * @Description: 指纹比对
	 * @param: @param mbFinger 指纹模板，长度[256]
	 * @param: @param tzFinger 指纹特征，长度[256]
	 * @param: @param level 默认为3
	 * @param: @return
	 * @return: int 返回为0成功；其他失败
	 * @throws
	 */
	public native static int verifyFinger(String mbFinger, String tzFinger,
			int level);

	/**
	 * 
	 * @Title: verifyBinFinger
	 * @Description: 用于校验指纹
	 * @param: @param mbFinger 指纹模板，长度[256]
	 * @param: @param tzFinger 指纹特征，长度[256]
	 * @param: @param level 默认为3
	 * @param: @return
	 * @return: int 返回为0成功；其他失败
	 * @throws
	 */
	public native static int verifyBinFinger(byte[] mbFinger, byte[] tzFinger,
			int level);

	public native static int getRfid(int timeout,  byte[] epcid, byte[] message);

	public native static int cancel();

	public native static int openRfid();

	public native static int closeRfid();

	/**
	 * 
	 * @Title: openFinger
	 * @Description: 打开指纹
	 * @param: @return
	 * @return: int 返回为0成功；其他失败
	 * @throws
	 */
	public native static int openFinger();

	/**
	 * 
	 * @Title: closeFinger
	 * @Description: 关闭指纹
	 * @param: @return
	 * @return: int 返回为0成功；其他失败
	 * @throws
	 */
	public native static int closeFinger();
}
