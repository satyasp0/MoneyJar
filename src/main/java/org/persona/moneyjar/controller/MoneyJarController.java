package org.persona.moneyjar.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Satya
 * @created 04/07/2024 - 09:53
 **/
@RestController
@RequestMapping("/")
public class MoneyJarController {

//    private UserService userService;
//
//    public MoneyJarController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("test")
    public String index() {
        return "Hello World";
    }

    @GetMapping("hello")
    public Object auth(OAuth2AuthenticationToken token){
        return token.getPrincipal().getAttributes();
    }

//    @GetMapping("user")
//    public ResponseEntity<User> findUser(@RequestParam String userName){
//        User user = userService.findByUsername(userName);
//        if(user == null){
//            return ResponseEntity.notFound().build();
//        }else {
//            return ResponseEntity.ok(user);
//        }
//    }

}
