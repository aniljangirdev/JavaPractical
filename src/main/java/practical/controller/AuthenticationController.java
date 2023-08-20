package practical.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import practical.exception.UserAlreadyExistsException;
import practical.models.user.User;
import practical.services.AuthenticationService;

/**
 * <h1>AuthenticationController</h1>
 * <p>
 * This class is responsible for the authentication of the user
 * It handles the registration and the login of the user.
 * </p>
 * <p>
 * check the user registration and login,.
 * </p>
 *
 * @see practical.services.AuthenticationService how it's done.
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    //====================================================================================================
    //============================== This is the register part of the code ================================
    //====================================================================================================

    /**
     * This method is responsible for the registration of the user
     *
     * @param user the user that is going to be registered
     * @return the login page
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = authenticationService.register(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }
    }

    @GetMapping("/hello")
    public String newEmployee() {
        return "Welcome : ";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationService.authenticate(user);
            return ResponseEntity.ok("login success!");
        } catch (Exception e) {
            throw new UsernameNotFoundException("user : " + user.getEmail() + "not Found!");
        }
    }

    /**
     * handles the exception
     *
     * @param e the exception that is going to be handled
     * @return the login page
     */
    @ExceptionHandler({Exception.class})
    public String handleException(Exception e) {
        return "redirect:/auth/login?error=Some Error Happened";
    }

    /**
     * handle specific exception for user already exists
     *
     * @param e the exception that is going to be handled
     * @return the register page
     */
    @ExceptionHandler({UserAlreadyExistsException.class})
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return "redirect:/auth/register?error=User already exists";
    }
}
