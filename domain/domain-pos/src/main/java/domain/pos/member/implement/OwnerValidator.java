package domain.pos.member.implement;

import org.springframework.stereotype.Component;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import domain.pos.member.entity.Owner;
import domain.pos.member.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OwnerValidator {
	private final OwnerRepository ownerRepository;

	public void validateOwner(Owner owner) {
		ownerRepository.findById(owner.getOwnerId())
			.orElseThrow(() -> new ServiceException(ErrorCode.NOT_VALID_OWNER));
	}
}
