package com.zwc.Boke.util;

import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@Service
public class SmsUtils{

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIGlFgnAeAhi4M";
    static final String accessKeySecret = "vd48YjYRu3XAlzLiDTpqnMjZw31bM8";
    
    static final String smsTemplateAddress = "SMS_140550942";
    
    static final String signName = "朱伟超";
    
    
    public static void main(String[] args){
    	
    	Boolean bool = sendMessage("18378919121", "18378919121");
    	System.out.println(bool);
    }
    
	/*
	 * 短信验证码发送接口
	 * @param smsMob
	 * @param smsText
	 * @throws Exception
	 */
	public static boolean sendMessage(String phoneNumber,String tel){
		
		try{
			//可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			
			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			
			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(phoneNumber);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(smsTemplateAddress);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam("{\"name\":\"【天猫超市】\",\"tel\":\""+tel+"\"}");
			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			//request.setOutId("yourOutId");
			
			//hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			System.out.println("短信接口返回的数据----------------");
	        System.out.println("Code=" + sendSmsResponse.getCode());
	        System.out.println("Message=" + sendSmsResponse.getMessage());
	        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
	        System.out.println("BizId=" + sendSmsResponse.getBizId());
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * 短信体验馆地址发送接口
	 * @param smsMob
	 * @param smsText
	 * @throws Exception
	 */
	public static boolean sendAddressMessage(String phoneNumbers,String province,String address){
		
		try{
			//可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			
			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			
			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(phoneNumbers);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(smsTemplateAddress);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam("{\"province\":\""+province+"\",\"address\":\""+address+"\"}");
			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			//request.setOutId("yourOutId");
			
			//hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			/*System.out.println("短信接口返回的数据----------------");
	        System.out.println("Code=" + sendSmsResponse.getCode());
	        System.out.println("Message=" + sendSmsResponse.getMessage());
	        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
	        System.out.println("BizId=" + sendSmsResponse.getBizId());*/
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}