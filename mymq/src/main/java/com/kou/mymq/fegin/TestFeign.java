package com.kou.mymq.fegin;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "demo",url = "192.168.4.67:28086")
public interface TestFeign {

    @GetMapping("api/admin/index/v3/equip-area-distribution")
    String test(@RequestParam Integer isMainPage);

}
