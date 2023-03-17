package heedoitdox.deliverysystem.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticationFailedException extends RuntimeException {}

