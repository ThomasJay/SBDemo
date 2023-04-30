package com.thomasjayconsulting.sbdemo.service;

import com.opsmatters.bitly.Bitly;
import com.opsmatters.bitly.api.model.v4.CreateBitlinkResponse;
import com.thomasjayconsulting.sbdemo.dto.BitlyResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class BitlyServiceImpl implements BitlyService {

    @Value("${bitly_token}")
    String BITLY_TOKEN;

    private Bitly client;

    @PostConstruct
    public void setup() {
        client = new Bitly(BITLY_TOKEN);
    }



    @Override
    public BitlyResponseDTO processBitly(String longURL) {
        String link = "error";

        try {
            CreateBitlinkResponse response = client.bitlinks().shorten(longURL).get();

            link = response.getLink();
        }
        catch (Exception e) {
            log.error("Bitly error e: ", e);
        }

        BitlyResponseDTO bitlyResponseDTO = new BitlyResponseDTO();

        bitlyResponseDTO.setShortenedURL(link);

        return bitlyResponseDTO;
    }
}
