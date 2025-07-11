package com.example.salledesport.Controller;

import com.example.salledesport.JWT.JwtUtils;
import com.example.salledesport.Request.LoginRequest;
import com.example.salledesport.Request.SignupRequest;
import com.example.salledesport.Response.JwtResponse;
import com.example.salledesport.Response.MessageResponse;
import com.example.salledesport.model.ERole;
import com.example.salledesport.model.RefreshToken;
import com.example.salledesport.model.Role;
import com.example.salledesport.model.User;
import com.example.salledesport.repositories.RoleRepository;
import com.example.salledesport.repositories.UserRepository;
import com.example.salledesport.services.RefreshTokenService;
import com.example.salledesport.services.UserDetailsImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AuthPageController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenServices;


    @RequestMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
        // Just return an empty body or respond as appropriate
    }

    // Render signup page
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";  // This will render signup.html from the templates folder
    }

    // Handle signup form submission
    @PostMapping("/signup")
    public String registerUser(@Valid SignupRequest signupRequest, Model model) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            model.addAttribute("message", "Error: username is already taken!");
            return "signup";
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            model.addAttribute("message", "Error: Email is already in use!");
            return "signup";
        }

        User user = new User(signupRequest.getFirst_name(), signupRequest.getLast_name(),
                signupRequest.getUsername(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "coach":
                        Role coachRole = roleRepository.findByName(ERole.ROLE_COACH)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(coachRole);
                        break;
                    case "client":
                        Role clientRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(clientRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        model.addAttribute("message", "User registered successfully!");
        return "redirect:/signin";
    }

    // Render signin page
    @GetMapping("/signin")
    public String showSigninPage() {
        return "signin";  // This will render signin.html from the templates folder
    }

    // Handle signin form submission
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImp userDetailsImp = (UserDetailsImp)  authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetailsImp);

        List<String> roles = userDetailsImp.getAuthorities()
                .stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenServices.createRefreshToken(userDetailsImp.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),
                userDetailsImp.getId(),

                userDetailsImp.getUsername(),
                userDetailsImp.getEmail(),
                roles));

    }

}



