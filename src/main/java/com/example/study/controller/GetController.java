package com.example.study.controller;

import com.example.study.model.SearchParam;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController  // controller(API의 주소를 모아두는 장소)로 활용한다고 명시하는 어노테이션
@RequestMapping("/api")  // Localhost:8080/api
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod") // Localhost:8080/api/getMethod
    public String getRequest() {
        return "Hi getMethod";
    }

    // RequestMapping과는 다르게, method를 따로 지정하지 않아도 된다.
    @GetMapping("/getParameter") // Localhost:8080/api/getParameter?id=1234&password=abcd
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd) {

        /* 지역변수명을 정할 때, 어쩔 수 없이 @RequestParam으로 지정한 파라미터 이름을 써야할 수 있다.
           이럴 경우, @RequestParam(name = "abc")를 통해, 실제 웹상에서 전달되는 파라미터 이름을 지정할 수 있다.
         */
        String password = "bbbb";

        System.out.println("id : " + id);
        System.out.println("pwd : " + pwd);

        return id + pwd;
    }

    // Localhost:8080/api/getMultiParameter?account=abcd&email=study@gmail.com&page=10
    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam) {
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());

//      객체를 반환하면 자동으로 JSON 포멧으로 변경하여 반환해줌 { "account" : "", "email" : "", "page" : 0}  JSON Format <- auto formmated by Jackson Library
        return searchParam;
    }

    @GetMapping("/header")
    public Header header() {
        // {"resultCode" : "OK", "description" : "OK"}
        return Header.builder().resultCode("OK").description("OK").build();
    }

}
