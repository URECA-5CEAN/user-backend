package com.ureca.ocean.jjh.client.dto;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandDto {
    private UUID id;
    private String name;
    private String image_url;

}