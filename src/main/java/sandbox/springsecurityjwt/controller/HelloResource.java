package sandbox.springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandbox.springsecurityjwt.model.AuthenticationRequest;
import sandbox.springsecurityjwt.model.AuthenticationResponse;
import sandbox.springsecurityjwt.util.JwtUtil;

@RestController
public class HelloResource {

  @Autowired
  UserDetailsService appUserDetailsService;

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @RequestMapping("/hello")
  public String hello() {
    return "Hello resource";
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) {

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getUsername());
    String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));

  }

}
