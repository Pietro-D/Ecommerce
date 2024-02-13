package ecommerce.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.backend.configurations.UserDetailsImpl;
import ecommerce.backend.configurations.security.JwtUtils;
import ecommerce.backend.dto.LoginRequest;
import ecommerce.backend.dto.MessageResponse;
import ecommerce.backend.dto.ModifyUserRequest;
import ecommerce.backend.dto.SignupRequest;
import ecommerce.backend.exceptions.CustomerAlreadyExistsException;
import ecommerce.backend.exceptions.CustomerNotExistsException;
import ecommerce.backend.services.CustomerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class CustomerController {


    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    JwtUtils jwtUtils;

    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
  }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    
        try{
            customerService.customerRegistration(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }catch(CustomerAlreadyExistsException e){
            return ResponseEntity.badRequest().body(new MessageResponse("user already exists"));
        }
    }

  @GetMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

  @PostMapping("/update")
  public ResponseEntity<?> modifyUser(Authentication auth, @RequestBody ModifyUserRequest request){

    UserDetails user=(UserDetails) auth.getPrincipal();
    try{
        customerService.modifyCustomer(request, user.getUsername());
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }catch(CustomerNotExistsException e){
         return new ResponseEntity<>(new MessageResponse("user not found"), HttpStatus.NOT_FOUND);
    }
  }
}
