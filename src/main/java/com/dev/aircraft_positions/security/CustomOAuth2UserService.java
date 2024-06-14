package com.dev.aircraft_positions.security;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dev.aircraft_positions.domain.Member;
import com.dev.aircraft_positions.domain.OAuth2UserInfo;
import com.dev.aircraft_positions.domain.PrincipalDetails;
import com.dev.aircraft_positions.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final MemberRepository memberRepository;

	public CustomOAuth2UserService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	 
	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// 1. 유저 정보(attributes) 가져오기
		Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
		// 2. resistrationId 가져오기 (third-party id)
		String regId = userRequest.getClientRegistration().getRegistrationId();
        //3. userNameAttributeName 가져오기
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		if (log.isDebugEnabled()) {
			log.debug("#############loadUser####################");
			log.debug("regId: {}", regId);
			log.debug("userNameAttributeName: {}", userNameAttributeName);
			
			log.debug(oAuth2UserAttributes.toString());
			
			for(Map.Entry<String, Object> entiry: oAuth2UserAttributes.entrySet()) {
				log.debug("userInfo key: {}", entiry.getKey());
				log.debug("userInfo value: {}", entiry.getValue());
			}
		}
		
		// 4. 유저 정보 dto 생성
		OAuth2UserInfo oauth2UserInfo = OAuth2UserInfo.of(regId, oAuth2UserAttributes);
		
		Member member = getOrSave(oauth2UserInfo);
		
		//6. OAuth2User로 반환
		return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
	}

	private Member getOrSave(OAuth2UserInfo oauth2UserInfo) {
		
		Member member = this.memberRepository.findByEmail(oauth2UserInfo.email());
		
		if(member == null) {
			//oAuth2UserInfo.toEntity()
			member = oauth2UserInfo.toEntity();
		}
		
		return this.memberRepository.save(member);
	}

	
	
	
	
}
