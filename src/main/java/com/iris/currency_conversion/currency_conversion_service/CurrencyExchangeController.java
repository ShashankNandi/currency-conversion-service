package com.iris.currency_conversion.currency_conversion_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeProxy proxy;



    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getConvertedCurrency(@PathVariable("from") String from,   @PathVariable("to") String to,   @PathVariable("quantity") int quantity) {
        CurrencyConversion  cnv= new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, from, to).getBody();
        BigDecimal qty=BigDecimal.valueOf(quantity);
        BigDecimal calculatedAmt=qty.multiply( cnv.getConversionMultiple());
        return new CurrencyConversion(cnv.getId(), from, to, qty, cnv.getConversionMultiple(),calculatedAmt, cnv.getEnvironment()+" rest template");

//        return new CurrencyConversion(1000L,from, to, BigDecimal.valueOf(quantity),BigDecimal.valueOf(85),  BigDecimal.valueOf(85*quantity)  );
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getConvertedCurrency2(@PathVariable("from") String from,   @PathVariable("to") String to,   @PathVariable("quantity") int quantity) {
        CurrencyConversion cnv = proxy.getCurrencyExchange(from, to);
        BigDecimal qty=BigDecimal.valueOf(quantity);
        BigDecimal calculatedAmt=qty.multiply( cnv.getConversionMultiple());
        return new CurrencyConversion(cnv.getId(), from, to, qty, cnv.getConversionMultiple(),calculatedAmt, cnv.getEnvironment()+" feign");

    }
}
