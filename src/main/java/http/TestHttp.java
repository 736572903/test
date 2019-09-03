package http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestHttp {

	public static void main(String[] args) {
		
		testGet();
//		testPost();
		
		testProxy();
	}

	private static void testProxy() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		HttpPost post = null;
		
		try {
			//代理ip 不能访问测试环境，就用了线上地址
			post = new HttpPost("http://callapi.ruifucredit.com/guanjia-manage-web/app/inter/contentpartpush/push.do");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("pushTitle", "123"));
			params.add(new BasicNameValuePair("pushContent", "456"));
			params.add(new BasicNameValuePair("pushJumpUrl", "ryx.xdbilllist.com"));
			params.add(new BasicNameValuePair("userId", "0"));
			
			HttpHost proxy = new HttpHost("114.221.40.46", 20003);
			RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
			post.setConfig(requestConfig);
			
//			post.setHeader("Proxy-Authorization","Basic eXVsb3JlOll1bG9yZVByb3h5MjAxMzo=");
			post.setHeader("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
			post.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
			post.setEntity(formEntity);
			response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println(String.format("代理post调用推送接口返回：%s",result));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null){
		        try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    if (httpClient != null){
		        try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}
	}

	private static void testPost() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		HttpPost post = null;
		
		try {
			post = new HttpPost("http://apitest.ruiyanhechuang.com/management/app/inter/common/contentpush/push.do");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("pushTitle", "123"));
			params.add(new BasicNameValuePair("pushContent", "456"));
			params.add(new BasicNameValuePair("pushJumpUrl", "ryx.xdbilllist.com"));
			params.add(new BasicNameValuePair("userId", "68"));
			
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
			post.setEntity(formEntity);
			response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println(String.format("post调用推送接口返回：%s",result));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        
	}

	private static void testGet() {
		CloseableHttpClient httpClient = HttpClients.createDefault(); 
		CloseableHttpResponse response = null;
		String result = "";
		
		HttpGet get = new HttpGet("http://apitest.ruiyanhechuang.com/management/app/inter/common/contentpush/push.do?pushTitle=123&pushContent=456&pushJumpUrl=ryx.xdbilllist.com&userId=68");
		try {
			response = httpClient.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(String.format("get调用推送接口返回：%s",result));
	}

}
