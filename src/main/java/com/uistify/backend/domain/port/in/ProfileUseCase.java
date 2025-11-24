package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.model.User;

public interface ProfileUseCase {

	User getProfile(String email);
}
