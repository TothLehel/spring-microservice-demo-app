package com.bankingdemo.accounts.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * This class helps Spring for resolving the createdBy and modifiedBy attributes
 * on entities
 */
@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// this should get the microservice's logged in users
		// TODO dummy value until that is finished
		return Optional.of("ACCOUNTS_MS");
	}

}
