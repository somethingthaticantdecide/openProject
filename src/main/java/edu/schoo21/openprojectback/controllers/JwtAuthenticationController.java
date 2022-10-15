package edu.schoo21.openprojectback.controllers;

import java.io.IOException;
import java.util.Objects;

import com.sun.mail.smtp.SMTPAddressFailedException;
import edu.schoo21.openprojectback.config.JwtTokenUtil;
import edu.schoo21.openprojectback.models.ConfirmationToken;
import edu.schoo21.openprojectback.models.JwtRequest;
import edu.schoo21.openprojectback.models.JwtResponse;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.UserDto;
import edu.schoo21.openprojectback.models.requests.NewPasswordRequest;
import edu.schoo21.openprojectback.models.requests.RestoreRequest;
import edu.schoo21.openprojectback.repository.ConfirmationTokenRepository;
import edu.schoo21.openprojectback.services.AvatarService;
import edu.schoo21.openprojectback.services.UsersService;
import edu.schoo21.openprojectback.services.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

	private final ConfirmationTokenRepository confirmationTokenRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UsersService usersService;
	private final PasswordEncoder passwordEncoder;
	private final VerificationService verificationService;

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		User user = usersService.findByLogin(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		return ResponseEntity.ok(new JwtResponse(token, user));
	}

	@PostMapping(value = "/signUp")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) {
		if (usersService.findUserByLogin(userDto.getLogin()) != null)
			return ResponseEntity.badRequest().body("User with this login already exist!");
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = usersService.addNewUser(userDto);
		final String token = jwtTokenUtil.generateToken(user);
		return ResponseEntity.ok(new JwtResponse(token, user));
	}

	@PostMapping(value = "/restore")
	public ResponseEntity<?> restorePassword(@RequestBody RestoreRequest restoreRequest) {
		User user = usersService.findByMail(restoreRequest.getMail());
		if (user == null) {
			return ResponseEntity.badRequest().body("Cannot find user with this email!");
		}
		try {
			verificationService.sendVerificationEmail(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Something went wrong!");
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/new_password")
	public ResponseEntity<?> changePassword(@RequestBody NewPasswordRequest newPasswordRequest) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(newPasswordRequest.getToken());
		if (token != null) {
			User user = token.getUser();
			user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
			usersService.save(user);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
