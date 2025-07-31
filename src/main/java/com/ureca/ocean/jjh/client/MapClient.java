package com.ureca.ocean.jjh.client;

import com.ureca.ocean.jjh.client.dto.BrandDto;
import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.common.constant.DomainConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapClient {
    private final RestTemplate restTemplate;

    public List<String> getUserByEmail(String email) {
        String url = DomainConstant.MAP_URL + "api/map/brand?sortBy=asc";
        System.out.println("url : "+ url);
        ResponseEntity<BaseResponseDto<List<String>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseResponseDto<List<String>>>() {}
        );
        return response.getBody().getData();
    }

    //brandId로 brandName 가져오는 메서드
    public BrandDto getBrandNameById(UUID brandId) {
        String url = DomainConstant.MAP_URL + "api/map/brandNameById?id=" + brandId;
        ResponseEntity<BaseResponseDto<BrandDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseResponseDto<BrandDto>>() {}
        );
        return response.getBody().getData();
    }



    //benefitId로 benefitName 가져오는 메서드
    public String getBenefitNameById(UUID benefitId) {
        String url = DomainConstant.MAP_URL + "api/map/benefitNameById?id=" + benefitId;
        ResponseEntity<BaseResponseDto<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseResponseDto<String>>() {}
        );
        return response.getBody().getData();
    }
}
