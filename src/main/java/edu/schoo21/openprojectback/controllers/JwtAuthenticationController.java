package edu.schoo21.openprojectback.controllers;

import java.io.IOException;
import java.util.Objects;

import edu.schoo21.openprojectback.config.JwtTokenUtil;
import edu.schoo21.openprojectback.models.JwtRequest;
import edu.schoo21.openprojectback.models.JwtResponse;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.UserDto;
import edu.schoo21.openprojectback.services.AvatarService;
import edu.schoo21.openprojectback.services.UsersService;
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

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UsersService usersService;
	private final AvatarService avatarService;
	private final PasswordEncoder passwordEncoder;

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
}
