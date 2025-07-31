package com.ureca.ocean.jjh.map.client;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.common.constant.DomainConstant;
import com.ureca.ocean.jjh.map.dto.StoreUsageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Service
public class MapClient {
    private final RestTemplate restTemplate;

    public List<StoreUsageDto> getStoreUsageEmail(String email) {
        String url = DomainConstant.MAP_URL + "/api/map/usage";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-email", URLEncoder.encode(email, StandardCharsets.UTF_8));
        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<BaseResponseDto<List<StoreUsageDto>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<BaseResponseDto<List<StoreUsageDto>>>() {}
        );

        return Objects.requireNonNull(response.getBody()).getData();
    }
}
