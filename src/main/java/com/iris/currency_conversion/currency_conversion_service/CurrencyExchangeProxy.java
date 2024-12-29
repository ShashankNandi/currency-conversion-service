package com.iris.currency_conversion.currency_conversion_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "localhost:8000") //this annotation makes req to currency exchange service running on port 8000
@FeignClient(name = "currency-exchange")  // this makes request to any currency exchange service running and by default the load balancer on feign balances the load b/w differenet instances of the exchange service

public interface CurrencyExchangeProxy {

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion getCurrencyExchange(@PathVariable String from, @PathVariable String to) ;

}
