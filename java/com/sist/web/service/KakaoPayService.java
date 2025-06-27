package com.sist.web.service;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sist.common.util.StringUtil;
import com.sist.web.model.KakaoPayApproveRequest;
import com.sist.web.model.KakaoPayApproveResponse;
import com.sist.web.model.KakaoPayReadyRequest;
import com.sist.web.model.KakaoPayReadyResponse;

@Service("kakaoPayService")
public class KakaoPayService 
{
	private static Logger logger = LoggerFactory.getLogger(KakaoPayService.class);
	//client id
	@Value("#{env['kakaopay.client.id']}")
	private String KAKAOPAY_CLIENT_ID;
	
	//client secret
	@Value("#{env['kakaopay.client.secret']}")
	private String KAKAOPAY_CLIENT_SECRET;

	//secret key
	@Value("#{env['kakaopay.secret.key']}")
	private String KAKAOPAY_SECRET_KEY;

	//ready url
	@Value("#{env['kakaopay.ready.url']}")
	private String KAKAOPAY_READY_URL;

	//approval url
	@Value("#{env['kakaopay.approval.url']}")
	private String KAKAOPAY_APPROVAL_URL;

	//결제 성공시 url
	@Value("#{env['kakaopay.client.success.url']}")
	private String KAKAOPAY_CLIENT_SUCCESS_URL;

	//결제 취소시 url
	@Value("#{env['kakaopay.client.cancel.url']}")
	private String KAKAOPAY_CLIENT_CANCEL_URL;

	//결제 실패시 url
	@Value("#{env['kakaopay.client.fail.url']}")
	private String KAKAOPAY_CLIENT_FAIL_URL;
	
	//카카오페이 request 헤더
	private HttpHeaders kakaoPayHeaders;
	
	
	
	//카카오페이 Request 헤더 설정
	@PostConstruct
	private void postConstruct()
	{
		kakaoPayHeaders = new HttpHeaders();
		kakaoPayHeaders.set("Authorization", "SECRET_KEY " + KAKAOPAY_SECRET_KEY);
		kakaoPayHeaders.set("Content-Type", "application/json");
	}
	
	//카카오페이 Ready메서드 호출 api (결제 준비)
	public KakaoPayReadyResponse ready(KakaoPayReadyRequest kakaoPayReadyRequest)
	{
		KakaoPayReadyResponse kakaoPayReadyResponse = null;
		
		StringBuilder log = new StringBuilder();
		
		log.append("\n=====================");
		log.append("\n#[KakaoPayService]ready");
		log.append("\n=====================");
		
		//각각 카카오에서 요구하는 파라미터 조립(각각 필수냐 선택이냐에 따라)
		if(kakaoPayReadyRequest != null)
		{
			log.append(kakaoPayReadyRequest.toString());
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("cid", KAKAOPAY_CLIENT_ID);	//가맹점 코드 10자리
			
			if(!StringUtil.isEmpty(kakaoPayReadyRequest.getCid_secret()))
			{
				parameters.put("cid_secret", kakaoPayReadyRequest.getCid_secret());
			}
			
			parameters.put("partner_order_id", kakaoPayReadyRequest.getPartner_order_id());
			parameters.put("partner_user_id", kakaoPayReadyRequest.getPartner_user_id());
			parameters.put("item_name", kakaoPayReadyRequest.getItem_name());
			
			
			if (!StringUtil.isEmpty(kakaoPayReadyRequest.getItem_code())) 
			{
			    parameters.put("item_code", kakaoPayReadyRequest.getItem_code());
			}
			
			parameters.put("quantity", kakaoPayReadyRequest.getQuantity());
			parameters.put("total_amount", kakaoPayReadyRequest.getTotal_amount());
			parameters.put("tax_free_amount", kakaoPayReadyRequest.getTax_free_amount());

			if(kakaoPayReadyRequest.getVat_amount() > 0)
			{
				parameters.put("vat_amount", kakaoPayReadyRequest.getVat_amount());
			}
			
			if(kakaoPayReadyRequest.getGreen_deposit() > 0)
			{
				parameters.put("green_deposit", kakaoPayReadyRequest.getGreen_deposit());
			}
			
			parameters.put("approval_url", KAKAOPAY_CLIENT_SUCCESS_URL);
			parameters.put("cancel_url", KAKAOPAY_CLIENT_CANCEL_URL);
			parameters.put("fail_url", KAKAOPAY_CLIENT_FAIL_URL);

			
			if (!StringUtil.isEmpty(kakaoPayReadyRequest.getAvailable_cards())) 
			{
			    parameters.put("available_cards", kakaoPayReadyRequest.getAvailable_cards());
			}
			
			
			if (!StringUtil.isEmpty(kakaoPayReadyRequest.getPayment_method_type())) 
			{
			    parameters.put("payment_method_type", kakaoPayReadyRequest.getPayment_method_type());
			}
			
			if(kakaoPayReadyRequest.getInstall_month() > 0)
			{
				parameters.put("install_month", kakaoPayReadyRequest.getInstall_month());
			}
			
			if (!StringUtil.isEmpty(kakaoPayReadyRequest.getUse_share_installment())) 
			{
			    parameters.put("use_share_installment", kakaoPayReadyRequest.getUse_share_installment());
			}
			
			if (!StringUtil.isEmpty(kakaoPayReadyRequest.getCustom_json())) 
			{
			    parameters.put("custom_json", kakaoPayReadyRequest.getCustom_json());
			}
			
			logger.debug("1111111111111111111111");
			
			
			
			//Http Header Http Body 설정
			//요청하기 위해 header와 body 합치기
			//spring framework에서 제공해주는 HttpEntity클래스에 header 와 body를 합치기
			HttpEntity<Map<String,Object>> requestEntity = 
					new HttpEntity<Map<String,Object>>(parameters, kakaoPayHeaders);
			
			//spring에서 제공하는 http통신 처리를 위한 유틸리티 클래스
			//클라이언트 쪽에서 http 요청을 만들고, 서버에 응답을 처리하는데 유용함.
			RestTemplate restTemplate = new RestTemplate();
			
			//postForEntity : POST 요청을 보내고, 응답을 ResponseEntity 로 반환 받는다.
			ResponseEntity<KakaoPayReadyResponse> responseEntity = restTemplate.postForEntity(KAKAOPAY_READY_URL, requestEntity, KakaoPayReadyResponse.class);
			logger.debug("2222222222222222222222");
			
			if(responseEntity != null)
			{
				log.append("\nready statusCode : " + responseEntity.getStatusCodeValue());
				
				kakaoPayReadyResponse = responseEntity.getBody();
				
				if(kakaoPayReadyResponse != null)
				{
					log.append("\nready body : \n" + kakaoPayReadyResponse);
				}
				else
				{
					log.append("\nready body : body is null" );
				}
			}
			else
			{
				log.append("\nready : ResponseEntity is null");
			}
			
		}
		
		log.append("\n=====================");
		logger.info(log.toString());

		return kakaoPayReadyResponse;
	}
	
	//카카오페이 결제 승인 요청
		public KakaoPayApproveResponse approve(KakaoPayApproveRequest kakaoPayApproveRequest)
		{
			KakaoPayApproveResponse kakaoPayApproveResponse = null;
			
			StringBuilder log = new StringBuilder();
			
			log.append("\n========================");
			log.append("\n=[KakaoPayService] approve");
			log.append("\n========================");
			
			if(kakaoPayApproveRequest != null)
			{
				log.append(kakaoPayApproveRequest.toString());
				
				Map<String, Object> parameters = new HashMap<String, Object>();
				
				parameters.put("cid", KAKAOPAY_CLIENT_ID);	
				
				if(!StringUtil.isEmpty(kakaoPayApproveRequest.getCid_secret()))
				{
					parameters.put("cid_secret", kakaoPayApproveRequest.getCid_secret());	

				}
				parameters.put("tid", kakaoPayApproveRequest.getTid());	
				parameters.put("partner_order_id", kakaoPayApproveRequest.getPartner_order_id());	
				parameters.put("partner_user_id", kakaoPayApproveRequest.getPartner_user_id());	
				parameters.put("pg_token", kakaoPayApproveRequest.getPg_token());	

				if(!StringUtil.isEmpty(kakaoPayApproveRequest.getPayload()))
				{
					parameters.put("payload", kakaoPayApproveRequest.getPayload());	

				}		
					
				if(kakaoPayApproveRequest.getTotal_amount() > 0)
				{
					parameters.put("total_amount", kakaoPayApproveRequest.getTotal_amount());	

				}
				HttpEntity<Map<String, Object>> requestEntity =
						new HttpEntity<Map<String, Object>>(parameters, kakaoPayHeaders);
						
				RestTemplate restTemplate = new RestTemplate();
				
				ResponseEntity<KakaoPayApproveResponse> responseEntity =
						restTemplate.postForEntity(KAKAOPAY_APPROVAL_URL, requestEntity, KakaoPayApproveResponse.class);
				
				if(responseEntity != null)
				{
					kakaoPayApproveResponse = responseEntity.getBody();
					
					if(kakaoPayApproveResponse != null)
					{
						log.append("\napprove body : \n" + kakaoPayApproveResponse);

					}
					else
					{
						log.append("\napprove body : body is null");

					}
				}
				else
				{
					log.append("\napprove : ResponseEntity is null");
				}
			
			}
			logger.info(log.toString());
			
			return kakaoPayApproveResponse;
		}
			
		
		
		
}
