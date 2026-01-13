package com.e_commerce.e_app.api;

import com.e_commerce.e_app.dto.response.ResponseUser;
import com.e_commerce.e_app.dto.user.UserPublicId;
import com.e_commerce.e_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

   @GetMapping("/authenticated")
    public ResponseEntity<ResponseUser> authenticateUser(@AuthenticationPrincipal Jwt jwt,
                                                        @RequestParam boolean forceResync)
   {
       System.out.println("JWT " + jwt.getClaims());
        return ResponseEntity.ok(ResponseUser.toResponseUser(userService.save(jwt, forceResync)));
   }

   @GetMapping("/get-by-publicId/")
   public ResponseEntity<ResponseUser> getAuthenticateUserByPublicId(@RequestParam("publicId") UUID id)
   {
      return ResponseEntity.
              ok(ResponseUser.toResponseUser(userService.getByPublicId(new UserPublicId(id))));
   }


}
